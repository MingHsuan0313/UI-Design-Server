package com.selab.uidesignserver.graph.construct.elasticity.visitor;


import com.selab.uidesignserver.config.Config;
import com.selab.uidesignserver.graph.*;
import com.selab.uidesignserver.model.matchMaking.LoadModel;
//import org.apache.log4j.Logger;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LDAElasticityVisitor extends ElasticityVisitor{
	
	private final static Logger logger = Logger.getLogger(LDAElasticityVisitor.class);
	
	private Map<String, Double> elasticityMap;

	private double dataTypeEla;
	private double connectEla;
	
	/**
	 * Usage: <br>
	 * new LDAElasticityVisitor().setRoot(root).visit(root);
	 */
	public LDAElasticityVisitor(){
		this.elasticityMap = new HashMap<>();
		this.dataTypeEla = Config.ELASTICITY_DATATYPE;
		this.connectEla = Config.ELASTICITY_CONNECTOR;
	}
	
	@Override
	public LDAElasticityVisitor initRootNode(ServiceNode serviceNode) {
		calculate_all_keyword_elasticity_in_graph(serviceNode);
		return this;
	}

	@Override
	public void terminate() {
		LoadModel.terminate();
	}
	
	/**
	 * Step1. Get all sub graph keywords
	 * Setp2. Create word-topic probability matrix
	 * Setp3. normalize word-topic probability matrix
	 * Step4. Calculate keyword similarity and save to elasticityMap
	 */
	@SuppressWarnings("unchecked")
	private void calculate_all_keyword_elasticity_in_graph(ServiceNode serviceNode){
		// Step1. Get all sub graph keywords
		List<String> keywordList = serviceNode.get_subgraph_keywords();
		HashMap<String, ArrayList<Double>> word_vector_map = new HashMap<>();
		
		// Setp2. Create word-topic probability matrix
		for (String keyword : keywordList){
			ArrayList<Double> vector = LoadModel.getInstance().word_topics_vector(keyword);
			word_vector_map.put(keyword, vector);
		}

		// Setp3.1 sum all vector
		ArrayList<Double> vector_sum = new ArrayList<Double>();
		for (ArrayList<Double> vector : word_vector_map.values()){
			if (vector_sum.size() == 0) {
				vector_sum = (ArrayList<Double>) vector.clone();
			} else {
				for (int i = 0; i < vector.size(); i++) {
					Double sum_value = vector_sum.get(i) + vector.get(i);
					vector_sum.set(i, sum_value);
				}
			}
		}
		
		// Setp3.2 normalize word-topic probability matrix
		for (ArrayList<Double> vector : word_vector_map.values()){
			for (int i = 0; i < vector.size(); i++) {
				Double normalized_value = vector.get(i) / vector_sum.get(i);
				vector.set(i, normalized_value);
			}
		}
		
		// Step4. Calculate keyword similarity and save to elasticityMap
		int keyword_num = word_vector_map.keySet().size();
		Double threshold = 1.0 / keyword_num; // mean
		if (Config.elasticityMode_LDA_threshFactor != null) {
			threshold *= Config.elasticityMode_LDA_threshFactor;
		}
		for (String keyword : word_vector_map.keySet()) {
			ArrayList<Double> vector = word_vector_map.get(keyword);
			Double elasticity = 0.0;
			for (Double value : vector) {
				if (value > threshold) {elasticity += 1.0;}
			}
			elasticityMap.put(keyword, elasticity);
		}
		
		// (Test)
		logger.debug("---------- (Start Info) " + this.getClass().getName() + " ----------");
		logger.debug(String.format("Service File Name: %s", serviceNode.getFilename()));
		for (String keyword : elasticityMap.keySet()) {
			logger.debug(String.format("Keyword: %s\tElasticity: %f", keyword, elasticityMap.get(keyword)));
		}
		logger.debug("---------- (End Info) " +  this.getClass().getName() + " ----------");
	}

	@Override
	public void visit(GraphNode node) {
		if (node instanceof ConnectorNode) {
			node.set_node_elasticity(this.connectEla);
		} else if (node instanceof DataTypeNode) {
			node.set_node_elasticity(this.dataTypeEla);
		} else if (node instanceof KeywordNode) {
			KeywordNode keywordNode = (KeywordNode) node;
			double node_elasticity = 0.0;
			for (String keyword : keywordNode.getKeywordList()) {
				double keyword_elasticity = this.elasticityMap.get(keyword);
				keywordNode.setElasticity(keyword, keyword_elasticity);
				node_elasticity += keyword_elasticity;
			}
			keywordNode.set_node_elasticity(node_elasticity);
		}
	}
	
}

package com.selab.uidesignserver.graph.construct.elasticity.visitor;

import com.selab.uidesignserver.config.Config;
import com.selab.uidesignserver.graph.*;
import com.selab.uidesignserver.model.matchMaking.OWAImportance;
import com.selab.uidesignserver.util.OWA;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OWAElasticityVisitor extends ElasticityVisitor {

	private final String quantifier = "Most"; // "At least one", "Few", "Some", "Half", "Many", "Most", "All"
	
	private OWAImportance imptDet;
	
	private Map<String, Double> elasticityMap;
	

	private double dataTypeEla;
	private double connectEla;
	
	/**
	 * visitor = new LDAElasticityVisitor(); <br>
	 * visitor.initRootNode(serviceNode); <br>
	 * serviceNode.visit(visitor) <br>
	 */
	public OWAElasticityVisitor(){
		this.imptDet = OWAImportance.getInstance();
		
		this.dataTypeEla = Config.ELASTICITY_DATATYPE;
		this.connectEla = Config.ELASTICITY_CONNECTOR;
	}
	
	@Override
	public OWAElasticityVisitor initRootNode(ServiceNode serviceNode) {
		this.elasticityMap = new HashMap<>();
		calculate_all_keyword_elasticity_in_graph(serviceNode);
		return this;
	}
	
	/**
	 * Step1. Get all sub graph keywords
	 * Setp2. Create word-topic probability matrix
	 * Setp3. normalize word-topic probability matrix
	 * Step4. Calculate keyword similarity and save to elasticityMap
	 */
	private void calculate_all_keyword_elasticity_in_graph(ServiceNode serviceNode){
		// Step1. Get all sub graph keywords
		List<String> keywordList = serviceNode.get_subgraph_keywords();
		
		Map<String, Double> docTopicImportances = this.imptDet.getDocTopicImportances(keywordList);
		Map<String, Double> keyword_raw_values = new HashMap<>();
		
		double min = 1000000;
		for (int i = 0; i < keywordList.size(); i++) {
			String kw = keywordList.get(i);
			Map<String, Double> termTopicImportances = this.imptDet.getTermTopicImportances(kw);
			double raw_value = OWA.calculateScore(termTopicImportances, docTopicImportances, quantifier);

			keyword_raw_values.put(kw, raw_value);
			
			if (raw_value < min && raw_value > 0) {
				min = raw_value;
			}
		}
		
		double scale = 1.0 / min;
		Iterator<String> iter = keyword_raw_values.keySet().iterator();
		
		while (iter.hasNext()) {
			String kw = iter.next();
			double elas = keyword_raw_values.get(kw) * scale;
			elasticityMap.put(kw, elas);
		}
	}

	@Override
	public void visit(GraphNode node) {
		if (node instanceof ConnectorNode) {
			node.set_node_elasticity(this.connectEla);
		} else if (node instanceof DataTypeNode) {
			node.set_node_elasticity(this.dataTypeEla);
		} else if (node instanceof KeywordNode) {
			KeywordNode keywordNode = (KeywordNode)node;
			double node_elasticity = 0.0;
			for (String keyword : keywordNode.getKeywordList()) {
				double keyword_elasticity = elasticityMap.get(keyword);
				keywordNode.setElasticity(keyword, keyword_elasticity);
				node_elasticity += keyword_elasticity;
			}
			keywordNode.set_node_elasticity(node_elasticity);
		}
	}

	@Override
	public void terminate() {
		OWAImportance.terminate();
	}

}

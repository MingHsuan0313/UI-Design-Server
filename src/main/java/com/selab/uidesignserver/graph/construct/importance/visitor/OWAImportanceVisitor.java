package com.selab.uidesignserver.graph.construct.importance.visitor;

import com.selab.uidesignserver.config.Config;
import com.selab.uidesignserver.graph.GraphNode;
import com.selab.uidesignserver.graph.KeywordNode;
import com.selab.uidesignserver.graph.ServiceNode;
import com.selab.uidesignserver.model.matchMaking.OWAImportance;
import com.selab.uidesignserver.util.OWA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OWAImportanceVisitor extends ImportanceVisitor {

	private Map<String, Double> keywordImportanceMap;
	
	public OWAImportanceVisitor() {
		
	}
	
	@Override
	public ImportanceVisitor initRootNode(ServiceNode serviceNode) {
		return initRootNode(serviceNode, Config.importanceMode_OWA_linguesticQuantifier);
	}
	
	public ImportanceVisitor initRootNode(ServiceNode serviceNode, String lingusticQuantifier) {
		processKeywordsImportance(serviceNode, lingusticQuantifier);
		return this;
	}

	private void processKeywordsImportance(ServiceNode serviceNode, String lingusticQuantifier){
		this.keywordImportanceMap = new HashMap<>();
		
		// Step1. Get all sub graph keywords
		List<String> all_wsdl_keywords = serviceNode.get_subgraph_keywords();
		
		// Setp2. Calculate all keyword importance
		Map<String, Double> docTopicImportance = OWAImportance.getInstance().getDocTopicImportances(all_wsdl_keywords);
		for (String keyword : all_wsdl_keywords) {
			Map<String, Double> termTopicImportance  = OWAImportance.getInstance().getTermTopicImportances(keyword);
			Double importance = OWA.calculateScore(termTopicImportance, docTopicImportance, lingusticQuantifier);
			this.keywordImportanceMap.put(keyword, importance);
		}
	}
	
	@Override
	public void visit(GraphNode node) {
		if (node instanceof KeywordNode) {
			KeywordNode keywordNode = (KeywordNode)node;
			for (String keyword : keywordNode.getKeywordList()) {
				Double keywordImportance = this.keywordImportanceMap.get(keyword);
				keywordNode.setImportance(keyword, keywordImportance);
			}
		}
	}

	@Override
	public void terminate() {
		OWAImportance.terminate();
	}

}

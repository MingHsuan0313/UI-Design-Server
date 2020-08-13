package com.selab.uidesignserver.graph;

import com.selab.uidesignserver.graph.visitor.GraphVisitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeywordNode extends GraphNode{
	
	private List<String> keywordList;
	private Map<String, Double> elasticityMap;
	private Map<String, Double> importanceMap;
	private Map<String, Double> frequencyMap;

	public KeywordNode() {
		this.keywordList = new ArrayList<String>();
		this.elasticityMap = new HashMap<String, Double>();
		this.importanceMap = new HashMap<String, Double>();
		this.frequencyMap = new HashMap<String, Double>();
	}
	
	// keywords
	public void addKeywords(String keyword) { this.keywordList.add(keyword); }
	public void setKeywords(List<String> keywords){ this.keywordList = keywords; }
	public List<String> getKeywordList() { return this.keywordList; }

	// elasticity
	public void setElasticity(String keyword, Double elasticity) { this.elasticityMap.put(keyword, elasticity); }
	public Double getElasticity(String keyword) { return this.elasticityMap.get(keyword); }
	public Map<String, Double> getElasticitiesMap() { return this.elasticityMap; }

	// importance
	public void setImportance(String keyword, Double importance) { this.importanceMap.put(keyword, importance); }
	public Double getImportance(String keyword) { return this.importanceMap.get(keyword); }
	public Map<String, Double> getImportanceMap() { return this.importanceMap; }
	
	// frequency
	public void setFrequency(String keyword, Double frequency) { this.frequencyMap.put(keyword, frequency); }
	public Double getFrequency(String keyword) { return this.frequencyMap.get(keyword); }
	public Map<String, Double> getFrequencyMap() { return this.frequencyMap; }
	
	// visit
	@Override
	public void visit(GraphVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String getNodeInfo() {
		StringBuilder nodeInfo = new StringBuilder(String.format("%s%n", super.getNodeInfo()));
		nodeInfo.append(String.format("keywordList = %s%n", keywordList));
		nodeInfo.append(String.format("elasticityMap = %s%n", elasticityMap));
		nodeInfo.append(String.format("importanceMap = %s%n", importanceMap));
		nodeInfo.append(String.format("frequencyMap = %s%n", frequencyMap));
		return nodeInfo.toString().trim();
	}
	
}

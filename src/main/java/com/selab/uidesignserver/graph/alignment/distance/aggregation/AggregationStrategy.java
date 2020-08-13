package com.selab.uidesignserver.graph.alignment.distance.aggregation;

import com.selab.uidesignserver.graph.KeywordNode;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

public abstract class AggregationStrategy {
	
	protected double threshold;

	protected List<String> resultReqSignificantList;
	protected List<String> resultReqNonSignificantList;
	protected List<String> resultCanSignificantList;
	protected List<String> resultCanNonSignificantList;

	public List<String> getResultReqSignificantList() { return this.resultReqSignificantList; }
	public List<String> getResultReqNonSignificantList() { return this.resultReqNonSignificantList; }
	public List<String> getResultCanSignificantList() { return this.resultCanSignificantList; }
	public List<String> getResultCanNonSignificantList() { return this.resultCanNonSignificantList; }
	
	public AggregationStrategy() { this.threshold = -1; }
	public AggregationStrategy(double threshold) { this.threshold = threshold; }
	
	public abstract void setUpSignificant(KeywordNode reqKeywordNode, KeywordNode canKeywordNode, int[] hungarianResult);
	
	protected void initResultList() {
		this.resultReqSignificantList = new ArrayList<>();
		this.resultReqNonSignificantList = new ArrayList<>();
		this.resultCanSignificantList = new ArrayList<>();
		this.resultCanNonSignificantList = new ArrayList<>();
	}
	
	protected List<Entry<Integer, Double>> rankKeywordByImportance(KeywordNode keywordNode) {
		// make wordIndex-importance pair list
		List<Entry<Integer, Double>> wordImportancePairs = new ArrayList<>();
		for (int idx = 0; idx < keywordNode.getKeywordList().size(); idx++) {
			String word = keywordNode.getKeywordList().get(idx);
			Double importance = keywordNode.getImportance(word) == null ? 0.0 : keywordNode.getImportance(word);
			Entry<Integer, Double> pair = new SimpleEntry<>(idx, importance);
			wordImportancePairs.add(pair);
		}
		
		// sort by importance
		wordImportancePairs.sort(new Comparator<Entry<Integer, Double>>() {
			@Override
			public int compare(Entry<Integer, Double> e1, Entry<Integer, Double> e2) {
				return -e1.getValue().compareTo(e2.getValue());
			}
		});
		
		return wordImportancePairs;
	}
	
	public static void main(String[] args) {
		KeywordNode keywordNode1 = new KeywordNode();
		keywordNode1.addKeywords("A");
		keywordNode1.getImportanceMap().put("A", 0.0);
		keywordNode1.addKeywords("B");
		keywordNode1.getImportanceMap().put("B", 1.0);
		keywordNode1.addKeywords("C");
		keywordNode1.getImportanceMap().put("C", 0.5);
		keywordNode1.addKeywords("D");
		keywordNode1.getImportanceMap().put("D", 0.0);
		keywordNode1.addKeywords("E");
		keywordNode1.getImportanceMap().put("E", 0.5);
		
		KeywordNode keywordNode2 = new KeywordNode();
		keywordNode2.addKeywords("V");
		keywordNode2.getImportanceMap().put("V", 1.0);
		keywordNode2.addKeywords("W");
		keywordNode2.getImportanceMap().put("W", 0.4);
		keywordNode2.addKeywords("X");
		keywordNode2.getImportanceMap().put("X", 0.1);
		keywordNode2.addKeywords("Y");
		keywordNode2.getImportanceMap().put("Y", 0.3);
		keywordNode2.addKeywords("Z");
		keywordNode2.getImportanceMap().put("Z", 0.5);
		
		int[] hungarianResult = new int[]{0,1,2,-1,-1};
		AggregationStrategy aggregationStrategy = new HungarianAggregation(0.5);
		aggregationStrategy.setUpSignificant(keywordNode1,keywordNode2, hungarianResult);
		System.out.print("Request:\t" + aggregationStrategy.getResultReqSignificantList());
		System.out.println("\t" + aggregationStrategy.getResultReqNonSignificantList());
		System.out.print("Candidate:\t" + aggregationStrategy.getResultCanSignificantList());
		System.out.println("\t" + aggregationStrategy.getResultCanNonSignificantList());
	}
	
	
}

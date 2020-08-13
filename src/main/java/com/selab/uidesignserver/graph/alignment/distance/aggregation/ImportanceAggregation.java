package com.selab.uidesignserver.graph.alignment.distance.aggregation;

import com.selab.uidesignserver.graph.KeywordNode;

import java.util.List;
import java.util.Map.Entry;

public class ImportanceAggregation extends AggregationStrategy{
	
	public ImportanceAggregation() {
		super();
	}
	
	public ImportanceAggregation(double threshold) {
		super(threshold);
	}
	
	@Override
	public void setUpSignificant(KeywordNode reqKeywordNode, KeywordNode canKeywordNode, int[] hungarianResult) {
		// create empty list
		this.initResultList();
		
		// rank request word by word importance
		List<Entry<Integer, Double>> reqImportancePairs = rankKeywordByImportance(reqKeywordNode);
		List<Entry<Integer, Double>> canImportancePairs = rankKeywordByImportance(canKeywordNode);
		
		// set request significant part
		int topKRequestWord = this.threshold == -1 ? 1 : (int) Math.round(reqImportancePairs.size() * threshold);
		for (int k = 0; k < topKRequestWord; k++) {
			int reqIdx = reqImportancePairs.get(k).getKey();
			String reqWord = reqKeywordNode.getKeywordList().get(reqIdx);
			this.resultReqSignificantList.add(reqWord);
		}
		
		// set candidate significant part
		int topKCandidateWord = this.threshold == -1 ? 1 : (int) Math.round(canImportancePairs.size() * threshold);
		for (int k = 0; k < topKCandidateWord; k++) {
			int canIdx = canImportancePairs.get(k).getKey();
			String canWord = canKeywordNode.getKeywordList().get(canIdx);
			this.resultCanSignificantList.add(canWord);
		}
		
		// set non significant part
		this.resultReqNonSignificantList.addAll(reqKeywordNode.getKeywordList());
		this.resultReqNonSignificantList.removeAll(this.resultReqSignificantList);
		this.resultCanNonSignificantList.addAll(canKeywordNode.getKeywordList());
		this.resultCanNonSignificantList.removeAll(this.resultCanSignificantList);
	}
}

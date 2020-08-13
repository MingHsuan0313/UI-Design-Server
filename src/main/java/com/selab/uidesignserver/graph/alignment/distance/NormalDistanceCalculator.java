package com.selab.uidesignserver.graph.alignment.distance;

import com.selab.uidesignserver.graph.KeywordNode;
import com.selab.uidesignserver.util.HungarianBipartiteMatching;

import java.util.List;
import java.util.Map;

public class NormalDistanceCalculator extends DistanceCalculator{
	
	public NormalDistanceCalculator() {
		super();
	}
	
	@Override
	public double calculateKeywordNodeDistance(KeywordNode requstKeywordNode, KeywordNode candidateKeywordNode) {
		if (requstKeywordNode.getKeywordList().size() == 0) { return 0.0; }
		if (candidateKeywordNode.getKeywordList().size() == 0) { return requstKeywordNode.get_node_elasticity(); }

		List<String> requestWordList = requstKeywordNode.getKeywordList();
		List<String> candidateWordList = candidateKeywordNode.getKeywordList();
		Map<String, Double> elasticityMap = requstKeywordNode.getElasticitiesMap();
		
		double[][] keywordDistanceTable = buildKeywordDistanceTable(requstKeywordNode.getKeywordList(), candidateKeywordNode.getKeywordList());
		HungarianBipartiteMatching hbm = new HungarianBipartiteMatching(keywordDistanceTable);
		int[] hungarianResult = hbm.execute();
		
		boolean[] reqMatched = new boolean[requestWordList.size()];
		boolean[] canMatched = new boolean[candidateWordList.size()];

		// matched
		double nodeDistance = 0.0;
		for (int r = 0; r < requestWordList.size(); r++) {
			int matchCanIdx = hungarianResult[r];
			if (matchCanIdx != -1) {
				reqMatched[r] = true;
				canMatched[matchCanIdx] = true;
				String reqWord = requestWordList.get(r);
				Double reqElasticity = elasticityMap.get(reqWord);
				double wordDistance = keywordDistanceTable[r][matchCanIdx];
				nodeDistance += wordDistance * reqElasticity;
			}
		}

		// unmatched request
		for (int r = 0; r < requestWordList.size(); r++) {
			if (!reqMatched[r] && candidateWordList.size() != 0) {
				String reqWord = requestWordList.get(r);
				Double reqElasticity = elasticityMap.get(reqWord);
				
				double avgDistance = 0.0;
				for (int c = 0; c < candidateWordList.size(); c++) {
					String canWord = candidateWordList.get(c);
					avgDistance += getWordDistance(reqWord, canWord);
				}
				avgDistance /= (double) candidateWordList.size();
				nodeDistance += avgDistance * reqElasticity;
				
				/* i think it best
				double minDistance = reqElasticity;
				for (int c = 0; c < candidateWordList.size(); c++) {
					String canWord = candidateWordList.get(c);
					double distance = getWordDistance(reqWord, canWord);
					if (distance < minDistance) { minDistance = distance; }
				}
				nodeDistance += minDistance * reqElasticity;
				*/
			}
		}
		
		
		return nodeDistance;
	}
}

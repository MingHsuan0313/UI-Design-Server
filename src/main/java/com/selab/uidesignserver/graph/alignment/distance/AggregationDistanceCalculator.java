package com.selab.uidesignserver.graph.alignment.distance;

import com.selab.uidesignserver.config.Config;
import com.selab.uidesignserver.graph.KeywordNode;
import com.selab.uidesignserver.graph.alignment.distance.aggregation.AggregationStrategy;
import com.selab.uidesignserver.graph.alignment.distance.aggregation.HungarianAggregation;
import com.selab.uidesignserver.graph.alignment.distance.aggregation.ImportanceAggregation;
import com.selab.uidesignserver.model.matchMaking.CooccurrenceFrequency;
//import org.apache.log4j.Logger;
import org.apache.log4j.Logger;
import com.selab.uidesignserver.util.HungarianBipartiteMatching;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AggregationDistanceCalculator extends DistanceCalculator {
	
	private final static Logger logger = Logger.getLogger(AggregationDistanceCalculator.class);

	private AggregationStrategy aggregationstrategy;

	public AggregationDistanceCalculator() {
		super();
		
		if ("importance_1".equals(Config.aggregationMode)) {
			this.aggregationstrategy = new ImportanceAggregation();
		} else if ("importance_*".equals(Config.aggregationMode)) {
			this.aggregationstrategy = new ImportanceAggregation(Config.aggregationMode_thresh);
		} else if ("hungarian_1".equals(Config.aggregationMode)) {
			this.aggregationstrategy = new HungarianAggregation();
		} else if ("hungarian_*".equals(Config.aggregationMode)) {
			this.aggregationstrategy = new HungarianAggregation(Config.aggregationMode_thresh);
		} else {
			logger.fatal("Unknow aggregationMode: " + Config.aggregationMode);
			throw new IllegalStateException();
		}
	}

	@Override
	public double calculateKeywordNodeDistance(KeywordNode requstKeywordNode, KeywordNode candidateKeywordNode) {
		if (requstKeywordNode.getKeywordList().size() == 0) { return 0.0; }
		if (candidateKeywordNode.getKeywordList().size() == 0) { return requstKeywordNode.get_node_elasticity(); }
		
		// run hungarian
		double[][] keywordDistanceTable = buildKeywordDistanceTable(requstKeywordNode.getKeywordList(), candidateKeywordNode.getKeywordList());
		int[] hungarianResult = new HungarianBipartiteMatching(keywordDistanceTable).execute();
		
		// split to significant and non-significant
		this.aggregationstrategy.setUpSignificant(requstKeywordNode, candidateKeywordNode, hungarianResult);
		List<String> reqSignificantList = this.aggregationstrategy.getResultReqSignificantList();
		List<String> reqNonSignificantList = this.aggregationstrategy.getResultReqNonSignificantList();
		List<String> canSignificantList = this.aggregationstrategy.getResultCanSignificantList();
		List<String> canNonSignificantList = this.aggregationstrategy.getResultCanNonSignificantList();
		
		// significant part
		double[] basicReqWeight = new double[reqSignificantList.size()];
		Arrays.fill(basicReqWeight, 1.0);
		double significantDistance = calAggregationDistance(reqSignificantList, canSignificantList,
				requstKeywordNode.getElasticitiesMap(), basicReqWeight);

		// non significant part
		double[] reqWeight = getNonSignificantWeights(reqSignificantList, reqNonSignificantList);
		double nonSignificantDistance = calAggregationDistance(reqNonSignificantList, canNonSignificantList,
				requstKeywordNode.getElasticitiesMap(), reqWeight);
		
		return significantDistance + nonSignificantDistance;
	}

	private double calAggregationDistance(List<String> requestWordList, List<String> candidateWordList,
			Map<String, Double> elasticityMap, double[] reqWeight) {
		if (requestWordList.size() == 0) { return 0.0; }
		if (candidateWordList.size() == 0) { return requestWordList.stream().map(elasticityMap::get).reduce(Double::sum).get().doubleValue(); }

		// matching by hungarian
		double[][] keywordDistanceTable = buildKeywordDistanceTable(requestWordList, candidateWordList);
		int[] hungarianResult = new HungarianBipartiteMatching(keywordDistanceTable).execute();

		boolean[] reqMatched = new boolean[requestWordList.size()];
		boolean[] canMatched = new boolean[candidateWordList.size()];

		// matched
		double aggregationDistance = 0.0;
		for (int r = 0; r < requestWordList.size(); r++) {
			int matchCanIdx = hungarianResult[r];
			if (matchCanIdx != -1) {
				reqMatched[r] = true;
				canMatched[matchCanIdx] = true;
				String reqWord = requestWordList.get(r);
				double distance = keywordDistanceTable[r][matchCanIdx];
				Double reqElasticity = elasticityMap.get(reqWord);
				aggregationDistance += distance * reqElasticity * reqWeight[r];
			}
		}

		// unmatched request
		for (int r = 0; r < requestWordList.size(); r++) {
			if (!reqMatched[r]) {
				double avgDistance = 0.0;
				String reqWord = requestWordList.get(r);
				for (int c = 0; c < candidateWordList.size(); c++) {
					String canWord = candidateWordList.get(c);
					avgDistance += getWordDistance(reqWord, canWord);
				}
				if (avgDistance != 0.0) {
					avgDistance /= (double) candidateWordList.size();
					Double reqElasticity = elasticityMap.get(reqWord);
					aggregationDistance += avgDistance * reqElasticity * reqWeight[r] ;
				}
			}
		}
		
		// unmatched candidate
		double avgAvgDist = 0.0;
		for (int c = 0; c < candidateWordList.size(); c++) {
			if (!canMatched[c]) {
				double avgDist = 0.0;
				for (int r = 0; r < requestWordList.size(); r++) {
					String reqWord = requestWordList.get(r);
					String canWord = candidateWordList.get(c);
					avgDist += getWordDistance(reqWord, canWord);
				}
				if (avgDist != 0) {
					avgDist /= (double) requestWordList.size();
					avgAvgDist += avgDist;
				}
			}
		}
		if (avgAvgDist != 0.0) {
			avgAvgDist /= (double) (candidateWordList.size() - requestWordList.size());
	
			// unmatched candidate, average request elasticity
			double averageKeywordEla = 0.0;
			for (int r = 0; r < requestWordList.size(); r++) {
				String reqWord = requestWordList.get(r);
				Double reqElasticity = elasticityMap.get(reqWord);
				averageKeywordEla += reqElasticity.doubleValue();
			}
			if (averageKeywordEla != 0.0) {
				averageKeywordEla /= (double) requestWordList.size();
				aggregationDistance += avgAvgDist * averageKeywordEla;
			}
		}
		
		return aggregationDistance;
	}

	private double[] getNonSignificantWeights(List<String> reqSignificantList, List<String> reqNonSignificantList) {
		double[] pmi_weight = new double[reqNonSignificantList.size()];
		for (int nonSigIdx = 0; nonSigIdx < reqNonSignificantList.size(); nonSigIdx++) {
			Integer leaveWordIndex = CooccurrenceFrequency.termIndex.get(reqNonSignificantList.get(nonSigIdx));
			for (int sigIdx = 0; sigIdx < reqSignificantList.size(); sigIdx++) {
				Integer significantWordIndex = CooccurrenceFrequency.termIndex.get(reqSignificantList.get(sigIdx));
				if (leaveWordIndex != null && significantWordIndex != null) {
					pmi_weight[nonSigIdx] += (double) CooccurrenceFrequency.cooccurrenceMatrix[significantWordIndex][leaveWordIndex]
							/ (double) reqSignificantList.size();
				}
			}
		}
		return pmi_weight;
	}

}

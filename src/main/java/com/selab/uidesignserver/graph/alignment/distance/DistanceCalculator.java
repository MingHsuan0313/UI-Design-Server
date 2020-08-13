package com.selab.uidesignserver.graph.alignment.distance;

import com.selab.uidesignserver.config.Config;
import com.selab.uidesignserver.graph.KeywordNode;
import com.selab.uidesignserver.model.matchMaking.similarity.VectorCombinationSimilarity;
import com.selab.uidesignserver.model.matchMaking.similarity.Word2VecSimilarity;
import com.selab.uidesignserver.model.matchMaking.similarity.WordSimilarity;

import java.util.List;

public abstract class DistanceCalculator {
	
	private WordSimilarity wordSimilarity;
	
	public DistanceCalculator() {
		if ("word2vec".equals(Config.wordSimilarityMode)) {
			this.wordSimilarity = Word2VecSimilarity.getInstance();
		} else if ("vector_combination".equals(Config.wordSimilarityMode)) {
			this.wordSimilarity = VectorCombinationSimilarity.getInstance();
		} else {
			throw new IllegalArgumentException("Unknow similarityCalculatorCore: " + Config.wordSimilarityMode);
		}
	}
	
	public abstract double calculateKeywordNodeDistance(KeywordNode requstKeywordNode, KeywordNode candidateKeywordNode);

	protected double[][] buildKeywordDistanceTable(List<String> requstKeywords, List<String> candidateKeywors) {
		double[][] keywordDistanceTable = new double[requstKeywords.size()][candidateKeywors.size()];
		for (int i = 0; i < requstKeywords.size(); i++) {
			for (int j = 0; j < candidateKeywors.size(); j++) {
				// get word pair
				String reqestWord = requstKeywords.get(i);
				String candidateWord = candidateKeywors.get(j);
				
				// calculate word pair distance
				keywordDistanceTable[i][j] = getWordDistance(reqestWord, candidateWord);
			}
		}
		return keywordDistanceTable;
	}
	
	protected double getWordDistance(String reqWord, String canWord) {
		double sim = this.wordSimilarity.getWordSimilarity(reqWord, canWord);
		return (1 - sim);
	}
}

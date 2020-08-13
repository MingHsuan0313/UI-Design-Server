package com.selab.uidesignserver.model.matchMaking.similarity;

import com.selab.uidesignserver.config.Config;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class VectorCombinationSimilarity  extends WordSimilarity {
	
	private final static Logger logger = Logger.getLogger(VectorCombinationSimilarity.class);
	
	private static VectorCombinationSimilarity instance;
	
	private Map<String, Double[]> wordVectors;
	
	public static synchronized VectorCombinationSimilarity getInstance() {
		if (instance == null) { instance = new VectorCombinationSimilarity(); }
		return instance;
	}
	
	public static synchronized void terminate() {
		if (instance != null) {
			instance = null;
			logger.info("VectorCombinationSimilarity terminated");
		}
	}
	
	public VectorCombinationSimilarity() {
		this.wordVectors = new HashMap<String, Double[]>();
		this.updateVector(Config.wordSimilarityMode_vector_combination_modelPath);
	}
	
	public void updateVector(String fname) {
		logger.info("Updatting vectors of VectorCombinationSimilarity");
		logger.info("Loading vector file from: " + fname);
		try {
			Files.lines(Paths.get(fname)).forEach(line -> {
				String[] part = line.trim().split("\\s+");
				String word = part[0];
				Double[] vector = new Double[part.length-1];
				for (int i = 1; i < part.length; i++) {
					vector[i-1] = new Double(part[i]);
				}
				this.wordVectors.put(word, vector);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public double getWordSimilarity(String word1, String word2) {
		Double[] vec1 = getWordVector(word1);
		Double[] vec2 = getWordVector(word2);
		double similarity = 0.0, norm1 = 0.0, norm2 = 0.0;
		for (int i = 0; i < vec1.length; i++) {
			similarity += vec1[i] * vec2[i];
			norm1 += Math.pow(vec1[i], 2);
			norm2 += Math.pow(vec2[i], 2);
		}
		double divide = Math.sqrt(norm1) * Math.sqrt(norm2);
		similarity = divide == 0.0 ? 0.0 : similarity / divide;
		return similarity;
	}
	
	public Double[] getWordVector(String word) {
		if (!this.wordVectors.containsKey(word)) {
			int vectorLength = this.wordVectors.values().iterator().next().length;
			Double[] zeros = new Double[vectorLength];
			for (int i = 0; i < zeros.length; i++) {
				zeros[i] = 0.0;
			}
			return zeros;
		}
		return this.wordVectors.get(word);
	}
	
	public static void main(String[] args) {
		//BasicConfigurator.configure();
		BasicConfigurator.configure();

		VectorCombinationSimilarity wordSimilarity = VectorCombinationSimilarity.getInstance();
		System.out.println(wordSimilarity.getWordSimilarity("dog", "cat"));
		System.out.println(wordSimilarity.getWordSimilarity("dog", "caaat"));
	}
	
}

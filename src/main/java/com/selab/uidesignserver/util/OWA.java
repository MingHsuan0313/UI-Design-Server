package com.selab.uidesignserver.util;

import java.util.*;
import java.util.Map.Entry;

public class OWA {
	
	private static Map<String, Double> linguisticQuantifiers;
	
	static {
		linguisticQuantifiers = new HashMap<>();
		linguisticQuantifiers.put("At least one", 0.0001);
		linguisticQuantifiers.put("Few", 0.1);
		linguisticQuantifiers.put("Some", 0.5);
		linguisticQuantifiers.put("Half", 1.0);
		linguisticQuantifiers.put("Many", 2.0);
		linguisticQuantifiers.put("Most", 10.0);
		linguisticQuantifiers.put("All", 1000.0);
	}
	
	private OWA() {
		// static class
	}
	
	public static double calculateScore(Map<String, Double> scoreMap, Map<String, Double> importanceMap, String linguisticQuantifier) {
		Double quantifier = linguisticQuantifiers.get(linguisticQuantifier);
		if (quantifier == null) { throw new IllegalArgumentException(String.format("Unknow Linguistic Quantifier: %s", linguisticQuantifier)); }
		return calculateScore(scoreMap, importanceMap, quantifier);
	}
	
	public static double calculateScore(Map<String, Double> scoreMap, Map<String, Double> importanceMap, Double quantifier) {
		// triple list <score, importance, ordered_weight>
		List<Double[]> tripleList = new ArrayList<>();
		for (Entry<String, Double> scoreEntry : scoreMap.entrySet()) {
			Double score = scoreEntry.getValue();
			Double importance = importanceMap.get(scoreEntry.getKey());
			Double orderedWeight = score; // initial
			Double[] triple = new Double[]{score, importance, orderedWeight};
			tripleList.add(triple);
		}
		
		// sort score by decent order
		tripleList.sort(new Comparator<Double[]>() {
			@Override
			public int compare(Double[] o1, Double[] o2) {
				return -o1[0].compareTo(o2[0]);
			}
		});

		// calculate order weight by quantifier
		double importanceSum = 0.0;
		for (Double[] triple : tripleList) {
			Double importance = triple[1];
			importanceSum += importance;
		}
		double cumulativeImportance = 0.0;
		for (Double[] triple : tripleList) {
			Double importance = triple[1];
			Double orderWeight = orderWeight((cumulativeImportance + importance) / importanceSum, cumulativeImportance / importanceSum, quantifier);
			triple[2] = orderWeight;
			cumulativeImportance += importance;
		}
		
		// ordered weighted averaging
		double weightedScore = 0.0;
		for (Double[] triple : tripleList) {
			weightedScore += triple[0] * triple[2];
		}
		
		return weightedScore;
	}
	
	private static double orderWeight(double current, double previous, double quantifier) {
		return Q(current, quantifier) - Q(previous, quantifier) ;
	}
	
	private static double Q(double value, double quantifier) {
		return Math.pow(value, quantifier);
	}
	
	public static void main(String[] argv) {
		Map<String, Double> scoreMap = new HashMap<>();
		scoreMap.put("4", 1.0);
		scoreMap.put("3", 0.7);
		scoreMap.put("1", 0.6);
		scoreMap.put("2", 0.5);
		Map<String, Double> importanceMap = new HashMap<>();
		importanceMap.put("4", 0.6);
		importanceMap.put("3", 1.0);
		importanceMap.put("1", 0.9);
		importanceMap.put("2", 0.5);
		System.out.println(OWA.calculateScore(scoreMap, importanceMap, "Many"));
	}
}

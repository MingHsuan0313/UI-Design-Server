package com.selab.uidesignserver.model.matchMaking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public class PMI {
	private HashMap<String,Double> wordPair_pmiValue_map;
	
	/**
	 * PMI API
	 * @param pmi_value_file_path pre-saved pmi value file path
	 */
	public PMI(String pmi_value_file_path) {
		try {
			loadModel(pmi_value_file_path);
		} catch (IOException e) {
			this.wordPair_pmiValue_map = null;
			e.printStackTrace();
			throw new IllegalStateException("Initial PMI fail.");
		}
	}
	
	private void loadModel(String pmi_value_file_path) throws IOException {
		this.wordPair_pmiValue_map = new HashMap<String,Double>();
		BufferedReader br = new BufferedReader(new FileReader(pmi_value_file_path));
		for (String line = br.readLine(); line != null; line = br.readLine()) {
	         String[] tokens = line.split("\\s+");
	         String word1 = tokens[0], word2 = tokens[1];
	         String wordPair = creatWordPair(word1, word2);
	         Double pmi_value = Double.parseDouble(tokens[2]);
	         wordPair_pmiValue_map.put(wordPair, pmi_value);
		}
		br.close();
	}
	
	private String creatWordPair(String word1, String word2) {
		String seperator = ",";
		String wordPair = String.format("%s" + seperator + "%s", word1, word2);
		return wordPair;
	}
	
	///////////////////
	//    PMI API    //
	///////////////////
	
	/**
	 * if Pair(word1, word2) or Pair(word2, word1) in map than return pmi value
	 * else return 0.
	 * @param word1
	 * @param word2
	 * @return pmi value
	 */
	public double get_pmi_value(String word1, String word2) {
		// first check
		String wordPair = creatWordPair(word1, word2);
		Double pmi_value = wordPair_pmiValue_map.get(wordPair);
		
		// check inverse pair
		if (pmi_value == null) {
			wordPair = creatWordPair(word2, word1);
			pmi_value = wordPair_pmiValue_map.get(wordPair);
		}
		
		// defalt value
		if (pmi_value == null) {
			pmi_value = 0.0;
		}
		
		return pmi_value.doubleValue();
	}
	
	
	
	public static void main(String[] args) {
		PMI lsa = new PMI("pmi_value");
		System.out.println(lsa.get_pmi_value("car", "vehicle"));
		System.out.println(lsa.get_pmi_value("vehicle", "car"));
		System.out.println(lsa.get_pmi_value("vehicle", "air"));
		System.out.println(lsa.get_pmi_value("air", "vehicle"));
		System.out.println(lsa.get_pmi_value("car", "vehicle"));
		System.out.println(lsa.get_pmi_value("amount", "money"));
		System.out.println(lsa.get_pmi_value("amount", "currency"));
		System.out.println(lsa.get_pmi_value("author", "government"));
		System.out.println(lsa.get_pmi_value("government","author" ));
	}
}

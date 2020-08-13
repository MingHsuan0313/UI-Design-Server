package com.selab.uidesignserver.model.matchMaking;

import com.selab.uidesignserver.config.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class CooccurrenceFrequency {
	
	public static double[][] cooccurrenceMatrix;
	
	public static HashMap<String, Integer> termIndex;
	
	static{
		termIndex = new HashMap<>();
		Scanner matrixInput = null, termIndexInput = null;
		try {
			if (Config.usePhraser) {
			matrixInput = new Scanner(new FileInputStream(Config.cooccurrence_frequency_comp_path + File.separator
							+ File.separator + "word_cooccurrence"));
			termIndexInput = new Scanner(new FileInputStream(Config.cooccurrence_frequency_comp_path + File.separator
					+ File.separator + "term_index"));
			}
			else{
				matrixInput = new Scanner(new FileInputStream(Config.cooccurrence_frequency_no_comp_path + File.separator
						+ File.separator + "word_cooccurrence"));
				termIndexInput = new Scanner(new FileInputStream(Config.cooccurrence_frequency_no_comp_path + File.separator
				+ File.separator + "term_index"));				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int term = 0;
		while (termIndexInput.hasNextLine()) {
			termIndex.put(termIndexInput.nextLine().trim(), term++);
		}
		cooccurrenceMatrix = new double[term][term];
		int index = 0;
		while(matrixInput.hasNextLine()){
			cooccurrenceMatrix[index++] = castArray(matrixInput.nextLine().split("\\s"));
		}
	}

	private static double[] castArray(String[] stringArray) {
		double[] intArray = new double[stringArray.length];
		for (int i = 0; i < stringArray.length; i++) {
			intArray[i] = Double.parseDouble(stringArray[i]);
		}
		return intArray;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for(int i = 0; i < cooccurrenceMatrix[0].length; i++){
			System.out.println(cooccurrenceMatrix[0][i]);
		}
	}

}

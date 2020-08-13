package com.selab.uidesignserver.model.matchMaking;

import com.selab.uidesignserver.config.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class TopocModelImportance {

	protected double[][] importanceMatrix;
	protected HashMap<String, Integer> termIndex = new HashMap<>();
	protected HashMap<String, Integer> docIndex = new HashMap<>();
	protected String termIndexPath;
	protected String docIndexPath;
	protected String importanceMatrixPath;
	private static TopocModelImportance instance;
	
	public TopocModelImportance(){
		if(Config.importanceMode_topicModel_mode.equals("plsa")){
			if (Config.usePhraser) {
				termIndexPath = Config.PLSA_comp_folder + File.separator + "indexToTermMap.txt";
				docIndexPath = Config.PLSA_comp_folder + File.separator + "docIndex.txt";
				importanceMatrixPath = Config.PLSA_comp_folder + File.separator + "importance_matrix";
			}
			else {
				termIndexPath =Config.PLSA_no_comp_folder + File.separator + "indexToTermMap.txt";
				docIndexPath = Config.PLSA_no_comp_folder + File.separator + "docIndex.txt";
				importanceMatrixPath = Config.PLSA_no_comp_folder + File.separator + "importance_matrix";
			}
		}
		else if(Config.importanceMode_topicModel_mode.equals("lda")){
			if (Config.usePhraser) {
				termIndexPath = Config.LDA_importance_comp_path + File.separator + "term_index";
				docIndexPath = Config.LDA_importance_comp_path + File.separator + "doc_index";
				importanceMatrixPath = Config.LDA_importance_comp_path + File.separator + "importance_matrix";
			}
			else {
				termIndexPath = Config.LDA_importance_no_comp_path + File.separator + "term_index";
				docIndexPath = Config.LDA_importance_no_comp_path + File.separator + "doc_index";
				importanceMatrixPath = Config.LDA_importance_no_comp_path + File.separator + "importance_matrix";
			}
		}
		else if(Config.importanceMode_topicModel_mode.equals("tfidf")){
			if (Config.usePhraser) {
				termIndexPath = Config.tfidf_comp_folder + File.separator + "term_index";
				docIndexPath = Config.tfidf_comp_folder + File.separator + "doc_index";
				importanceMatrixPath = Config.tfidf_comp_folder + File.separator + "tfidf";
			}
			else {
				termIndexPath = Config.tfidf_no_comp_folder + File.separator + "term_index";
				docIndexPath = Config.tfidf_no_comp_folder + File.separator + "doc_index";
				importanceMatrixPath = Config.tfidf_no_comp_folder + File.separator + "tfidf";
			}
		}
		Scanner termIndexInput = null, docIndexInput = null, importanceMatrixInput = null;
		try {
			termIndexInput = new Scanner(
					new FileInputStream(termIndexPath));
			docIndexInput = new Scanner(
					new FileInputStream(docIndexPath));
			importanceMatrixInput = new Scanner(
					new FileInputStream(importanceMatrixPath));
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int terms = 0, docs = 0;
		while (termIndexInput.hasNextLine())
			termIndex.put(termIndexInput.nextLine().trim(), terms++);
		while (docIndexInput.hasNextLine())
			docIndex.put(docIndexInput.nextLine().trim(), docs++);
		importanceMatrix = new double[terms][docs];
		int index = 0;
		while (importanceMatrixInput.hasNextLine())
			importanceMatrix[index++] = castArray(importanceMatrixInput.nextLine().split("\\s"));
	}
	
	public static TopocModelImportance getInstance(){
		if (instance == null) {
			synchronized (TopocModelImportance.class) {
				if (instance == null) {
					instance = new TopocModelImportance();
				}
			}
		}
		return instance;
	}

	protected double[] castArray(String[] stringArray) {
		double[] doubleArray = new double[stringArray.length];
		for (int i = 0; i < stringArray.length; i++)
			doubleArray[i] = Double.parseDouble(stringArray[i]);
		return doubleArray;
	}
	
	public double getImportance(String word, String wsdl) {
		if (!termIndex.containsKey(word) || !docIndex.containsKey(wsdl))
			return 0;
		return importanceMatrix[termIndex.get(word)][docIndex.get(wsdl)];
	}
}

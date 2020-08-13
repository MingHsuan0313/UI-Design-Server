package com.selab.uidesignserver.model.matchMaking;

import com.selab.uidesignserver.config.Config;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OWAImportance {

	private static OWAImportance instance;
	
	private Process process;
	private BufferedWriter toProcess;
	private BufferedReader fromProcess;
	private int topicNumber;
	private Map<String, Integer> termIndexMap;
	private Map<Integer, String> indexTermMap;
	private Map<String, Double[]> termImportancesMap;
	
	
	public static synchronized OWAImportance getInstance() {
		if (instance == null) { instance = new OWAImportance(); }
		return instance;
	}
	
	/** terminate lda model process and set instance null */
	public static synchronized void terminate() {
		if (instance != null) {
			try {
				instance.toProcess.write(String.format("%s%n", "terminate"));
				instance.toProcess.flush();
				instance.toProcess.close();
				instance.fromProcess.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			instance = null;
		}
	}
	
	private OWAImportance() {
		// initial process command (virtualenv and python)
		String cmds[] = new String[]{
				"bash", "-c", 
				String.format(
					"%s; python %s %s", 
					Config.python_virtualenv_cmd, 
					Config.word_importance_script_path, 
					Config.word_importance_model_path
				)
			};
		
		// initial process and reader/writer
		try {
			ProcessBuilder processBuilder = new ProcessBuilder(cmds);
			processBuilder = processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
			this.process = processBuilder.start();
			this.toProcess = new BufferedWriter(new OutputStreamWriter(this.process.getOutputStream()));
			this.fromProcess = new BufferedReader(new InputStreamReader(this.process.getInputStream()));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		// initial topic number
		try {
			this.toProcess.write(String.format("%s%n", "get_topic_num"));
			this.toProcess.flush();
			this.topicNumber = Integer.parseInt(fromProcess.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// load keyword importance matrix
		this.termIndexMap = new HashMap<>();
		this.indexTermMap = new HashMap<>();
		this.termImportancesMap = new HashMap<>();
		try {
			// term index
			BufferedReader term_index_reader = new BufferedReader(new FileReader(Config.word_importance_matrix_folder + File.separator + "term_index"));
			for (String term = term_index_reader.readLine(); term != null; term = term_index_reader.readLine()) {
				this.termIndexMap.put(term, this.termIndexMap.size());
				this.indexTermMap.put(this.indexTermMap.size(), term);
			}
			term_index_reader.close();
			
			// importance matrix
			int index = 0;
			BufferedReader importance_matrix_reader = new BufferedReader(new FileReader(Config.word_importance_matrix_folder + File.separator + "importance_matrix"));
			for (String importances = importance_matrix_reader.readLine(); importances != null; importances = importance_matrix_reader.readLine()) {
				Double[] doubleValues = Arrays.stream(importances.split("\\s+")).map(Double::valueOf).toArray(Double[]::new);
				this.termImportancesMap.put(this.indexTermMap.get(index++), doubleValues);
			}
			importance_matrix_reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/** return topic number in this model */
	public int getTopicNum() { return this.topicNumber; }
	
	/**
	 * Return WSDL's topic importances in LDA doc-topic distribution <br>
	 * Map <Topic_id, DegreeOfImportance> 
	 * @param all_wsdl_keywords
	 * @return
	 */
	public Map<String, Double> getDocTopicImportances(List<String> all_wsdl_keywords) {
		String line = String.join(" ", all_wsdl_keywords);
		double[] importances = null;
		try {
			this.toProcess.write(String.format("%s%n", "topic_importance"));
			this.toProcess.write(String.format("%s%n", line));
			this.toProcess.flush();
			importances = Arrays.stream(fromProcess.readLine().trim().split("\\s+")).mapToDouble(Double::parseDouble).toArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// set Map
		Map<String, Double> importanceMap = new HashMap<>();
		for (double value : importances) {
			String topic_id = Integer.toString(importanceMap.size());
			importanceMap.put(topic_id, value);
		}
		
		return importanceMap;
	}
	
	/**
	 * Return keyword importances in each LDA topic-term distribution <br>
	 * Map <Topic_id, DegreeOfImportance> 
	 * @param keyword
	 * @return
	 */
	public Map<String, Double> getTermTopicImportances(String term) {
		// get importances
		Double[] importances = termImportancesMap.get(term);
		if (importances == null) { 
			importances = new Double[this.topicNumber]; 
			Arrays.fill(importances, new Double(0.0));
		}
		
		// set Map
		Map<String, Double> importanceMap = new HashMap<>();
		for (Double value : importances) {
			String topic_id = Integer.toString(importanceMap.size());
			importanceMap.put(topic_id, value);
		}
		
		return importanceMap;
	}
	
}

package com.selab.uidesignserver.model.matchMaking.similarity;

import com.selab.uidesignserver.config.Config;
//import org.apache.logging.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class LDASimilarity extends WordSimilarity {
	
	private final static Logger logger = Logger.getLogger(LDASimilarity.class);
	
	private static LDASimilarity instance;
	
	private Process process;
	private BufferedWriter toProcess;
	private BufferedReader fromProcess;
	
	private String modelPath;
	
	public static synchronized LDASimilarity getInstance() {
		if (instance == null) { instance = new LDASimilarity(); }
		return instance;
	}
	
	public static synchronized void terminate() {
		if (instance != null) {
			if (instance.process != null) {
				try {
					instance.toProcess.write(String.format("%s%n", "exit"));
					instance.toProcess.flush();
					instance.toProcess.close();
					instance.fromProcess.close();
				} catch (IOException e) {
					throw new IllegalStateException(e);
				}
			}
			instance = null;
			logger.info("Word2VecSimilarity terminated");
		}
	}
	
	private LDASimilarity() {
		this.modelPath = "/Users/selab/git/SDEGM/model/LDA/enwiki_full/enwiki_lda_model";
	}
	
	private synchronized void lazyLoadModel() {
		try {
			if (this.process == null) {
				logger.info("Lazy load LDA ...");		
				String cmds[] = new String[]{
						"bash", "-c", 
						String.format(
							"%s; python %s", 
							Config.python_virtualenv_cmd, 
							this.modelPath + ".api.py"
						)
					};
				ProcessBuilder processBuilder = new ProcessBuilder(cmds).redirectError(ProcessBuilder.Redirect.INHERIT);
				this.process = processBuilder.start();
				this.toProcess = new BufferedWriter(new OutputStreamWriter(this.process.getOutputStream()));
				this.fromProcess = new BufferedReader(new InputStreamReader(this.process.getInputStream()));
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
	
	public double getDocumentSimilarity(List<String> doc1, List<String> doc2) {
		lazyLoadModel();
		String arg1 = String.join(",", doc1);
		String arg2 = String.join(",", doc2);
		try {
			this.toProcess.write(String.format("%s%n", "doc_similarity"));
			this.toProcess.write(String.format("%s%n", arg1));
			this.toProcess.write(String.format("%s%n", arg2));
			this.toProcess.flush();
			double docSimilarity = Double.parseDouble(this.fromProcess.readLine().trim());
			return docSimilarity;
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
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
		return null;
	}
	
	public static void main(String[] args) {
		////BasicConfigurator.configure();
		BasicConfigurator.configure();

		LDASimilarity ldaSimilarity = LDASimilarity.getInstance();
		List<String> doc1 = Arrays.asList("cat","cat");
		List<String> doc2 = Arrays.asList("cat");
		System.out.println(ldaSimilarity.getDocumentSimilarity(doc1, doc2));
		LDASimilarity.terminate();
	}
}

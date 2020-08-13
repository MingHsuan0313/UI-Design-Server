package com.selab.uidesignserver.model.matchMaking.similarity;

import com.selab.uidesignserver.config.Config;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;




import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Word2VecSimilarity extends WordSimilarity {
	
	private final static Logger logger = Logger.getLogger(Word2VecSimilarity.class);
	
	private static Word2VecSimilarity instance;
	
	private Process process;
	private BufferedWriter toProcess;
	private BufferedReader fromProcess;

	private String modelPath;
	private String modelAPIPath;
	private String vecCachePath;
	private Map<String, Double[]> cacheWordVector;
	
	public static synchronized Word2VecSimilarity getInstance() {
		if (instance == null) { instance = new Word2VecSimilarity(); }
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
	
	private Word2VecSimilarity() {
		this.modelPath = Config.wordSimilarityMode_word2vec_modelPath;
		this.modelAPIPath = this.modelPath + ".api.py";
		this.vecCachePath = this.modelPath + ".vec.cache";
		
		// initial process and reader/writer
		try {
			this.cacheWordVector = new HashMap<>();
			Files.lines(Paths.get(this.vecCachePath)).forEach(line -> {
				String[] part = line.trim().split("\\s+");
				String word = part[0];
				Double[] vector = Arrays.stream(part[1].split("\\,")).map(Double::parseDouble).toArray(Double[]::new);
				this.cacheWordVector.put(word, vector);
			});
		} catch(Exception e) {
			throw new IllegalStateException(e);
		}
	}
	
	private synchronized void lazyLoadModel() {
		try {
			if (this.process == null) {
				logger.info("Lazy load Word2Vec ...");		
				String cmds[] = new String[]{
						"bash", "-c", 
						String.format(
							"%s; python %s", 
							Config.python_virtualenv_cmd, 
							this.modelAPIPath
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
		try {
			if (!this.cacheWordVector.containsKey(word)) {
				// lazy load
				lazyLoadModel();
				
				// get result
				this.toProcess.write(String.format("%s%n", "vector"));
				this.toProcess.write(String.format("%s%n", word));
				this.toProcess.flush();
				String vectorString = this.fromProcess.readLine().trim();
				
				// cache
				Double[] vector = Arrays.stream(vectorString.split("\\,")).map(Double::parseDouble).toArray(Double[]::new);
				this.cacheWordVector.put(word, vector);
				String line = word + "\t" + vectorString + "\n";
				Files.write(Paths.get(this.vecCachePath), line.getBytes(), StandardOpenOption.APPEND);
				logger.info("Cache word vector: " + word);
			}
			return this.cacheWordVector.get(word);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
	public static void main(String[] args) {
		//BasicConfigurator.configure();
		BasicConfigurator.configure();

		Word2VecSimilarity word2VecSimilarity = Word2VecSimilarity.getInstance();
		System.out.println("--------------------open");
		System.out.println(word2VecSimilarity.getWordSimilarity("open", "close"));
		System.out.println(word2VecSimilarity.getWordSimilarity("lock", "unlock"));

		System.out.println("--------------------open");
		System.out.println(word2VecSimilarity.getWordSimilarity("open", "unlock"));
		System.out.println(word2VecSimilarity.getWordSimilarity("lock", "close"));

		System.out.println("--------------------ebook");
		System.out.println(word2VecSimilarity.getWordSimilarity("ebook", "book"));
		System.out.println(word2VecSimilarity.getWordSimilarity("ebook", "media"));
		System.out.println(word2VecSimilarity.getWordSimilarity("ebook", "video"));
		System.out.println(word2VecSimilarity.getWordSimilarity("order", "title"));
		System.out.println(word2VecSimilarity.getWordSimilarity("order", "publication"));
		System.out.println(word2VecSimilarity.getWordSimilarity("order", "number"));
		System.out.println(word2VecSimilarity.getWordSimilarity("order", "author"));
		System.out.println(word2VecSimilarity.getWordSimilarity("order", "price"));
		System.out.println(word2VecSimilarity.getWordSimilarity("order", "review"));
		System.out.println(word2VecSimilarity.getWordSimilarity("order", "book"));
		System.out.println(word2VecSimilarity.getWordSimilarity("order", "video"));
		System.out.println(word2VecSimilarity.getWordSimilarity("order", "recommended"));

		System.out.println("--------------------zip_codes");
		System.out.println(word2VecSimilarity.getWordSimilarity("zip_codes", "postal_code"));
		System.out.println(word2VecSimilarity.getWordSimilarity("zip_code", "postal_code"));
		System.out.println(word2VecSimilarity.getWordSimilarity("zip_code", "address"));
		System.out.println(word2VecSimilarity.getWordSimilarity("postal_code", "address"));

		System.out.println("--------------------weather");
		System.out.println(word2VecSimilarity.getWordSimilarity("weather", "warm"));
		System.out.println(word2VecSimilarity.getWordSimilarity("weather", "warm_front"));
		System.out.println(word2VecSimilarity.getWordSimilarity("weather", "icing"));
		System.out.println(word2VecSimilarity.getWordSimilarity("weather", "lightning"));
		System.out.println(word2VecSimilarity.getWordSimilarity("geopolitical", "geographical"));

		System.out.println("--------------------credit");
		System.out.println(word2VecSimilarity.getWordSimilarity("credit", "price"));
		System.out.println(word2VecSimilarity.getWordSimilarity("credit_card", "price"));
		
		System.out.println("--------------------distance");
		System.out.println(word2VecSimilarity.getWordSimilarity("distance", "map"));
		
		System.out.println("--------------------destination");
		System.out.println(word2VecSimilarity.getWordSimilarity("destination", "farmland"));
		System.out.println(word2VecSimilarity.getWordSimilarity("destination", "town"));
		System.out.println(word2VecSimilarity.getWordSimilarity("destination", "area"));
		System.out.println(word2VecSimilarity.getWordSimilarity("destination", "city"));
		System.out.println(word2VecSimilarity.getWordSimilarity("destination", "beach"));
		System.out.println(word2VecSimilarity.getWordSimilarity("destination", "park"));

		System.out.println(word2VecSimilarity.getWordSimilarity("book", "author"));
		System.out.println(word2VecSimilarity.getWordSimilarity("book", "novel"));
		System.out.println(word2VecSimilarity.getWordSimilarity("book", "price"));

		System.out.println(word2VecSimilarity.getWordSimilarity("price", "author"));
		System.out.println(word2VecSimilarity.getWordSimilarity("price", "novel"));
		System.out.println(word2VecSimilarity.getWordSimilarity("price", "price"));
		
		System.out.println(word2VecSimilarity.getWordSimilarity("price", "novel"));
		System.out.println(word2VecSimilarity.getWordSimilarity("price", "price"));

		System.out.println("book   -> " + Arrays.asList(word2VecSimilarity.getWordVector("book")));
		System.out.println("price  -> " + Arrays.asList(word2VecSimilarity.getWordVector("price")));
		System.out.println("author -> " + Arrays.asList(word2VecSimilarity.getWordVector("author")));
		System.out.println("novel  -> " + Arrays.asList(word2VecSimilarity.getWordVector("novel")));
		
		Word2VecSimilarity.terminate();
	}
}

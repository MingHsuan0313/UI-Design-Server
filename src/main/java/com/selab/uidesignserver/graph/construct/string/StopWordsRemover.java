package com.selab.uidesignserver.graph.construct.string;

import com.selab.uidesignserver.config.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StopWordsRemover {

	private Set<String> stopWords;

	public StopWordsRemover() {
		this.stopWords = new HashSet<String>();
		try {
			Files.lines(Paths.get(Config.stopwords_path)).forEach(line -> this.stopWords.add(line.trim()));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public Set<String> getStopWords() { return this.stopWords; }
	
	public List<String> removeStopWords(List<String> tokenList) {
		List<String> removeStopList = new ArrayList<>();
		for (String token : tokenList) {
			if (!isStopWord(token)) {
				removeStopList.add(token);
			}
		}
		return removeStopList;
	}

	public boolean isStopWord(String word) {
		return word.length() <= 1 || this.stopWords.contains(word);
	}
}

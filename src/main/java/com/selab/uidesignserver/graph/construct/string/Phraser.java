package com.selab.uidesignserver.graph.construct.string;

import com.selab.uidesignserver.config.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Phraser {
	
	private Set<String> phrases;

	public Phraser() {
		this.phrases = new HashSet<String>();
		try {
			Files.lines(Paths.get(Config.phrases_path)).forEach(line -> this.phrases.add(line.trim()));
			//Files.lines(Paths.get("res/google_word2vec.phrases")).forEach(line -> this.phrases.add(line.trim()));
			//Files.lines(Paths.get("res/enwiki_word2vec.phrases")).forEach(line -> this.phrases.add(line.trim()));
			//Files.lines(Paths.get("res/cat-enwiki_word2vec.phrases")).forEach(line -> this.phrases.add(line.trim()));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	public Set<String> getPhrases() { return this.phrases; }
	
	public List<String> phrases(List<String> tokenList, String delimiter, int maxPhrasesLen) {
		if (maxPhrasesLen < 1) {
			throw new IllegalArgumentException("maxPhrasesLen must greater than 0");
		}
		
		// phrase
		List<String> phrasesList = new ArrayList<>();
		int windowHeadIdx = 0;
		while (windowHeadIdx + maxPhrasesLen <= tokenList.size()) {
			List<String> window = tokenList.subList(windowHeadIdx, windowHeadIdx + maxPhrasesLen);
			String phrase = String.join("_", window);
			if (this.phrases.contains(phrase)) {
				phrasesList.add(String.join(delimiter, window));
				windowHeadIdx += maxPhrasesLen;
			} else {
				phrasesList.add(window.get(0));
				windowHeadIdx += 1;
			}
		}
		
		// remind tokens
		while (windowHeadIdx < tokenList.size()) {
			phrasesList.add(tokenList.get(windowHeadIdx));
			windowHeadIdx += 1;
		}
		
		// recursive 
		if (maxPhrasesLen > 2) {
			return phrases(phrasesList, delimiter, maxPhrasesLen -1);
		} else {
			return phrasesList;
		}
	}
	
	public static void main(String[] args) {
		Phraser phraser = new Phraser();
		for (int i = 0; i < 10; i++) {
			System.out.println(phraser.getPhrases().toArray()[i]);
		}
		List<String> test1 = Arrays.asList(new String[]{"a","google","maps","street","yo","ramsay", "kitchen", "nightmares"});
		List<String> test2 = Arrays.asList(new String[]{"new","york"});
		System.out.println(phraser.phrases(test1, "_", 3));
		System.out.println(phraser.phrases(test2, "_", 3));
	}
}

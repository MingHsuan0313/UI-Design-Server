package com.selab.uidesignserver.graph.construct.string;

import com.selab.uidesignserver.config.Config;
import com.selab.uidesignserver.model.matchMaking.WordSegmentation;
import net.didion.jwnl.JWNL;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.dictionary.Dictionary;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

public class Tokenizer {
	
	private Dictionary wordNetDictionary;
	private Set<String> CustomWordSet;

	public Tokenizer() {
		// initial WordNet Library by property file
		try {
			Locale.setDefault(Locale.ENGLISH);
			JWNL.initialize(new FileInputStream(Config.JWNL_properties_path));
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		this.wordNetDictionary = Dictionary.getInstance();
		this.CustomWordSet = new HashSet<>();
		try {
			Files.lines(Paths.get(Config.words_path)).forEach(line -> this.CustomWordSet.add(line.trim()));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private static final Pattern UNDERSCORE_OR_DASH_PATTERN = Pattern.compile("_|-");
	
	public List<String> tokenize(String string) {
		string = UNDERSCORE_OR_DASH_PATTERN.matcher(string).replaceAll(" ");
		List<String> tokenList = new ArrayList<>();
		for (String token : WordSegmentation.getInstance().queryString(string)) {
			List<String> wordNetSplits = splitByWordNet(token);
			int preSize = 1;
			while (preSize != wordNetSplits.size()) {
				preSize = wordNetSplits.size();
				List<String> nexrWordNetSplits = new ArrayList<>();
				for (String word : wordNetSplits) {
					nexrWordNetSplits.addAll(splitByWordNet(word));
				}
				wordNetSplits = nexrWordNetSplits;
			}; 
			tokenList.addAll(wordNetSplits);
		}
		return tokenList;
	}
	
	public List<String> simpleStem(List<String> tokenList) {
		List<String> stemList = new ArrayList<>();
		for (String token : tokenList) {
			try {
				boolean inWordNet = false;
				for (IndexWord indexWord : this.wordNetDictionary.lookupAllIndexWords(token).getIndexWordArray()) {
					Synset[] senses = indexWord.getSenses();
					if (senses != null && senses.length > 0) {
						inWordNet = true;
						stemList.add(indexWord.getLemma());
						break;
					}
				}
				if (!inWordNet) {
					stemList.add(token);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return stemList;
	}
	
	private List<String> splitByWordNet(String string) {
		if (isWord(string) || string.length() <= 1) {
			return new ArrayList<String>(Arrays.asList(string));
		}
		
		// find logest split
		int maxWordLen = 0;
		int bestSplitIdx = 1;
		for (int splitIdx = 1; splitIdx < string.length(); splitIdx++) {
			String left = string.substring(0, splitIdx);
			String right = string.substring(splitIdx, string.length());
			if (isWord(left) && left.length() > maxWordLen) {
				maxWordLen = left.length();
				bestSplitIdx = splitIdx;
			}
			if (isWord(right) && right.length() > maxWordLen) {
				maxWordLen = right.length();
				bestSplitIdx = splitIdx;
			}
		}
		
		// recursive
		List<String> tokenList = new ArrayList<>();
		tokenList.addAll(splitByWordNet(string.substring(0, bestSplitIdx)));
		tokenList.addAll(splitByWordNet(string.substring(bestSplitIdx, string.length())));
		return tokenList;
	}
	
	private boolean isWord(String word) {
		if (word.length() <= 1) { return false; }
		return inCustomWordSet(word) || inWordNet(word);
	}

	private boolean inCustomWordSet(String word) {
		return this.CustomWordSet.contains(word);
	}

	private boolean inWordNet(String word) {
		try {
			for (IndexWord indexWord : this.wordNetDictionary.lookupAllIndexWords(word).getIndexWordArray()) {
				Synset[] senses = indexWord.getSenses();
				if (senses != null && senses.length > 0) {
					for(Synset sense : senses) {
						if(sense.toString().toLowerCase().contains(word) || sense.toString().contains(word.replace(" ","_"))) {
							return true;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public static void main(String[] args) {
		Tokenizer tokenizer = new Tokenizer();
		System.out.println(tokenizer.isWord("bedandbreakfast"));
		System.out.println(tokenizer.inWordNet("bedandbreakfast"));
		System.out.println(tokenizer.splitByWordNet("bedandbreakfast"));
		System.out.println(tokenizer.tokenize("bedandbreakfast"));
		System.out.println(tokenizer.isWord("bedand"));
		System.out.println(tokenizer.inWordNet("bedand"));
		System.out.println(tokenizer.splitByWordNet("bedand"));
		System.out.println(tokenizer.tokenize("bedand"));
	}
}

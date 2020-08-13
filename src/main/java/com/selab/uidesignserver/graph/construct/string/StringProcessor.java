package com.selab.uidesignserver.graph.construct.string;

import com.selab.uidesignserver.config.Config;

import java.util.ArrayList;
import java.util.List;

public class StringProcessor {
	
	private Tokenizer tokenizer;
	private Phraser phraser;
	private StopWordsRemover stopWordRemover;
	
	public StringProcessor(){
		this.tokenizer = new Tokenizer();
		this.phraser = new Phraser();
		this.stopWordRemover = new StopWordsRemover();
	}

	public List<String> parseString(String string) {
		string = string.trim().toLowerCase();
		string = correctSpellingMistakes(string);
		List<String> tokenList =  this.tokenizer.tokenize(string);
		if (Config.usePhraser) { tokenList = this.phraser.phrases(tokenList, "_", 3); }
		//tokenList = this.tokenizer.simpleStem(tokenList);
		tokenList = this.stopWordRemover.removeStopWords(tokenList);
		return tokenList;
	}
	
	private String correctSpellingMistakes(String string) {
		string = string.replaceAll("goverment", "government");
		string = string.replaceAll("recomendation", "recommendation");
		string = string.replaceAll("accomodation", "accommodation");
		string = string.replaceAll("fantacy", "fantasy");
		string = string.replaceAll("fantancy", "fantasy");
		string = string.replaceAll("modul", "module");
		string = string.replaceAll("modal", "module");
		string = string.replaceAll("fantansy", "fantasy");
		string = string.replaceAll("colour", "color");
		// synonyms
		//string = string.replaceAll("ebook", "media");
		//string = string.replaceAll("unlock", "open");
		//
		return string;
	}
	
		
	public static void main(String[] args) throws Exception {
		ArrayList<String> arrayList = new ArrayList<>();
		arrayList.add("dog");
		arrayList.add("donald");
		arrayList.add("trump");
		arrayList.add("hello");
		arrayList.add("los");
		arrayList.add("angeles");
		arrayList.add("world");
		
		StringProcessor processor = new StringProcessor();
		processor.parseString("1personbicycle4wheeledcarPriceService");
		processor.parseString("2personbicycle4wheeledcarPriceService");
		processor.parseString("Auto2personbicycleMaxpriceService");
		processor.parseString("Dvdplayermp3playerRecpriceService");
		processor.parseString("get_FUNDING1_FUNDING2");
		processor.parseString("4wheeledcar1personbicycleMaxpriceService");
		processor.parseString("Car2personbicyclePriceService");
		processor.parseString("lm_id");
		processor.parseString("BEDANDBREAKFAST".toLowerCase());
		processor.parseString("BEDAND".toLowerCase());
	}
}

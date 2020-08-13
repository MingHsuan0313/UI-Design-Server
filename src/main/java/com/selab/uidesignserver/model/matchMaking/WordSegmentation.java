package com.selab.uidesignserver.model.matchMaking;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class WordSegmentation {

	private Process child;
	private PrintWriter toChild;
	private Scanner fromChild;
		
	private static WordSegmentation instance;
	
	private WordSegmentation() {
		String cmd[] = new String[]{"python", String.format("src/main/java/com/selab/uidesignserver/res/model/word_segmentation/word_segmentation.py")};
		try{
			ProcessBuilder childBuilder = new ProcessBuilder(cmd);
			childBuilder = childBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
			this.child = childBuilder.start();
			this.toChild = new PrintWriter(child.getOutputStream());
			this.fromChild = new Scanner(child.getInputStream());
		} catch(Exception e){
			throw new IllegalStateException(e);
		}
	}
	
	public static synchronized WordSegmentation getInstance() {
		if (instance == null) {
			instance = new WordSegmentation();		
		}
		return instance;
	}
	
	public List<String> queryString(String string){

		this.toChild.println(string);
		this.toChild.flush();

		String[] tokens  = this.fromChild.nextLine().trim().split("\\s+");

		return new ArrayList<String>(Arrays.asList(tokens));
	}

	public static synchronized void terminate(){
		if (instance != null) {
			instance.toChild.println("exit");
			instance.toChild.flush();
			instance = null;
		}
	}
	
//	public static void main(String[] args){
//		WordSegmentation wordSegmentation = WordSegmentation.getInstance();
//		System.out.println(String.join(" ", wordSegmentation.queryString("dvdplayer")));
//		WordSegmentation.terminate();
//	}
}

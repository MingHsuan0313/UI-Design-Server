package com.selab.uidesignserver.model.matchMaking;

import com.selab.uidesignserver.config.Config;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadModel{
	private Process child;
	private PrintWriter toChild;
	private Scanner fromChild;
		
	private static LoadModel loadModel;
	
	private LoadModel(String[] command){
		try{
			ProcessBuilder childBuilder = new ProcessBuilder(command);
			childBuilder = childBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);
			child = childBuilder.start();
			toChild = new PrintWriter(child.getOutputStream());
			fromChild = new Scanner(child.getInputStream());
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static synchronized LoadModel getInstance() {
		String cmd[] = new String[]{
			"bash", "-c", 
			String.format(
						"%s; python %s %s", 
						Config.python_virtualenv_cmd, 
						Config.LDA_script_path, 
						Config.LDA_model_path
			)
		};
		if(loadModel==null){
			loadModel = new LoadModel(cmd);		
		}
		return loadModel;
	}

	public String queryWord(String word1, String word2){
		toChild.println("word");
		toChild.flush();
		toChild.println(word1);
		toChild.flush();
		toChild.println(word2);
		toChild.flush();
		return fromChild.nextLine().trim();
	}

	public String queryDocument(String document){
		toChild.println("document");
		toChild.flush();
		toChild.println(document);
		toChild.flush();
		return fromChild.nextLine().trim();
	}
	
	public ArrayList<Double> word_topics_vector(String word){
		toChild.println("word_topics_vector");
		toChild.flush();
		toChild.println(word);
		toChild.flush();
		String vector_str = fromChild.nextLine().trim();
		ArrayList<Double> vector = new ArrayList<Double>();
		if (vector_str.length() > 0){
			for (String value_str : vector_str.split("\\,")) {
				Double value = Double.parseDouble(value_str);
				vector.add(value);
			}
		}
		return vector;
	}

	public int terminateProcess(){
		toChild.println("exit");
		toChild.flush();
		while(child.isAlive())
			child.destroyForcibly();
		int exit = -1;
		
		try{
			exit = child.waitFor();
		} catch(Exception e){
			e.printStackTrace();
		}
		loadModel = null;
		toChild.close();
		fromChild.close();
		return exit;
	}
	
	public static void terminate() {
		if (loadModel != null) {
			loadModel.terminateProcess();
		}
	}

	public static void main(String[] args){
		
	}
}

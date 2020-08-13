package com.selab.uidesignserver.graph.alignment.distance.datatype;

import java.util.HashMap;

public class DataTypeTable {
	private int[][] table;
	private int maxDistance;
	private HashMap<String,Integer> dataTypeIndex;
	private String root;
	
	public DataTypeTable(){
		dataTypeIndex = new HashMap<String,Integer>();
	}
	
	public int[][] getTable() {
		return table;
	}
	public void setTable(int[][] table) {
		this.table = table;
	}
	public int getMaxDistance() {
		return maxDistance;
	}
	public void setMaxDistance(int maxDistance) {
		this.maxDistance = maxDistance;
	}
	public HashMap<String,Integer> getDataTypeIndex() {
		return dataTypeIndex;
	}
	
	public void addDataTypeIndex(String key, int value) {
		dataTypeIndex.put(key, value);
	}
	
	
	public void setDataTypeIndex(HashMap<String,Integer> dataTypeIndex) {
		this.dataTypeIndex = dataTypeIndex;
	}
	
	public void findMaxDistance(){
		int max=0;
		for(int i=0;i<table.length;i++){
			for(int j=0;j<table.length;j++){
				if(table[i][j]>max){
					max = table[i][j];
				}
			}
		}
		maxDistance = max;
	}
	
	public boolean contain(String type){
		return dataTypeIndex.keySet().contains(type);
	}


	public String getRoot() {
		return root;
	}


	public void setRoot(String root) {
		this.root = root;
	}
	
	public int findDistance(String start,String end){
		int startIndex = dataTypeIndex.get(start);
		int endIndex = dataTypeIndex.get(end);
		return table[startIndex][endIndex];
	}
	
}

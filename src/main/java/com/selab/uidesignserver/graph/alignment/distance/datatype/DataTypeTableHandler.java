package com.selab.uidesignserver.graph.alignment.distance.datatype;

import com.selab.uidesignserver.config.Config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringTokenizer;

public class DataTypeTableHandler {
	
	public static DataTypeTableHandler instance;
	
	private DataTypeTable numericalDataTypeDistanceTable;
	private DataTypeTable dateTimeDataTypeDistanceTable;
	private DataTypeTable stringDataTypeDistanceTable;
	private DataTypeTable miscDataTypeDistanceTable;
	
	private DataTypeTableHandler() {
		buildDataTypeDistanceTable();
	}
	
	public static synchronized DataTypeTableHandler getInstance() {
		if (instance == null) {
			instance = new DataTypeTableHandler();
		}
		return instance; 
	}
	private void buildDataTypeDistanceTable(){
		numericalDataTypeDistanceTable = new DataTypeTable();
		int[][] adj = buildDataTypeAdj(Config.datatype_table_folder + "numericalDataTypes",numericalDataTypeDistanceTable);
		numericalDataTypeDistanceTable.setTable(shortestpath(adj,initialPathMatrix(adj)));	
		numericalDataTypeDistanceTable.findMaxDistance();
		
		dateTimeDataTypeDistanceTable = new DataTypeTable();
		adj = buildDataTypeAdj(Config.datatype_table_folder + "dateTimeDataTypes",dateTimeDataTypeDistanceTable);
		dateTimeDataTypeDistanceTable.setTable(shortestpath(adj,initialPathMatrix(adj)));
		dateTimeDataTypeDistanceTable.findMaxDistance();
		
		miscDataTypeDistanceTable = new DataTypeTable();	
		adj = buildDataTypeAdj(Config.datatype_table_folder + "miscDataTypes",miscDataTypeDistanceTable);
		miscDataTypeDistanceTable.setTable(shortestpath(adj,initialPathMatrix(adj)));
		miscDataTypeDistanceTable.findMaxDistance();
		
		stringDataTypeDistanceTable = new DataTypeTable();
		adj = buildDataTypeAdj(Config.datatype_table_folder + "stringDataTypes",stringDataTypeDistanceTable);
		stringDataTypeDistanceTable.setTable(shortestpath(adj,initialPathMatrix(adj)));
		stringDataTypeDistanceTable.findMaxDistance();
	}
	
	private int[][] initialPathMatrix(int[][] m){
		int[][] path = new int[m.length][m.length];
		for (int i = 0; i < m.length; i++) {
	 		for (int j = 0; j < m.length; j++) {
	 			if (m[i][j] == 10000) {
	 				path[i][j] = -1;
	 			} else {
	 				path[i][j] = i;
	 			}
	 		}
		}
	 	for (int i = 0; i < m.length; i++) {
	 		path[i][i] = i;
	 	}
	 	return path;
	}
	
	private int[][] buildDataTypeAdj(String path, DataTypeTable dataTypeTable){
		//The first line is type names and the root is the first type of the first line.
		String thisLine = null;
		int t = 0;
		int[][] adj=null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			thisLine = br.readLine();
			StringTokenizer st = new StringTokenizer(thisLine," \t\n\r\f:");
			int m = st.countTokens();
			for (int i = 0; i < m; i++) {
				String dataType = st.nextToken();
				if(i==0){
					dataTypeTable.setRoot(dataType);
				}
				dataTypeTable.addDataTypeIndex(dataType, i);
			}
			adj = new int[m][m];
			while ((thisLine = br.readLine()) != null) {
				st = new StringTokenizer(thisLine," \t\n\r\f:");
				m = st.countTokens();		
				for (int i = 0; i < m; i++) {
					adj[t][i]=Integer.valueOf(st.nextToken());
				}		
				t++;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return adj;
	}
	
	private int[][] shortestpath(int[][] adj, int[][] path) {
		// FloydWarshall
		int n = adj.length;
		int [][] ans = new int[n][];
		for(int i = 0; i < ans.length; i++) {
			ans[i] = adj[i].clone();
		}

		// Compute successively better paths through vertex k.
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (ans[i][k] + ans[k][j] < ans[i][j]) {
						ans[i][j] = ans[i][k] + ans[k][j];
						path[i][j] = path[k][j];
					}
				}
			}
		}
		return ans;
	}
	
	public DataTypeTable findCategory(String type){
		if(numericalDataTypeDistanceTable.contain(type)){
			return numericalDataTypeDistanceTable;
		}else if(dateTimeDataTypeDistanceTable.contain(type)){
			return dateTimeDataTypeDistanceTable;
		}else if(miscDataTypeDistanceTable.contain(type)){
			return miscDataTypeDistanceTable;
		}else if(stringDataTypeDistanceTable.contain(type)){
			return stringDataTypeDistanceTable;
		}else{
			return null;
		}
	}
	
	public double getDataTypeDistance(String type1, String type2){
		DataTypeTable type1Table = findCategory(type1);
		DataTypeTable type2Table = findCategory(type2);
		double dis = Config.ELASTICITY_DATATYPE;
		if(type1Table == null || type2Table == null) {
//			System.err.println("dataType belong to no DataTypeTable");
		}else if(type1Table == type2Table ){
			dis = type1Table.findDistance(type1, type2)
					* Config.ELASTICITY_DATATYPE
					/ type1Table.getMaxDistance();
		}else{
			dis = type1Table.findDistance(type1Table.getRoot(), type1) 
					* Config.ELASTICITY_DATATYPE
					/ type1Table.getMaxDistance() 
					+ type2Table.findDistance(type2Table.getRoot(), type2) 
					* Config.ELASTICITY_DATATYPE
					/ type2Table.getMaxDistance();
		}
		return dis;
	}
}

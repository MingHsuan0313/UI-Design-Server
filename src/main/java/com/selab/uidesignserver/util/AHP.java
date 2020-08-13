package com.selab.uidesignserver.util;

import org.apache.log4j.BasicConfigurator;
//import org.apache.log4j.Logger;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AHP {

	private final static Logger logger = Logger.getLogger(AHP.class);

	private static Map<Integer, Double> randomIndexTable;
	
	static {
		randomIndexTable = new HashMap<Integer, Double>();
		randomIndexTable.put(1, 0.0); // Values pulled from Saaty's paper, 2007
		randomIndexTable.put(2, 0.0);
		randomIndexTable.put(3, 0.52);
		randomIndexTable.put(4, 0.89);
		randomIndexTable.put(5, 1.11);
		randomIndexTable.put(6, 1.25);
		randomIndexTable.put(7, 1.35);
		randomIndexTable.put(8, 1.40);
		randomIndexTable.put(9, 1.45);
		randomIndexTable.put(10, 1.49);
	}

	private AHP() {
		// static class
	}

	public static double[] calculateEigenVector(double[][] pairwiseComparisonMatirx) {
		logger.debug("Pairwise Comparison Matirx:\n" + Arrays.deepToString(pairwiseComparisonMatirx).replaceAll("\\],", "\\]\n"));
		
		int len = pairwiseComparisonMatirx.length;
		double[] eigenVector = new double[pairwiseComparisonMatirx.length];
		
		// calculate matrix sum
		double matrixSum = 0.0;
		for (int r = 0; r < len; r++) {
			for (int c = 0; c < len; c++) {
				matrixSum += pairwiseComparisonMatirx[r][c];
			}
		}
		
		// calculate approximate eigenVector
		for (int k = 0; k < len; k++) {
			for (int c = 0; c < len; c++) {
				eigenVector[k] += pairwiseComparisonMatirx[k][c] / matrixSum;
			}
		}
		
		return eigenVector;
	}
	
	public static double checkConsistency(double[][] pairwiseComparisonMatirx, double[] eigenVector) {
		int len = pairwiseComparisonMatirx.length;
		
		// calculate approximate maximum eigenValue
		double maxEigenvalue = 0.0;
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				maxEigenvalue += pairwiseComparisonMatirx[i][j] * eigenVector[j] / eigenVector[i];
			}
		}
		maxEigenvalue /= len;
		
		// calculate consistency
		double CI = (maxEigenvalue - len) / (len - 1.0);
		double RI = randomIndexTable.get(len);
		double CR = CI / RI;
		logger.debug("Max eigenvalue is " + maxEigenvalue);
		logger.debug("Consistency Index is " + CI);
		logger.debug("Consistency Ratio is " + CR);
		if (CR > 0.10) {
			logger.debug("Ratio is larger than 0.10. Pair-wise comparison is inconsistent.");
		} else {
			logger.debug("Ratio is less than 0.10. Pair-wise comparison is consistent.");
		}
		
		return CR;
	}


	public static double[][] toPairwiseComparisonMatirx(double[] pairwiseComparison, int size) {
		double[][] matrix = new double[size][size];

		int index = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i == j) {
					matrix[i][j] = 1.0;
				} else if (i < j) {
					matrix[i][j] = pairwiseComparison[index];
					matrix[j][i] = 1.0 / pairwiseComparison[index];
					index++;
				}
			}
		}

		return matrix;
	}

	public static void main(String[] args) {
		//BasicConfigurator.configure();
		BasicConfigurator.configure();

		double[][] matrix = toPairwiseComparisonMatirx(new double[] {10, 10, 1}, 3);
		double[] eigenVector = AHP.calculateEigenVector(matrix);
		System.out.println(Arrays.toString(eigenVector));
		System.out.println(AHP.checkConsistency(matrix, eigenVector));

	}
}

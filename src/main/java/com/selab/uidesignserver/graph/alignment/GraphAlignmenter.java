package com.selab.uidesignserver.graph.alignment;

import com.selab.uidesignserver.config.Config;
import com.selab.uidesignserver.graph.ConnectorNode;
import com.selab.uidesignserver.graph.PrimitiveDataTypeNode;
import com.selab.uidesignserver.graph.alignment.distance.AggregationDistanceCalculator;
import com.selab.uidesignserver.graph.alignment.distance.DistanceCalculator;
import com.selab.uidesignserver.graph.alignment.distance.NormalDistanceCalculator;
import com.selab.uidesignserver.graph.alignment.distance.datatype.DataTypeTableHandler;
import org.apache.log4j.Logger;
import com.selab.uidesignserver.util.HungarianBipartiteMatching;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphAlignmenter {
	private final static Logger logger = Logger.getLogger(GraphAlignmenter.class);
	
	private DistanceCalculator distanceCalculator;
	
	public GraphAlignmenter() {
		if ("Normal".equals(Config.graphSimmilarityMode)){
			this.distanceCalculator = new NormalDistanceCalculator();
		} else if ("Aggregation".equals(Config.graphSimmilarityMode)) {
			this.distanceCalculator = new AggregationDistanceCalculator();
		} else {
			logger.fatal("Unknow similarityCalculatorStrategy: " + Config.graphSimmilarityMode);
			throw new IllegalStateException();
		}
	}

	public Set<MatchingPair> alignmentNodes(List<ConnectorNode> requestNodeList, List<ConnectorNode> candidateNodeList) {
		// build node distance table
		double[][] nodeDistanceTable = new double[requestNodeList.size()][candidateNodeList.size()];
		for (int i = 0; i < requestNodeList.size(); i++) {
			for (int j = 0; j < candidateNodeList.size(); j++) {
				ConnectorNode reqNode = requestNodeList.get(i);
				ConnectorNode canNode = candidateNodeList.get(j);
				nodeDistanceTable[i][j] = this.distanceCalculator.calculateKeywordNodeDistance(reqNode.getKeywordNode(), canNode.getKeywordNode());
				if (reqNode instanceof PrimitiveDataTypeNode && canNode instanceof PrimitiveDataTypeNode) {
					String reqType = ((PrimitiveDataTypeNode) reqNode).getDataTypeNode().getType();
					String canType = ((PrimitiveDataTypeNode) reqNode).getDataTypeNode().getType();
					if (reqType != null && canType != null) {
						nodeDistanceTable[i][j] += DataTypeTableHandler.getInstance().getDataTypeDistance(reqType, canType);
					}
				}
			}
		}
		
		// matching by hungarian
		Set<MatchingPair> matchingPairs = new HashSet<>();
		int[] matchingResults = new HungarianBipartiteMatching(nodeDistanceTable).execute();
		for (int reqOpIdx = 0; reqOpIdx < requestNodeList.size(); reqOpIdx++) {
			ConnectorNode reqNode = requestNodeList.get(reqOpIdx);
			ConnectorNode canNode;
			double matchingDistance = reqNode.getKeywordNode().get_node_elasticity();
			int matchCanOpIdx = matchingResults[reqOpIdx];
			if (matchCanOpIdx != -1) { // match
				canNode = candidateNodeList.get(matchCanOpIdx);
				matchingDistance = nodeDistanceTable[reqOpIdx][matchCanOpIdx];
			} else { // uumatch
				canNode = null;
				matchingDistance = reqNode.getKeywordNode().get_node_elasticity();
			}
			MatchingPair opMatchingPair = new MatchingPair(reqNode, canNode, matchingDistance);
			matchingPairs.add(opMatchingPair);
		}
		
		return matchingPairs;
	}
}

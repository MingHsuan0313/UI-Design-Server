package com.selab.uidesignserver.model.matchMaking.searchmodel;

import com.selab.uidesignserver.config.Config;
import com.selab.uidesignserver.graph.ConnectorNode;
import com.selab.uidesignserver.graph.MessageNode;
import com.selab.uidesignserver.graph.PrimitiveDataTypeNode;
import com.selab.uidesignserver.graph.ServiceNode;
import com.selab.uidesignserver.graph.alignment.GraphAlignmenter;
import com.selab.uidesignserver.graph.alignment.MatchingPair;
import org.apache.log4j.Logger;

import java.util.*;


public class SimilarityCalculator {
	
	private final static Logger logger = Logger.getLogger(SimilarityCalculator.class);
	
	private GraphAlignmenter graphAlignmenter;
	
	public SimilarityCalculator() {
		this.graphAlignmenter = new GraphAlignmenter();
	}

	public double calServiceSimilarity(ServiceNode request, ServiceNode candidate) {
		// print filename
		logger.debug("--------------------------------------------------");
		logger.debug("Request Service: " + request.getFilename());
		logger.debug("Candidate Service: " + candidate.getFilename());
		
		// calculate three part similarity
		Set<MatchingPair> returnOpMatchingPairs = new HashSet<>();
		double opSimilarity = calOpPartSim(request, candidate, returnOpMatchingPairs);
		
		//return opSimilarity;
		
		logger.debug("Operation Similarity: " + opSimilarity);
		double inputSimilarity = calInOutPartSim(returnOpMatchingPairs, MessageNode.NEXT_LAYER_INPUT_PART);
		logger.debug("Input Similarity: " + inputSimilarity);
		double outputSimilarity = calInOutPartSim(returnOpMatchingPairs, MessageNode.NEXT_LAYER_OUTPUT_PART);
		logger.debug("Output Similarity: " + outputSimilarity);
		double similarity = weightedSum(opSimilarity, inputSimilarity, outputSimilarity);
		logger.debug("Similarity: " + similarity);
		
		return similarity;
	}

	private double calOpPartSim(ServiceNode request, ServiceNode candidate, Set<MatchingPair> returnOpMatchingPairs) {
		// service layer
		List<ConnectorNode> reqServiceList = new ArrayList<>(Arrays.asList(request));
		List<ConnectorNode> canServiceList = new ArrayList<>(Arrays.asList(candidate));
		Set<MatchingPair> serviceMatchingPairs = this.graphAlignmenter.alignmentNodes(reqServiceList, canServiceList);
		double serviceElasticity = sumMatchingElasticity(serviceMatchingPairs);
		double serviceDistance = sumMatchingDistance(serviceMatchingPairs);
		
		// operation layer
		List<ConnectorNode> reqOpList = request.getNextLayerNodes();
		List<ConnectorNode> canOpList = candidate.getNextLayerNodes();
		Set<MatchingPair> opMatchingPairs = this.graphAlignmenter.alignmentNodes(reqOpList, canOpList);
		double opElasticity = sumMatchingElasticity(opMatchingPairs);
		double opDistance = sumMatchingDistance(opMatchingPairs);
				
		// part similarity
		double opPartElasticity = serviceElasticity + opElasticity;
		double opPartDistance = serviceDistance + opDistance;
		double opPartSimilarity = opPartElasticity == 0.0 ? 0.0 : (opPartElasticity - opPartDistance) / opPartElasticity;
		
		// return
		returnOpMatchingPairs.clear();
		returnOpMatchingPairs.addAll(opMatchingPairs);
		return opPartSimilarity;
	}

	private double calInOutPartSim(Set<MatchingPair> opMatchingPairs, int INPUT_OUTPUT) {
		double avgInOutPartSimilarity = 0.0;
		for(MatchingPair opMatchingPair : opMatchingPairs) {
			// CDT layer
			ConnectorNode reqCDT = opMatchingPair.getRequestNode().getNextLayerNodes().get(INPUT_OUTPUT).getNextLayerNodes().get(0);
			ConnectorNode canCDT = opMatchingPair.getCandidateNode().getNextLayerNodes().get(INPUT_OUTPUT).getNextLayerNodes().get(0);
			List<ConnectorNode> reqCDTList = new ArrayList<>(Arrays.asList(reqCDT));
			List<ConnectorNode> canCDTList = new ArrayList<>(Arrays.asList(canCDT));
			Set<MatchingPair> cdtMatchingPairs = this.graphAlignmenter.alignmentNodes(reqCDTList, canCDTList);
			double cdtElasticity = sumMatchingElasticity(cdtMatchingPairs);
			double cdtDistance = sumMatchingDistance(cdtMatchingPairs);
			
			// PDT layer
			List<ConnectorNode> reqPDTList = reqCDT.getNextLayerNodes();
			List<ConnectorNode> canPDTList = canCDT.getNextLayerNodes();
			Set<MatchingPair> pdtMatchingPairs = this.graphAlignmenter.alignmentNodes(reqPDTList, canPDTList);
			double pdtElasticity = sumMatchingElasticity(pdtMatchingPairs);
			double pdtDistance = sumMatchingDistance(pdtMatchingPairs);
			
			// part similarity
			double inOutPartElasticity = cdtElasticity + pdtElasticity;
			double inOutPartDistance = cdtDistance + pdtDistance;
			double inOutPartSimilarity = inOutPartElasticity == 0.0 ? 0.0 : (inOutPartElasticity - inOutPartDistance) / inOutPartElasticity;
			
			// average similarity
			avgInOutPartSimilarity += inOutPartSimilarity;
		}
		avgInOutPartSimilarity = opMatchingPairs.size() == 0 ? 0.0 : avgInOutPartSimilarity / (double) opMatchingPairs.size();
		
		return avgInOutPartSimilarity;
	}
	
	private double sumMatchingElasticity(Set<MatchingPair> matchingParis) {
		double sumEla = 0.0;
		for (MatchingPair matchingPair : matchingParis) {
			ConnectorNode requestNode = matchingPair.getRequestNode();
			// connector node elasticity
			sumEla += requestNode.get_node_elasticity();
			
			// keyword node elasticity
			if (requestNode.getKeywordNode() != null) {
				sumEla += requestNode.getKeywordNode().get_node_elasticity();
			}
			
			// data type node elasticity
			if (requestNode instanceof PrimitiveDataTypeNode) {
				PrimitiveDataTypeNode pdt = (PrimitiveDataTypeNode) matchingPair.getRequestNode();
				if (pdt.getDataTypeNode() != null) {
					sumEla += pdt.getDataTypeNode().get_node_elasticity();
				}
			}
		}
		return sumEla;
	}
	
	private double sumMatchingDistance(Set<MatchingPair> matchingParis) {
		double sumDistance = 0.0;
		for (MatchingPair matchingPair : matchingParis) {
			sumDistance += matchingPair.getNodeDistance();
		}
		return sumDistance;
	}
	
	private double weightedSum(double opSim, double inputSim, double outputSim) {
		double weightedValue = (
					opSim * Config.PART_WEIGHT_OPERATION 
					+ inputSim * Config.PATR_WEIGHT_INPUT 
					+ outputSim * Config.PART_WEIGHT_OUTPUT
				);
		weightedValue /= (Config.PART_WEIGHT_OPERATION + Config.PATR_WEIGHT_INPUT + Config.PART_WEIGHT_OUTPUT);
		return weightedValue;
	}

}

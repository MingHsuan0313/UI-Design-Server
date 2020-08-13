package com.selab.uidesignserver.util;

import com.selab.uidesignserver.graph.*;
import com.selab.uidesignserver.graph.visitor.GraphInfoVisitor;
import com.selab.uidesignserver.model.matchMaking.searchmodel.precision.ServiceRelevant.Offer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class GraphUtil {
	/**
	 * Analysis Benchmark Keyword Distribution <br>
	 * Show witch keyword is more importance (Appear more time in relevance)
	 * @param requestServiceList
	 * @param candidateServiceList
	 */
	public static void analysisBenchmark(ArrayList<ServiceNode> requestServiceList, ArrayList<ServiceNode> candidateServiceList){
		for (ServiceNode req_serviceNode : requestServiceList) {
			System.out.println("--------------------");
			int offerSize = req_serviceNode.getRelevants().getOfferList().size();
			int zeroSize = req_serviceNode.getRelevants().getZeroRelevanceMap().values().size();
			System.out.printf("Offer Size: %d\n", req_serviceNode.getRelevants().getOfferList().size());
			System.out.printf("%-70sk: %s\n\n", req_serviceNode.getFilename(), req_serviceNode.get_subgraph_keywords());
			HashMap<String, Integer> OfferInReq = new HashMap<String, Integer>();
			HashMap<String, Integer> OfferNotReq = new HashMap<String, Integer>();
			HashMap<String, Integer> ZeroInReq = new HashMap<String, Integer>();
			HashMap<String, Integer> ZeroNotReq = new HashMap<String, Integer>();
			for (String word : req_serviceNode.get_subgraph_keywords()) {
				OfferInReq.put(word, 0);
				ZeroInReq.put(word, 0);
			}
			
			HashSet<String> OfferSet = new HashSet<String>();
			for (Offer offer : req_serviceNode.getRelevants().getOfferMap().values()){
				OfferSet.add(offer.FileName);
			}
			HashSet<String> ZeroSet = new HashSet<String>();
			for (Offer zero : req_serviceNode.getRelevants().getZeroRelevanceMap().values()){
				ZeroSet.add(zero.FileName);
			}
			
			for (ServiceNode can_serviceNode : candidateServiceList) {
				if (OfferSet.contains(can_serviceNode.getFilename())) {
					System.out.printf("O %-70sk: %s\n", can_serviceNode.getFilename(), can_serviceNode.get_subgraph_keywords() );
					for (String word : can_serviceNode.get_subgraph_keywords()) {
						if (OfferInReq.keySet().contains(word)) {
							OfferInReq.put(word, OfferInReq.get(word) + 1);
						} else {
							OfferNotReq.put(word, OfferNotReq.getOrDefault(word, 0) + 1);
						}
					}
				}
			}
			for (ServiceNode can_serviceNode : candidateServiceList) {
				if (ZeroSet.contains(can_serviceNode.getFilename())) {
					System.out.printf("X %-70sk: %s\n", can_serviceNode.getFilename(), can_serviceNode.get_subgraph_keywords() );
					for (String word : can_serviceNode.get_subgraph_keywords()) {
						if (ZeroInReq.keySet().contains(word)) {
							ZeroInReq.put(word, ZeroInReq.get(word) + 1);
						} else {
							ZeroNotReq.put(word, ZeroNotReq.getOrDefault(word, 0) + 1);
						}
					}
				}
			}

			System.out.printf("\n%-20s%-20s%-20s\n", "InReq", "OfferInReq:" + offerSize, "ZeroInReq:" + zeroSize);
			for (String word : OfferInReq.keySet()) {
				System.out.printf("%-20s%-15d%-15d\n", word, OfferInReq.getOrDefault(word, 0), ZeroInReq.getOrDefault(word, 0));
			}
			
			System.out.printf("\n%-20s%-20s%-20s\n", "NotReq", "OfferInReq:" + offerSize, "ZeroInReq:" + zeroSize);
			for (String word : ZeroNotReq.keySet()) {
				System.out.printf("%-20s%-20d%-20d\n", word, OfferNotReq.getOrDefault(word, 0), ZeroNotReq.getOrDefault(word, 0));
			}
		}
	}
	
	/**
	 * Show graph tree hierarchy <br>
	 * Show Node keywords and data type 
	 * @param serviceNode
	 * @return
	 */
	public static String getGraphHierarchy(ServiceNode serviceNode) {
		StringBuilder info = new StringBuilder("--------------------------------------------------\n");
		
		// Service
		info.append(String.format("ServiceFileName: %s%n", serviceNode.getFilename()));
		info.append(String.format("Servive: %s %s%n", serviceNode.getKeywordNode().getKeywordList(), serviceNode.getNodeName()));
		
		// Operation
		for (GraphNode opGraph : serviceNode.getNextLayerNodes()) {
			OperationNode opNode = (OperationNode)opGraph;
			info.append(String.format("\tOperation: %s %s%n", opNode.getKeywordNode().getKeywordList(), opNode.getNodeName()));
			
			// InputCDT
			for (GraphNode inCDTGraph : ((MessageNode)opNode.getNextLayerNodes().get(0)).getNextLayerNodes()) {
				ComplexDataTypeNode inCDT = (ComplexDataTypeNode)inCDTGraph;
				info.append(String.format("\t\tInpCDT: %s %s%n", inCDT.getKeywordNode().getKeywordList(), inCDT.getNodeName()));
				
				// InputPDT
				for (GraphNode inPDTGraph : inCDT.getNextLayerNodes()) {
					PrimitiveDataTypeNode inPDTNode = (PrimitiveDataTypeNode)inPDTGraph;
					info.append(String.format("\t\t\tInpPDT: Type: %-15sKeywords: %s %s%n", inPDTNode.getDataTypeNode().getType(), inPDTNode.getKeywordNode().getKeywordList(), inPDTNode.getNodeName()));
				}
			}
			
			// OutputCDT
			for (GraphNode outCDTGraph : ((MessageNode)opNode.getNextLayerNodes().get(1)).getNextLayerNodes()) {
				ComplexDataTypeNode outCDT = (ComplexDataTypeNode)outCDTGraph;
				info.append(String.format("\t\tOutCDT: %s %s%n", outCDT.getKeywordNode().getKeywordList(), outCDT.getNodeName()));
				
				// OutputPDT
				for (GraphNode outPDTGraph : outCDT.getNextLayerNodes()) {
					PrimitiveDataTypeNode outPDTNode = (PrimitiveDataTypeNode)outPDTGraph;
					info.append(String.format("\t\t\tOutPDT: Type: %-15sKeywords: %s %s%n", outPDTNode.getDataTypeNode().getType(), outPDTNode.getKeywordNode().getKeywordList(), outPDTNode.getNodeName()));
				}
			}
		}
		
		return info.toString().trim();
	}
	
	public static String getGraphInfo(ServiceNode serviceNode) {
		GraphInfoVisitor graphInfoVisitor = new GraphInfoVisitor();
		serviceNode.visit(graphInfoVisitor);
		return graphInfoVisitor.getResult();
	}
}

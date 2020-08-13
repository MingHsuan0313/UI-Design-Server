package com.selab.uidesignserver.model.matchMaking.searchmodel;

import com.selab.uidesignserver.graph.ServiceNode;

public class CandidateRankingInfo {
	
	private final ServiceNode requestService;
	private final ServiceNode candidateService;
	private final double similarity;

	public ServiceNode getRequest() { return this.requestService; }
	public ServiceNode getCandidate() { return this.candidateService; }
	public double getSimilarity() { return this.similarity; }
	
	public CandidateRankingInfo(ServiceNode requestService, ServiceNode candidateService, double similarity) {
		this.requestService = requestService;
		this.candidateService = candidateService;
		this.similarity = similarity;
	}
	
}

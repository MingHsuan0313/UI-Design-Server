package com.selab.uidesignserver.model.matchMaking.searchmodel;

import com.selab.uidesignserver.graph.ServiceNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Usage: <br>
 * 1. clear() <br>
 * 2. addCandidateRankingInfo(), ... <br>
 * 3. rankingAllCandidateRankingInfo() <br>
 * 4. getResultList() <br>
 */
public class RankingCalculator {
	
	List<CandidateRankingInfo> candidateRankingInfoList;

	public List<CandidateRankingInfo> getResultList() { return this.candidateRankingInfoList; }
	
	public RankingCalculator() {
		this.candidateRankingInfoList = new ArrayList<CandidateRankingInfo>();
	}
		
	/** Clear all added CandidateRankingInfo. */
	public void clear() {
		this.candidateRankingInfoList = new ArrayList<CandidateRankingInfo>();
	}
	
	/**
	 * If in new round, run clear() first.
	 * @param requestService
	 * @param candidateService
	 * @param similarity
	 */
	public void addCandidateRankingInfo(ServiceNode requestService, ServiceNode candidateService, double similarity) {
		this.candidateRankingInfoList.add(new CandidateRankingInfo(requestService, candidateService, similarity));
	}
	
	/** Sort by similarity from smallest to biggest. */
	public void rankingAllCandidateRankingInfo() {
		List<CandidateRankingInfo> rankedList = new ArrayList<CandidateRankingInfo>();
		for (CandidateRankingInfo candidateRankingInfo : this.candidateRankingInfoList) {
			rankedList.add(candidateRankingInfo);
		}
		rankedList.sort(new Comparator<CandidateRankingInfo>() {
			@Override
			public int compare(CandidateRankingInfo info1, CandidateRankingInfo info2) {
				return -Double.compare(info1.getSimilarity(), info2.getSimilarity());
			}
		});
		this.candidateRankingInfoList = rankedList;
	}
	
}

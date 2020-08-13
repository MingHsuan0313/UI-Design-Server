package com.selab.uidesignserver.model.matchMaking.searchmodel.precision;

import com.selab.uidesignserver.graph.ServiceNode;
//import org.apache.log4j.Logger;
import org.apache.log4j.Logger;
import com.selab.uidesignserver.model.matchMaking.searchmodel.CandidateRankingInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Usage: <br>
 * 1. calculateAveragePrecision(... ) <br>
 * 2. getResultAveragePrecision() or getResultDetailMessage() <br>
 * 3. loop 1. and 2. <br>
 * 4. getMAP()
 */
public class PrecisionCalculator {
	private final static Logger logger = Logger.getLogger(PrecisionCalculator.class);
	
	private Double resultAveragePrecision;
	private ArrayList<Double> avgPrecisionHistory;

	public Double getResultAveragePrecision() { return this.resultAveragePrecision; }
	public ArrayList<Double> getAvgPrecisionHistory() { return this.avgPrecisionHistory; }
	
	public PrecisionCalculator() {
		this.resultAveragePrecision = null;
		this.avgPrecisionHistory = new ArrayList<Double>();
	}
	
	/**
	 * Calculate Mean Average Precision. (depend on Average Precision history) <br>
	 * Call clearAvgPrecisionHistory() if you want to remove all history.
	 * @return
	 */
	public double calMAP() {
		double MAP = 0.0;
		for (Double avgPrecision : avgPrecisionHistory) {
			MAP += avgPrecision;
		}
		MAP /= avgPrecisionHistory.size();
		return MAP;
	}
	
	/**
	 * Clear all Average Precision history.
	 */
	public void clearAvgPrecisionHistory() {
		this.avgPrecisionHistory = new ArrayList<Double>();
	}
	
	/**
	 * Calculate Average Precision with rankedInfoList and the relevant in request. <br>
	 * After calculate average precision add to history for calculate MAP. <br>
	 * Get result by calling function getResultDetailMessage() and getAvgPrecisionHistory()
	 * @param request request service must contain relevant
	 * @param rankedInfoList all pair similarity information
	 */
	public void calculateAveragePrecision(ServiceNode request, List<CandidateRankingInfo> rankedInfoList) {
		if (request.getRelevants() != null){
			// clear result
			this.resultAveragePrecision = null;

			Set<String> offerFileNameSet = request.getRelevants().getOfferMap().keySet(); 
			Set<String> zeroFileNameSet = request.getRelevants().getZeroRelevanceMap().keySet();
			
			// relevant information
			logger.info("--------------------------------------------------");
			logger.info(String.format("Request: %s", request.getFilename()));
			logger.info(String.format("(#Offer, #Zero): (%d, %d)", offerFileNameSet.size(), zeroFileNameSet.size()));
			
			// init 
			int K = 0;
			int relevanceAtK = 0;
			double averagePrecision = 0.0;
			
			// calculate average precision
			for (int i = 0; i < rankedInfoList.size(); i++) {
				CandidateRankingInfo rnakingInfo = rankedInfoList.get(i);
				String candidateFileName = rnakingInfo.getCandidate().getFilename();
				double similarity = rnakingInfo.getSimilarity();
				int rank = i + 1;

				String mes_format = "Rank: %-8d Rel:%s    k: %-5d  P@k: %.6f    Sim: %.6f    Candidate: %s";
				if (offerFileNameSet.contains(candidateFileName)) { // candidate is relevance
					K++; 
					relevanceAtK++;
					double PatK = ((double)relevanceAtK) / K;
					averagePrecision += PatK;
					logger.info(String.format(mes_format, rank, "O", K, PatK, similarity, candidateFileName));
				} else if(zeroFileNameSet.contains(candidateFileName)) {  // candidate in not relevance
					K++;
					double PatK = ((double)relevanceAtK) / K;
					logger.info(String.format(mes_format, rank, "X", K, PatK, similarity, candidateFileName));
				} else { // candidate not define in both offer and zero
					// ignore this candidate
				}
			}
			averagePrecision /= offerFileNameSet.size(); 
			
			// set result
			logger.info(String.format("Average Precision: %f", averagePrecision));
			this.resultAveragePrecision = averagePrecision;
			this.avgPrecisionHistory.add(this.resultAveragePrecision);
		} else {
			logger.fatal("Request has no relevants: " + request.getFilename());
			throw new IllegalStateException("Request has no relevants: " + request.getFilename());
		}
	}
}

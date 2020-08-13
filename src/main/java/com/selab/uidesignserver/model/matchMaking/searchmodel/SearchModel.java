package com.selab.uidesignserver.model.matchMaking.searchmodel;

import com.selab.uidesignserver.config.Config;
import com.selab.uidesignserver.graph.ServiceNode;
import com.selab.uidesignserver.graph.construct.ServiceGetter;
import com.selab.uidesignserver.graph.construct.elasticity.ElasticityDeterminator;
import com.selab.uidesignserver.graph.construct.importance.ImportanceDeterminator;
import com.selab.uidesignserver.model.matchMaking.WordSegmentation;
import com.selab.uidesignserver.model.matchMaking.similarity.LDASimilarity;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import com.selab.uidesignserver.model.matchMaking.searchmodel.precision.PrecisionCalculator;
import com.selab.uidesignserver.util.GraphUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchModel {
	
	private final static Logger logger = Logger.getLogger(SearchModel.class);
	
	private ServiceGetter serviceGetter;
	
	private SimilarityCalculator similarityCalculator;
	private RankingCalculator rankingCalculator;
	private PrecisionCalculator precisionCalculator;
	
	private List<ServiceNode> candidateServiceList = new ArrayList<ServiceNode>();
	private List<ServiceNode> requestServiceList = new ArrayList<ServiceNode>();
	
	public SearchModel(){				
		// initial calculator
		this.serviceGetter = new ServiceGetter();
		this.similarityCalculator = new SimilarityCalculator();
		this.precisionCalculator = new PrecisionCalculator();
		this.rankingCalculator = new RankingCalculator();
	}
	
	public void graphConstructionCandidate(){
		logger.info("Graph Construction Candidate ...");
		this.candidateServiceList = this.serviceGetter.getAllServices(new File(Config.testdata_Wsdl_pool));
		WordSegmentation.terminate();
	}
	
	public void graphConstructionRequest(){
		logger.info("Graph Construction Request ...");
		this.requestServiceList = this.serviceGetter.getAllServices(new File(Config.testdata_queries_folder));
		WordSegmentation.terminate();
	}
	
	public void importanceDetermination() {
		logger.info("Importance Determination ...");
		for (ServiceNode request : this.requestServiceList) {
			ImportanceDeterminator.getInstance().detKeywordsImportance(request);
			logger.debug(String.format("%-60s%s", request.getFilename(), request.getKeywordNode().getImportanceMap()));
		}
		ImportanceDeterminator.terminate();
	}
	
	public void elasticityDetermination() {
		logger.info("Elasticity Determination ...");
		for (ServiceNode request : this.requestServiceList) {
			ElasticityDeterminator.getInstance().detGraphElasticity(request);
		}
		ElasticityDeterminator.terminate();
	}
	
	/*
	 * 1. Calculate all request and candidate pair similarity
	 * 2. Ranking by similarity
	 * 3. Calculate Mean Average Precision (MAP) with Benchmark
	 */
	public void rankService() {
		logger.info("Ranking services ...");
		for (ServiceNode request : requestServiceList) {
			this.rankingCalculator.clear();
			for (ServiceNode candidate : candidateServiceList) {
				/*
				List<String> docR = request.get_subgraph_keywords();
				List<String> docC = candidate.get_subgraph_keywords();
				List<String> docR = request.getKeywordNode().getKeywordList();
				docR.addAll(request.getNextLayerNodes().get(0).getKeywordNode().getKeywordList());
				List<String> docC = candidate.getKeywordNode().getKeywordList();
				docC.addAll(candidate.getNextLayerNodes().get(0).getKeywordNode().getKeywordList());
				*/
				//double similarity = LDASimilarity.getInstance().getDocumentSimilarity(docR, docC);
				double similarity = this.similarityCalculator.calServiceSimilarity(request, candidate);
				this.rankingCalculator.addCandidateRankingInfo(request, candidate, similarity);
			}
			this.rankingCalculator.rankingAllCandidateRankingInfo();
			this.precisionCalculator.calculateAveragePrecision(request, this.rankingCalculator.getResultList());
		}
		LDASimilarity.terminate();
		
		// log MAP
		logger.info("------------------------------------------------------------");
		logger.info(String.format("Request Number: %d", this.requestServiceList.size()));
		logger.info(String.format("mean average precision: %f", this.precisionCalculator.calMAP()));
		logger.info("------------------------------------------------------------");
	}
	
	/** 
	 * Search all candidate services, and return the ranking information
	 * @param requetWSDL WSDL file
	 * @return
	 */
	public List<CandidateRankingInfo> search(File requetWSDL) {
		logger.info("Building request services ...");
		ServiceNode request = this.serviceGetter.getService(requetWSDL);
		ElasticityDeterminator.getInstance().detGraphElasticity(request);
		
		logger.info("Searching candidate services ...");
		this.rankingCalculator.clear();
		for (ServiceNode candidate : candidateServiceList) {
			double similarity = this.similarityCalculator.calServiceSimilarity(request, candidate);
			this.rankingCalculator.addCandidateRankingInfo(request, candidate, similarity);
		}
		this.rankingCalculator.rankingAllCandidateRankingInfo();
		
		logger.info("Done.");
		return this.rankingCalculator.getResultList();
	}
	
	/**
	 * Calculate similarity with specific request and candidate service
	 * @param requestPath
	 * @param candidatePath
	 */
	public void compareSpecificTwoWSDL(String requestPath, String candidatePath) {
		// graph construction
		ServiceNode request = this.serviceGetter.getService(new File(requestPath));
		ServiceNode candidate = this.serviceGetter.getService(new File(candidatePath));
		
		// importance determination
		ImportanceDeterminator.getInstance().detKeywordsImportance(request);
		logger.debug(String.format("%-60s%s", request.getFilename(), request.getKeywordNode().getImportanceMap()));
		ImportanceDeterminator.terminate();
		
		// elasticity determination
		ElasticityDeterminator.getInstance().detGraphElasticity(request);
		logger.debug(String.format("%-60s%s", request.getFilename(), request.getKeywordNode().getElasticitiesMap()));
		ElasticityDeterminator.getInstance().detGraphElasticity(candidate);
		ElasticityDeterminator.terminate();
		
		// print graph information
		logger.info("Request:\n" + GraphUtil.getGraphHierarchy(request));
		logger.info("Candidate:\n" + GraphUtil.getGraphHierarchy(candidate));
		
		// calculate similarity
		this.similarityCalculator = new SimilarityCalculator();
		double similarity = this.similarityCalculator.calServiceSimilarity(request, candidate);
		logger.info("Similarity: " + similarity);
	}
	
	public void terminate() {
		WordSegmentation.terminate();
	}
	
//	public static void main(String[] args){
//		// initial logger
//        String datetime = new SimpleDateFormat("YYYY_MM_dd_HHmmss").format(new Date());
//		System.setProperty("log.datetime", datetime);
//		PropertyConfigurator.configure(Config.logger_config_path);
//		logger.info("Initial SearchModel ...");
//
//		// set configuration by properties file
//		if (args.length > 0) {
//			Config.wordSimilarityMode_vector_combination_modelPath = args[0];
////			Config.changeConfigByPropertiesFile(args[0]);
//		}
//		Config.logConfig();
//
//		testAll();
//		//testTwo("getzipcodeforuscity.wsdl", "findplacenamepostalcode.wsdl");
//	}
	
	public static void testAll() {
		// run all benchmark 42 * 1081
		SearchModel searchModel = new SearchModel();
		searchModel.graphConstructionCandidate();
		searchModel.graphConstructionRequest();
		searchModel.importanceDetermination();
		searchModel.elasticityDetermination();
		searchModel.rankService();
		searchModel.terminate();
	}
	
	public static void testTwo(String requestFileName, String candidateFileName) {
		// run specific two service
		String file1 = Config.testdata_Wsdl_pool + requestFileName;
		String file2 = Config.testdata_Wsdl_pool + candidateFileName;

		SearchModel searchModel = new SearchModel();
		searchModel.compareSpecificTwoWSDL(file1, file2);
		searchModel.terminate();
	}
}

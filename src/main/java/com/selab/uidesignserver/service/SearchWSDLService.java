package com.selab.uidesignserver.service;

import com.selab.uidesignserver.config.Config;
import org.apache.log4j.PropertyConfigurator;



import com.selab.uidesignserver.model.matchMaking.searchmodel.CandidateRankingInfo;
import com.selab.uidesignserver.model.matchMaking.searchmodel.SearchModel;

import java.io.File;
import java.util.List;

public class SearchWSDLService {
	
	private SearchModel searchModel;
	
	public SearchWSDLService() {
		PropertyConfigurator.configure(Config.logger_config_path);


		Config.wordSimilarityMode = "vector_combination";
		Config.testdata_Wsdl_pool = "testdata/demo";
		
		this.searchModel = new SearchModel();
		searchModel.graphConstructionCandidate();
	}
	
	public String search(File requestWSDL) {
		return this.search(requestWSDL, -1);
	}
	
	public String search(File requestWSDL, int maxResults) {
		List<CandidateRankingInfo> results = this.searchModel.search(requestWSDL);
		
		StringBuilder sb = new StringBuilder("<tr><th>Order</th><th>Name</th><th>Similarity</th></tr>");
		for (int i = 0; i < results.size(); i++) {
			CandidateRankingInfo result = results.get(i);
			String line = String.format("<tr><td>%d</td><td>%s</td><td>%.5f</td></tr>", (i+1), result.getCandidate().getFilename(), result.getSimilarity());
			sb.append(line);
			if (maxResults != -1 && (i+1) >= maxResults) {
				break;
			}
		}
		return sb.toString();
	}
	
	public void terminate() {
		this.searchModel.terminate();
	}
	
	public static void main(String[] args) {
		new SearchWSDLService().search(new File("testdata/demo/createFlippedBuffer.wsdl"));
	}

}

package com.selab.uidesignserver.util.parser;

import com.selab.uidesignserver.config.Config;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import com.selab.uidesignserver.model.matchMaking.searchmodel.precision.ServiceRelevant;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class OwlsTCPaser {
	private TreeMap<String, ServiceRelevant> relevantMap;
	private XMLParser xmlParser;
	
	public OwlsTCPaser() {
		this.xmlParser = new XMLParser();
		this.relevantMap = new TreeMap<String, ServiceRelevant>();
		
		Document doc = xmlParser.getDocument(Config.testdata_benchmark_file);		
		ArrayList<Node> requestNodes = xmlParser.getSubNodes(doc.getElementsByTagName("relevancegrades").item(0), "request");
		for(Node requestNode : requestNodes){
			ServiceRelevant request = new ServiceRelevant();
			String requestFilePath = xmlParser.getSubNodeText(requestNode, "uri").split("#")[0];
			request.setRequestFileName(this.getWsdlFileName(getFileName(requestFilePath)));
			ArrayList<Node> offerNodes= xmlParser.getSubNodes(requestNode, "offer"); 
			for(Node offerNode : offerNodes){
				int offerGrade = Integer.valueOf(xmlParser.getSubNodeText(offerNode, "value"));
				ServiceRelevant.Offer offer = request.getNewOffer();
				String offerFilePath = xmlParser.getSubNodeText(offerNode, "uri").split("#")[0].toLowerCase();
				offer.FileName = this.getWsdlFileName(getFileName(offerFilePath));
				offer.Grade = offerGrade;
				request.addOffer(offer);
			}
			relevantMap.put(request.getRequestFileName(), request);
		}
	}
	
	public Map<String, ServiceRelevant> getRelevantMap() {return relevantMap;}
	
	public String getWsdlFileName(String owlsFileName){
		Document doc =xmlParser.getDocument(Config.testdata_Owls_pool + owlsFileName);
		Node wsdlDocument = doc.getElementsByTagName("grounding:wsdlDocument").item(0);
		return getFileName(xmlParser.getNodeText(wsdlDocument)).toLowerCase();		
	}
	
	public static String getFileName(String fileNamePath) {
		String[] ss = fileNamePath.split("\\\\");
		if (ss.length <= 1) {
			ss = fileNamePath.split("/");
		}
		return ss[ss.length - 1];
	}
}

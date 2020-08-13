package com.selab.uidesignserver.model.matchMaking.searchmodel.precision;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ServiceRelevant {
	
	private String requestFileName;
	private List<Offer> offerList;// Store all offers
	private Map<String, Offer> offerMap;//Store non-zero value offers
	private Map<String, Offer> highRelevantOfferMap;
	private Map<String, Offer> middleRelevantOfferMap;	
	private Map<String, Offer> zeroRelevanceMap;

	public ServiceRelevant() {
		this.offerList = new ArrayList<>();	
		this.offerMap = new LinkedHashMap<>();
		this.highRelevantOfferMap = new LinkedHashMap<>();
		this.middleRelevantOfferMap = new LinkedHashMap<>();
		this.zeroRelevanceMap = new LinkedHashMap<>();
	}
	
	public Offer getNewOffer(){
		return new Offer();		
	}
	
	public ServiceRelevant addOffer(Offer offer){
		
		offerList.add(offer);
		
		if(offer.Grade == 0){
			zeroRelevanceMap.put(offer.FileName, offer);
			return this;
		}
		
		offerMap.put(offer.FileName, offer);
		if(offer.Grade == 3){
			highRelevantOfferMap.put(offer.FileName, offer);
		}
		if(offer.Grade >= 2){
			middleRelevantOfferMap.put(offer.FileName, offer);
		}
		if(offer.Grade<0){
			System.err.println("offer.Grade<0!!!!!!");
		}
		return this;
	}

	
	public void setZeroRelevanceMap(Map<String, Offer> zeroRelevanceMap) { this.zeroRelevanceMap = zeroRelevanceMap; }
	public Map<String, Offer> getZeroRelevanceMap() { return zeroRelevanceMap; }


	public List<Offer> getOfferList() { return this.offerList; }
	
	public Map<String, Offer> getOfferMap() { return this.offerMap; }
	
	public Map<String, Offer> getHighRelevantOfferMap() { return this.highRelevantOfferMap; }
	
	public Map<String, Offer> getMiddleRelevantOfferMap() { return this.middleRelevantOfferMap; }	
	
	public String getRequestFileName() { return this.requestFileName; }
	
	public ServiceRelevant setRequestFileName(String requestFileName) {
		this.requestFileName = requestFileName;
		return this;
	}
	
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("Request Name: " + this.getRequestFileName() + "\r\n");
		for(Offer offer : this.getOfferList()){
			sb.append("  Offer Grade: " + offer.Grade + ";  Name: " + offer.FileName + "\r\n");
		}
		
		return sb.toString();		
	}

	public class Offer{
		public String FileName;
		public int Grade;		
	}

}

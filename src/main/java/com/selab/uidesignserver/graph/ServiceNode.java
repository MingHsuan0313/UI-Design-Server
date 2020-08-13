package com.selab.uidesignserver.graph;

import com.selab.uidesignserver.model.matchMaking.searchmodel.precision.ServiceRelevant;

public class ServiceNode extends ConnectorNode {

	private String filename;
	private ServiceRelevant relevants ;
	
	// file name
	public void setFilename(String filename) { this.filename = filename; }
	public String getFilename() { return this.filename; }

	// relevant
	public ServiceRelevant getRelevants() { return this.relevants; }
	public void setRelevants(ServiceRelevant relevants) { this.relevants = relevants; }
	
	@Override
	public String getNodeInfo() {
		StringBuilder nodeInfo = new StringBuilder(String.format("%s%n", super.getNodeInfo()));
		nodeInfo.append(String.format("filename = %s%n", filename));
		return nodeInfo.toString().trim();
	}
}

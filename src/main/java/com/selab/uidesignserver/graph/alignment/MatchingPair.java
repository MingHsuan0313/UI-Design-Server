package com.selab.uidesignserver.graph.alignment;

import com.selab.uidesignserver.graph.ConnectorNode;

public class MatchingPair {
	private ConnectorNode requestNode;
	private ConnectorNode candidateNode;
	private double nodeDistance;
	
	public MatchingPair(ConnectorNode requestNode, ConnectorNode candidateNode, double nodeDistance) {
		this.requestNode = requestNode;
		this.candidateNode = candidateNode;
		this.nodeDistance = nodeDistance;
	}

	public ConnectorNode getRequestNode() { return this.requestNode; }
	public ConnectorNode getCandidateNode() { return this.candidateNode; }
	public double getNodeDistance() { return this.nodeDistance; }
	
}

package com.selab.uidesignserver.graph;

import com.selab.uidesignserver.graph.visitor.GraphVisitor;

public class DataTypeNode extends GraphNode {

	private String type;

	public DataTypeNode() {
		super();
		this.type = null;
	}

	// type
	public void setType(String type) { this.type = type; }
	public String getType() { return this.type; }

	// visit
	@Override
	public void visit(GraphVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String getNodeInfo() {
		StringBuilder nodeInfo = new StringBuilder(String.format("%s%n", super.getNodeInfo()));
		nodeInfo.append(String.format("type = %s%n", type));
		return nodeInfo.toString().trim();
	}
}

package com.selab.uidesignserver.graph.visitor;

import com.selab.uidesignserver.graph.GraphNode;

public class GraphInfoVisitor extends GraphVisitor {
	
	private StringBuilder result;
	
	public String getResult() { return this.result.toString().trim(); }
	
	/**
	 * Usage: <br>
	 * new(), visit(root), getResult():double
	 */
	public GraphInfoVisitor() {
		this.result = new StringBuilder();
	}
	
	@Override
	public void visit(GraphNode node) {
		this.result.append(String.format("%s%n", node.getNodeInfo()));
	}

}

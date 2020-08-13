package com.selab.uidesignserver.graph.visitor;

import com.selab.uidesignserver.graph.GraphNode;

public class CDTPartElasticityVisitor extends GraphVisitor {
	
	private double result;
	
	public double getResult() { return this.result; }
	
	/**
	 * Usage: <br>
	 * new(), visit(root), getResult():double
	 */
	public CDTPartElasticityVisitor() {
		this.result = 0.0;
	}
	
	@Override
	public void visit(GraphNode node) {
		this.result += node.get_node_elasticity();
	}

}

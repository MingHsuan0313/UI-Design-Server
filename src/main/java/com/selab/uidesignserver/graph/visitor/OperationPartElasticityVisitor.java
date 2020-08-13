package com.selab.uidesignserver.graph.visitor;

import com.selab.uidesignserver.graph.*;

public class OperationPartElasticityVisitor extends GraphVisitor {
	
	private double result;
	
	public double getResult() { return this.result; }
	
	/**
	 * Usage: <br>
	 * new(), visit(root), getResult():double
	 */
	public OperationPartElasticityVisitor() {
		this.result = 0.0;
	}
	
	@Override
	public void visit(GraphNode node) {
		if (node instanceof ServiceNode || node instanceof OperationNode || node instanceof MessageNode || node instanceof KeywordNode) {
			this.result += node.get_node_elasticity();
		}
	}

}

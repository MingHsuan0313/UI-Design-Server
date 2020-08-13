package com.selab.uidesignserver.graph.visitor;

import com.selab.uidesignserver.graph.GraphNode;

public abstract class GraphVisitor {
	
	public GraphVisitor() {
	}
	
	// visit
	public abstract void visit(GraphNode node);
	
}

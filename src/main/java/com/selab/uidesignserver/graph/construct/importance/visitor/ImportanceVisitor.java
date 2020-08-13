package com.selab.uidesignserver.graph.construct.importance.visitor;

import com.selab.uidesignserver.graph.ServiceNode;
import com.selab.uidesignserver.graph.visitor.GraphVisitor;

public abstract class ImportanceVisitor extends GraphVisitor{

	public abstract ImportanceVisitor initRootNode(ServiceNode serviceNode);
	
	public abstract void terminate();
	
}

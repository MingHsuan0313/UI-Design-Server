package com.selab.uidesignserver.graph.construct.elasticity.visitor;

import com.selab.uidesignserver.graph.ServiceNode;
import com.selab.uidesignserver.graph.visitor.GraphVisitor;

public abstract class ElasticityVisitor extends GraphVisitor {

	public abstract ElasticityVisitor initRootNode(ServiceNode serviceNode);
	
	public abstract void terminate();
	
}

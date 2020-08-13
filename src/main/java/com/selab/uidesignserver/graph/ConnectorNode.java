package com.selab.uidesignserver.graph;

import com.selab.uidesignserver.graph.visitor.GraphVisitor;

import java.util.ArrayList;
import java.util.List;

public class ConnectorNode extends GraphNode{
	
	protected KeywordNode keywordNode;
	protected List<ConnectorNode> nextLayerNodes;
	
	public ConnectorNode() {
		super();
		this.keywordNode = null;
		this.nextLayerNodes = new ArrayList<>();
	}
	
	// keywordNode
	public void setKeywordNode(KeywordNode keywordNode) { this.keywordNode = keywordNode; }
	public KeywordNode getKeywordNode() { return this.keywordNode; }

	// nextLayerNodes
	public void addNextLayerNode(ConnectorNode node) { this.nextLayerNodes.add(node); }
	public List<ConnectorNode> getNextLayerNodes() { return this.nextLayerNodes; }

	// visit
	@Override
	public void visit(GraphVisitor visitor) {
		for(GraphNode childNode: nextLayerNodes){
			childNode.visit(visitor);
		}
		
		if (this.keywordNode != null) { this.keywordNode.visit(visitor); }
		visitor.visit(this);
	}
}

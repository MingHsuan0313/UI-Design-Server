package com.selab.uidesignserver.graph;

import com.selab.uidesignserver.graph.visitor.GraphVisitor;

public class PrimitiveDataTypeNode extends ConnectorNode{
	
	private DataTypeNode dataTypeNode;
	
	// dataTypeNode
	public void setDataTypeNode(DataTypeNode dataTypeNode) { this.dataTypeNode = dataTypeNode; }
	public DataTypeNode getDataTypeNode() { return this.dataTypeNode; }

	@Override
	public void visit(GraphVisitor visitor) {
		if (this.dataTypeNode != null) { this.dataTypeNode.visit(visitor); }
		if (this.keywordNode != null) { this.keywordNode.visit(visitor); }
		visitor.visit(this);
	}
}

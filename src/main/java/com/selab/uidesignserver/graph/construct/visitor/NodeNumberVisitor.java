package com.selab.uidesignserver.graph.construct.visitor;

import com.selab.uidesignserver.graph.*;
import com.selab.uidesignserver.graph.visitor.GraphVisitor;

public class NodeNumberVisitor extends GraphVisitor{

	@Override
	public void visit(GraphNode node) {
		// handle current node
		if (node instanceof ConnectorNode) {
			// connector node
			node.set_subgraph_ConnectorNode_num(1);
			node.set_subgraph_DataTypeNode_num(0);
			node.set_subgraph_Keywords_num(0);
		} else if (node instanceof DataTypeNode) {
			// data type node
			node.set_subgraph_ConnectorNode_num(0);
			node.set_subgraph_DataTypeNode_num(1);
			node.set_subgraph_Keywords_num(0);
		} else if (node instanceof KeywordNode) {
			// keyword node
			node.set_subgraph_ConnectorNode_num(0);
			node.set_subgraph_DataTypeNode_num(0);
			node.set_subgraph_Keywords_num(((KeywordNode)node).getKeywordList().size());
		} else {
			// ignore unknow node type
			System.err.println("[NodeNumberVisitor] Ignore Unknow Node Type: " + node.getClass().getName());
		}
		
		// handle child node
		if (node instanceof ConnectorNode) {
			for (GraphNode childNode : ((ConnectorNode)node).getNextLayerNodes()) {
				sumNodeNum(node, childNode);
			}
			if (((ConnectorNode)node).getKeywordNode() != null) {
				sumNodeNum(node, ((ConnectorNode)node).getKeywordNode());
			}
		}
		if (node instanceof PrimitiveDataTypeNode) {
			sumNodeNum(node, ((PrimitiveDataTypeNode)node).getDataTypeNode());
		}
	}
	
	private void sumNodeNum(GraphNode node, GraphNode childNode) {
		node.set_subgraph_ConnectorNode_num(node.get_subgraph_ConnectorNode_num() + childNode.get_subgraph_ConnectorNode_num());
		node.set_subgraph_DataTypeNode_num(node.get_subgraph_DataTypeNode_num() + childNode.get_subgraph_DataTypeNode_num());
		node.set_subgraph_Keywords_num(node.get_subgraph_Keywords_num() + childNode.get_subgraph_Keywords_num());
	}
	
}

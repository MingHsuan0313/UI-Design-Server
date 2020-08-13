package com.selab.uidesignserver.graph.construct.visitor;

import com.selab.uidesignserver.graph.ConnectorNode;
import com.selab.uidesignserver.graph.GraphNode;
import com.selab.uidesignserver.graph.PrimitiveDataTypeNode;
import com.selab.uidesignserver.graph.visitor.GraphVisitor;

public class SubgraphElasticityVisitor extends GraphVisitor{

	@Override
	public void visit(GraphNode node) {
		double childEla = 0.0;
		if (node instanceof ConnectorNode) {
			ConnectorNode connectorNode = (ConnectorNode)node;
			for (GraphNode childNode : connectorNode.getNextLayerNodes()) {
				childEla += childNode.get_subgraph_elasticity();
			}
			if (connectorNode.getKeywordNode() != null) { childEla += connectorNode.getKeywordNode().get_subgraph_elasticity(); }	
		}
		
		if (node instanceof PrimitiveDataTypeNode) {
			PrimitiveDataTypeNode PDTNode = (PrimitiveDataTypeNode)node;
			if (PDTNode.getDataTypeNode() != null) { childEla += PDTNode.getDataTypeNode().get_subgraph_elasticity(); }	
		}
		
		node.set_subgraph_elasticity(node.get_node_elasticity() + childEla);
	}
}

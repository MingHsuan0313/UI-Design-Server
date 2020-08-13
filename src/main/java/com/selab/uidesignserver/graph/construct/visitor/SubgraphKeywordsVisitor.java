package com.selab.uidesignserver.graph.construct.visitor;

import com.selab.uidesignserver.graph.ConnectorNode;
import com.selab.uidesignserver.graph.GraphNode;
import com.selab.uidesignserver.graph.KeywordNode;
import com.selab.uidesignserver.graph.visitor.GraphVisitor;

public class SubgraphKeywordsVisitor extends GraphVisitor{

	@Override
	public void visit(GraphNode node) {
		// handle keyword node
		if (node instanceof KeywordNode) {
			KeywordNode keywordNode = (KeywordNode)node;
			node.get_subgraph_keywords().addAll((keywordNode.getKeywordList()));
		}
		
		// handle child node
		if (node instanceof ConnectorNode) {
			for (GraphNode childNode : ((ConnectorNode)node).getNextLayerNodes()) {
				addSubgraphKeywords(node, childNode);
			}
			if (((ConnectorNode)node).getKeywordNode() != null) {
				addSubgraphKeywords(node, ((ConnectorNode)node).getKeywordNode());
			}
		}
	}
	
	private void addSubgraphKeywords(GraphNode node, GraphNode childNode) {
		node.get_subgraph_keywords().addAll(childNode.get_subgraph_keywords());
	}

}

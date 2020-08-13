package com.selab.uidesignserver.graph.visitor;

import com.selab.uidesignserver.graph.GraphNode;
import com.selab.uidesignserver.graph.KeywordNode;

public class SumSubgraphKeywordsElaVisitor extends GraphVisitor{
	
	private double result;
	
	public double getResult() { return this.result; }
	
	/**
	 * Usage: <br>
	 * new(), visit(root), getResult():double
	 */
	public SumSubgraphKeywordsElaVisitor() {
		this.result = 0.0;
	}
	
	@Override
	public void visit(GraphNode node) {
		// handle keyword node
		if (node instanceof KeywordNode) {
			KeywordNode keywordNode = (KeywordNode)node;
			for (String keyword : keywordNode.getKeywordList()) {
				result += keywordNode.getElasticity(keyword);
			}
		}
	}
	
}

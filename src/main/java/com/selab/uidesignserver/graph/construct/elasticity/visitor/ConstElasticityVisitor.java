package com.selab.uidesignserver.graph.construct.elasticity.visitor;

import com.selab.uidesignserver.config.Config;
import com.selab.uidesignserver.graph.*;

public class ConstElasticityVisitor extends ElasticityVisitor{
	
	private double keywordEla;
	private double dataTypeEla;
	private double connectEla;
	
	public ConstElasticityVisitor() {
		this.keywordEla = Config.ELASTICITY_KEYWORD;
		this.dataTypeEla = Config.ELASTICITY_DATATYPE;
		this.connectEla = Config.ELASTICITY_CONNECTOR;
	}

	@Override
	public void visit(GraphNode node) {
		if (node instanceof ConnectorNode) { 
			((ConnectorNode)node).set_node_elasticity(this.connectEla);
		} else if (node instanceof DataTypeNode) {
			((DataTypeNode)node).set_node_elasticity(this.dataTypeEla);
		} else if (node instanceof KeywordNode) {
			KeywordNode keywordNode = (KeywordNode) node;
			double node_elasticity = 0.0;
			for (String keyword : keywordNode.getKeywordList()) {
				double keyword_elasticity = this.keywordEla;
				keywordNode.setElasticity(keyword, keyword_elasticity);
				node_elasticity += keyword_elasticity;
			}
			keywordNode.set_node_elasticity(node_elasticity);
		}
	}

	@Override
	public ConstElasticityVisitor initRootNode(ServiceNode serviceNode) {
		// do nothing
		return this;
	}

	@Override
	public void terminate() {
		// do nothing
	}
	
}

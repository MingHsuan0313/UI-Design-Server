package com.selab.uidesignserver.graph.construct.elasticity.visitor;

import com.selab.uidesignserver.graph.*;
import com.selab.uidesignserver.util.AHP;

import java.util.Arrays;

public class AHPElasticityVisitor extends ElasticityVisitor{
	
	private double keywordEla;
	private double dataTypeEla;
	private double connectEla;
	
	public AHPElasticityVisitor() {
		// { keyword-datatype, datatype-connector, datatype-connector }
		double[] pairwiseComparison = new double[]{10, 10, 1};
		int len = 3;
		
		// AHP
		double[][] pairwiseComparisonMatirx = AHP.toPairwiseComparisonMatirx(pairwiseComparison, len);
		double[] eigenVector = AHP.calculateEigenVector(pairwiseComparisonMatirx);
		
		// normalize 
		double[] elasticity = new double[len];
		double scale = 1.0 / Arrays.stream(eigenVector).min().getAsDouble();
		for(int i = 0; i < elasticity.length; i++){
			elasticity[i] = eigenVector[i] * scale;
		}
		
		// set elasticity
		this.keywordEla = elasticity[0];
		this.dataTypeEla = elasticity[1];
		this.connectEla = elasticity[2];
	}
	
	@Override
	public AHPElasticityVisitor initRootNode(ServiceNode serviceNode) {
		// do nothing in AHP
		return this;
	}

	@Override
	public void terminate() {
		// do nothing in AHP
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

}

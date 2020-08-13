package com.selab.uidesignserver.graph;

import com.selab.uidesignserver.graph.visitor.GraphVisitor;

import java.util.ArrayList;
import java.util.List;

public abstract class GraphNode {

	protected String nodeName;
	
	// ConstElasticityVisitor or LDAElasticityVisitor
	protected double node_elasticity;
	
	// SubgraphElasticityVisitor
	protected double subgraph_elasticity;
	
	// NodeNumberVisitor
	protected int subgraph_ConnectorNode_num;
	protected int subgraph_Keywords_num;
	protected int subgraph_DataTypeNode_num;
	
	// SubgraphKeywordsVisitor
	protected List<String> subgraph_keywords;
	
	public GraphNode(){
		this.nodeName = null;
		this.node_elasticity = 0.0;
		this.subgraph_elasticity = 0.0;
		this.subgraph_ConnectorNode_num = 0;
		this.subgraph_Keywords_num = 0;
		this.subgraph_DataTypeNode_num = 0;
		this.subgraph_keywords = new ArrayList<String>();
	}

	// nodeName
	public void setNodeName(String nodeName) { this.nodeName = nodeName; }
	public String getNodeName() { return this.nodeName; }

	// node elasticity
	public void set_node_elasticity(double value) { this.node_elasticity = value; }
	public double get_node_elasticity() { return this.node_elasticity; }
	
	// subgraph elasticity
	public void set_subgraph_elasticity(double value) { this.subgraph_elasticity = value; }
	public double get_subgraph_elasticity() { return this.subgraph_elasticity; }
	
	// subgraph_ConnectorNode_num
	public void set_subgraph_ConnectorNode_num(int number) { this.subgraph_ConnectorNode_num = number; }
	public int get_subgraph_ConnectorNode_num() { return this.subgraph_ConnectorNode_num; }

	// subgraph_Keywords_num
	public void set_subgraph_Keywords_num(int number) { this.subgraph_Keywords_num = number; }
	public int get_subgraph_Keywords_num() { return this.subgraph_Keywords_num; }
	
	// subgraph_DataTypeNode_num
	public void set_subgraph_DataTypeNode_num(int number) { this.subgraph_DataTypeNode_num = number; }
	public int get_subgraph_DataTypeNode_num() { return this.subgraph_DataTypeNode_num; }
	
	// subgraph_keywords
	public void set_subgraph_keywords(List<String> subgraph_keywords) { this.subgraph_keywords = subgraph_keywords; }
	public List<String> get_subgraph_keywords() { return this.subgraph_keywords; }
	
	// visit
	/**
	 * DFS, Post-Order Traversal <br>
	 * @param visitor
	 */
	public abstract void visit(GraphVisitor visitor);
	
	public String getNodeInfo() {
		StringBuilder nodeInfo = new StringBuilder(String.format("---------- Node Info ----------%n"));
		nodeInfo.append(String.format("nodeType = %s%n", this.getClass().getName()));
		nodeInfo.append(String.format("nodeName = %s%n", nodeName));
		nodeInfo.append(String.format("node_elasticity = %f%n", node_elasticity));
		return nodeInfo.toString().trim();
	}
	
	@Override
	public String toString() {
		StringBuilder allInfo = new StringBuilder();
		allInfo.append(String.format("%s%n", getNodeInfo()));
		allInfo.append(String.format("subgraph_elasticity = %f%n", subgraph_elasticity));
		allInfo.append(String.format("subgraph_ConnectorNode_num = %d%n", subgraph_ConnectorNode_num));
		allInfo.append(String.format("subgraph_Keywords_num = %d%n", subgraph_Keywords_num));
		allInfo.append(String.format("subgraph_DataTypeNode_num = %d%n", subgraph_DataTypeNode_num));
		allInfo.append(String.format("subgraph_keywords = %s%n", subgraph_keywords));
		return allInfo.toString().trim();
	}
}

package com.selab.uidesignserver.graph;

import java.util.HashSet;
import java.util.Set;

public class ComplexDataTypeNode extends ConnectorNode{
	
	private HashSet<String> types;

	public  ComplexDataTypeNode() { 
		this.types = new HashSet<>(); 
	}
	
	// type
	public void addType(String type) { this.types.add(type); }
	public Set<String> getTypes() { return this.types; }

}

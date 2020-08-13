package com.selab.uidesignserver.graph.construct;

import com.selab.uidesignserver.graph.*;
import com.selab.uidesignserver.graph.alignment.distance.datatype.DataTypeTableHandler;
import com.selab.uidesignserver.graph.construct.string.StringProcessor;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.wsdl.*;
import javax.wsdl.extensions.schema.Schema;
import java.util.*;

public class ServiceGraphBuilder {
	
	private StringProcessor stringProcessor;
	private Map<String,Node> typeMap;
	
	public ServiceGraphBuilder(){
		this.stringProcessor = new StringProcessor();
	}
	
	@SuppressWarnings("unchecked")
	public ServiceNode constructServiceGraph(Definition definition){
		// initial typeMap
		fetchTypes(definition);
		
		// build service layer
		String serviceName = ((Service)definition.getAllServices().values().iterator().next()).getQName().getLocalPart();
		ServiceNode serviceNode = new ServiceNode();
		serviceNode.setNodeName(serviceName);
		serviceNode.setKeywordNode(new KeywordNode());
		serviceNode.getKeywordNode().setKeywords(stringProcessor.parseString(serviceNode.getNodeName()));
		
		for(PortType portType : (Collection<PortType>)definition.getAllPortTypes().values()){
			for(Operation op : (List<Operation>)portType.getOperations()){
				// build operation layer
				OperationNode operationNode = new OperationNode();
				operationNode.setNodeName(op.getName());
				operationNode.setKeywordNode(new KeywordNode());
				operationNode.getKeywordNode().setKeywords(stringProcessor.parseString(operationNode.getNodeName()));
				serviceNode.addNextLayerNode(operationNode);
				
				// build message layer
				Message inputMsg = op.getInput().getMessage();
				Message outputMsg = op.getOutput().getMessage();
				MessageNode inputMessageNode = new MessageNode();
				MessageNode outputMessageNode = new MessageNode();
				inputMessageNode.setNodeName(inputMsg.getQName().getLocalPart());
				outputMessageNode.setNodeName(outputMsg.getQName().getLocalPart());
				operationNode.setInputMassageNode(inputMessageNode);
				operationNode.setOutputMassageNode(outputMessageNode);
				operationNode.addNextLayerNode(inputMessageNode);
				operationNode.addNextLayerNode(outputMessageNode);
				
				// build CDT graph
				addCDT(inputMessageNode, inputMsg);
				addCDT(outputMessageNode, outputMsg);
			}
		}
		return serviceNode ;
	}
	
	@SuppressWarnings("unchecked")
	private void addCDT(MessageNode messageNode, Message message) {
		// initial keyword node and add layer
		ComplexDataTypeNode CDTNode = new ComplexDataTypeNode();
		CDTNode.setKeywordNode(new KeywordNode());
		CDTNode.getKeywordNode().setKeywords(new ArrayList<String>());
		messageNode.addNextLayerNode(CDTNode);
		
		// build CDT
		for(Part part : (Collection<Part>)message.getParts().values()){
			// add part name to CDT word
			CDTNode.getKeywordNode().getKeywordList().addAll(stringProcessor.parseString(part.getName()));
			
			String type = part.getElementName() == null 
					? part.getTypeName().getLocalPart()
					: part.getElementName().getLocalPart();
					
			if (type != null) {
				Set<String> keywordSet = new HashSet<String>();
				Set<String> visitedType = new HashSet<String>();
				visitedType.add(type);
				CDTNode.addType(type);
				buildCDTGraph(CDTNode, type, keywordSet, null, visitedType);
			}
		}
	}
	
	private void buildCDTGraph(
			ComplexDataTypeNode complexDataTypeNode,
			String dataType,
			Set<String> keywords,
			Node pNode, 
			Set<String> visitedType) {
		
		Node parentNode = dataType == null 
				? pNode
				: this.typeMap.get(dataType);
		
		if (parentNode != null) {
				NodeList childNodes = parentNode.getChildNodes();
				for(int i = 0; i < childNodes.getLength(); i++) {
					Node node = childNodes.item(i);
					String nodeName = node.getNodeName();
					if (	nodeName.contains("element") 
							|| nodeName.contains("attribute") 
							|| (	nodeName.contains("restriction") 
									&& !getNodeAttributeValue(node, "base").contains("Array"))) {
						
						String type = "", name = "";
						if (nodeName.contains("element") 
							|| (	nodeName.contains("restriction") 
									&& !getNodeAttributeValue(node, "base").contains("Array"))) {
							if (nodeName.contains("restriction")){
								name = getNodeAttributeValue(node.getParentNode(), "name");
								type = getNodeAttributeValue(node, "base");
							} else {
								name = getNodeAttributeValue(node, "name");
								type = getNodeAttributeValue(node, "type");
							}
							if (!type.isEmpty() && type.contains(":")) {
								type = type.split(":")[1];	
							}	
						}
						
						Set<String> thisKeywords = new HashSet<String>();
						thisKeywords.addAll(stringProcessor.parseString(name));
						thisKeywords.addAll(keywords);
						Set<String> hasVisitedType = new HashSet<String>();
						
						if (visitedType.contains(type)) {
							if (DataTypeTableHandler.getInstance().findCategory(type) != null) {
								addPDT(complexDataTypeNode, thisKeywords, type);
							}
							continue;
						} else {
							hasVisitedType.add(type);
							hasVisitedType.addAll(visitedType);
						}
						
						if (type.equals("")) {
							//addPDT(complexDataTypeNode, thisKeywords, type);
						} else if (DataTypeTableHandler.getInstance().findCategory(type) != null) {
							addPDT(complexDataTypeNode, thisKeywords, type);			
						} else if (type == dataType) {
							addPDT(complexDataTypeNode, thisKeywords, type);
						} else {				
							buildCDTGraph(complexDataTypeNode, type, thisKeywords, null, hasVisitedType);
						}
					} else {
						buildCDTGraph(complexDataTypeNode, null, keywords, node, visitedType);
					}
				}
		}
	}

	private void addPDT(ComplexDataTypeNode complexDataTypeNode, Set<String> thisKeywords, String type) {
		PrimitiveDataTypeNode primitiveDataTypeNode = new PrimitiveDataTypeNode();
		primitiveDataTypeNode.setKeywordNode(new KeywordNode());
		primitiveDataTypeNode.getKeywordNode().setKeywords(new ArrayList<String>(thisKeywords));
		primitiveDataTypeNode.setDataTypeNode(new DataTypeNode());
		primitiveDataTypeNode.getDataTypeNode().setType(type);
		complexDataTypeNode.addNextLayerNode(primitiveDataTypeNode);
	}
	
	
	@SuppressWarnings("unchecked")
	private void fetchTypes(Definition definition) {
		this.typeMap = new HashMap<String,Node>();
		Types types = definition.getTypes();
		if (types != null) {
			for(Schema schema : (List<Schema>)types.getExtensibilityElements()) {
				String prefix = schema.getElement().getPrefix(); // xsd or s
				prefix = (prefix.length() == 0 || prefix == null) ? "" : prefix + ":";
				
				NodeList complexTypeList = schema.getElement().getElementsByTagName(prefix + "complexType");
				NodeList simpleTypeList = schema.getElement().getElementsByTagName(prefix + "simpleType");
				NodeList elementList = schema.getElement().getElementsByTagName(prefix + "element");
				
				parseNodeList(complexTypeList);
				parseNodeList(simpleTypeList);
				parseNodeList(elementList);
			}
		}
	}
	
	private void parseNodeList(NodeList nodeList) {
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			String name = getNodeAttributeValue(node, "name");
			if (name != null) {
				this.typeMap.put(name, node);
			}
			
		}
	}
	
	private String getNodeAttributeValue(Node node, String attributeName) {
		if (node.getAttributes().getLength() == 0) {
			return "";
		} else if (node.getAttributes().getNamedItem(attributeName) == null) {
			return "";
		} else {
			return node.getAttributes().getNamedItem(attributeName).getNodeValue();		
		}
	}
}

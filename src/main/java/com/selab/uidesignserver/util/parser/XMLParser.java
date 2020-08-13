package com.selab.uidesignserver.util.parser;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;


public class XMLParser {
	
	public XMLParser() {}

	public Document getDocument(String filePath) {
		Document document = null;

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.parse(filePath);
			document.getDocumentElement().normalize();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return document;
	}

	public ArrayList<Node> getSubNodes(Node parentNode, String nodeName) {
		ArrayList<Node> nodeContainer = new ArrayList<Node>();
		this.fetchNodes(parentNode, nodeName, nodeContainer);

		return nodeContainer;
	}

	private void fetchNodes(Node parentNode, String nodeName, ArrayList<Node> nodeContainer) {
		NodeList childNodes = parentNode.getChildNodes();
		Node node = null;
		for (int i = 0; i < childNodes.getLength(); i++) {
			node = childNodes.item(i);
			if (node.getNodeName().equals(nodeName)) {
				nodeContainer.add(node);
				continue;
			}

			if (node.hasChildNodes()) {
				fetchNodes(node, nodeName, nodeContainer);
			}
		}
	}

	public String getSubNodeText(Node parentNode, String nodeName) {
		String[] nodeTextContainer = new String[1];
		this.fetchNodeText(parentNode, nodeName, nodeTextContainer);

		return nodeTextContainer[0];
	}

	private void fetchNodeText(Node parentNode, String nodeName, String[] nodeTextContainer) {
		NodeList childNodes = parentNode.getChildNodes();
		Node node = null;
		for (int i = 0; i < childNodes.getLength(); i++) {
			if (!(nodeTextContainer[0] == null)) {
				break;
			}

			node = childNodes.item(i);
			if (node.getNodeName().trim().equals("#text") && !(node.getNodeValue().trim().isEmpty())) {
				if (node.getParentNode().getNodeName().equals(nodeName)) {
					nodeTextContainer[0] = node.getNodeValue();
				}
				break;
			}

			if (node.hasChildNodes()) {
				fetchNodeText(node, nodeName, nodeTextContainer);
			}
		}
	}

	public String getNodeText(Node parentNode) {
		NodeList childNodes = parentNode.getChildNodes();
		Node node = null;
		for (int i = 0; i < childNodes.getLength(); i++) {
			node = childNodes.item(i);
			if (node.getNodeName().trim().equals("#text") && !(node.getNodeValue().trim().isEmpty())) {
				return node.getNodeValue();
			}
		}

		return "";
	}

	public String getNodeAttributeValue(Node node, String attributeName) {
		if (node.getAttributes().getLength() == 0 || node.getAttributes().getNamedItem(attributeName) == null) {
			return null;
		}
		return node.getAttributes().getNamedItem(attributeName).getNodeValue();
	}

}

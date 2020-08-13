package com.selab.uidesignserver.graph.construct.importance.visitor;

import com.selab.uidesignserver.graph.GraphNode;
import com.selab.uidesignserver.graph.KeywordNode;
import com.selab.uidesignserver.graph.ServiceNode;
import com.selab.uidesignserver.model.matchMaking.TopocModelImportance;

public class TopicModelImportanceVisitor extends ImportanceVisitor {
	
	TopocModelImportance importance;
	
	String wsdlFileName;
	
	public TopicModelImportanceVisitor() {
		this.importance = TopocModelImportance.getInstance();
	}
	
	@Override
	public ImportanceVisitor initRootNode(ServiceNode serviceNode) {
		this.wsdlFileName = serviceNode.getFilename();
		return this;
	}

	@Override
	public void visit(GraphNode node) {
		if (node instanceof KeywordNode) {
			KeywordNode keywordNode = (KeywordNode)node;
			for (String keyword : keywordNode.getKeywordList()) {
				Double keywordImportance = this.importance.getImportance(keyword, this.wsdlFileName);
				keywordNode.setImportance(keyword, keywordImportance);
			}
		}
	}

	@Override
	public void terminate() {
		this.importance = null;
	}

}

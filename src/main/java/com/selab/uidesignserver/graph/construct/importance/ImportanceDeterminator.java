package com.selab.uidesignserver.graph.construct.importance;

import com.selab.uidesignserver.config.Config;
import com.selab.uidesignserver.graph.ServiceNode;
import com.selab.uidesignserver.graph.construct.importance.visitor.ImportanceVisitor;
import com.selab.uidesignserver.graph.construct.importance.visitor.OWAImportanceVisitor;
import com.selab.uidesignserver.graph.construct.importance.visitor.TopicModelImportanceVisitor;

public class ImportanceDeterminator {
	
	private static ImportanceDeterminator instance;
	
	private ImportanceVisitor importanceVisitor;
	
	public static synchronized ImportanceDeterminator getInstance() {
		if (instance == null) { instance = new ImportanceDeterminator(); }
		return instance;
	}
	
	public static synchronized void terminate() {
		if (instance != null) {
			instance.importanceVisitor.terminate();
			instance = null;
		}
	}
	
	private ImportanceDeterminator() {
		if ("OWA".equals(Config.importanceMode)) {
			this.importanceVisitor = new OWAImportanceVisitor();
		} else if ("TopicModel".equals(Config.importanceMode)) {
			this.importanceVisitor = new TopicModelImportanceVisitor();
		} else {
			throw new IllegalStateException("Unknow importanceMode: " + Config.importanceMode);
		}
	}
	
	public void detKeywordsImportance(ServiceNode service) {
		this.importanceVisitor.initRootNode(service);
		service.visit(importanceVisitor);
	}
	
}

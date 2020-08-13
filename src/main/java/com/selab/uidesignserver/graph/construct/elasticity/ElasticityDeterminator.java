package com.selab.uidesignserver.graph.construct.elasticity;

import com.selab.uidesignserver.config.Config;
import com.selab.uidesignserver.graph.ServiceNode;
import com.selab.uidesignserver.graph.construct.elasticity.visitor.*;
//import org.apache.log4j.Logger;
import org.apache.log4j.Logger;

public class ElasticityDeterminator {

	private final static Logger logger = Logger.getLogger(ElasticityDeterminator.class);
	
	private static ElasticityDeterminator instance;
	
	private ElasticityVisitor elasticityVisitor;
	
	public static synchronized ElasticityDeterminator getInstance() {
		if (instance == null) { instance = new ElasticityDeterminator(); }
		return instance;
	}
	
	public static synchronized void terminate() {
		if (instance != null) {
			instance.elasticityVisitor.terminate();
			instance = null;
		}
	}
	
	private ElasticityDeterminator() {
		if ("Const".equals(Config.elasticityMode)) {
			this.elasticityVisitor = new ConstElasticityVisitor();
		} else if ("AHP".equals(Config.elasticityMode)) {
			this.elasticityVisitor = new AHPElasticityVisitor();
		} else if ("LDA".equals(Config.elasticityMode)) {
			this.elasticityVisitor = new LDAElasticityVisitor();
		} else if ("OWA".equals(Config.elasticityMode)) {
			this.elasticityVisitor = new OWAElasticityVisitor();
		} else {
			logger.fatal("Unknow elasticityMode: " + Config.elasticityMode);
			throw new IllegalStateException("Unknow elasticityMode: " + Config.elasticityMode);
		}
	}
	
	public void detGraphElasticity(ServiceNode service) {
		this.elasticityVisitor.initRootNode(service);
		service.visit(elasticityVisitor);
	}
	
}

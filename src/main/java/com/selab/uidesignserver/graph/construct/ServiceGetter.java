package com.selab.uidesignserver.graph.construct;

import com.ibm.wsdl.Constants;
import com.selab.uidesignserver.config.Config;
import com.selab.uidesignserver.graph.ServiceNode;
import com.selab.uidesignserver.graph.construct.visitor.NodeNumberVisitor;
import com.selab.uidesignserver.graph.construct.visitor.SubgraphKeywordsVisitor;
import net.didion.jwnl.JWNL;
import org.apache.log4j.Logger;
import com.selab.uidesignserver.model.matchMaking.searchmodel.precision.ServiceRelevant;
import com.selab.uidesignserver.util.GraphUtil;
import com.selab.uidesignserver.util.parser.OwlsTCPaser;

import javax.wsdl.Definition;
import javax.wsdl.WSDLException;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class ServiceGetter {
	private final static Logger logger = Logger.getLogger(ServiceGetter.class);
	
	private OwlsTCPaser owlsTCPaser;
	private WSDLReader wsdlReader;
	private ServiceGraphBuilder serviceGraphBuilder;
	
	public ServiceGetter() {
		this.owlsTCPaser = new OwlsTCPaser();
		try {
			this.wsdlReader = WSDLFactory.newInstance().newWSDLReader();
			this.wsdlReader.setFeature(Constants.FEATURE_VERBOSE, false);
			this.wsdlReader.setFeature("javax.wsdl.importDocuments", true);
		} catch (WSDLException e) {
			logger.fatal("Exception:", e);
			throw new IllegalStateException(e);
		}
		this.serviceGraphBuilder = new ServiceGraphBuilder();
	}

	public List<ServiceNode> getAllServices(File wsdlDir) {
		List<ServiceNode> serviceList = new ArrayList<ServiceNode>();
        List<File> fileList = Arrays.asList(wsdlDir.listFiles());
        Collections.sort(fileList, new Comparator<File>() { // sort files by name
            @Override
            public int compare(File o1, File o2) {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        });
		for (File file : fileList) {
			if (file.isFile() && file.getName().toLowerCase().endsWith(".wsdl")) {
				logger.info(String.format("Construct Service Graph: %-10dFilename: %s", (serviceList.size() + 1), file.getName()));
				serviceList.add(getService(file));
			}
		}
		return serviceList;
	}
	
	public ServiceNode getService(File WSDL) {
		// parse wsdl to definition
		Definition def;
		try {
			def = this.wsdlReader.readWSDL(WSDL.getAbsolutePath());
		} catch (WSDLException e) {
			logger.fatal("Exception:", e);
			throw new IllegalStateException(e);
		}
		
		// construct service graph
		ServiceNode service = this.serviceGraphBuilder.constructServiceGraph(def);
		service.setFilename(WSDL.getName().toLowerCase());			
		
		// set relevant if any
		ServiceRelevant relevants = owlsTCPaser.getRelevantMap().get(service.getFilename());
		service.setRelevants(relevants);
		
		// run visitor
		service.visit(new NodeNumberVisitor());
		service.visit(new SubgraphKeywordsVisitor());
		
		return service;
	}
	
	public static void main(String args[]) {
		try {
			Locale.setDefault(Locale.ENGLISH);
			JWNL.initialize(new FileInputStream(Config.JWNL_properties_path));
		} catch (Exception e) {
			logger.fatal("Exception:", e);
			throw new IllegalStateException(e);
		}
		
		ServiceGetter serviceGetter = new ServiceGetter();
		ServiceNode serviceNode = serviceGetter.getService(new File("testdata/candidates/WsdlPool/Bankeraddress.wsdl"));
		String info = GraphUtil.getGraphHierarchy(serviceNode);
		System.out.println(info);
	}
	
}

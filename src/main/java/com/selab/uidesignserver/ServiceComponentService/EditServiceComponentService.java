package com.selab.uidesignserver.ServiceComponentService;

import org.springframework.stereotype.Service;

import freemarker.template.TemplateException;

import java.io.IOException;

import com.sun.source.tree.*;

@Service
public class EditServiceComponentService {
	public String projectName;
	public String className;	
	public String originalServiceID;
	public String newServiceCode;

	public EditServiceComponentService() {

	}

	public void initialize(String projectName, String className, String originalServiceID, String newServiceCode) {
		this.projectName = projectName;
		this.className = className;
		this.originalServiceID = originalServiceID;
		this.newServiceCode = newServiceCode;
	}

	public boolean editService() throws IOException, TemplateException {
		CodeGeneration codeGeneration = new CodeGeneration();
		codeGeneration.createTempServiceComponent(this.newServiceCode);
		NewCodeParser codeParser = new NewCodeParser();
		MethodTree method = codeParser.parseServiceComponent("./tempService");
		ClassTree klass = codeParser.parseJavaFile("./originalFile.java");
		if(codeParser.identifySignatureUnique(klass, method)) {
			//do add service
			codeGeneration.doGitVersionControl(this.projectName);
			this.addNewService(method);
		}
		else {
			// do override
			codeGeneration.doGitVersionControl(this.projectName);
			this.overrideService(klass, method);
		}
		return true;
	}

	public boolean overrideService(ClassTree originClass, MethodTree newService) {
		return true;
	}

	public boolean addNewService(MethodTree newService) {
		return true;
	}
}

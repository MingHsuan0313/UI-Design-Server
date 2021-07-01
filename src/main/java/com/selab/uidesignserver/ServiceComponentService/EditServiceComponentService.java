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
		System.out.println("initialize edit service");
		this.projectName = projectName;
		this.className = this.convertClassNameToFilePath(className);
		this.originalServiceID = originalServiceID;
		this.newServiceCode = newServiceCode;
		this.showInfo();
	}

    public String convertClassNameToFilePath(String className) {
        String result = "";
        System.out.println("start convert...");
        System.out.println(className);
        String[] stringLst = className.split("\\.");
        // String[] stringLst =
        // "ntu-csie-selab-inventorysystem-service-ItemHistoryService".split("-");
        int index;
        for (index = 0; index < stringLst.length - 1; index++) {
            System.out.println(index);
            result += stringLst[index] + "/";
        }
        result += stringLst[index] + ".java";
        return result;
    }


	public boolean editService() throws IOException, TemplateException {
		String projectBaseUrl = "/home/timhsieh/Desktop/Selab/UI-Team";
		CodeGeneration codeGeneration = new CodeGeneration();
		codeGeneration.createTempServiceComponent(this.newServiceCode);
		NewCodeParser codeParser = new NewCodeParser();
		MethodTree method = codeParser.parseServiceComponent("./temp/tempService.java");
		System.out.println(method.toString());
		System.out.println(projectBaseUrl + "/" + this.projectName + "/" + this.className);
		ClassTree klass = codeParser.parseJavaFile(projectBaseUrl + "/" + this.projectName + "/src/main/java/" + this.className);

		if(codeParser.identifySignatureUnique(klass, method)) {
			//do add service
			codeGeneration.doGitVersionControl(projectBaseUrl + "/" + this.projectName);
			this.addNewService(method);
		}
		else {
			// do override
			codeGeneration.doGitVersionControl(projectBaseUrl + "/" + this.projectName);
			this.overrideService(klass, method);
		}
		return true;
	}

	public boolean overrideService(ClassTree originClass, MethodTree newService) {
		System.out.println("override service in original project");
		return true;
	}

	public boolean addNewService(MethodTree newService) {
		System.out.println("add new service in original project");
		return true;
	}

	public void showInfo() {
		System.out.println("project name = " + this.projectName);
		System.out.println("class name = " + this.className);
		System.out.print("new code = " + this.newServiceCode);
		System.out.println("old service id = " + this.originalServiceID);
	}
}

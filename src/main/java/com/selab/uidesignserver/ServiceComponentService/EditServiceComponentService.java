package com.selab.uidesignserver.ServiceComponentService;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import freemarker.template.TemplateException;

import java.io.IOException;

import com.fasterxml.uuid.Generators;
import com.selab.uidesignserver.ServiceComponentService.visitors.*;
import com.selab.uidesignserver.dao.serviceComponent.ModifiedRecordRepository;
import com.selab.uidesignserver.entity.serviceComponent.ModifiedRecordTable;

import java.util.*;

import com.sun.source.tree.*;

@Service
public class EditServiceComponentService {
	public String projectName;
	public String className;
	public String originalServiceID;
	public String newServiceCode;
	public CodeGeneration codeGeneration;
	public NewCodeParser codeParser;
	public String result;

    @Autowired
    ModifiedRecordRepository modifiedRecordRepository;

	public EditServiceComponentService() {
		this.codeGeneration = new CodeGeneration();
		this.codeParser = new NewCodeParser();
	}

	public String getAbsoluteServiceComponentPath() {
		String projectBaseUrl = "/home/timhsieh/Desktop/Selab/UI-Team";
		return projectBaseUrl + "/" + this.projectName + "/src/main/java/" + this.className;
	}

	public String getAbsoluteProjectPath() {
		String projectBaseUrl = "/home/timhsieh/Desktop/Selab/UI-Team";
		return projectBaseUrl + "/" + this.projectName;
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

	public boolean saveModifiedRecord() throws IOException, TemplateException {
		// clean all records first
		this.modifiedRecordRepository.deleteAll();
		String serviceId = this.originalServiceID;
		this.codeGeneration.createTempServiceComponent(this.newServiceCode);
		String signature = this.getMethodSignature(this.codeParser.parseServiceComponent("./temp/tempService.java"));
		ModifiedRecordTable modifiedRecordTable = new ModifiedRecordTable(signature, serviceId);
		this.modifiedRecordRepository.save(modifiedRecordTable);

		return true;
	}

	public String getMethodSignature(MethodTree method) {
		
		return "";
	}

	public String editService() throws IOException, TemplateException {
		this.result = "";
		codeGeneration.createTempServiceComponent(this.newServiceCode);
		MethodTree method = codeParser.parseServiceComponent("./temp/tempService.java");
		CompilationUnitTree javaFile = codeParser
				.parseJavaFile(this.getAbsoluteServiceComponentPath());
		ClassTree klass = codeParser
				.parseClass(this.getAbsoluteServiceComponentPath());

		this.writePackageAndImports(javaFile);
		this.writeClassStart(klass);
		if (codeParser.identifySignatureUnique(klass, method)) {
			this.addNewService(klass, method);
		} else {
			this.overrideService(klass, method);
			this.saveModifiedRecord();
		}
		this.result += "}";
		this.codeGeneration.writeFile(this.getAbsoluteServiceComponentPath(), this.result);
		JSONObject result = codeGeneration.buildCode(this.getAbsoluteProjectPath());
		// success
		if(result.getInt("statusCode") == 1) {
			codeGeneration.doGitVersionControl(this.getAbsoluteProjectPath(), "edit", method.getName().toString());
			return this.result;
		}
		else {
			codeGeneration.doGitStash(this.getAbsoluteProjectPath());
			return "failed";
		}
	}

	public boolean writeClassStart(ClassTree klass) {
		List<AnnotationTree> annotationTreeContainer = new ArrayList<>();
		klass.accept(new AnnotationVisitor(), annotationTreeContainer);
		for(AnnotationTree annotationTree: annotationTreeContainer) {
			// System.out.println(annotationTree.toString());
			// System.out.println(annotationTree.getAnnotationType().toString());
			// System.out.println("******");
			this.result += annotationTree.toString() + "\n";
		}
		this.result += "public class " + klass.getSimpleName() + "{\n";
		return true;
	}

	public boolean writePackageAndImports(CompilationUnitTree javaFileTree) {
		this.result += "package " + javaFileTree.getPackageName() + ";\n";
		List<ImportTree> importTreeContainer = new ArrayList<>();
		javaFileTree.accept(new ImportVisitor(), importTreeContainer);
		for (ImportTree importTree : importTreeContainer) {
			this.result += importTree.toString();
		}
		this.result += "\n";
		return true;
	}

	public boolean overrideService(ClassTree originClass, MethodTree newService) {
		List<VariableTree> propertiesContainer = new ArrayList<>();
		List<MethodTree> methodsContainer = new ArrayList<>();
		originClass.accept(new MethodVisitor(), methodsContainer);
		originClass.accept(new VariableVisitor(), propertiesContainer);

		for (int index = 0; index < propertiesContainer.size(); index++) {
			VariableTree originalProperty = propertiesContainer.get(index);
			this.result += originalProperty.toString() + ";\n";
		}

		for (int index = 0; index < methodsContainer.size(); index++) {
			// System.out.println(methodsContainer.get(index));
			MethodTree originService = methodsContainer.get(index);
			if (this.codeParser.isSignatureTheSame(newService, originService)) {
				this.result += newService.toString();
			} else
				this.result += originService.toString();
			this.result += "\n";
		}
		return true;
	}

	public boolean addNewService(ClassTree originClass, MethodTree newService) {
		System.out.println("add new service in original project");
		List<VariableTree> propertiesContainer = new ArrayList<>();
		List<MethodTree> methodsContainer = new ArrayList<>();
		originClass.accept(new MethodVisitor(), methodsContainer);
		originClass.accept(new VariableVisitor(), propertiesContainer);

		for (int index = 0; index < propertiesContainer.size(); index++) {
			VariableTree originalProperty = propertiesContainer.get(index);
			this.result += originalProperty.toString() + ";\n";
		}

		for (int index = 0; index < methodsContainer.size(); index++) {
			// System.out.println(methodsContainer.get(index));
			MethodTree originService = methodsContainer.get(index);
			this.result += originService.toString();
			this.result += "\n";
		}
		this.result += newService.toString();
		this.result += "\n";

		return true;
	}

	public void showInfo() {
		System.out.println("project name = " + this.projectName);
		System.out.println("class name = " + this.className);
		System.out.print("new code = " + this.newServiceCode);
		System.out.println("old service id = " + this.originalServiceID);
	}
}

package com.selab.uidesignserver.ServiceComponentService;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.selab.uidesignserver.service.FreeMarkerUtil;

import freemarker.template.TemplateException;
import freemarker.template.Template;


public class CodeGeneration {
	public String projectName;
	public String className;
	public Mode mode;

	public enum Mode {
		EDIT_SERVICE {
		    public String toString() {
     			return "Edit Service";
		    }
		},

		ADD_SERVICE {
	        public String toString() {
        	    return "Add Service";
      		}
		}
	}

    // create service component temp java file
    public void createTempServiceComponent(String code) throws IOException, TemplateException {
        Template mainCodeTemplate = FreeMarkerUtil.getInstance().getTemplate("./TempServiceComponent.ftl");
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("code", code);
        Writer writer = new StringWriter();
        mainCodeTemplate.process(templateData, writer);
        this.writeFile("./temp/tempService.java", writer.toString());
    }

	public boolean genJavaFile(String code, String path) {
		return true;
	}

	public boolean doGitVersionControl(String projectName) {
		String baseProjectsUrl = "";
		String projectUrl = baseProjectsUrl + "/" + projectName;
		// git add .
		// git commit 
        System.out.println("do git version control");
		return true;
	}

	public boolean doGitBackward(String projectName) {
		String baseProjectsUrl = "";
		String projectUrl = baseProjectsUrl + "/" + projectName;
		// git stash
		// git check out HEAD^
        System.out.println("do git version backward");
		return true;
	}

    public void writeFile(String path, String text) {
        System.out.println("path heree : " + path);
        File fileObj = new File(path);
        if (fileObj.exists()) {
            try {
                FileWriter myWriter = new FileWriter(path);
                System.out.println(text);
                myWriter.write(text);
                myWriter.close();
                System.out.println("Successfully wrote to the file. EditorCode");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        } else {
            try {
                fileObj.createNewFile();
                FileWriter myFileWriter = new FileWriter(path);
                myFileWriter.write(text);
                myFileWriter.close();
                System.out.println("create file successfully");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("create not success");
            }
        }
    }
}
package com.selab.uidesignserver.ServiceComponentService;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.selab.uidesignserver.service.FreeMarkerUtil;

import org.json.JSONObject;

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

    public boolean doGitVersionControl(String projectName, String mode, String serviceName) {
        String baseProjectsUrl = "";
        String projectUrl = baseProjectsUrl + "/" + projectName;
        String executeString = "./script/doGitVersionControl.sh";
        executeString = executeString + " " + mode + " " + projectUrl + " " + serviceName;
        // git add .
        // git commit
        Process p;
        String log = "";
        String s;
        int commitStatus = 0; // 0: success 1: nothingToCommit
        try {
            p = Runtime.getRuntime().exec(executeString);
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null) {
                if (s.contains("nothing to commit")) {
                    System.out.println("nothing to commit");
                    commitStatus = 1;
                }
                log += s + "\n";
                System.out.println(s);
            }
            p.waitFor();
            p.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                // System.out.println(text);
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

    public void doGitStash() {
        System.out.println("Do Git Stash...");
        String s;
        Process p;
        String log = "";
        try {
            p = Runtime.getRuntime().exec("./script/doGitStash.sh");
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null) {
                log += s + "\n";
           }
            p.waitFor();
            p.destroy();
        } catch (Exception e) {
            System.out.println("build process exception");
            e.printStackTrace();
        }
    }

    public JSONObject buildCode() {
        // statusCode
        // -1 signature same
        // 0 build error
        // 1 build success
        String projectUrl = baseProjectsUrl + "/" + projectName;
        System.out.println("Bulding Code...");
        String s;
        Process p;
        String log = "";
        int statusCode = 0;
        try {
            p = Runtime.getRuntime().exec("./script/build.sh");
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null) {
                log += s + "\n";
                System.out.println(s);
                if (s.contains("BUILD SUCCESSFUL")) {
                    System.out.println("Build success");
                    statusCode = 1;
                } else if (s.contains("FAILED")) {
                    System.out.println("Build failed");
                    statusCode = 0;
                }
            }
            p.waitFor();
            p.destroy();
        } catch (Exception e) {
            System.out.println("build process exception");
            e.printStackTrace();
        }
        System.out.println("hello " + statusCode);
        JSONObject response = new JSONObject();
        response.put("log", log);
        response.put("statusCode", statusCode);
        return response;
    }
}
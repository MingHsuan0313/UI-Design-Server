package com.selab.uidesignserver.ServiceComponentService;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

import com.selab.uidesignserver.Configuration;
import com.selab.uidesignserver.service.FreeMarkerUtil;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class EditCodeService {
    public String filePath;
    public CodeParser codeParser;
    public String tempServiceComponentPath = "./temp/Temp.java";

    // @Autowired
    // RestTemplate restTemplate;

    public EditCodeService(String className) {
        this.filePath = this.convertClassNameToFilePath(className);
        this.filePath = Configuration.Base_Source_Code_DIR_PATH + this.filePath;
        this.codeParser = new CodeParser(this.filePath);
        System.out.println("Source Code File Path : " + this.filePath);
    }
    
    public JSONObject editServiceComponent(String code) throws IOException, TemplateException {
        // write editcode to ./temp/Temp.java
        this.createTempServiceComponent(code);
        JSONObject result = new JSONObject();

        // checking is the edited signature unique
        // if signature unique return complete code
        // else return ""
        String codeResult = this.codeParser.checkingSignatureUnique(tempServiceComponentPath,this.filePath);

        if(codeResult.length() == 0) {
            System.out.println("ggggg");
            result.put("statusCode",-1);
            result.put("log","Signature is the same");
        }
        else {
            result.put("statusCode",2);
            this.writeFile(this.filePath,codeResult);
        }
        return result;
    }

    public String addEditServiceComponent() {
        String code = this.codeParser.addEditedServiceComponent("./temp/Temp.java", this.filePath);
        if (code.length() > 0) {
            System.out.println("Write file...");
            System.out.println(this.filePath);
            this.writeFile(this.filePath, code);
        }
        return code;
    }

    // create service component temp java file
    public void createTempServiceComponent(String code) throws IOException, TemplateException {
        Template mainCodeTemplate = FreeMarkerUtil.getInstance().getTemplate("TempJavaTemplate.ftl");
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("code", code);
        Writer writer = new StringWriter();
        mainCodeTemplate.process(templateData, writer);
        this.writeFile(this.tempServiceComponentPath, writer.toString());
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

    public String updateEditedJavaFile(String code) {

        try {
            FileOutputStream output = new FileOutputStream(this.filePath);
            byte[] array = code.getBytes();
            output.write(array);
            output.close();
        } catch (Exception e) {
            e.getStackTrace();
        }

        return "success";
    }

    public JSONObject buildCode() {
        // statusCode
        // -1 signature same
        // 0 build error
        // 1 build success
        System.out.println("Bulding Code...");
        String s;
        Process p;
        String log = "";
        int statusCode = 0;
        try {
            p = Runtime.getRuntime().exec("./build.sh");
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
                    this.codeParser.recover();
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

    public String triggerJenkinsBuild() {
        String uri = "http://localhost:8080/buildByToken/build";
        String jenkinsToken = "SelabServiceGeneratorToken";
        String jenkinsJob = "Service Generator Pipeline";

        // uri = "http://localhost:8080/buildByToken/build?token=SelabServiceGeneratorToken&job=Service Generator Pipeline";
        uri = "http://localhost:8080/buildByToken/build?token=" + jenkinsToken + "&" + "job=" + jenkinsJob;
        RestTemplate restTemplate = new RestTemplate();
         
        String result = restTemplate.getForObject(uri, String.class);
        System.out.println(result);
        System.out.println("Trigger Jenkins Build");
        return result;
    }
}

package com.selab.uidesignserver.ServiceComponentService;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

import com.selab.uidesignserver.Configuration;
import com.selab.uidesignserver.service.FreeMarkerUtil;

import org.json.JSONObject;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class EditCodeService {
    public String filePath;
    public CodeParser codeParser;

    public EditCodeService(String className) {
        this.codeParser = new CodeParser();
        this.filePath = this.convertClassNameToFilePath(className);
        this.filePath = Configuration.Base_DIR_PATH + this.filePath;
        System.out.println("File Path : " + this.filePath);
    }
    
    public String addEditServiceComponent() {
        String code = this.codeParser.addEditedServiceComponent("./temp/Temp.java",this.filePath);
        System.out.println("final code string hereee");
        System.out.println(code);
        if(code.length() > 0) {
            System.out.println("Write file...");
           this.writeFile(this.filePath,code);
        }
        return code;
    }

    // create service component temp java file
    public void createTempServiceComponent(String code) throws IOException, TemplateException {
        String serviceCompoentTempDIR = Configuration.Temp_Service_DIR;
        Template mainCodeTemplate = FreeMarkerUtil.getInstance().getTemplate("MainTemplate.ftl");
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("code", code);
        Writer writer = new StringWriter();
        mainCodeTemplate.process(templateData, writer);
        this.writeFile("./temp/Temp.java", writer.toString());
    }

    public void writeFile(String path, String text) {
        System.out.println("path heree : " + path);
        File fileObj = new File(path);
        if (fileObj.exists()) {
            try {
                FileWriter myWriter = new FileWriter(path);
                myWriter.write(text);
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
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

    public String buildCode() {
        //statusCode
        // -1 signature same
        // 0 no thing happen
        // 1 build success
        // 2 build error
        System.out.println("Bulding Code...");
        String s;
        Process p;
        String log = "";
        int statusCode = 0;
        try {
            p = Runtime.getRuntime().exec("./buildService.sh");
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null) {
                log += s;
                System.out.println(s);
                if(s.contains("BUILD SUCCESSFUL")) {
                    statusCode = 1;
                }
                else if(s.contains("BUILD FAILED"))
                    statusCode = 2;
                // System.out.println("line: " + s);
            }
            p.waitFor();
            // System.out.println("exit: " + p.exitValue());
            // statusCode = p.exitValue();
            p.destroy();
        } catch (Exception e) {
        }
        JSONObject response = new JSONObject();
        response.put("log",log);
        response.put("statusCode",statusCode);
        return response.toString();
    }

}

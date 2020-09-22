package com.selab.uidesignserver.ServiceComponentService;

import java.io.*;

import com.selab.uidesignserver.Configuration;

public class EditCodeService {
    public String filePath;

    public EditCodeService(String className) {
        this.filePath = this.convertClassNameToFilePath(className);
        this.filePath = Configuration.Base_DIR_PATH + this.filePath;
        System.out.println("File Path : " + this.filePath);
    }

    public String convertClassNameToFilePath(String className) {
        String result = "";
        System.out.println("start convert...");
        System.out.println(className);
        String[] stringLst = className.split("\\.");
        // String[] stringLst = "ntu-csie-selab-inventorysystem-service-ItemHistoryService".split("-");
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
        String s;
        Process p;
        try {
            p = Runtime.getRuntime().exec("./buildService.sh");
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null)
                System.out.println("line: " + s);
            p.waitFor();
            System.out.println("exit: " + p.exitValue());
            p.destroy();
        } catch (Exception e) {
        }
        return "build code";
    }

}

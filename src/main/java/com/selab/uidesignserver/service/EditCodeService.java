package com.selab.uidesignserver.service;

import java.io.*;

import com.selab.uidesignserver.Configuration;

public class EditCodeService {
    public String fileName;

    public EditCodeService(String fileName) {
        this.fileName = fileName;    
    }

    public String updateEditedJavaFile(String code) {
        String filePath = Configuration.Base_Service_Projects_DIR_PATH + "/" + fileName;
        System.out.println(filePath);

        try {
            FileOutputStream output = new FileOutputStream(filePath);
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

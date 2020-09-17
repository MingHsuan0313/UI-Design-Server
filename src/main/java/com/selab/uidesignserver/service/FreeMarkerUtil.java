package com.selab.uidesignserver.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

@Service
public class FreeMarkerUtil {
    private static final Logger LOG = Logger.getLogger(FreeMarkerUtil.class.getName());
    private static FreeMarkerUtil instance = new FreeMarkerUtil();
    public static FreeMarkerUtil getInstance(){
        return FreeMarkerUtil.instance;
    }

    Configuration configuration = null;

    private FreeMarkerUtil(){
        this.configuration = new Configuration(Configuration.VERSION_2_3_28);
        try {
            configuration.setDirectoryForTemplateLoading(new File("src/main/java/com/selab/uidesignserver/template"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Template getTemplate(String templatePath){
        try {
            Template template = configuration.getTemplate(templatePath);
            return template;
        } catch (IOException e) {
            LOG.warning("Cannot find template");
            e.printStackTrace();
        }
        return null;
    }
}

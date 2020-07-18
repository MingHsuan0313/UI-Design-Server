package com.selab.uidesignserver.service;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class TableStrategy {
    public String getComponentHTML(JSONObject uicdl) throws IOException, TemplateException {

        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("headers",uicdl.getString("headers").split(" +"));
        Template template = FreeMarkerUtil.getInstance().getTemplate("table.ftl");

        Writer writer = new StringWriter();
        template.process(dataMap,writer);
        String htmlStr = writer.toString().trim().replaceAll(" +", " ")
                .replace("\n","")
                .replace("> ",">");

        return htmlStr;
    }
}

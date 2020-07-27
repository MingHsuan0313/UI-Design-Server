package com.selab.uidesignserver.service;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class DropdownStrategy {
    public String getComponentHTML(JSONObject uicdl) throws IOException, TemplateException {

        Map<String, Object> dataMap = new HashMap<>();


        dataMap.put("width",String.valueOf(uicdl.getInt("width")).replace(",", ""));
        dataMap.put("height",String.valueOf(uicdl.getInt("height")).replace(",", ""));
        dataMap.put("x",String.valueOf(uicdl.getInt("x")).replace(",", ""));
        dataMap.put("y",String.valueOf(uicdl.getInt("y")).replace(",", ""));
        dataMap.put("items",uicdl.getString("items").split(" +"));
        Template template = FreeMarkerUtil.getInstance().getTemplate("dropdown.ftl");

        Writer writer = new StringWriter();
        template.process(dataMap,writer);
        String htmlStr = writer.toString().trim().replaceAll(" +", " ")
                .replace("\n","")
                .replace("> ",">");

        return htmlStr;
    }
}

package com.selab.uidesignserver.service;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class InputgroupStrategy {
    public String getComponentHTML(JSONObject uicdl) throws IOException, TemplateException {

        InputgroupBuilder inputgroupBuilder = new InputgroupBuilder();
        JSONArray componentList = uicdl.getJSONArray("componentList");
        for(int i=0;i<componentList.length();i++){
            JSONObject component = componentList.getJSONObject(i);
            String type = component.getString("type");
            if(type.equals("text")){
                inputgroupBuilder.buildText(component.getString("text"));
            }
            else if(type.equals("button")){
                inputgroupBuilder.buildButton(component);
            }
            else if(type.equals("icon")){
                inputgroupBuilder.buildIcon(component);
            }
        }

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("items", inputgroupBuilder.createInputGroup());

        Template template = FreeMarkerUtil.getInstance().getTemplate("inputgroup.ftl");

        Writer writer = new StringWriter();
        template.process(dataMap,writer);
        String htmlStr = writer.toString().trim().replaceAll(" +", " ")
                .replace("\n","")
                .replace("> ",">");

        return htmlStr;
    }
}

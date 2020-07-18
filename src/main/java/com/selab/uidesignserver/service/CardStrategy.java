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

public class CardStrategy {
    public String getComponentHTML(JSONObject uicdl) throws IOException, TemplateException {
        CardBuilder cardBuilder = new CardBuilder();

        cardBuilder.buildHeader(uicdl.getString("header"));
        JSONArray componentList = uicdl.getJSONArray("componentList");
        for(int i=0;i<componentList.length();i++){
            JSONObject component = componentList.getJSONObject(i);
            String type = component.getString("type");
            if(type.equals("text")){
               cardBuilder.buildText(component);
            }
            else if(type.equals("button")){
                cardBuilder.buildButton(component);
            }
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("content", cardBuilder.createCard());
        Template template = FreeMarkerUtil.getInstance().getTemplate("card.ftl");

        Writer writer = new StringWriter();
        template.process(dataMap,writer);
        String htmlStr = writer.toString().trim().replaceAll(" +", " ")
                .replace("\n","")
                .replace("> ",">");

        return htmlStr;
    }
}

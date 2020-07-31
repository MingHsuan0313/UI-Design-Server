package com.selab.uidesignserver.service;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class BreadcrumbBuilder {

    String contentHTML="";
    String html="";
    JSONObject uicdl;
    BreadcrumbBuilder(JSONObject uicdl){
        this.uicdl = uicdl;
    }

    // Text and indicator is in bootstrap breadcrumb default
    void buildText(JSONObject component) throws IOException, TemplateException {
        if(component.getString("href").equals("")) {
            this.contentHTML += "<li class=\"breadcrumb-item\"><a>" + component.getString("text")+ "</a></li>";
        }
        else{
            this.contentHTML += "<li class=\"breadcrumb-item\"><a href=\"" + component.getString("href")+ "\">" + component.getString("text") + "</a></li>";
        }
    }


    String createBreadcrumb() throws IOException, TemplateException {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("content", this.contentHTML);

        dataMap.put("width",String.valueOf(uicdl.getInt("width")).replace(",", ""));
        dataMap.put("height",String.valueOf(uicdl.getInt("height")).replace(",", ""));
        dataMap.put("x",String.valueOf(uicdl.getInt("x")).replace(",", ""));
        dataMap.put("y",String.valueOf(uicdl.getInt("y")).replace(",", ""));
        Template template = FreeMarkerUtil.getInstance().getTemplate("breadcrumb.ftl");

        Writer writer = new StringWriter();
        template.process(dataMap,writer);
        String htmlStr = writer.toString().trim().replaceAll(" +", " ")
                .replace("\n","")
                .replace("> ",">");
        this.html = htmlStr;
        return this.html;
    }
}

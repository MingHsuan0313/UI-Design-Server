package com.selab.uidesignserver.service;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.json.JSONObject;

import javax.smartcardio.Card;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class CardBuilder {
    String headerHTML="";
    String contentHTML="";
    String html="";
    CardBuilder(){
    }
    void buildHeader(String header){
        this.headerHTML +=  "<div class=\"card-header\">" + header + "</div>";
    }

    void buildText(JSONObject component) throws IOException, TemplateException {
        TextStrategy textStrategy = new TextStrategy();
        String content = textStrategy.getComponentHTML(component);
        content = "<div>" + content + "</div>";
        this.contentHTML += content;
    }

    void buildButton(JSONObject component) throws IOException, TemplateException {
        ButtonStrategy buttonStrategy = new ButtonStrategy();
        String content = buttonStrategy.getComponentHTML(component);
        content = "<div>" + content + "</div>";
        this.contentHTML += content;
    }

    String createCard() throws IOException, TemplateException {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("header", this.headerHTML);
        dataMap.put("content", this.contentHTML);
        Template template = FreeMarkerUtil.getInstance().getTemplate("card.ftl");

        Writer writer = new StringWriter();
        template.process(dataMap,writer);
        String htmlStr = writer.toString().trim().replaceAll(" +", " ")
                .replace("\n","")
                .replace("> ",">");
        this.html = htmlStr;
        return this.html;
    }
}

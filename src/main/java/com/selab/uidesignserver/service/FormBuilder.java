package com.selab.uidesignserver.service;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class FormBuilder {

    PositionTransformer positionTransformer = new PositionTransformer();
    String contentHTML="";
    String html="";
    JSONObject uicdl;
    FormBuilder(JSONObject uicdl){
        this.uicdl = uicdl;
    }
    void buildInput(JSONObject component) throws IOException, TemplateException {
        InputStrategy inputStrategy = new InputStrategy();
        inputStrategy.setIsCompositeELement(true);
        String content = inputStrategy.getComponentHTML(component);
        content = "<div>" + content + "</div>";
        this.contentHTML += content;

    }

    void buildText(JSONObject component) throws IOException, TemplateException {
        TextStrategy textStrategy = new TextStrategy();
        textStrategy.setIsCompositeELement(true);
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

    String createForm() throws IOException, TemplateException {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("content", this.contentHTML);

        dataMap.put("width",String.valueOf(uicdl.getInt("width")).replace(",", ""));
        dataMap.put("height",String.valueOf(uicdl.getInt("height")).replace(",", ""));
        positionTransformer.transform(uicdl.getInt("x"),uicdl.getInt("y"));
        dataMap.put("x",positionTransformer.getTargetWidth());
        dataMap.put("y",positionTransformer.getTargetHeight());
        Template template = FreeMarkerUtil.getInstance().getTemplate("form.ftl");

        Writer writer = new StringWriter();
        template.process(dataMap,writer);
        String htmlStr = writer.toString().trim().replaceAll(" +", " ")
                .replace("\n","")
                .replace("> ",">");
        this.html = htmlStr;
        return this.html;
    }
}

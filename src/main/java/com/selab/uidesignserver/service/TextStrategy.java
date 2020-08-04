package com.selab.uidesignserver.service;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Service
public class TextStrategy {

    PositionTransformer positionTransformer = new PositionTransformer();
    boolean isCompositeElement = false;

    public void setIsCompositeELement(boolean res) {
        this.isCompositeElement = true;
    }

    public String getComponentHTML(JSONObject uicdl) throws IOException, TemplateException {

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("text", uicdl.getString("text"));
        dataMap.put("href", uicdl.getString("href"));
        if (isCompositeElement) {
            dataMap.put("x",String.valueOf(uicdl.getInt("x")).replace(",", ""));
            dataMap.put("y",String.valueOf(uicdl.getInt("y")).replace(",", ""));
            dataMap.put("isCompositeElement", "true");
        } else {
            positionTransformer.transform(uicdl.getInt("x"), uicdl.getInt("y"));
            dataMap.put("x", positionTransformer.getTargetWidth());
            dataMap.put("y", positionTransformer.getTargetHeight());
            dataMap.put("isCompositeElement", "false");
        }
        Template template = FreeMarkerUtil.getInstance().getTemplate("text.ftl");

        Writer writer = new StringWriter();
        template.process(dataMap, writer);
        String htmlStr = writer.toString().trim().replaceAll(" +", " ")
                .replace("\n", "")
                .replace("> ", ">");

        return htmlStr;
    }
}

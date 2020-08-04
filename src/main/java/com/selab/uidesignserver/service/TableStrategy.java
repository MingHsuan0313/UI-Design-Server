package com.selab.uidesignserver.service;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Service
public class TableStrategy {


    PositionTransformer positionTransformer = new PositionTransformer();

    public String getComponentHTML(JSONObject uicdl) throws IOException, TemplateException {

        Map<String, Object> dataMap = new HashMap<>();

        dataMap.put("headers",uicdl.getString("headers").split(" +"));
        dataMap.put("rows",uicdl.getString("rows").split(" +"));
        dataMap.put("width",uicdl.getInt("width"));
        dataMap.put("height",uicdl.getInt("height"));
        // height is sometimes not needed cuz table will distributed spaces equally
        positionTransformer.transform(uicdl.getInt("x"),uicdl.getInt("y"));
        dataMap.put("x",positionTransformer.getTargetWidth());
        dataMap.put("y",positionTransformer.getTargetHeight());
        Template template = FreeMarkerUtil.getInstance().getTemplate("table.ftl");

        Writer writer = new StringWriter();
        template.process(dataMap,writer);
        String htmlStr = writer.toString().trim().replaceAll(" +", " ")
                .replace("\n","")
                .replace("> ",">");

        return htmlStr;
    }
}

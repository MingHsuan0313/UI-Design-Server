package com.selab.uidesignserver.service;

import freemarker.template.TemplateException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class BreadcrumbStrategy {
    public String getComponentHTML(JSONObject uicdl) throws IOException, TemplateException {
        BreadcrumbBuilder breadcrumbBuilder = new BreadcrumbBuilder(uicdl);

        JSONArray componentList = uicdl.getJSONArray("componentList");
        for(int i=0;i<componentList.length();i++){
            JSONObject component = componentList.getJSONObject(i);
            String type = component.getString("type");
            if(type.equals("text")){
                breadcrumbBuilder.buildText(component);
            }
        }


        return breadcrumbBuilder.createBreadcrumb();
    }
}

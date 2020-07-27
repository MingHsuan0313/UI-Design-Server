package com.selab.uidesignserver.service;

import freemarker.template.TemplateException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class FormStrategy {
    public String getComponentHTML(JSONObject uicdl) throws IOException, TemplateException {
        FormBuilder formBuilder = new FormBuilder(uicdl);

        JSONArray componentList = uicdl.getJSONArray("componentList");
        for(int i=0;i<componentList.length();i++){
            JSONObject component = componentList.getJSONObject(i);
            String type = component.getString("type");
            if(type.equals("text")){
                formBuilder.buildText(component);
            }
            else if(type.equals("button")){
                formBuilder.buildButton(component);
            }
            else if(type.equals("input")){
                formBuilder.buildInput(component);
            }
        }


        return formBuilder.createForm();
    }
}

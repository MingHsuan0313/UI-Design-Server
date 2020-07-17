package com.selab.uidesignserver.service;

import com.selab.uidesignserver.model.PageComponent;
import com.selab.uidesignserver.model.UIComponent;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class HTMLGenerator{
    @Autowired
    TextStrategy textStrategy;

    private JSONObject pageUICDL;
    private List<JSONObject> compositeComponentsUICDL = new LinkedList<>();
    private List<String> compositeComponentsHTML = new LinkedList<>();

    public void setPageUICDL(String pageUICDL){
        this.pageUICDL = new JSONObject(pageUICDL);
        this.compositeComponentsHTML = new LinkedList<>();
    }
    public void parse() throws IOException, TemplateException {

        System.out.println(this.pageUICDL.get("componentList"));

        for(int i=0;i<this.pageUICDL.getJSONArray("componentList").length();i++){
            JSONObject component = this.pageUICDL.getJSONArray("componentList").getJSONObject(i);
            setStrategy(component.getString("type"), component);
        }

    }

    public void setStrategy(String type, JSONObject component) throws IOException, TemplateException {

        if(type.equals("text")){
            TextStrategy textStrategy = new TextStrategy();
            String htmlStr = textStrategy.getComponentHTML(component);
            compositeComponentsHTML.add(htmlStr);
        }
        else if(type.equals("button")){
            ButtonStrategy buttonStrategy = new ButtonStrategy();
            String htmlStr = buttonStrategy.getComponentHTML(component);
            compositeComponentsHTML.add(htmlStr);
        }
        else if(type.equals("icon")){
            IconStrategy iconStrategy = new IconStrategy();
            String htmlStr = iconStrategy.getComponentHTML(component);
            compositeComponentsHTML.add(htmlStr);
        }

    }
    public void getPageHTML(){
        System.out.println(compositeComponentsHTML);
    }

}

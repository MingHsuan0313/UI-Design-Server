package com.selab.uidesignserver.service;

import com.selab.uidesignserver.model.PageComponent;
import com.selab.uidesignserver.model.UIComponent;
import com.selab.uidesignserver.respository.TemplateDao;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@Service
public class HTMLGenerator{

    @Autowired
    TemplateDao templateDao;


    PositionTransformer positionTransformer = new PositionTransformer();

    private JSONObject pageUICDL;
    private List<String> compositeComponentsHTML = new LinkedList<>();

    public void setPageUICDL(String pageUICDL){
        this.pageUICDL = new JSONObject(pageUICDL);
        this.compositeComponentsHTML = new LinkedList<>();
    }

    public JSONObject getPageUICDL(){
        return this.pageUICDL;
    }

    public void parse() throws IOException, TemplateException, SQLException {

        if(!positionTransformer.isSet){
            int width = this.pageUICDL.getJSONObject("componentList").getInt("width");
            int height = this.pageUICDL.getJSONObject("componentList").getInt("height");
            positionTransformer.setSourceHeight(height);
            positionTransformer.setSourceWidth(width);
            positionTransformer.isSet = true;
        }

        for(int i=0;i<this.pageUICDL.getJSONObject("componentList").getJSONArray("componentList").length();i++){
            JSONObject component = this.pageUICDL.getJSONObject("componentList").getJSONArray("componentList").getJSONObject(i);
            setStrategy(component.getString("type"), component);
        }

    }

    public void setStrategy(String type, JSONObject component) throws IOException, TemplateException, SQLException {

        if(type.equals("text")){
            TextStrategy textStrategy = new TextStrategy();
            String htmlStr = textStrategy.getComponentHTML(component);
            compositeComponentsHTML.add(htmlStr);
            templateDao.addTemplate(component,htmlStr);

        }
        else if(type.equals("button")){
            ButtonStrategy buttonStrategy = new ButtonStrategy();
            String htmlStr = buttonStrategy.getComponentHTML(component);
            compositeComponentsHTML.add(htmlStr);
            templateDao.addTemplate(component,htmlStr);
        }
        else if(type.equals("icon")){
            IconStrategy iconStrategy = new IconStrategy();
            String htmlStr = iconStrategy.getComponentHTML(component);
            compositeComponentsHTML.add(htmlStr);
            templateDao.addTemplate(component,htmlStr);
        }
        else if(type.equals("dropdown")){
            DropdownStrategy dropdownStrategy = new DropdownStrategy();
            String htmlStr = dropdownStrategy.getComponentHTML(component);
            compositeComponentsHTML.add(htmlStr);
            templateDao.addTemplate(component,htmlStr);
        }
        else if(type.equals("table")){
            TableStrategy tableStrategy = new TableStrategy();
            String htmlStr = tableStrategy.getComponentHTML(component);
            compositeComponentsHTML.add(htmlStr);
            templateDao.addTemplate(component,htmlStr);
        }
        else if(type.equals("inputgroup")){
            InputgroupStrategy inputgroupStrategy = new InputgroupStrategy();
            String htmlStr = inputgroupStrategy.getComponentHTML(component);
            compositeComponentsHTML.add(htmlStr);
            templateDao.addTemplate(component,htmlStr);
        }
        else if(type.equals("card")){
            CardStrategy cardStrategy = new CardStrategy();
            String htmlStr = cardStrategy.getComponentHTML(component);
            compositeComponentsHTML.add(htmlStr);
            templateDao.addTemplate(component,htmlStr);
        }
        else if(type.equals("form")){
            FormStrategy formStrategy = new FormStrategy();
            String htmlStr = formStrategy.getComponentHTML(component);
            compositeComponentsHTML.add(htmlStr);
            templateDao.addTemplate(component,htmlStr);
        }
        else if(type.equals("breadcrumb")){
            BreadcrumbStrategy breadcrumbStrategy = new BreadcrumbStrategy();
            String htmlStr = breadcrumbStrategy.getComponentHTML(component);
            compositeComponentsHTML.add(htmlStr);
            templateDao.addTemplate(component,htmlStr);
        }
        else{
            System.out.println("No corresponding template for " + type);
        }

    }
    public List<String> getPageHTML(){
        return this.compositeComponentsHTML;
    }

}

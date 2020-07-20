package com.selab.uidesignserver.service;

import freemarker.template.TemplateException;
import org.json.JSONObject;

import java.io.IOException;

public class InputgroupBuilder {
    String html="";

    InputgroupBuilder(){
    }

    void buildText(String text){
        this.html += "<span class=\"input-group-text\">"+text+"</span>";
    }

    void buildButton(JSONObject component){
        this.html += "<span class=\"btn\">"+component.getString("text")+"</span>";
    }

    void buildIcon(JSONObject component){
        this.html += "<span class=\"btn\"><i class=\""+ component.getString("text") +"\"></i></span>";
    }

    String createInputGroup(){
        return this.html;
    }

}

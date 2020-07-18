package com.selab.uidesignserver.service;

import freemarker.template.TemplateException;
import org.json.JSONObject;

import javax.smartcardio.Card;
import java.io.IOException;

public class CardBuilder {
    String html="";

    CardBuilder(){
    }
    void buildHeader(String header){
        this.html +=  "<div class=\"card-header\">" + header + "</div>";
    }

    void buildText(JSONObject component) throws IOException, TemplateException {
        TextStrategy textStrategy = new TextStrategy();
        String htmlStr = textStrategy.getComponentHTML(component);
        this.html += htmlStr;
    }

    void buildButton(JSONObject component) throws IOException, TemplateException {
        ButtonStrategy buttonStrategy = new ButtonStrategy();
        String htmlStr = buttonStrategy.getComponentHTML(component);
        this.html += htmlStr;
    }

    String createCard(){
        return this.html;
    }
}

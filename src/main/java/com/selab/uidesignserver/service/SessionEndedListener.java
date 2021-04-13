package com.selab.uidesignserver.service;

import com.selab.uidesignserver.repositoryService.AuthenticationService;
import com.selab.uidesignserver.repositoryService.InternalRepresentationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionDeletedEvent;
import org.springframework.session.events.SessionDestroyedEvent;
import org.springframework.session.events.SessionExpiredEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SessionEndedListener implements ApplicationListener<ApplicationEvent> {

    @Autowired
    AuthenticationService authenticationService;

    @Override
    public void onApplicationEvent(ApplicationEvent event)
    {
        if(event instanceof SessionExpiredEvent){
            System.out.println("session expired");
            String userID = ((SessionExpiredEvent) event).getSession().getAttribute("userId").toString();
            List<String> openedThemeIDList = (List<String>)((SessionExpiredEvent) event).getSession().getAttribute("openedThemeIDList");
            authenticationService.logout(userID, openedThemeIDList);
            System.out.println(userID+ "'s session has timeout" );
        }
        else if(event instanceof SessionCreatedEvent){

        }

    }
}
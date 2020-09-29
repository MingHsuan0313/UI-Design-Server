package com.selab.uidesignserver.repositoryService;

import java.util.List;

import com.selab.uidesignserver.entity.ServiceComponentTable;

import org.json.JSONObject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

public interface ServiceComponentService {
    public List<ServiceComponentTable> getServiceComponents();
    
    // need to add classname and remove code part , with argument count
    public List<JSONObject> getServiceComponentsWithRestriction(int argumentCount);
    public String getServiceComponentCode(int serviceID);
    public List<String> getArgumentsByServiceID(int serviceID);
}
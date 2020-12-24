package com.selab.uidesignserver.repositoryService;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.google.gson.Gson;
import com.selab.uidesignserver.dao.serviceComponent.*;
import com.selab.uidesignserver.entity.serviceComponent.ServiceComponentTable;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ServiceComponentServiceImp implements ServiceComponentService {
    
    @Autowired
    ServiceComponentRepository serviceComponentRepository;
	
	@Autowired
	ArgumentRepository argumentRepository;
	
	@Autowired
	ClassRepository classRepository;

	@Override
	public List<ServiceComponentTable> getServiceComponents() {
		return serviceComponentRepository.getServiceComponents();
	}

	@Override
	@Transactional
	public List<JSONObject> getServiceComponentsWithRestriction(int argumentCount) {
		//connect
		List<ServiceComponentTable> serviceComponents = serviceComponentRepository
				.getServiceComponentsWithRestriction(argumentCount);
		List<JSONObject> serviceComponentResponse = new ArrayList<JSONObject>();
		for(int index = 0;index < serviceComponents.size();index++) {
			ServiceComponentTable serviceComponent = serviceComponents.get(index);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("serviceID",serviceComponent.getServiceID());
			jsonObject.put("name",serviceComponent.getName());
			jsonObject.put("className",serviceComponent.getKlass().getClassName());
			serviceComponentResponse.add(jsonObject);

		}
		return serviceComponentResponse;
	}

	@Override
	public String getServiceComponentCode(int serviceID) {
		return serviceComponentRepository.getCodeByServiceID(serviceID);
	}

	@Override
	public List<JSONObject> getArgumentsByServiceID(int serviceID) {
		return argumentRepository.getArgumentNamesByServiceID(serviceID);
	}
}

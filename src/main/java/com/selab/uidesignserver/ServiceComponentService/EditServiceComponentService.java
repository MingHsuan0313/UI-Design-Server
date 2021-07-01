package com.selab.uidesignserver.ServiceComponentService;

import org.springframework.stereotype.Service;
import com.sun.source.tree.*;

@Service
public class EditServiceComponentService {
	public String projectName;
	public String className;	
	public String originalServiceID;
	public String newServiceCode;

	public EditServiceComponentService() {

	}

	public void initialize(String projectName, String className, String originalServiceID) {

	}

	public boolean editService() {
		return true;
	}

	public boolean overrideService(MethodTree newService, ClassTree originClass) {
		return true;
	}

	public boolean addNewService(MethodTree newService, ClassTree originClass) {
		return true;
	}

}

package com.selab.uidesignserver.ServiceComponentService;

import org.springframework.stereotype.Service;

@Service
public class AddServiceComponentService {		
	public String newServiceClassCode;

	public void initialize(String newServiceClassCode) {
		this.newServiceClassCode = newServiceClassCode;
	}

	public boolean addService() {
		return true;
	}
}
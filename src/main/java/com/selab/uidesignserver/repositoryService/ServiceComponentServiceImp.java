package com.selab.uidesignserver.repositoryService;

import java.util.List;

import com.selab.uidesignserver.dao.ServiceComponentRepository;
import com.selab.uidesignserver.entity.ServiceComponent;

import org.springframework.beans.factory.annotation.Autowired;

public class ServiceComponentServiceImp implements ServiceComponentService {
    
    @Autowired
    ServiceComponentRepository serviceComponentRepository;

	@Override
	public List<ServiceComponent> findAll() {
        return serviceComponentRepository.findAll();
		// TODO Auto-generated method stub
	}

	@Override
	public ServiceComponent findById(String theId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findCodeByServiceID(int theID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ServiceComponent> test() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(ServiceComponent serviceComponent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(int theId) {
		// TODO Auto-generated method stub
		
    }

    
    
}

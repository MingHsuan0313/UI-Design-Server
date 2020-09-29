package com.selab.uidesignserver.repositoryService;

import java.util.List;

import com.selab.uidesignserver.dao.ServiceComponentRepository;
import com.selab.uidesignserver.entity.ServiceComponentTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceComponentServiceImp implements ServiceComponentService {
    
    @Autowired
    ServiceComponentRepository serviceComponentRepository;

	@Override
	public List<ServiceComponentTable> findAll() {
        return serviceComponentRepository.findAll();
		// TODO Auto-generated method stub
	}

	@Override
	public ServiceComponentTable findById(String theId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> findCodeByServiceID(int theID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ServiceComponentTable> test() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(ServiceComponentTable serviceComponent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(int theId) {
		// TODO Auto-generated method stub
		
    }

    
    
}

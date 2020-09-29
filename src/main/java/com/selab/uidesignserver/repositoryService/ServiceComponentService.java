package com.selab.uidesignserver.repositoryService;

import java.util.List;

import com.selab.uidesignserver.entity.ServiceComponentTable;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

public interface ServiceComponentService {
    public List<ServiceComponentTable> findAll();

    public ServiceComponentTable findById(String theId);
    public List<String> findCodeByServiceID(int theID);
    
    public List<ServiceComponentTable> test() ;
    public void save(ServiceComponentTable serviceComponent);
    
    public void deleteById(int theId);
}

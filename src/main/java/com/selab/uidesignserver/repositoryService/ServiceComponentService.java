package com.selab.uidesignserver.repositoryService;

import java.util.List;

import com.selab.uidesignserver.entity.ServiceComponent;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

@Service
public interface ServiceComponentService {
    public List<ServiceComponent> findAll();

    public ServiceComponent findById(String theId);
    public List<String> findCodeByServiceID(int theID);
    
    public List<ServiceComponent> test() ;
    public void save(ServiceComponent serviceComponent);
    
    public void deleteById(int theId);
}

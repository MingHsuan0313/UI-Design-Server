package com.selab.uidesignserver.dao;

import java.util.List;

import com.selab.uidesignserver.entity.ServiceComponentTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ServiceComponentRepository extends JpaRepository<ServiceComponentTable, Integer> {
    
    @Query(value = "SELECT * FROM ServiceComponent",nativeQuery = true)
    public List<ServiceComponentTable> getServiceComponents();
    
    @Query(value = "SELECT * FROM ServiceComponent n WHERE n.argumentcount = ?1",nativeQuery = true)
    public List<ServiceComponentTable> getServiceComponentsWithRestriction(int argumentCount);

    @Query(value = "SELECT n.code FROM ServiceComponent n WHERE n.serviceID = ?1",nativeQuery = true)
    public String getCodeByServiceID(int id);
}
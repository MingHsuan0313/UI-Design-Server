package com.selab.uidesignserver.dao;

import java.util.List;

import com.selab.uidesignserver.entity.ServiceComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ServiceComponentRepository extends JpaRepository<ServiceComponent, Integer> {
    
    @Query(value = "SELECT * FROM ServiceComponent",nativeQuery = true)
    public List<ServiceComponent> findByArgumentCount(int theArgCount);
    
    @Query(value = "SELECT n.code FROM ServiceComponent n WHERE n.serviceID = ?1",nativeQuery = true)
    // @Query(value = "SELECT * FROM ServiceComponent",nativeQuery = true)
    public List<String> findCodeByServiceID(int id);
}
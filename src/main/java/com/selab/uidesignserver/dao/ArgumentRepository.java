package com.selab.uidesignserver.dao;

import com.selab.uidesignserver.entity.ArgumentTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ArgumentRepository extends JpaRepository<ArgumentTable, Integer> {
    
    @Query(value = "SELECT n.name FROM Argument n WHERE n.serviceID = ?1",nativeQuery = true)
    public List<String> findArgumentNameByServiceID(int serviceID);
}

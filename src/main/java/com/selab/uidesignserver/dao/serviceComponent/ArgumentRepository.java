package com.selab.uidesignserver.dao.serviceComponent;

import com.selab.uidesignserver.entity.serviceComponent.ArgumentTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ArgumentRepository extends JpaRepository<ArgumentTable, Integer> {
    
    @Query(value = "SELECT n.name FROM Argument n WHERE n.serviceID = ?1",nativeQuery = true)
    public List<String> getArgumentNamesByServiceID(int serviceID);
}
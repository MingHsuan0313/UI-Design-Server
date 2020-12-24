package com.selab.uidesignserver.dao.serviceComponent;

import com.selab.uidesignserver.entity.serviceComponent.ArgumentTable;

import org.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ArgumentRepository extends JpaRepository<ArgumentTable, Integer> {
    
    @Query(value = "SELECT * FROM Argument n LEFT JOIN Argument_Annotation t ON n.argumentID = t.argumentID LEFT JOIN AnnotationType k ON t.annotationID = k.annotationID WHERE n.serviceID = ?1",nativeQuery = true)
    public List<JSONObject> getArgumentNamesByServiceID(int serviceID);
}
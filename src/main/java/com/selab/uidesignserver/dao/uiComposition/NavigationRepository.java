package com.selab.uidesignserver.dao.uiComposition;

import com.selab.uidesignserver.entity.uiComposition.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NavigationRepository extends JpaRepository<NavigationsTable, String> {
    @Query(value = "SELECT * FROM NDLs n WHERE n.projectID = ?1", nativeQuery = true)
    List<NavigationsTable> findNDLsTablesByProjectID(String projectID);

    @Query(value = "SELECT * FROM NDLs n WHERE n.pageID = ?1", nativeQuery = true)
    NavigationsTable findNDLsTablesByPageID(String pageID);
}
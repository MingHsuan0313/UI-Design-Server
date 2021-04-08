package com.selab.uidesignserver.dao.uiComposition;


import java.util.List;

import com.selab.uidesignserver.entity.uiComposition.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectsRepository extends JpaRepository<ProjectsTable, String> {
    @Query(value = "SELECT * FROM Projects p WHERE p.projectID = ?1", nativeQuery = true)
    ProjectsTable findProjectsTableByProjectID(String projectID);

    @Query(value = "SELECT * FROM Projects p WHERE p.projectName = ?1", nativeQuery = true)
    ProjectsTable findProjectsTableByProjectName(String projectName);

    @Query(value = "SELECT * FROM Projects p WHERE p.groupID = ?1", nativeQuery = true)
    List<ProjectsTable> findProjectsTablesByGroupID(String groupID);
}

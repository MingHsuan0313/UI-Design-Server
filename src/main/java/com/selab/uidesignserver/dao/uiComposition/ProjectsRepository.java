package com.selab.uidesignserver.dao.uiComposition;


import com.selab.uidesignserver.entity.uiComposition.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ProjectsRepository extends JpaRepository<ProjectsTable, String> {
    @Query(value = "SELECT p FROM Projects p WHERE p.projectID = ?1", nativeQuery = true)
    ProjectsTable findProjectsTableByProjectID(String projectID);

    @Query(value = "SELECT p FROM Projects p WHERE p.projectName = ?1", nativeQuery = true)
    ProjectsTable findProjectsTableByProjectName(String projectName);
}

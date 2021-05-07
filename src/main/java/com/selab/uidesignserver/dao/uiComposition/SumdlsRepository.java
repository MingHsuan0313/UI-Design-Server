package com.selab.uidesignserver.dao.uiComposition;

import com.selab.uidesignserver.entity.uiComposition.ProjectsTable;
import com.selab.uidesignserver.entity.uiComposition.SumdlsTable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SumdlsRepository extends JpaRepository<SumdlsTable, String> {
    @Query(value = "SELECT * FROM SUMDLs s WHERE s.projectID = ?1", nativeQuery = true)
    List<SumdlsTable> findSUMDLTablesByProjectID(String projectID);

    @Query(value = "SELECT * FROM SUMDLs s WHERE s.pageID = ?1", nativeQuery = true)
    SumdlsTable findSUMDLTablesByPageID(String pageID);

    @Query(value = "SELECT * FROM SUMDLs s WHERE s.themeID = ?1", nativeQuery = true)
    List<SumdlsTable> findSUMDLTableByThemeID(String themeID);
}

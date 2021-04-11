package com.selab.uidesignserver.dao.uiComposition;

import com.selab.uidesignserver.entity.uiComposition.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagesRepository extends JpaRepository<PagesTable, Integer>{
    @Query(value = "SELECT * FROM PageUICDLs p WHERE p.pageID = ?1", nativeQuery = true)
    PagesTable findPagesTableByPageID(String pageID);

    @Query(value = "SELECT * FROM PageUICDLs p WHERE p.projectID = ?1", nativeQuery = true)
    List<PagesTable> findPDLsTablesByProjectID(String projectID);

    @Query(value = "SELECT * FROM PageUICDLs p WHERE p.themeID = ?1", nativeQuery = true)
    List<PagesTable> findPDLsTablesByThemeID(String themeID);
}

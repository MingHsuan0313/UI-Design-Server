package com.selab.uidesignserver.dao.uiComposition;

import java.util.List;

import com.selab.uidesignserver.entity.uiComposition.ThemesTable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemesRepository extends JpaRepository<ThemesTable, String> {

    @Query(value = "SELECT * FROM Themes t WHERE t.projectID = ?1", nativeQuery = true)
    List<ThemesTable> findThemesTableByProjectID(String projectID);
}   

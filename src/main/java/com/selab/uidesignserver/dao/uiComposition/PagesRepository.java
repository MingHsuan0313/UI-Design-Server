package com.selab.uidesignserver.dao.uiComposition;

import com.selab.uidesignserver.entity.uiComposition.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PagesRepository extends JpaRepository<PagesTable, Integer>{
    @Query(value = "SELECT p FROM Pages p WHERE p.pageID = ?1", nativeQuery = true)
    PagesTable findPagesTableByPageID(String pageID);
}

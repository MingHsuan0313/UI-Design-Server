package com.selab.uidesignserver.dao.uiComposition;

import com.selab.uidesignserver.entity.uiComposition.ServiceListBpelJsonIR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ServiceListBpelJsonIRRepository extends JpaRepository<ServiceListBpelJsonIR, Integer> {
    @Query("select slbj " +
            "from ServiceListBpelJsonIR slbj " +
            "inner join ProjectsTable pj on pj.projectName = ?1 " +
            "inner join ThemesTable th on th.id = ?2 " +
            "inner join PagesTable pg on pg.id = ?3 " +
            "where slbj.selectorOperation = ?4")
    ServiceListBpelJsonIR findByScopeSelectorOperation(String projectName, String themeId, String pageId,
                                                       String selectorOperation);
}

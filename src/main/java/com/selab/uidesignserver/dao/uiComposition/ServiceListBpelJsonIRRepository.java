package com.selab.uidesignserver.dao.uiComposition;

import com.selab.uidesignserver.entity.uiComposition.ServiceListBpelJsonIR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ServiceListBpelJsonIRRepository extends JpaRepository<ServiceListBpelJsonIR, Integer> {
    @Query("select slbj " +
            "from ServiceListBpelJsonIR slbj " +
            "where slbj.projectsTable.projectName = ?1 " +
            "and slbj.themesTable.id = ?2 " +
            "and slbj.pagesTable.id = ?3 " +
            "and slbj.selector = ?4")
    ServiceListBpelJsonIR findByScopeSelector(String projectName, String themeId, String pageId,
                                              String selector);
}

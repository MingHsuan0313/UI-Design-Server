package com.selab.uidesignserver.dao.uiComposition;

import com.selab.uidesignserver.entity.uiComposition.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplatesRepository extends JpaRepository<TemplatesTable, Integer> {
    
}

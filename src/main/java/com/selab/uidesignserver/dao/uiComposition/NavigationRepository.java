package com.selab.uidesignserver.dao.uiComposition;

import com.selab.uidesignserver.entity.uiComposition.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NavigationRepository extends JpaRepository<NavigationsTable, String> {
    
}
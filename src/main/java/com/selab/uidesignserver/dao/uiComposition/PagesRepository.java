package com.selab.uidesignserver.dao.uiComposition;

import java.util.List;

import com.selab.uidesignserver.entity.uiComposition.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PagesRepository extends JpaRepository<PagesTable, Integer>{
}

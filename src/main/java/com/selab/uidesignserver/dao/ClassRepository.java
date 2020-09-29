package com.selab.uidesignserver.dao;

import com.selab.uidesignserver.entity.ClassTable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClassRepository extends JpaRepository<ClassTable, Integer> {
    
    @Query(value = "SELECT n.className FROM CLASS n WHERE n.classID = ?1",nativeQuery = true)
    public String getClassNameByID(int id);
}
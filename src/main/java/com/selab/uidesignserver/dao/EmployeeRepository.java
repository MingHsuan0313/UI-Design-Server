package com.selab.uidesignserver.dao;

import com.selab.uidesignserver.entity.Employee;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    
}

package com.selab.uidesignserver.respository;

import com.selab.uidesignserver.entity.Employee;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployRepository extends JpaRepository<Employee,  Integer> {

}
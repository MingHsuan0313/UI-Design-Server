package com.selab.uidesignserver.repositoryService;

import java.util.List;

import com.selab.uidesignserver.entity.Employee;

public interface EmployeeService {
    public List<Employee> findAll();        

    public Employee findById(int theId);

    public void save(Employee theEmployee);
    
    public void deleteById(int theId);
}

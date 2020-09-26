package com.selab.uidesignserver.repositoryService;

import java.util.List;

import com.selab.uidesignserver.dao.EmployeeRepository;
import com.selab.uidesignserver.entity.Employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    
    @Autowired
    public EmployeeServiceImpl(EmployeeRepository theEmployeeRepository) {
        employeeRepository = theEmployeeRepository;
    }

	@Override
	public List<Employee> findAll() {
        System.out.println("Testing");
        System.out.println(employeeRepository.findAll().toString());
		// TODO Auto-generated method stub
        return employeeRepository.findAll();
	}

	@Override
	public Employee findById(int theId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Employee theEmployee) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(int theId) {
		// TODO Auto-generated method stub
		
    }
    
}

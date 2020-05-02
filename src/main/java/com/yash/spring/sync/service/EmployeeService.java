package com.yash.spring.sync.service;

import com.yash.spring.sync.entity.Employee;

public interface EmployeeService {
	
	Employee saveEmployee(Employee employee);
	
	Employee updateEmployee(Employee employee);
	
	Employee getEmployeeById(String employeeId);

	void deleteEmployee(long id);

}

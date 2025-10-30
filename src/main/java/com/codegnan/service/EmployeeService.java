package com.codegnan.service;

import java.util.List;

import com.codegnan.model.Employee;

public interface EmployeeService {
	String addEmployee(Employee employee);
	
	Employee getEmployeeById(int eno);
	
	List<Employee> getAllEmployee();
	
	String updateEmployee(Employee employee);
	
	String deleteEmployee(int eno);
}

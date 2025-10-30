package com.codegnan.Dao;

import java.util.List;

import com.codegnan.model.Employee;

public interface EmployeeDao {
	
	String save(Employee employee);
	
	Employee findById(int eno);
	
	List<Employee> findAll();
	
	String update(Employee employee);
	
	String deleteById(int eno);
}

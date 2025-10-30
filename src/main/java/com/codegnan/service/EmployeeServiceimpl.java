package com.codegnan.service;

import java.util.List;

import com.codegnan.model.Employee;
import com.codegnan.Dao.EmployeeDao;
import com.codegnan.Dao.EmployeeDaoImpl;

public class EmployeeServiceimpl implements EmployeeService{
	
	EmployeeDao dao=new EmployeeDaoImpl();

	@Override
	public String addEmployee(Employee employee) {
		// TODO Auto-generated method stub
		return dao.save(employee);
	}

	@Override
	public Employee getEmployeeById(int eno) {
		// TODO Auto-generated method stub
		return dao.findById(eno);
	}

	@Override
	public List<Employee> getAllEmployee() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public String updateEmployee(Employee employee) {
		// TODO Auto-generated method stub
		return dao.update(employee);
	}

	@Override
	public String deleteEmployee(int eno) {
		// TODO Auto-generated method stub
		return dao.deleteById(eno);
	}

}

package com.codegnan.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.codegnan.factories.connectionFactory;
import com.codegnan.model.Employee;

public class EmployeeDaoImpl implements EmployeeDao {

	@Override
	public String save(Employee employee) {
		// TODO Auto-generated method stub
		String status="failure";
		String sql="insert into empl values (?,?,?,?)";
		try(Connection con=connectionFactory.getConnection();
				PreparedStatement pst=con.prepareStatement(sql)){
			if(findById(employee.getEno())==null) {
				pst.setInt(1, employee.getEno());
				pst.setString(2, employee.getEname());
				pst.setFloat(3, employee.getEsal());
				pst.setString(4, employee.getEaddr());
				
				int rowsAffected=pst.executeUpdate();
				if(rowsAffected>0) {
					status="success";
				}
			}
			else {
				status="exsisted";
			}
			
		}catch(SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println(status);
		return status;

	}

	@Override
	public Employee findById(int eno) {
		// TODO Auto-generated method stub
		Employee emp=null;
		String sql="select * from empl where eno=?";
		try(Connection con=connectionFactory.getConnection();
				PreparedStatement pst=con.prepareStatement(sql)){
			pst.setInt(1, eno);
			
			ResultSet res=pst.executeQuery();
			if(res.next()) {
				emp=new Employee();
				emp.setEno(res.getInt(1));
				emp.setEname(res.getString(2));
				emp.setEsal(res.getFloat(3));
				emp.setEaddr(res.getString(4));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return emp;
	}

	@Override
	public List<Employee> findAll() {
		// TODO Auto-generated method stub
		List<Employee> list=new ArrayList<>();
		String sql="select * from empl";
		try(Connection con=connectionFactory.getConnection();
				PreparedStatement pst=con.prepareStatement(sql)){
			ResultSet res=pst.executeQuery();
			while(res.next()) {
				Employee emp=null;
				emp=new Employee();
				emp.setEno(res.getInt(1));
				emp.setEname(res.getString(2));
				emp.setEsal(res.getFloat(3));
				emp.setEaddr(res.getString(4));
				list.add(emp);	
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String update(Employee employee) {
		// TODO Auto-generated method stub
		String status="failure";
		String sql="update empl set ename=?,esal=?,eaddr=? where eno=?";
		try(Connection con=connectionFactory.getConnection();
				PreparedStatement pst=con.prepareStatement(sql)){
			
			pst.setString(1, employee.getEname());
			pst.setFloat(2, employee.getEsal());
			pst.setString(3, employee.getEaddr());
			pst.setInt(4, employee.getEno());
			
			int rowsAffected=pst.executeUpdate();
			
			if(rowsAffected>0) {
				status="success";
			}
			else {
				status="NotFound";
			}
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public String deleteById(int eno) {
		// TODO Auto-generated method stub
		String status="failure";
		String sql="delete from empl where eno=?";
		try(Connection con=connectionFactory.getConnection();
				PreparedStatement pst=con.prepareStatement(sql)){
			pst.setInt(1, eno);
			
			int rowsAffected=pst.executeUpdate();
			
			if(rowsAffected>0) {
				status="success";
			}
			else {
				status="NotFound";
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
	}

}

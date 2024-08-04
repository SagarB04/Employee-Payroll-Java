package com.employeePayroll.utilityClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectionClass {

	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String user = "hr";
	private static final String pass = "root";

	/**
	 * connection class to get connection
	 */
	public static Connection getConnection() {
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, user, pass);
			return con;

		} catch (ClassNotFoundException e) {
			System.err.println(e);
		} catch (SQLException e) {
			System.err.println(e);
		}
		return null;
	}

	/**
	 * connection class to get employees list from database
	 */
	public static ArrayList<Employee> getEmpListDao() {
		ArrayList<Employee> arrEmp = new ArrayList<>();
		Connection con = ConnectionClass.getConnection();
		String query = "SELECT * FROM employee";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Employee emp = new Employee();
				emp.setEmp_id(rs.getInt(1));
				emp.setEmp_name(rs.getString(2));
				emp.setPosition(rs.getString(3));
				emp.setDepartment(rs.getString(4));
				emp.setBaseSalary(rs.getDouble(5));
				emp.setOvertimeHours(rs.getDouble(6));
				emp.setOvertimeRate(rs.getDouble(7));
				emp.setBonus(rs.getDouble(8));
				emp.setDeductions(rs.getDouble(9));
				emp.setTotalSalary(rs.getDouble(10));

				arrEmp.add(emp);
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
		return arrEmp;
	}

	/**
	 * connection class to get single employee from database using Employee Id
	 */
	public static Employee getEmpDao(int id) {
		Employee emp = new Employee();
		Connection con = ConnectionClass.getConnection();

		String query = "SELECT * FROM employee WHERE employee_id = ?";

		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				emp.setEmp_id(rs.getInt(1));
				emp.setEmp_name(rs.getString(2));
				emp.setPosition(rs.getString(3));
				emp.setDepartment(rs.getString(4));
				emp.setBaseSalary(rs.getDouble(5));
				emp.setOvertimeHours(rs.getDouble(6));
				emp.setOvertimeRate(rs.getDouble(7));
				emp.setBonus(rs.getDouble(8));
				emp.setDeductions(rs.getDouble(9));
				emp.setTotalSalary(rs.getDouble(10));
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
		return emp;
	}

	/**
	 * connection class for inserting new employee
	 */
	public static int addEmpDao(Employee emp) {
		Connection con = ConnectionClass.getConnection();
		
		String query = "INSERT INTO employee values(?,?,?,?,?,?,?,?,?,?)";
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, emp.getEmp_id());
			ps.setString(2, emp.getEmp_name());
			ps.setString(3, emp.getPosition());
			ps.setString(4, emp.getDepartment());
			ps.setDouble(5, emp.getBaseSalary());
			ps.setDouble(6, emp.getOvertimeHours());
			ps.setDouble(7, emp.getOvertimeRate());
			ps.setDouble(8, emp.getBonus());
			ps.setDouble(9, emp.getDeductions());
			ps.setDouble(10, emp.getTotalSalary());

			int r = ps.executeUpdate();
			return r;
		} catch (SQLException e) {
			System.err.println(e);
		}
		return 0;
	}

	/**
	 * connection class to update employee data using employee id
	 */
	public static int updateEmpDao(Employee emp) {
		Connection con = ConnectionClass.getConnection();

		String query = "update employee set employee_name=?, emp_position=?, department=?, base_salary=?, overtime_hours=?, overtime_rate=?, bonus=?, deduction=?, total_salary=? where employee_id=?";
		
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, emp.getEmp_name());
			ps.setString(2, emp.getPosition());
			ps.setString(3, emp.getDepartment());
			ps.setDouble(4, emp.getBaseSalary());
			ps.setDouble(5, emp.getOvertimeHours());
			ps.setDouble(6, emp.getOvertimeRate());
			ps.setDouble(7, emp.getBonus());
			ps.setDouble(8, emp.getDeductions());
			ps.setDouble(9, emp.getTotalSalary());
			ps.setInt(10, emp.getEmp_id());

			int r = ps.executeUpdate();
			return r;
		} catch (SQLException e) {
			System.err.println(e);
		}
		return 0;
	}

	/**
	 * connection class to delete employee data using employee id
	 */
	public static int deleteEmpDao(int id) {
		Connection con = ConnectionClass.getConnection();
		
		String query = "DELETE FROM employee WHERE employee_id=?";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, id);
			int r = ps.executeUpdate();
			return r;
		} catch (SQLException e) {
			System.err.println(e);
		}
		return 0;
	}

	/**
	 * Connection class to delete all employee data from the database
	 */
	public static int deleteAllEmpDao() {
		Connection con = ConnectionClass.getConnection();
		String query = "DELETE FROM employee";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			int r = ps.executeUpdate();
			return r;
		} catch (SQLException e) {
			System.err.println(e);
		}
		return -1;
	}
}

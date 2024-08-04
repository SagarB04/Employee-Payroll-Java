package com.employeePayroll.utilityClasses;

import java.util.ArrayList;

public class ServiceClass {
	
	/**
	 * service class to return employees list
	 */
	public static ArrayList<Employee> getEmpListService() {
		return ConnectionClass.getEmpListDao();
	}

	/**
	 * service class to return single employee
	 */
	public static Employee getEmpService(int id) {
		return ConnectionClass.getEmpDao(id);
	}

	/**
	 * service class to calculate total salary then add employee to database
	 */
	public static int addService(Employee emp) {
		emp.setTotalSalary(calculateTotalSal(emp));
		return ConnectionClass.addEmpDao(emp);
	}

	/**
	 * service class to calculate total salary then update employee in database
	 */
	public static int updateService(Employee emp) {
		emp.setTotalSalary(calculateTotalSal(emp));
		return ConnectionClass.updateEmpDao(emp);
	}

	/**
	 * service class to delete employee from database
	 */
	public static int deleteService(int id) {
		return ConnectionClass.deleteEmpDao(id);
	}
	
	/**
     * Service class to delete all employees from database
     */
    public static int deleteAllEmployees() {
        return ConnectionClass.deleteAllEmpDao();
    }

	/**
	 * service class to calculate salary 
	 */
	public static double calculateTotalSal(Employee emp) {
		double overtimePay = emp.getOvertimeHours() * emp.getOvertimeRate();
		double grossSalary = emp.getBaseSalary() + overtimePay + emp.getBonus();
        double totalSalary = grossSalary - emp.getDeductions();
        return totalSalary;
	}
}

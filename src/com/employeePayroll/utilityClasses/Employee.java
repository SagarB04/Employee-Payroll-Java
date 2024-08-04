package com.employeePayroll.utilityClasses;

/**
 * Employee model class to get store data
 */
public class Employee {
	
	private int emp_id;
	private String emp_name;
	private String position;
	private String department;
	private double baseSalary;
	private double overtimeHours;
	private double overtimeRate;
	private double bonus;
	private double deductions;
	private double totalSalary;

	public Employee() {
		super();
	}

	public Employee(int emp_id, String emp_name, String position, String department, double baseSalary,
			double overtimeHours, double overtimeRate, double bonus, double deductions, double totalSalary) {
		super();
		this.emp_id = emp_id;
		this.emp_name = emp_name;
		this.position = position;
		this.department = department;
		this.baseSalary = baseSalary;
		this.overtimeHours = overtimeHours;
		this.overtimeRate = overtimeRate;
		this.bonus = bonus;
		this.deductions = deductions;
		this.totalSalary = totalSalary;
	}

	public int getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public double getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(double baseSalary) {
		this.baseSalary = baseSalary;
	}

	public double getOvertimeHours() {
		return overtimeHours;
	}

	public void setOvertimeHours(double overtimeHours) {
		this.overtimeHours = overtimeHours;
	}

	public double getOvertimeRate() {
		return overtimeRate;
	}

	public void setOvertimeRate(double overtimeRate) {
		this.overtimeRate = overtimeRate;
	}

	public double getBonus() {
		return bonus;
	}

	public void setBonus(double bonus) {
		this.bonus = bonus;
	}

	public double getDeductions() {
		return deductions;
	}

	public void setDeductions(double deductions) {
		this.deductions = deductions;
	}

	public double getTotalSalary() {
		return totalSalary;
	}

	public void setTotalSalary(double totalSalary) {
		this.totalSalary = totalSalary;
	}

	@Override
	public String toString() {
		return "Employee [emp_id=" + emp_id + ", emp_name=" + emp_name + ", position=" + position + ", department="
				+ department + ", baseSalary=" + baseSalary + ", overtimeHours=" + overtimeHours + ", overtimeRate="
				+ overtimeRate + ", bonus=" + bonus + ", deductions=" + deductions + ", totalSalary=" + totalSalary
				+ "]";
	}

}

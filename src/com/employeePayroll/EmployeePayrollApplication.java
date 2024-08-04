package com.employeePayroll;

import javax.swing.SwingUtilities;

import com.employeePayroll.frames.MainWindowFrame;

public class EmployeePayrollApplication {
	/**
	 * main function
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainWindowFrame();
			}
		});
	}
}

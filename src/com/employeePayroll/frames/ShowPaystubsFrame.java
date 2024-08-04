package com.employeePayroll.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.employeePayroll.utilityClasses.Employee;
import com.employeePayroll.utilityClasses.ServiceClass;

public class ShowPaystubsFrame {
	/**
	 * function to show employee pay stub and download
	 */
	public static void showPaystubDetails(String value) {

		/**
		 * creating new frame for employee pay stub details
		 */
		JFrame payStubsFrame = new JFrame("Employee Pay stubs/Report");
		JPanel panel = new JPanel(new GridLayout(0, 2));
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		int id = Integer.parseInt(value);

		Employee emp = ServiceClass.getEmpService(id);


		panel.add(new JLabel("Employee ID:"));
		panel.add(new JLabel(Integer.toString(emp.getEmp_id())));
		panel.add(new JLabel("Employee Name:"));
		panel.add(new JLabel(emp.getEmp_name()));
		panel.add(new JLabel("Position:"));
		panel.add(new JLabel(emp.getPosition()));
		panel.add(new JLabel("Department:"));
		panel.add(new JLabel(emp.getDepartment()));
		panel.add(new JLabel("Base Salary:"));
		panel.add(new JLabel(Double.toString(emp.getBaseSalary())));
		panel.add(new JLabel("Overtime Hour:"));
		panel.add(new JLabel(Double.toString(emp.getOvertimeHours())));
		panel.add(new JLabel("Overtime Rate:"));
		panel.add(new JLabel(Double.toString(emp.getOvertimeRate())));
		panel.add(new JLabel("Bonus:"));
		panel.add(new JLabel(Double.toString(emp.getBonus())));
		panel.add(new JLabel("Deductions/Taxes:"));
		panel.add(new JLabel(Double.toString(emp.getDeductions())));
		panel.add(new JLabel("Total Salary:"));
		panel.add(new JLabel(Double.toString(emp.getTotalSalary())));

		/**
		 * Adding download button
		 */
		JButton downloadButton = new JButton("Download");
		buttonPanel.add(downloadButton);

		/**
		 * adding event on download button and calling function to download in various
		 * format
		 */
		downloadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				ExportToFileFrame.exportToFile(id);
				payStubsFrame.dispose();
			}
		});

		panel.setPreferredSize(new Dimension(400, 400));
		panel.setBorder(BorderFactory.createEmptyBorder(0, 80, 0, 0));
		payStubsFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		payStubsFrame.getContentPane().add(panel);
		payStubsFrame.pack();
		payStubsFrame.setLocationRelativeTo(null);
		payStubsFrame.setVisible(true);

	}

	
}

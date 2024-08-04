package com.employeePayroll.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.employeePayroll.EmployeePayrollApplication;
import com.employeePayroll.utilityClasses.Employee;
import com.employeePayroll.utilityClasses.ServiceClass;

public class AddEmployeeFrame {

	/**
	 * function to add employee data and store in database
	 */
	public static void addNewEmployeeWindow() {

		/**
		 * creating new frame for new employee addition
		 */
		JFrame newEmployeeFrame = new JFrame("New Employee Details");
		JPanel panel = new JPanel(new GridLayout(0, 2));
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JTextField idField = new JTextField(5);
		idField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c) || idField.getText().length() >= 5) {
					e.consume();
				}
			}
		});

		JTextField nameField = new JTextField(20);
		nameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!(Character.isAlphabetic(c) || Character.isSpaceChar(c)) || nameField.getText().length() >= 20) {
					e.consume();
				}
			}
		});

		JTextField posField = new JTextField(20);
		posField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!(Character.isAlphabetic(c) || Character.isSpaceChar(c)) || posField.getText().length() >= 20) {
					e.consume();
				}
			}
		});
		
		JTextField deptField = new JTextField(20);
		deptField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!(Character.isAlphabetic(c) || Character.isSpaceChar(c)) || deptField.getText().length() >= 20) {
					e.consume();
				}
			}
		});
		
		JTextField salField = new JTextField(15);
		salField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!( Character.isDigit(c) || c == '.' ) || salField.getText().length() >= 10) {
					e.consume();
				}
			}
		});
		
		JTextField ohField = new JTextField(3);
		ohField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!( Character.isDigit(c) || c == '.' ) || ohField.getText().length() >= 3) {
					e.consume();
				}
			}
		});
		
		JTextField orField = new JTextField(5);
		orField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!( Character.isDigit(c) || c == '.' ) || orField.getText().length() >= 5) {
					e.consume();
				}
			}
		});
		
		JTextField bonusField = new JTextField(8);
		bonusField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!( Character.isDigit(c) || c == '.' ) || bonusField.getText().length() >= 8) {
					e.consume();
				}
			}
		});
		
		JTextField deductField = new JTextField(8);
		deductField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!( Character.isDigit(c) || c == '.' ) || deductField.getText().length() >= 8) {
					e.consume();
				}
			}
		});

		panel.add(new JLabel("Employee ID:"));
		panel.add(idField);
		panel.add(new JLabel("Employee Name:"));
		panel.add(nameField);
		panel.add(new JLabel("Position:"));
		panel.add(posField);
		panel.add(new JLabel("Department:"));
		panel.add(deptField);
		panel.add(new JLabel("Base Salary:"));
		panel.add(salField);
		panel.add(new JLabel("Overtime Hours:"));
		panel.add(ohField);
		panel.add(new JLabel("Overtime Rate:"));
		panel.add(orField);
		panel.add(new JLabel("Bonus:"));
		panel.add(bonusField);
		panel.add(new JLabel("Deductions/Taxes:"));
		panel.add(deductField);

		/**
		 * adding save button
		 */
		JButton saveButton = new JButton("Save");
		buttonPanel.add(saveButton);

		/**
		 * adding event on save button to save employee on database
		 */
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					/**
					 * validate each empty fields validate Employee id, Employee name, position and
					 * department validate salary, overtime hours, overtime rate, bonus and
					 * deductions
					 */
					if (idField.getText().isEmpty() || nameField.getText().isEmpty() || posField.getText().isEmpty()
							|| deptField.getText().isEmpty() || salField.getText().isEmpty()
							|| ohField.getText().isEmpty() || orField.getText().isEmpty()
							|| bonusField.getText().isEmpty() || deductField.getText().isEmpty()) {
						throw new IllegalArgumentException("All fields must be filled.");
					}

					if (!idField.getText().matches("\\d{1,5}")) {
						throw new NumberFormatException("Employee ID must be a number between 1 to 99999.");
					}

					if (!nameField.getText().matches("[a-zA-Z\\s]{1,20}")) {
						throw new IllegalArgumentException(
								"Employee Name must contain only alphabets and spaces, and be up to 20 characters long (e.g., Amit Kumar).");
					}

					if (!posField.getText().matches("[a-zA-Z\\s]{1,20}")) {
						throw new IllegalArgumentException(
								"Position must contain only alphabets and spaces, and be up to 20 characters long (e.g., Manager).");
					}

					if (!deptField.getText().matches("[a-zA-Z\\s]{1,20}")) {
						throw new IllegalArgumentException(
								"Department must contain only alphabets and spaces, and be up to 20 characters long (e.g., HR).");
					}

					if (!salField.getText().matches("\\d{1,10}(\\.\\d+)?")) {
						throw new NumberFormatException(
								"Base Salary must be a valid number with up to 10 digits (e.g., 5000000.000).");
					}

					if (!ohField.getText().matches("\\d{1,3}(\\.\\d+)?")) {
						throw new NumberFormatException(
								"Overtime Hours must be a valid number with up to 3 digits (e.g., 200).");
					}

					if (!orField.getText().matches("\\d{1,5}(\\.\\d+)?")) {
						throw new NumberFormatException(
								"Overtime Rate must be a valid number with up to 5 digits e.g., 500.5).");
					}

					if (!bonusField.getText().matches("\\d{1,8}(\\.\\d+)?")) {
						throw new NumberFormatException(
								"Bonus must be a valid number with up to 8 digits (e.g., 10000.99).");
					}

					if (!deductField.getText().matches("\\d{1,8}(\\.\\d+)?")) {
						throw new NumberFormatException(
								"Deductions/Taxes must be a valid number with up to 8 digits (e.g., 10000.99).");
					}

					int id = Integer.parseInt(idField.getText());
					String name = nameField.getText();
					String pos = posField.getText();
					String dept = deptField.getText();
					double baseSal = Double.parseDouble(salField.getText());
					double  overHour = Double.parseDouble(ohField.getText());
					double overRate = Double.parseDouble(orField.getText());
					double bonus = Double.parseDouble(bonusField.getText());
					double deductions = Double.parseDouble(deductField.getText());

					/**
					 * validate deductions must be less than gross salary
					 */
					double grossSalary = (overHour * overRate) + baseSal + bonus;
					if (deductions > grossSalary) {
						JOptionPane.showMessageDialog(newEmployeeFrame,
								"Deductions/Taxes must be less than Base Salary + OverTime Pay + Bonus.",
								"Validation Error", JOptionPane.WARNING_MESSAGE);
						return;
					}

					Employee emp = new Employee();
					emp.setEmp_id(id);
					emp.setEmp_name(name);
					emp.setPosition(pos);
					emp.setDepartment(dept);
					emp.setBaseSalary(baseSal);
					emp.setOvertimeHours(overHour);
					emp.setOvertimeRate(overRate);
					emp.setBonus(bonus);
					emp.setDeductions(deductions);

					int msg = ServiceClass.addService(emp);
					if (msg == 1) {
						JOptionPane.showMessageDialog(newEmployeeFrame, "Employee added successfully.",
								"Employee Added", JOptionPane.INFORMATION_MESSAGE);
						newEmployeeFrame.dispose();
						MainWindowFrame.getInstance();
						EmployeePayrollApplication.main(new String[] {});
					} else {
						JOptionPane.showMessageDialog(newEmployeeFrame, "Employee not added, please try again.",
								"Try Again", JOptionPane.WARNING_MESSAGE);
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(newEmployeeFrame, ex.getMessage(), "Number Format Error",
							JOptionPane.WARNING_MESSAGE);
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(newEmployeeFrame, ex.getMessage(), "Validation Error",
							JOptionPane.WARNING_MESSAGE);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(newEmployeeFrame, "Something went wrong, please try again.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			}

		});

		panel.setPreferredSize(new Dimension(400, 400));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		newEmployeeFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		newEmployeeFrame.getContentPane().add(panel);
		newEmployeeFrame.pack();
		newEmployeeFrame.setLocationRelativeTo(null);
		newEmployeeFrame.setVisible(true);
	}

}

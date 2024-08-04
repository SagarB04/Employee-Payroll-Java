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

public class UpdateEmployeeFrame {
	/**
	 * function to update data and store in database
	 */
	public static void updateEmployee(String value) {
		JFrame updateEmployeeFrame = new JFrame("Update Employee Details");
		JPanel panel = new JPanel(new GridLayout(0, 2));
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
	

		Employee emp = ServiceClass.getEmpService(Integer.parseInt(value));

		JTextField idField = new JTextField(5);
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
		idField.setText(Integer.toString(emp.getEmp_id()));
		idField.setEnabled(false);

		panel.add(new JLabel("Employee Name:"));
		panel.add(nameField);
		nameField.setText(emp.getEmp_name());

		panel.add(new JLabel("Position:"));
		panel.add(posField);
		posField.setText(emp.getPosition());

		panel.add(new JLabel("Department:"));
		panel.add(deptField);
		deptField.setText(emp.getDepartment());

		panel.add(new JLabel("Base Salary:"));
		panel.add(salField);
		salField.setText(Double.toString(emp.getBaseSalary()));

		panel.add(new JLabel("Overtime Hours:"));
		panel.add(ohField);
		ohField.setText(Double.toString(emp.getOvertimeHours()));

		panel.add(new JLabel("Overtime Rate:"));
		panel.add(orField);
		orField.setText(Double.toString(emp.getOvertimeRate()));

		panel.add(new JLabel("Bonus:"));
		panel.add(bonusField);
		bonusField.setText(Double.toString(emp.getBonus()));

		panel.add(new JLabel("Deductions/Taxes:"));
		panel.add(deductField);
		deductField.setText(Double.toString(emp.getDeductions()));

		JButton updateButton = new JButton("Update");
		buttonPanel.add(updateButton);

		/**
		 * adding event on update button
		 */
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					
					/**
					 * validate each empty fields
					 * validate Employee id, Employee name, position and department
					 * validate salary, overtime hours, overtime rate, bonus and deductions
					 */
                    if (idField.getText().isEmpty() || nameField.getText().isEmpty() || posField.getText().isEmpty() ||
                        deptField.getText().isEmpty() || salField.getText().isEmpty() || ohField.getText().isEmpty() ||
                        orField.getText().isEmpty() || bonusField.getText().isEmpty() || deductField.getText().isEmpty()) {
                        throw new IllegalArgumentException("All fields must be filled.");
                    }
                    
                    if (!nameField.getText().matches("[a-zA-Z\\s]{1,20}")) {
                        throw new IllegalArgumentException("Employee Name must contain only alphabets and spaces, and be up to 20 characters long (e.g., Amit Kumar).");
                    }
                    
                    if (!posField.getText().matches("[a-zA-Z\\s]{1,20}")) {
                        throw new IllegalArgumentException("Position must contain only alphabets and spaces, and be up to 20 characters long (e.g., Manager).");
                    }
                    
                    if (!deptField.getText().matches("[a-zA-Z\\s]{1,20}")) {
                        throw new IllegalArgumentException("Department must contain only alphabets and spaces, and be up to 20 characters long (e.g., HR).");
                    }
                    
                    if (!salField.getText().matches("\\d{1,10}(\\.\\d+)?")) {
                        throw new NumberFormatException("Base Salary must be a valid number with up to 10 digits (e.g., 5000000.000).");
                    }
                    
                    if (!ohField.getText().matches("\\d{1,3}(\\.\\d+)?")) {
                        throw new NumberFormatException("Overtime Hours must be a valid number with up to 3 digits (e.g., 200).");
                    }
                    
                    if (!orField.getText().matches("\\d{1,5}(\\.\\d+)?")) {
                        throw new NumberFormatException("Overtime Rate must be a valid number with up to 5 digits e.g., 500.5).");
                    }
                    
                    if (!bonusField.getText().matches("\\d{1,8}(\\.\\d+)?")) {
                        throw new NumberFormatException("Bonus must be a valid number with up to 8 digits (e.g., 10000).");
                    }
                    
                    if (!deductField.getText().matches("\\d{1,8}(\\.\\d+)?")) {
                        throw new NumberFormatException("Deductions/Taxes must be a valid number with up to 8 digits (e.g., 20000.8000).");
                    }

					int id = Integer.parseInt(idField.getText());
					String name = nameField.getText();
					String pos = posField.getText();
					String dept = deptField.getText();
					double baseSal = Double.parseDouble(salField.getText());
					double overHour = Double.parseDouble(ohField.getText());
					double overRate = Double.parseDouble(orField.getText());
					double bonus = Double.parseDouble(bonusField.getText());
					double deduction = Double.parseDouble(deductField.getText());
					
					// Check if there are any changes
                    if (name.equals(emp.getEmp_name()) && pos.equals(emp.getPosition()) && dept.equals(emp.getDepartment()) &&
                        baseSal == emp.getBaseSalary() && overHour == emp.getOvertimeHours() && overRate == emp.getOvertimeRate() &&
                        bonus == emp.getBonus() && deduction == emp.getDeductions()) {
                        
                    	JOptionPane.showMessageDialog(updateEmployeeFrame,
                            "Employee details not updated, No changes made.",
                            "No Changes", JOptionPane.INFORMATION_MESSAGE);
                    	
                    	updateEmployeeFrame.dispose();
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
					emp.setDeductions(deduction);
					
					int msg = ServiceClass.updateService(emp);
					
					if (msg == 1) {
						JOptionPane.showMessageDialog(updateEmployeeFrame, "Employee details updated successfully.",
								"Employee Updated", JOptionPane.INFORMATION_MESSAGE);

						updateEmployeeFrame.dispose();
						MainWindowFrame.getInstance();
						EmployeePayrollApplication.main(new String[] {});

					} else {
						JOptionPane.showMessageDialog(updateEmployeeFrame, "Employee details not updated, Please try again.",
								"Try Again", JOptionPane.WARNING_MESSAGE);
						
						updateEmployeeFrame.dispose();
					}
				} catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(updateEmployeeFrame, ex.getMessage(),
                            "Number Format Error", JOptionPane.WARNING_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(updateEmployeeFrame, ex.getMessage(),
                            "Validation Error", JOptionPane.WARNING_MESSAGE);
                } catch (Exception e2) {
					JOptionPane.showMessageDialog(updateEmployeeFrame,
							"Something wrong, Please check values and try again.", 
							"Try Again", JOptionPane.WARNING_MESSAGE);
				}
			}

		});

		panel.setPreferredSize(new Dimension(400, 400));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		updateEmployeeFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		updateEmployeeFrame.getContentPane().add(panel);
		updateEmployeeFrame.pack();
		updateEmployeeFrame.setLocationRelativeTo(null);
		updateEmployeeFrame.setVisible(true);
	}

}

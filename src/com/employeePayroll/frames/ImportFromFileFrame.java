package com.employeePayroll.frames;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import com.employeePayroll.EmployeePayrollApplication;
import com.employeePayroll.utilityClasses.Employee;
import com.employeePayroll.utilityClasses.ServiceClass;
import com.opencsv.CSVReader;

public class ImportFromFileFrame {

	/**
	 * Creating new frame for import format selection
	 */
	private static JFrame importFrame = new JFrame("Import Employees Data");

	/**
	 * function to read from file
	 */
	public static void importFromFile() {

		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		panel.add(new JLabel("Select Format to Import."));

		/**
		 * buttons (csv, excel) for import
		 */
		JButton csv = new JButton("CSV");
		JButton excel = new JButton("Excel");

		buttonPanel.add(csv);
		buttonPanel.add(excel);

		/**
		 * Adding event in csv button to read csv file
		 */
		csv.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File csvFile = fileChooser();

				if (csvFile != null) {

					if (isCsvFile(csvFile)) {
						readCSVFile(csvFile);
					} else {
						JOptionPane.showMessageDialog(importFrame, "Format not matched", "Error",
								JOptionPane.WARNING_MESSAGE);
					}

				} else {
					JOptionPane.showMessageDialog(importFrame, "Operation Canceled", "Canceled",
							JOptionPane.WARNING_MESSAGE);
				}

				importFrame.dispose();
			}
		});

		/**
		 * Adding event in excel button to read excel file
		 */
		excel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File excelFile = fileChooser();

				if (excelFile != null) {

					if (isExcelFile(excelFile)) {
						readExcelFile(excelFile);
					} else {
						JOptionPane.showMessageDialog(importFrame, "Format not matched", "Error",
								JOptionPane.WARNING_MESSAGE);
					}

				} else {
					JOptionPane.showMessageDialog(importFrame, "Operation Canceled", "Canceled",
							JOptionPane.WARNING_MESSAGE);
				}

				importFrame.dispose();
			}
		});

		panel.setPreferredSize(new Dimension(400, 80));
		importFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		importFrame.getContentPane().add(panel);
		importFrame.pack();
		importFrame.setLocationRelativeTo(null);
		importFrame.setVisible(true);

	}

	/**
	 * function to read csv file and store data in database
	 */
	private static void readCSVFile(File csvFile) {

		int count = 0;

		try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {

			String[] line;
			reader.readNext();

			while ((line = reader.readNext()) != null) {

				int id = Integer.parseInt(line[0]);
				String name = line[1];
				String pos = line[2];
				String dept = line[3];
				double baseSal = Double.parseDouble(line[4]);
				double overHour = Double.parseDouble(line[5]);
				double overRate = Double.parseDouble(line[6]);
				double bonus = Double.parseDouble(line[7]);
				double deductions = Double.parseDouble(line[8]);

				/**
				 * validate Employee id, Employee name, position and department validate salary,
				 * overtime hours, overtime rate, bonus and deductions
				 */
				validateFields(id, name, pos, dept, baseSal, overHour, overRate, bonus, deductions);

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

				int res = ServiceClass.addService(emp);
				if (res == 1) {
					count++;
				}
			}
			if (count > 0) {
				JOptionPane.showMessageDialog(importFrame, count + " Employees added successfully.", "Employees Added",
						JOptionPane.INFORMATION_MESSAGE);

				importFrame.dispose();
				MainWindowFrame.getInstance();
				EmployeePayrollApplication.main(new String[] {});

			} else {
				JOptionPane.showMessageDialog(importFrame, " Employees not added, Please check the file and try again.",
						"Employees not Added", JOptionPane.INFORMATION_MESSAGE);

				importFrame.dispose();

			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(importFrame, count + " Employees added.\n\n" + ex.getLocalizedMessage(),
					"Try Again", JOptionPane.WARNING_MESSAGE);

			importFrame.dispose();
			if (count > 0) {
				MainWindowFrame.getInstance();
				EmployeePayrollApplication.main(new String[] {});
			}

		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(importFrame, count + " Employees added.\n\n" + ex.getLocalizedMessage(),
					"Try Again", JOptionPane.WARNING_MESSAGE);

			importFrame.dispose();
			if (count > 0) {
				MainWindowFrame.getInstance();
				EmployeePayrollApplication.main(new String[] {});
			}

		} catch (Exception e2) {
			JOptionPane.showMessageDialog(importFrame, "Something wrong, Please check your file and try again.",
					"Try again", JOptionPane.WARNING_MESSAGE);

			importFrame.dispose();
			if (count > 0) {
				MainWindowFrame.getInstance();
				EmployeePayrollApplication.main(new String[] {});
			}
		}
	}

	/**
	 * function to read excel file and store data in database
	 */
	private static void readExcelFile(File excelFile) {

		int count = 0;

		try (FileInputStream fis = new FileInputStream(excelFile)) {

			Workbook workbook = WorkbookFactory.create(fis);
			Sheet sheet = workbook.getSheetAt(0);

			for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);

				if (row == null) {
					continue;
				}

				int id = (int) row.getCell(0).getNumericCellValue();
				String name = row.getCell(1).getStringCellValue();
				String pos = row.getCell(2).getStringCellValue();
				String dept = row.getCell(3).getStringCellValue();
				double baseSal = row.getCell(4).getNumericCellValue();
				double overHour = row.getCell(5).getNumericCellValue();
				double overRate = row.getCell(6).getNumericCellValue();
				double bonus = row.getCell(7).getNumericCellValue();
				double deductions = row.getCell(8).getNumericCellValue();

				/**
				 * validate Employee id, Employee name, position and department validate salary,
				 * overtime hours, overtime rate, bonus and deductions
				 */
				validateFields(id, name, pos, dept, baseSal, overHour, overRate, bonus, deductions);

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

				int res = ServiceClass.addService(emp);
				if (res == 1) {
					count++;
				}
			}
			if (count > 0) {
				JOptionPane.showMessageDialog(importFrame, count + " Employees added successfully.", "Employee Added",
						JOptionPane.INFORMATION_MESSAGE);

				importFrame.dispose();
				MainWindowFrame.getInstance();
				EmployeePayrollApplication.main(new String[] {});

			} else {
				JOptionPane.showMessageDialog(importFrame, " Employees not added, Check your file and try again.",
						"Employee not Added", JOptionPane.INFORMATION_MESSAGE);

				importFrame.dispose();
			}

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(importFrame, count + " Employees added.\n\n" + ex.getLocalizedMessage(),
					"Try Again", JOptionPane.WARNING_MESSAGE);

			importFrame.dispose();
			if (count > 0) {
				MainWindowFrame.getInstance();
				EmployeePayrollApplication.main(new String[] {});
			}
		} catch (IllegalArgumentException ex) {
			JOptionPane.showMessageDialog(importFrame, count + " Employees added.\n\n" + ex.getLocalizedMessage(),
					"Try Again", JOptionPane.WARNING_MESSAGE);

			importFrame.dispose();
			if (count > 0) {
				MainWindowFrame.getInstance();
				EmployeePayrollApplication.main(new String[] {});
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(importFrame, "Something wrong, Please check your file and try again.",
					"Try again", JOptionPane.WARNING_MESSAGE);

			importFrame.dispose();
			if (count > 0) {
				MainWindowFrame.getInstance();
				EmployeePayrollApplication.main(new String[] {});
			}
		}

	}

	/**
	 * function to validate fields
	 */
	private static void validateFields(int id, String name, String pos, String dept, double baseSal, double overHour,
			double overRate, double bonus, double deductions) {

		if (String.valueOf(id).length() < 0 || String.valueOf(id).length() > 5) {

			throw new NumberFormatException("Employee ID must be a number between 1 to 99999.");
		}

		if (!name.matches("[a-zA-Z\\s]{1,20}")) {
			throw new IllegalArgumentException(
					"Employee Name must contain only alphabets and spaces, and be up to 20 characters long (e.g., Amit Kumar).");
		}

		if (!pos.matches("[a-zA-Z\\s]{1,20}")) {
			throw new IllegalArgumentException(
					"Position must contain only alphabets and spaces, and be up to 20 characters long (e.g., Manager).");
		}

		if (!dept.matches("[a-zA-Z\\s]{1,20}")) {
			throw new IllegalArgumentException(
					"Department must contain only alphabets and spaces, and be up to 20 characters long (e.g., HR).");
		}

		if (String.valueOf(baseSal).length() > 10) {
			throw new NumberFormatException(
					"Base Salary must be a valid number with up to 10 digits (e.g., 5000000.000).");
		}

		if (String.valueOf(overHour).length() > 3) {
			throw new NumberFormatException("Overtime Hours must be a valid number with up to 3 digits (e.g., 200).");
		}

		if (String.valueOf(overRate).length() > 5) {
			throw new NumberFormatException("Overtime Rate must be a valid number with up to 5 digits e.g., 500.55).");
		}

		if (String.valueOf(bonus).length() > 8) {
			throw new NumberFormatException("Bonus must be a valid number with up to 8 digits (e.g., 10000).");
		}

		if (String.valueOf(deductions).length() > 8) {
			throw new NumberFormatException(
					"Deductions/Taxes must be a valid number with up to 8 digits (e.g., 20000.8000).");
		}

		double grossSalary = (overHour * overRate) + baseSal + bonus;
		if (deductions > grossSalary) {
			throw new IllegalArgumentException(
					"Deductions/Taxes must be less than Base Salary + OverTime Pay + Bonus.");
		}
	}

	/**
	 * function to get file selection frame (window)
	 */
	private static File fileChooser() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select a File");

		int userSelection = fileChooser.showOpenDialog(null);

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			return file;

		} else if (userSelection == JFileChooser.CANCEL_OPTION) {
			return null;
		}
		return null;
	}

	/**
	 * checks file is csv of not
	 */
	private static boolean isCsvFile(File file) {

		String extension = getFileExtension(file);
		return extension != null && extension.equals("csv");
	}

	/**
	 * checks file is excel of not
	 */
	private static boolean isExcelFile(File file) {

		String extension = getFileExtension(file);
		return extension != null && extension.equals("xlsx");
	}

	private static String getFileExtension(File file) {
		String extension = null;
		String fileName = file.getName();
		int dotIndex = fileName.lastIndexOf('.');

		if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
			extension = fileName.substring(dotIndex + 1).toLowerCase();
		}
		return extension;
	}
}

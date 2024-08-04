package com.employeePayroll.frames;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.employeePayroll.utilityClasses.Employee;
import com.employeePayroll.utilityClasses.ServiceClass;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ExportToFileFrame {

	/**
	 * function to export (download) data in various formats
	 */
	public static void exportToFile(int emp_id) {

		/**
		 * Creating new frame for download format selection
		 */
		JFrame exportFrame = new JFrame("Export Employees Data");
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		panel.add(new JLabel("Select Format to Export."));

		/**
		 * buttons (pdf, csv, excel) to download
		 */
		JButton pdf = new JButton("PDF");
		JButton csv = new JButton("CSV");
		JButton excel = new JButton("Excel");

		buttonPanel.add(pdf);
		buttonPanel.add(csv);
		buttonPanel.add(excel);

		/**
		 * adding event on pdf button to download data in pdf format from database
		 */
		pdf.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				ArrayList<Employee> emplist = new ArrayList<Employee>();
				String path;
				int random = (int) Math.floor(Math.random() * 1000000);

				try {

					if (emp_id == -1) {
						emplist = ServiceClass.getEmpListService();
						path = "EmployeeList-" + random + ".pdf";
						
					} else {
						emplist.add(ServiceClass.getEmpService(emp_id));
						path = "Employee-" + emp_id + "-" + random + ".pdf";
						
					}

					Document document = new Document();

					PdfWriter.getInstance(document, new FileOutputStream(path));
					document.open();

					document.add(new Paragraph("Employees Data : \n\n"));

					for (Employee emp : emplist) {
						document.add(new Paragraph("ID: " + emp.getEmp_id()));
						document.add(new Paragraph("Name: " + emp.getEmp_name()));
						document.add(new Paragraph("Postion: " + emp.getPosition()));
						document.add(new Paragraph("Department: " + emp.getDepartment()));
						document.add(new Paragraph("Base Salary: " + emp.getBaseSalary()));
						document.add(new Paragraph("Overtime Hours: " + emp.getOvertimeHours()));
						document.add(new Paragraph("Overtime Rate: " + emp.getOvertimeRate()));
						document.add(new Paragraph("Bonus: " + emp.getBonus()));
						document.add(new Paragraph("Deduction: " + emp.getDeductions()));
						document.add(new Paragraph("Total Salary: " + emp.getTotalSalary()));
						document.add(new Paragraph("\n")); // Empty line for separation
					}

					document.close();
					JOptionPane.showMessageDialog(exportFrame, "File created successfully.", 
							"File Created", JOptionPane.INFORMATION_MESSAGE);
					
					exportFrame.dispose();

				}

				catch (Exception e2) {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(exportFrame, "Something wrong, Please try again.", 
							"Try Again", JOptionPane.WARNING_MESSAGE);

					exportFrame.dispose();
				}
			}
		});

		/**
		 * adding event on excel button to download data in excel format from database
		 */
		excel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				ArrayList<Employee> emplist = new ArrayList<Employee>();
				String path;
				int random = (int) Math.floor(Math.random() * 1000000);

				try {

					if (emp_id == -1) {

						emplist = ServiceClass.getEmpListService();
						path = "EmployeeList-" + random + ".xlsx";

					} else {

						emplist.add(ServiceClass.getEmpService(emp_id));
						path = "Employee-" + emp_id + "-" + random + ".xlsx";

					}

					Workbook workbook = new XSSFWorkbook();
					Sheet sheet = workbook.createSheet("Employees");

					Row headerRow = sheet.createRow(0);
					headerRow.createCell(0).setCellValue("ID");
					headerRow.createCell(1).setCellValue("Name");
					headerRow.createCell(2).setCellValue("Position");
					headerRow.createCell(3).setCellValue("Department");
					headerRow.createCell(4).setCellValue("Base Salary");
					headerRow.createCell(5).setCellValue("Overtime Hour");
					headerRow.createCell(6).setCellValue("Overtime Rate");
					headerRow.createCell(7).setCellValue("Bonus");
					headerRow.createCell(8).setCellValue("Deduction");
					headerRow.createCell(9).setCellValue("Total Salary");

					int rowNum = 1;
					for (Employee emp : emplist) {
						Row row = sheet.createRow(rowNum++);
						row.createCell(0).setCellValue(emp.getEmp_id());
						row.createCell(1).setCellValue(emp.getEmp_name());
						row.createCell(2).setCellValue(emp.getPosition());
						row.createCell(3).setCellValue(emp.getDepartment());
						row.createCell(4).setCellValue(emp.getBaseSalary());
						row.createCell(5).setCellValue(emp.getOvertimeHours());
						row.createCell(6).setCellValue(emp.getOvertimeRate());
						row.createCell(7).setCellValue(emp.getBonus());
						row.createCell(8).setCellValue(emp.getDeductions());
						row.createCell(9).setCellValue(emp.getTotalSalary());
					}

					for (int i = 0; i < emplist.size(); i++) {
						sheet.autoSizeColumn(i);
					}

					FileOutputStream file = new FileOutputStream(path);
					workbook.write(file);

					file.close();
					workbook.close();

					JOptionPane.showMessageDialog(exportFrame, "File created successfully.", 
							"File Created", JOptionPane.INFORMATION_MESSAGE);
					exportFrame.dispose();

				} catch (Exception e2) {
					JOptionPane.showMessageDialog(exportFrame, "Something wrong, Please try again.", 
							"Try Again", JOptionPane.WARNING_MESSAGE);
					System.out.println(e2);
					exportFrame.dispose();
				}
			}
		});

		/**
		 * adding event on csv button to download data in csv format from database
		 */
		csv.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				ArrayList<Employee> emplist = new ArrayList<Employee>();
				String path;
				int random = (int) Math.floor(Math.random() * 1000000);

				try {

					if (emp_id == -1) {
						emplist = ServiceClass.getEmpListService();
						path = "EmployeeList-" + random + ".csv";

					} else {
						emplist.add(ServiceClass.getEmpService(emp_id));
						path = "Employee-" + emp_id + "-" + random + ".csv";

					}

					BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path)));
					// Write CSV file header
					writer.write(
							"ID,Name,Position,Department,Base Salary, Overtime Hour, Overtime Rate, Bonus, Deduction, Total Salary");
					writer.newLine();

					// Write employees data to CSV file
					for (Employee emp : emplist) {

						writer.write(String.format("%d,%s,%s,%s,%f,%f,%f,%f,%f,%f", emp.getEmp_id(), emp.getEmp_name(),
								emp.getPosition(), emp.getDepartment(), emp.getBaseSalary(), emp.getOvertimeHours(),
								emp.getOvertimeRate(), emp.getBonus(), emp.getDeductions(), emp.getTotalSalary()));
						writer.newLine();
					}
					writer.close();
					JOptionPane.showMessageDialog(exportFrame, "File created successfully.", 
							"File Created", JOptionPane.INFORMATION_MESSAGE);
					
					exportFrame.dispose();

				} catch (Exception e2) {
					JOptionPane.showMessageDialog(exportFrame, "Something wrong, Please try again.", 
							"Try Again", JOptionPane.WARNING_MESSAGE);
					
					exportFrame.dispose();
				}
			}
		});

		panel.setPreferredSize(new Dimension(400, 80));
		exportFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		exportFrame.getContentPane().add(panel);
		exportFrame.pack();
		exportFrame.setLocationRelativeTo(null);
		exportFrame.setVisible(true);

	}

}

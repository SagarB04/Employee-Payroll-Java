package com.employeePayroll.frames;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.employeePayroll.EmployeePayrollApplication;
import com.employeePayroll.utilityClasses.Employee;
import com.employeePayroll.utilityClasses.ServiceClass;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class MainWindowFrame extends JFrame {
	
	private static MainWindowFrame instance;

	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem addItem, importItem, exportItem, refreshItem;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton updateButton, deleteButton, payStubsButton, deleteAllButton;
	
	/**
	 * This is main frame (main window)
	 */
	public MainWindowFrame() {

		instance = this;
		setTitle("Employee Payroll System");
		setSize(820, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		menuBar = new JMenuBar();
		menu = new JMenu("Menu");
		/**
		 * Menu bar buttons initialization
		 */
		addItem = new JMenuItem("Add Employee");
		importItem = new JMenuItem("Import Employees Data");
		exportItem = new JMenuItem("Export Employees Data");
		refreshItem = new JMenuItem("Refresh");

		menu.add(addItem);
		menu.add(importItem);
		menu.add(exportItem);
		menu.add(refreshItem);
		menuBar.add(menu);
		setJMenuBar(menuBar);

		/**
		 * Adding table with employees data from database getting ArrayList of employees
		 * from database
		 */
		String[] columnNames = { "Employee ID", "Employee Name", "Position", "Department", "Total Salary" };
		ArrayList<Employee> arrEmp = ServiceClass.getEmpListService();
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

		for (Employee emp : arrEmp) {
			Object[] data = { emp.getEmp_id(), emp.getEmp_name(), emp.getPosition(), emp.getDepartment(),
					emp.getTotalSalary() };
			tableModel.addRow(data);
		}

		table = new JTable(tableModel);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment(SwingConstants.CENTER);

		scrollPane = new JScrollPane(table);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		/**
		 * bottom layer buttons (Pay stubs, Update, Delete)
		 */
		updateButton = new JButton("Update Employee");
		deleteButton = new JButton("Delete Employee");
		deleteAllButton = new JButton("Delete All Employees");
		payStubsButton = new JButton("Pay stubs/Report");

		buttonPanel.add(updateButton);
		buttonPanel.add(deleteButton);
		buttonPanel.add(deleteAllButton);
		buttonPanel.add(payStubsButton);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		/**
		 * Adding ActionListener (event) for Pay stubs button
		 */
		payStubsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow > -1) {

					String value = table.getModel().getValueAt(selectedRow, 0).toString();

					/**
					 * calling function to get pay stub detail
					 */
					ShowPaystubsFrame.showPaystubDetails(value);
					
				} else {
					JOptionPane.showMessageDialog(MainWindowFrame.this, "Please select a row to view details.",
							"No Row Selected", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		/**
		 * Adding ActionListener (event) for update button
		 */
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow > -1) {

					String value = table.getModel().getValueAt(selectedRow, 0).toString();
					/**
					 * calling fuction for updation
					 */
					UpdateEmployeeFrame.updateEmployee(value);
					
				} else {
					JOptionPane.showMessageDialog(MainWindowFrame.this, "Please select a row to update details.",
							"No Row Selected", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

		/**
		 * Adding ActionListener (event) for delete button
		 */
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow > -1) {

					String value = table.getModel().getValueAt(selectedRow, 0).toString();

					/**
					 * calling function for deletion
					 */
					DeleteEmployeeFrame.deleteEmployee(value);
					
				} else {
					JOptionPane.showMessageDialog(MainWindowFrame.this, "Please select a row to delete details.",
							"No Row Selected", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		/**
		 * Adding ActionListener (event) for delete All button
		 */
		deleteAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteAllEmployeeFrame.showDeleteAllEmployeesFrame();
            }
        });

		/**
		 * Adding ActionListener (event) for Menu bar menu buttons
		 * 
		 * ActionListener for Add Employee menu button
		 */
		addItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/**
				 * calling function to add new employee
				 */

				AddEmployeeFrame.addNewEmployeeWindow();
			}
		});

		/**
		 * ActionListener for import data from a file menu button
		 */
		importItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/**
				 * calling function to read from file
				 */
				ImportFromFileFrame.importFromFile();
			}
		});

		/**
		 * ActionListener to exports data in various format menu button
		 */
		exportItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				/**
				 * calling fuction to download employee list in various formats
				 * 
				 * passing -1 as id for downloading list of employee, method will identify
				 * whether to download all employee or single employee
				 */
				ExportToFileFrame.exportToFile(-1);
			}
		});

		/**
		 * ActionListener for Refresh menu button
		 * reloads the whole page
		 */
		refreshItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getInstance();
				EmployeePayrollApplication.main(new String[] {});
			}
		});

		setVisible(true);
	}

	
	/**
	 * function to dispose main frame
	 */
	public static void getInstance() {
        instance.dispose();
    }
	
}

package com.employeePayroll.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.employeePayroll.EmployeePayrollApplication;
import com.employeePayroll.utilityClasses.ServiceClass;

public class DeleteAllEmployeeFrame {

    /**
     * Function to create and display the frame for deleting all employee records.
     */
    public static void showDeleteAllEmployeesFrame() {
    	
    	/**
		 * creating new frame for deletion confirmation
		 */
        JFrame deleteFrame = new JFrame("Delete All Employee Details");
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        panel.add(new JLabel("Are you sure, You want to delete all Employee details?"));
        
        /**
		 * adding button (yes, no)
		 */
        JButton yes = new JButton("YES");
        JButton no = new JButton("NO");

        buttonPanel.add(yes);
        buttonPanel.add(no);

        /**
		 * adding event on yes button to confirm delete from database
		 */
        yes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int result = ServiceClass.deleteAllEmployees();
                    if (result >= 0) {
                        JOptionPane.showMessageDialog(deleteFrame, "All Employees deleted successfully.",
                                "All Employees Deleted", JOptionPane.INFORMATION_MESSAGE);
                        
                        deleteFrame.dispose();
                        MainWindowFrame.getInstance();
                        EmployeePayrollApplication.main(new String[] {});
                        
                    } else {
                        JOptionPane.showMessageDialog(deleteFrame, "Failed to delete all Employees, Please try again.",
                                "Try Again", JOptionPane.WARNING_MESSAGE);
                        
                        deleteFrame.dispose();
                    }
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(deleteFrame, "Something went wrong, Please try again.",
                            "Try Again", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        /**
		 * adding event on no button to cancel deletion
		 */
        no.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                deleteFrame.dispose();
            }
        });

        panel.setPreferredSize(new Dimension(400, 80));
        deleteFrame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        deleteFrame.getContentPane().add(panel);
        deleteFrame.pack();
        deleteFrame.setLocationRelativeTo(null);
        deleteFrame.setVisible(true);
    }
}

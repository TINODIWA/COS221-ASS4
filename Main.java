/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.u24825532u24580482.assignment4;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
/**
 *
 * @author natha
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Northwind Traders GUI");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 600);
            
            JTabbedPane tabbedPane = new JTabbedPane();
            
            ReportTab reportTab = new ReportTab();
            NotificationsTab notifTab = new NotificationsTab();
            
            tabbedPane.addTab("Employees", new EmployeesTab());
            tabbedPane.addTab("Products", new ProductsTab());
            tabbedPane.addTab("Report", reportTab);
            tabbedPane.addTab("Notifications", notifTab);

            //Listen for tab changes
            tabbedPane.addChangeListener((ChangeEvent n) -> {
                int selectedIndex = tabbedPane.getSelectedIndex();
                String selectedTitle = tabbedPane.getTitleAt(selectedIndex);
                
                if("Report".equals(selectedTitle)) {
                    reportTab.refreshOnTabOpen();
                }
                if("Notifications".equals(selectedTitle)) {
                    notifTab.refreshOnTabOpen();
                }
            });
            
            frame.add(tabbedPane);
            frame.setVisible(true);
        });
    }
}

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
            JFrame frame = new JFrame("Northwind Employees GUI");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 600);
            
            JTabbedPane tabbedPane = new JTabbedPane();
            
            ReportTab reportTab = new ReportTab();
            
            tabbedPane.addTab("Employees", new EmployeesTab());
            tabbedPane.addTab("Products", new ProductsTab());
            tabbedPane.addTab("Reports", reportTab);
//            tabbedPane.addTab("Products", new NotificationsTab());

            //Listen for tab changes
            tabbedPane.addChangeListener((ChangeEvent n) -> {
                int selectedIndex = tabbedPane.getSelectedIndex();
                String selectedTitle = tabbedPane.getTitleAt(selectedIndex);
                
                if("Reports".equals(selectedTitle)) {
                    reportTab.refreshOnTabOpen();
                }
            });
            
            frame.add(tabbedPane);
            frame.setVisible(true);
        });
    }
}

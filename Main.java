/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.u24825532u24580482.assignment4;

import javax.swing.*;
/**
 *
 * @author natha
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Northwind Employees GUI");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            
            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.addTab("Employees", new EmployeesTab());
            tabbedPane.addTab("Products", new ProductsTab());
//            tabbedPane.addTab("Products", new ReportTab());
//            tabbedPane.addTab("Products", new NotificationsTab());
            
            frame.add(tabbedPane);
            frame.setVisible(true);
        });
    }
}

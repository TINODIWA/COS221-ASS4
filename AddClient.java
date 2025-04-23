/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.u24825532u24580482.assignment4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
/**
 *
 * @author natha
 */
public class AddClient extends JDialog{
    private JTextField firstNameField, lastNameField, emailField, companyField, phoneField;
    private JButton saveButton, cancelButton;

    public AddClient(NotificationsTab parent) {
        setTitle("Add Client");
        setModal(true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(6, 2, 10, 10));

        firstNameField = new JTextField();
        lastNameField = new JTextField();
        emailField = new JTextField();
        companyField = new JTextField();
        phoneField = new JTextField();

        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        add(new JLabel("First Name:")); add(firstNameField);
        add(new JLabel("Last Name:")); add(lastNameField);
        add(new JLabel("Email:")); add(emailField);
        add(new JLabel("Company:")); add(companyField);
        add(new JLabel("Phone:")); add(phoneField);
        add(cancelButton); add(saveButton);

        saveButton.addActionListener(e -> {
            Connection conn = null;
            PreparedStatement pstmt = null;
            
            try {
                conn = U24825532u24580482Assignment4.connect();
                pstmt = conn.prepareStatement("INSERT INTO customers (first_name, last_name, email_address, company, phone) "
                    + "VALUES (?, ?, ?, ?, ?)");

                pstmt.setString(1, firstNameField.getText().trim());
                pstmt.setString(2, lastNameField.getText().trim());
                pstmt.setString(3, emailField.getText().trim());
                pstmt.setString(4, companyField.getText().trim());
                pstmt.setString(5, phoneField.getText().trim());

                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Client added.");
                parent.refreshOnTabOpen();
                dispose();

            } catch (SQLException n) {
                n.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to add client.");
            }
            finally {
                try {
                    if (pstmt != null) pstmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException n) {
                    System.out.println("Error closing resources: " + n.getMessage());
                }
            }
        });

        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }
}

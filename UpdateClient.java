/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.u24825532u24580482.assignment4;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
/**
 *
 * @author natha
 */
public class UpdateClient extends JDialog{
    private JTextField firstNameField, lastNameField, emailField, companyField, phoneField;
    private JButton saveButton, cancelButton;
    private int clientId;
    
    public UpdateClient(NotificationsTab parent, int clientId){
        this.clientId = clientId;

        setTitle("Edit Client");
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

        getClientData();

        saveButton.addActionListener(e -> {
            Connection conn = null;
            PreparedStatement pstmt = null;
            
            try {
                conn = U24825532u24580482Assignment4.connect();
                pstmt = conn.prepareStatement("UPDATE customers "
                    + "SET first_name = ?, last_name = ?, email_address = ?, company = ?, phone = ? "
                    + "WHERE id = ?");

                pstmt.setString(1, firstNameField.getText().trim());
                pstmt.setString(2, lastNameField.getText().trim());
                pstmt.setString(3, emailField.getText().trim());
                pstmt.setString(4, companyField.getText().trim());
                pstmt.setString(5, phoneField.getText().trim());
                pstmt.setInt(6, clientId);

                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Client updated.");
                parent.refreshOnTabOpen();
                dispose();

            } catch (SQLException n) {
                n.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to update client.");
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

    private void getClientData() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = U24825532u24580482Assignment4.connect();
            pstmt = conn.prepareStatement("SELECT * FROM customers WHERE id = ?");

            pstmt.setInt(1, clientId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                firstNameField.setText(rs.getString("first_name"));
                lastNameField.setText(rs.getString("last_name"));
                emailField.setText(rs.getString("email_address"));
                companyField.setText(rs.getString("company"));
                phoneField.setText(rs.getString("phone"));
            }

        } catch (SQLException n) {
            n.printStackTrace();
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException n) {
                System.out.println("Error closing resources: " + n.getMessage());
            }
        }
    }
}

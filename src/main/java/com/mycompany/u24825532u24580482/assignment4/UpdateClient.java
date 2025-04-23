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
    private JTextField company_field, first_name_field, last_name_field, email_field, job_title_field, bus_phone_field, home_phone_field, 
            mobile_phone_field, fax_number_field, address_field, city_field, state_province_field, zip_postal_code_field,
            country_region_field, web_page_field, notes_field, attachments_field;
    private JButton save_button, cancel_button;
    private int client_id;
    
    public UpdateClient(NotificationsTab parent, int client_ID){
        this.client_id = client_ID;

        setTitle("Update Client");
        setModal(true);
        setSize(1000, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(6, 2, 10, 10));

        company_field = new JTextField();
        first_name_field = new JTextField();
        last_name_field = new JTextField();
        email_field = new JTextField();
        job_title_field = new JTextField();
        bus_phone_field = new JTextField();
        home_phone_field = new JTextField();
        mobile_phone_field = new JTextField();
        fax_number_field = new JTextField();
        address_field = new JTextField();
        city_field = new JTextField();
        state_province_field = new JTextField();
        zip_postal_code_field = new JTextField();
        country_region_field = new JTextField();
        web_page_field = new JTextField();
        notes_field = new JTextField();
        attachments_field = new JTextField();

        save_button = new JButton("Save");
        cancel_button = new JButton("Cancel");

        add(new JLabel("Company:")); add(company_field);
        add(new JLabel("First Name:")); add(first_name_field);
        add(new JLabel("Last Name:")); add(last_name_field);
        add(new JLabel("Email:")); add(email_field);
        add(new JLabel("Job Title:")); add(job_title_field);
        add(new JLabel("Business Phone:")); add(bus_phone_field);
        add(new JLabel("Home Phone:")); add(home_phone_field);
        add(new JLabel("Mobile Phone:")); add(mobile_phone_field);
        add(new JLabel("Fax Number:")); add(fax_number_field);
        add(new JLabel("Address:")); add(address_field);
        add(new JLabel("City:")); add(city_field);
        add(new JLabel("State Province:")); add(state_province_field);
        add(new JLabel("Zip Postal Code:")); add(zip_postal_code_field);
        add(new JLabel("Country Region:")); add(country_region_field);
        add(new JLabel("Web Page:")); add(web_page_field);
        add(new JLabel("Notes:")); add(notes_field);
        add(new JLabel("Attachments:")); add(attachments_field);
        
        add(save_button);
        add(cancel_button); 

        getClientData();

        save_button.addActionListener(e -> {
            Connection conn = null;
            PreparedStatement pstmt = null;
            
            try {
                conn = U24825532u24580482Assignment4.connect();
                pstmt = conn.prepareStatement("UPDATE customers "
                    + "SET company = ?, last_name = ?, first_name = ?, email_address = ?, job_title = ?, business_phone = ?, home_phone = ?, "
                    + "mobile_phone = ?, fax_number = ?, address = ?, city = ?, state_province = ?, zip_postal_code = ?, country_region = ?, "
                    + "web_page = ?, notes = ?, attachments = ? "
                    + "WHERE id = ?");

                pstmt.setString(1, company_field.getText().trim());
                pstmt.setString(2, last_name_field.getText().trim());
                pstmt.setString(3, first_name_field.getText().trim());
                pstmt.setString(4, email_field.getText().trim());
                pstmt.setString(5, job_title_field.getText().trim());
                pstmt.setString(6, bus_phone_field.getText().trim());
                pstmt.setString(7, home_phone_field.getText().trim());
                pstmt.setString(8, mobile_phone_field.getText().trim());
                pstmt.setString(9, fax_number_field.getText().trim());
                pstmt.setString(10, address_field.getText().trim());
                pstmt.setString(11, city_field.getText().trim());
                pstmt.setString(12, state_province_field.getText().trim());
                pstmt.setString(13, zip_postal_code_field.getText().trim());
                pstmt.setString(14, country_region_field.getText().trim());
                pstmt.setString(15, web_page_field.getText().trim());
                pstmt.setString(16, notes_field.getText().trim());
                pstmt.setString(17, attachments_field.getText().trim());
                
                pstmt.setInt(18, client_ID);

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

        cancel_button.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void getClientData() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = U24825532u24580482Assignment4.connect();
            pstmt = conn.prepareStatement("SELECT * FROM customers WHERE id = ?");

            pstmt.setInt(1, client_id);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                company_field.setText(rs.getString("company"));
                last_name_field.setText(rs.getString("last_name"));
                first_name_field.setText(rs.getString("first_name"));
                email_field.setText(rs.getString("email_address"));
                job_title_field.setText(rs.getString("job_title"));
                bus_phone_field.setText(rs.getString("business_phone"));
                home_phone_field.setText(rs.getString("home_phone"));
                mobile_phone_field.setText(rs.getString("mobile_phone"));
                fax_number_field.setText(rs.getString("fax_number"));
                address_field.setText(rs.getString("address"));
                city_field.setText(rs.getString("city"));
                state_province_field.setText(rs.getString("state_province"));
                zip_postal_code_field.setText(rs.getString("zip_postal_code"));
                country_region_field.setText(rs.getString("country_region"));
                web_page_field.setText(rs.getString("web_page"));
                notes_field.setText(rs.getString("notes"));
                attachments_field.setText(rs.getString("attachments"));
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

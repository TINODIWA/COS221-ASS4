/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.u24825532u24580482.assignment4;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
/**
 *
 * @author natha
 */
public class EmployeesTab extends JPanel {
    private final JTable table;
    private final DefaultTableModel dTableModel;
    private JTextField search_field;

    public EmployeesTab() {
        setLayout(new BorderLayout());
        
        //Setup UI components
        JPanel search_panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        search_field = new JTextField(20);
        JButton search_button = new JButton("Search");
        
        search_panel.add(new JLabel("Search by Name or City:"));
        search_panel.add(search_field);
        search_panel.add(search_button);
        
        add(search_panel, BorderLayout.NORTH);

        // Define table columns & setup table
        String[] columns = {"First Name", "Last Name", "Address", "Adress Line 2", "City", "Region", "Postal Code", "Phone"
                                ,"Work Place", "Active"};
        dTableModel = new DefaultTableModel(columns, 0);
        table = new JTable(dTableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Load data from database initially
        getEmployeeData(null);

        //Search button logic
        search_button.addActionListener(n -> {
            String filter = search_field.getText().trim();
            getEmployeeData(filter);
        });
    }

    private void getEmployeeData(String filter) {
        //clear table
        dTableModel.setRowCount(0);
        
        Connection conn = null;
        //prepared to stop sql injection
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        //sql query
        String query = "SELECT first_name, last_name, email_address, address, city, country_region"
                     + ", zip_postal_code, business_phone, company, job_title FROM employees";
        
        if(filter != null && !filter.isEmpty()){
            query += " WHERE first_name LIKE ? "
                    + "OR last_name LIKE ? "
                    + "OR city LIKE ?";
            System.out.println("DEBUG: Filtered Query = " + query);
        }else {
            System.out.println("DEBUG: Unfiltered Query = " + query);
        }
        
        try {
            conn = U24825532u24580482Assignment4.connect();
            pstmt = conn.prepareStatement(query);
            
            if(filter != null && !filter.isEmpty()){
                String like = "%" + filter + "%";
                pstmt.setString(1, like);
                pstmt.setString(2, like);
                pstmt.setString(3, like);
            }
            
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Object[] row = {
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email_address"),
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getString("country_region"),
                        rs.getString("zip_postal_code"),
                        rs.getString("business_phone"),
                        rs.getString("company"),
                        rs.getString("job_title")
                };
                dTableModel.addRow(row);
            }
        } catch (SQLException n) {
            System.out.println("Couldn't load employees: " + n.getMessage());
        }
        finally {
            try{
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if(conn != null) conn.close();
            } catch(SQLException n){
                System.out.println("Error closing connection: " + n.getMessage());
            }
        }
    }
}


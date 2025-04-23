package com.mycompany.u24825532u24580482.assignment4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author natha
 */
public class ProductsTab extends JPanel {
    private final JTable table;
    private final DefaultTableModel tableModel;
    private NotificationsTab notifTab;
//    private JTextField searchField;

    public ProductsTab(NotificationsTab notifTab) {
        this.notifTab = notifTab;
        setLayout(new BorderLayout());

        // Define table columns & setup table
        String[] columns = {"Supplier ID(s)", "ID", "Product Code", "Product Name", "Description", "Standard Cost", "List Price", "Reorder level"
                                ,"Target Level", "Quantity Per Unit", "Discontinued", "Minimum Reorder Quantity", "Category", "Attachments"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        //Button panel
        JButton addButton = new JButton("Add Product");
//        addButton.addActionListener(n -> openAddProduct());
        addButton.addActionListener(n -> new AddProduct(this, notifTab));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.add(addButton);
        add(topPanel, BorderLayout.NORTH);
        
        // Load data from database initially
        getProductsData();
    }

    private void getProductsData() {
        //clear table
        tableModel.setRowCount(0);
        
        Connection conn = null;
        //prepared to stop sql injection
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        //sql query
        String query = "SELECT * " +
                       "FROM products";
        
        try {
            conn = U24825532u24580482Assignment4.connect();
            pstmt = conn.prepareStatement(query);
            
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Object[] row = {
                        rs.getString("supplier_ids"),
                        rs.getInt("id"),
                        rs.getString("product_code"),
                        rs.getString("product_name"),
                        rs.getString("description"),
                        rs.getBigDecimal("standard_cost"),
                        rs.getBigDecimal("list_price"),
                        rs.getInt("reorder_level"),
                        rs.getInt("target_level"),
                        rs.getString("quantity_per_unit"),
                        rs.getBoolean("discontinued"),
                        rs.getInt("minimum_reorder_quantity"),
                        rs.getString("category"),
                        rs.getBinaryStream("attachments")
                };
                tableModel.addRow(row);
            }
        } catch (SQLException n) {
            System.out.println("Couldn't load products: " + n.getMessage());
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
    
//    private void openAddProduct(){
//        new AddProduct(this);
//    }
    
    public void refreshTable(){
        getProductsData();
    }
}

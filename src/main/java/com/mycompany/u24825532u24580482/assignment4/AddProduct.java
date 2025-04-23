/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.u24825532u24580482.assignment4;

import javax.swing.*;
import java.awt.*;
//import java.math.BigDecimal;
import java.sql.*;
/**
 *
 * @author natha
 */
public class AddProduct extends JDialog {
    private JTextField product_code_field, product_name_field, description_field, 
            standard_cost_field, list_price_field, reorder_level_field, target_level_field, 
            quantity_per_unit_field, discontinued_field, minimum_reorder_quantity_field, attachments_field;
    private JComboBox<String> supplier_id_box, category_box;
    private NotificationsTab notifTab;
    
    public AddProduct(ProductsTab parent, NotificationsTab notifTab){
        this.notifTab = notifTab;
        setTitle("Add New Product");
        setModal(true);
        setSize(1000, 500);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(6, 2, 5, 5));

        supplier_id_box = new JComboBox<>();
//        id_field = new JTextField();
        product_code_field = new JTextField();
        product_name_field = new JTextField();
        description_field = new JTextField();
        standard_cost_field = new JTextField();
        list_price_field = new JTextField();
        reorder_level_field = new JTextField();
        target_level_field = new JTextField();
        quantity_per_unit_field = new JTextField();
        discontinued_field = new JTextField();
        minimum_reorder_quantity_field = new JTextField();
        category_box = new JComboBox<>();
        attachments_field = new JTextField();
        
        add(new JLabel("Supplier ID(s):"));
        add(supplier_id_box);
//        add(new JLabel ("ID:"));
//        add(id_field);
        add(new JLabel("Product Code:"));
        add(product_code_field);
        add(new JLabel("Product Name:"));
        add(product_name_field);
        add(new JLabel("Description:"));
        add(description_field);
        add(new JLabel("Standard Cost:"));
        add(standard_cost_field);
        add(new JLabel("List price:"));
        add(list_price_field);
        add(new JLabel("Reorder Level:"));
        add(reorder_level_field);
        add(new JLabel("Target Level:"));
        add(target_level_field);
        add(new JLabel("Quantity Per Unit:"));
        add(quantity_per_unit_field);
        add(new JLabel("Discontinued:"));
        add(discontinued_field);
        add(new JLabel("Minimum Reorder Quantity:"));
        add(minimum_reorder_quantity_field);
        add(new JLabel("Category:"));
        add(category_box);
        add(new JLabel("Attachments:"));
        add(attachments_field);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(n -> addProduct(parent));
        add(new JLabel());
        add(addButton);

        loadDropdowns();

        setVisible(true);
    }
    
    private void loadDropdowns() {
        Connection conn = null;
        try {
            conn = U24825532u24580482Assignment4.connect();
            
            // Load distinct supplier IDs from products
            String supplierQuery = "SELECT id FROM suppliers";
            PreparedStatement pstmt1 = null, pstmt2 = null;
            ResultSet rs1 = null, rs2 = null;
            try {
                pstmt1 = conn.prepareStatement(supplierQuery);
                rs1 = pstmt1.executeQuery();
                
                supplier_id_box.addItem("");
                while (rs1.next()) {
                    String supplierId = rs1.getString("id");
                    
                    if (supplierId != null && !supplierId.isEmpty()) {
                        supplier_id_box.addItem(supplierId);
                    }
                }
            }catch (SQLException n) {
                System.out.println("Couldn't load supplier ids: " + n.getMessage());
            }
            finally {
                try{
                    if (rs1 != null) rs1.close();
                    if (pstmt1 != null) pstmt1.close();
                } catch(SQLException n){
                    System.out.println("Error closing connection: " + n.getMessage());
                }
            }

            // Load distinct categories from products
            String categoryQuery = "SELECT DISTINCT category FROM products WHERE category IS NOT NULL";
            try {
                pstmt2 = conn.prepareStatement(categoryQuery);
//                pstmt2.setString(1, "IS NOT NULL");
                rs2 = pstmt2.executeQuery();
                
                category_box.addItem("");
                while (rs2.next()) {
                    String category = rs2.getString("category");
                    
                    if (category != null && !category.isEmpty()) {
                        category_box.addItem(category);
                    }
                }
            }catch (SQLException n) {
                System.out.println("Couldn't load categories: " + n.getMessage());
            }
            finally {
                try{
                    if (rs2 != null) rs2.close();
                    if (pstmt2 != null) pstmt2.close();
                } catch(SQLException n){
                    System.out.println("Error closing connection: " + n.getMessage());
                }
            }

        } 
//        catch (SQLException n) {
//            JOptionPane.showMessageDialog(this, "Error loading dropdowns: " + .getMessage());
//        }
        finally {
            try{
                if(conn != null) conn.close();
            } catch(SQLException n){
                System.out.println("Error closing connection: " + n.getMessage());
            }
        }
    }

    private void addProduct(ProductsTab parent) {
        // Retrieve and trim input
//        String idText = id_field.getText().trim();
        String supplierId = (String) supplier_id_box.getSelectedItem();
        String productCode = product_code_field.getText().trim();
        String productName = product_name_field.getText().trim();
        String description = description_field.getText().trim();
        String standardCostText = standard_cost_field.getText().trim();
        String listPriceText = list_price_field.getText().trim();
        String reorderLevelText = reorder_level_field.getText().trim();
        String targetLevelText = target_level_field.getText().trim();
        String quantityPerUnit = quantity_per_unit_field.getText().trim();
        String discontinuedText = discontinued_field.getText().trim();
        String minReorderQtyText = minimum_reorder_quantity_field.getText().trim();
        String category = (String) category_box.getSelectedItem();
        String attachments = attachments_field.getText().trim();

        // Validation
        if (listPriceText.isEmpty() || discontinuedText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill at List Price and Discontinued.");
            return;
        }
        if (discontinuedText.toLowerCase().equals("true") || discontinuedText.toLowerCase().equals("false")) {
            
        }else{
            JOptionPane.showMessageDialog(this, "Please only fill in True or False for Discontinued.\n\nNote: It is not case sensitive.");
            return;
        }

        try {
            // Optional parsing, depending on DB column types
//            int id = idText.isEmpty() ? 0 : Integer.parseInt(idText);
//            BigDecimal p;
            double standardCost = standardCostText.isEmpty() ? 0 : Double.parseDouble(standardCostText);
            double listPrice = Double.parseDouble(listPriceText);
            int reorderLevel = reorderLevelText.isEmpty() ? 0 : Integer.parseInt(reorderLevelText);
            int targetLevel = targetLevelText.isEmpty() ? 0 : Integer.parseInt(targetLevelText);
            boolean discontinued = discontinuedText.equalsIgnoreCase("true") || discontinuedText.equals("1");
            int minReorderQty = minReorderQtyText.isEmpty() ? 0 : Integer.parseInt(minReorderQtyText);

            // Prepare insert
            String sql = "INSERT INTO products (supplier_ids, product_code, product_name, description, standard_cost, list_price, " +
                         "reorder_level, target_level, quantity_per_unit, discontinued, minimum_reorder_quantity, " +
                         "category, attachments) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            Connection conn = null;
            PreparedStatement pstmt = null;
            
            try {
                conn = U24825532u24580482Assignment4.connect();
                pstmt = conn.prepareStatement(sql);

//                pstmt.setInt(1, id);
                pstmt.setString(1, supplierId);
                pstmt.setString(2, productCode);
                pstmt.setString(3, productName);
                pstmt.setString(4, description);
                pstmt.setBigDecimal(5, new java.math.BigDecimal(standardCost));
                pstmt.setBigDecimal(6, new java.math.BigDecimal(listPrice));
                pstmt.setInt(7, reorderLevel);
                pstmt.setInt(8, targetLevel);
                pstmt.setString(9, quantityPerUnit);
                pstmt.setBoolean(10, discontinued);
                pstmt.setInt(11, minReorderQty);
                pstmt.setString(12, category);
                pstmt.setString(13, attachments);

                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "GREAT!!! Product has been added successfully!");
                notifTab.addNewProductNotif(productName);
                parent.refreshTable();
                dispose();

            } catch (SQLException n) {
                JOptionPane.showMessageDialog(this, "Sorry.  Error inserting product: " + n.getMessage());
            }
            finally{
                try{
                    if(conn != null) conn.close();
                    if(pstmt != null) pstmt.close();
                }
                catch(SQLException n){
                    System.out.println("Error closing connection: " + n.getMessage());
                }
            }

        } catch (NumberFormatException n) {
            JOptionPane.showMessageDialog(this, "Please ensure you use numeric values for numeric.");
        } 
    }
    
//    public AddProduct(ProductsTab parent, NotificationsTab notifTab){
//        this.notifTab = notifTab;
//    }
}

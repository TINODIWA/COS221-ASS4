/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.u24825532u24580482.assignment4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
/**
 *
 * @author natha
 */
public class NotificationsTab extends JPanel{
    private JTable all_clients_table, inactive_clients_table;
    private DefaultTableModel all_clients_model, inactive_clients_model;
    private JTextField search_field;
    private JButton addBtn, updateBtn, deleteBtn, dropCusTableBtn;
    private DefaultListModel<String> notifModel;
    private JList<String> notifList;

    public NotificationsTab() {
        setLayout(new BorderLayout());

        // Top Panel for Search + Buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        search_field = new JTextField();
        search_field.setToolTipText("Search clients...");
        topPanel.add(search_field, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addBtn = new JButton("Add");
        updateBtn = new JButton("Edit");
        deleteBtn = new JButton("Delete");
        dropCusTableBtn = new JButton("Drop Table");
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(dropCusTableBtn);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Center Panel for Tables
        JTabbedPane tabbedPane = new JTabbedPane();

        all_clients_model = new DefaultTableModel();
        all_clients_table = new JTable(all_clients_model);
        JScrollPane scrollAll = new JScrollPane(all_clients_table);
        tabbedPane.add("All Clients", scrollAll);

        inactive_clients_model = new DefaultTableModel();
        inactive_clients_table = new JTable(inactive_clients_model);
        JScrollPane scrollInactive = new JScrollPane(inactive_clients_table);
        tabbedPane.add("Inactive Clients", scrollInactive);

        add(tabbedPane, BorderLayout.CENTER);
        
        notifModel = new DefaultListModel<>();
        notifList = new JList<>(notifModel);

        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.setBorder(BorderFactory.createTitledBorder("!!! Notifications !!!"));
        messagePanel.add(new JScrollPane(notifList), BorderLayout.CENTER);

        add(messagePanel, BorderLayout.SOUTH);
        
        // Event Listeners
        search_field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchClients(search_field.getText());
            }
        });

        addBtn.addActionListener(n -> new AddClient(this));
        updateBtn.addActionListener(n -> updateClient());
        deleteBtn.addActionListener(n -> deleteClient());
        dropCusTableBtn.addActionListener(n -> dropCustTable());

        // Initial Load
        getAllClients();
        getInactiveClients();
    }

    private void getAllClients() {
        all_clients_model.setRowCount(0);
        all_clients_model.setColumnCount(0);
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSetMetaData meta = null;
        
        try {
            conn = U24825532u24580482Assignment4.connect();
            pstmt = conn.prepareStatement("SELECT * FROM customers");
            rs = pstmt.executeQuery();
            meta = rs.getMetaData();

            for (int i = 1; i <= meta.getColumnCount(); i++) {
                all_clients_model.addColumn(meta.getColumnName(i));
            }

            while (rs.next()) {
                Object[] row = new Object[meta.getColumnCount()];
                for (int i = 0; i < meta.getColumnCount(); i++) {
                    row[i] = rs.getObject(i + 1);
                }
                all_clients_model.addRow(row);
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

    private void getInactiveClients() {
        inactive_clients_model.setRowCount(0);
        inactive_clients_model.setColumnCount(0);
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSetMetaData meta = null;

        try {
            conn = U24825532u24580482Assignment4.connect();
            
            pstmt = conn.prepareStatement(
                "SELECT c.* FROM customers c "
                + "LEFT JOIN orders o ON c.id = o.customer_id "
                + "WHERE o.customer_id IS NULL"
             );

            rs = pstmt.executeQuery();
            meta = rs.getMetaData();

            for (int i = 1; i <= meta.getColumnCount(); i++) {
                inactive_clients_model.addColumn(meta.getColumnName(i));
            }

            while (rs.next()) {
                Object[] row = new Object[meta.getColumnCount()];
                for (int i = 0; i < meta.getColumnCount(); i++) {
                    row[i] = rs.getObject(i + 1);
                }
                inactive_clients_model.addRow(row);
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

    private void searchClients(String keyword) {
        all_clients_model.setRowCount(0);

        String query = "SELECT * FROM customers "
//            + "WHERE first_name LIKE ? OR last_name LIKE ? OR company LIKE ? OR email_address LIKE ?";
              + "WHERE company LIKE ? OR last_name LIKE ? OR first_name LIKE ? OR email_address LIKE ? OR job_title LIKE ? OR business_phone LIKE ? OR home_phone LIKE ? OR "
                    + "mobile_phone LIKE ? OR fax_number LIKE ? OR address LIKE ? OR city LIKE ? OR state_province LIKE ? OR zip_postal_code LIKE ? OR country_region LIKE ? OR "
                    + "web_page LIKE ? OR notes LIKE ? OR attachments LIKE ? ";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSetMetaData meta = null;

        try {
            conn = U24825532u24580482Assignment4.connect();
            pstmt = conn.prepareStatement(query);

            for (int i = 1; i <= 17; i++) {
                pstmt.setString(i, "%" + keyword + "%");
            }

            rs = pstmt.executeQuery();
            meta = rs.getMetaData();

            // If model has no columns (after full reset), re-add columns
            if (all_clients_model.getColumnCount() == 0) {
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    all_clients_model.addColumn(meta.getColumnName(i));
                }
            }

            while (rs.next()) {
                Object[] row = new Object[meta.getColumnCount()];
                for (int i = 0; i < meta.getColumnCount(); i++) {
                    row[i] = rs.getObject(i + 1);
                }
                all_clients_model.addRow(row);
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

    private void updateClient() {
        int selected = all_clients_table.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Select a client to edit.");
            return;
        }

        int id = (int) all_clients_model.getValueAt(selected, 0);
        new UpdateClient(this, id);
    }

    private void deleteClient() {
        int selected = all_clients_table.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Select a client to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        int id = (int) all_clients_model.getValueAt(selected, 0);
        
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = U24825532u24580482Assignment4.connect();
            pstmt = conn.prepareStatement("DELETE FROM customers WHERE id = ?");

            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            getAllClients();
            getInactiveClients();

        } catch (SQLException n) {
            n.printStackTrace();
        }
        finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException n) {
                System.out.println("Error closing resources: " + n.getMessage());
            }
        }
    }

    public void refreshOnTabOpen() {
        getAllClients();
        getInactiveClients();
    }
    
    public void addNewProductNotif(String product_name) {
        if("".equals(product_name))
        {
            notifModel.addElement("A NEW Product has been added - Let clients know.");
        }
        else{
            notifModel.addElement("A NEW Product has been added " + product_name + " - Let clients know.");
        }
    }
    
    public void dropCustTable() {
        int askForConfirmation = JOptionPane.showConfirmDialog(this,
            "Are you certain you want to drop the 'customers' table? This action can't be reversed.",
            "⚠️ Confirm Drop", JOptionPane.YES_NO_OPTION);

        if (askForConfirmation != JOptionPane.YES_OPTION) return;
        
        String delQuery = "DROP TABLE IF EXISTS customers";
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = U24825532u24580482Assignment4.connect();
            pstmt = conn.prepareStatement(delQuery);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "'customers' table has been dropped.");

        } catch (SQLException n) {
            JOptionPane.showMessageDialog(this, "Failed to drop table: " + n.getMessage());
            n.printStackTrace();
        }
        finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException n) {
                System.out.println("Error closing resources: " + n.getMessage());
            }
        }
    }

}

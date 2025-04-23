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
    private JTable allClientsTable, inactiveClientsTable;
    private DefaultTableModel allClientsModel, inactiveClientsModel;
    private JTextField searchField;
    private JButton addBtn, updateBtn, deleteBtn;

    public NotificationsTab() {
        setLayout(new BorderLayout());

        // Top Panel for Search + Buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        searchField = new JTextField();
        searchField.setToolTipText("Search clients...");
        topPanel.add(searchField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addBtn = new JButton("Add");
        updateBtn = new JButton("Edit");
        deleteBtn = new JButton("Delete");
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Center Panel for Tables
        JTabbedPane tabbedPane = new JTabbedPane();

        allClientsModel = new DefaultTableModel();
        allClientsTable = new JTable(allClientsModel);
        JScrollPane scrollAll = new JScrollPane(allClientsTable);
        tabbedPane.add("All Clients", scrollAll);

        inactiveClientsModel = new DefaultTableModel();
        inactiveClientsTable = new JTable(inactiveClientsModel);
        JScrollPane scrollInactive = new JScrollPane(inactiveClientsTable);
        tabbedPane.add("Inactive Clients", scrollInactive);

        add(tabbedPane, BorderLayout.CENTER);
        
        // Event Listeners
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchClients(searchField.getText());
            }
        });

        addBtn.addActionListener(n -> new AddClient(this));
        updateBtn.addActionListener(n -> updateClient());
        deleteBtn.addActionListener(n -> deleteClient());

        // Initial Load
        getAllClients();
        getInactiveClients();
    }

    private void getAllClients() {
        allClientsModel.setRowCount(0);
        allClientsModel.setColumnCount(0);
        
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
                allClientsModel.addColumn(meta.getColumnName(i));
            }

            while (rs.next()) {
                Object[] row = new Object[meta.getColumnCount()];
                for (int i = 0; i < meta.getColumnCount(); i++) {
                    row[i] = rs.getObject(i + 1);
                }
                allClientsModel.addRow(row);
            }

        } catch (SQLException n) {
            n.printStackTrace();
        }
    }

    private void getInactiveClients() {
        inactiveClientsModel.setRowCount(0);
        inactiveClientsModel.setColumnCount(0);
        
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
                inactiveClientsModel.addColumn(meta.getColumnName(i));
            }

            while (rs.next()) {
                Object[] row = new Object[meta.getColumnCount()];
                for (int i = 0; i < meta.getColumnCount(); i++) {
                    row[i] = rs.getObject(i + 1);
                }
                inactiveClientsModel.addRow(row);
            }

        } catch (SQLException n) {
            n.printStackTrace();
        }
    }

    private void searchClients(String keyword) {
        allClientsModel.setRowCount(0);

        String query = "SELECT * FROM customers "
            + "WHERE first_name LIKE ? OR last_name LIKE ? OR company LIKE ? OR email_address LIKE ?";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSetMetaData meta = null;

        try {
            conn = U24825532u24580482Assignment4.connect();
            pstmt = conn.prepareStatement(query);

            for (int i = 1; i <= 4; i++) {
                pstmt.setString(i, "%" + keyword + "%");
            }

            rs = pstmt.executeQuery();
            meta = rs.getMetaData();

            // If model has no columns (after full reset), re-add columns
            if (allClientsModel.getColumnCount() == 0) {
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    allClientsModel.addColumn(meta.getColumnName(i));
                }
            }

            while (rs.next()) {
                Object[] row = new Object[meta.getColumnCount()];
                for (int i = 0; i < meta.getColumnCount(); i++) {
                    row[i] = rs.getObject(i + 1);
                }
                allClientsModel.addRow(row);
            }

        } catch (SQLException n) {
            n.printStackTrace();
        }
    }

    private void updateClient() {
        int selected = allClientsTable.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Select a client to edit.");
            return;
        }

        int id = (int) allClientsModel.getValueAt(selected, 0);
        new UpdateClient(this, id);
    }

    private void deleteClient() {
        int selected = allClientsTable.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Select a client to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        int id = (int) allClientsModel.getValueAt(selected, 0);
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ResultSetMetaData meta = null;

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
    }

    public void refreshTables() {
        getAllClients();
        getInactiveClients();
    }
}

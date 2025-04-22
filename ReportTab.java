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
public class ReportTab extends JPanel {
    private final JTable reportTable;
    private final DefaultTableModel tableModel;
    
    public ReportTab(){
        setLayout(new BorderLayout());
        
        String[] columns = {"Category", "Product Count"};
        tableModel = new DefaultTableModel(columns, 0);
        reportTable = new JTable(tableModel);
        add(new JScrollPane(reportTable), BorderLayout.CENTER);
        
        getReportData();
    }
    
    private void getReportData(){
        tableModel.setRowCount(0); 
        String query = "SELECT category, COUNT(*) AS product_count " 
                + "FROM products " 
                + "GROUP BY category " 
                + "ORDER BY category";
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = U24825532u24580482Assignment4.connect(); 
            pstmt = conn.prepareStatement(query);
//            pstmt.setString(1, "category");
//            pstmt.setString(2, "category");
            
            rs = pstmt.executeQuery(); 
            
            while (rs.next()) { 
                Object[] row = { 
//                    rs.getString("warehouse_name"), 
                    rs.getString("category"), 
                    rs.getInt("product_count") 
                }; 
                tableModel.addRow(row); 
            }
        } catch (SQLException n) { 
            JOptionPane.showMessageDialog(this, "Error making report: " + n.getMessage()); 
        } 
        finally{
            try{
                if(conn != null) conn.close();
                if(pstmt != null) pstmt.close();
                if(rs != null) rs.close();
            }
            catch(SQLException n){
                JOptionPane.showMessageDialog(this, "Error closing connection: " + n.getMessage());
            }
        }
    }
}

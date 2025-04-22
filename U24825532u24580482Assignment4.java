/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.u24825532u24580482.assignment4;

import java.sql.*;
//import java.util.Scanner;
/**
 *
 * @author natha
 */
public class U24825532u24580482Assignment4 {
    
    private static final String URL = "jdbc:mariadb://localhost:3307/u24825532u24580482_northwind";
    private static final String username = "root";
    private static final String password = "Grandnubian1";

    public static Connection connect() {
        Connection conn = null;
//        Scanner scanner = new Scanner(System.in);
        
        try{
//            System.out.print("Enter username: ");
//            String username = scanner.nextLine();
//            System.out.print("Enter password: ");
//            String password = scanner.nextLine();
            
            Class.forName("org.mariadb.jdbc.Driver");
            
            conn = DriverManager.getConnection(URL, username, password);
            
            System.out.print("Successfully connected to the database");
            
            return conn;
        }
        catch(ClassNotFoundException n){
            System.out.println("JDBC Driver not found: " + n.getMessage());
        }
        catch(SQLException n){
            System.out.println("Connection failed: " + n.getMessage());
        }
//        finally {
//            scanner.close();
//        }
        return null;
    }
}

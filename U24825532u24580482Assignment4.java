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
    
    private static String URL;
//    = "jdbc:mariadb://localhost:3307/u24825532u24580482_northwind";
    private static String db_username;
    private static String db_password;

    public static Connection connect() {
        Connection conn = null;
//        Scanner scanner = new Scanner(System.in);
        
        try{
//            System.out.print("Enter username: ");
//            String username = scanner.nextLine();
//            System.out.print("Enter password: ");
//            String password = scanner.nextLine();

            String db_proto = System.getenv("dvdrental_DB_PROTO");
            String db_host = System.getenv("dvdrental_DB_HOST");
            String db_port = System.getenv("dvdrental_DB_PORT");
            String db_name = System.getenv("dvdrental_DB_NAME");
            
            db_username = System.getenv("dvdrental_DB_USERNAME");
            db_password = System.getenv("dvdrental_DB_PASSWORD");
            URL = "jdbc:" + db_proto + "://" + db_host + ":" + db_port + "/" + db_name;
            
            Class.forName("org." + db_proto + ".jdbc.Driver");
            
            conn = DriverManager.getConnection(URL, db_username, db_password);
            
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

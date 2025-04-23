readme.txt — COS221 Assignment 4 (u24825532 & u24580482)

Project Overview
This Java Swing application has 4 tabs EmployeesTab, ProductTab, ReportTab and NotificationsTab. It serves as a management system, connected to a MariaDB instance using JDBC.

Requirements
- Java JDK 24 or higher
- Maven
- NetBeans (or any IDE that supports Maven)
- MariaDB Server running
- The `northwind` dataset imported into a database named:
  u24825532u24580482_northwind

Building and Running the Application
1. Open the project in NetBeans
2. Change password in .env file to the password for your MariaDB
3. Change the port to the port you use.
4. Right-click the project > Clean and Build
5. Then right-click > Run

The main class is:
com.mycompany.u24825532u24580482.assignment4.MainApp. You need to run the main class for everything to work.

Additional Notes
- Adding a new product will trigger a notification message in the Notifications tab.
- All database credentials are securely handled using environment variables.
- Dropping the customers table is permanent.


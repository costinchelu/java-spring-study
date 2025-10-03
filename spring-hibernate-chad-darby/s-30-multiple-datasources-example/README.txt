Multiple Data Sources in Spring

This project shows how to configure multiple datasources in Spring. 

In this project, we connect to two different databases: web_customer_tracker and employee_directory

The application uses the following architecture

                                            /---- EmployeeDAO
DemoController -> MultiDataSourceService ---
                                            \---- CustomerDAO


The complete source code for the project is available.

1. SQL Scripts
The SQL scripts are located in the directory:
sql-scripts
- customer.sql: creates the database schema "web_customer_tracker", also adds sample data
- employee.sql: creates the database schema "employee_directory", also adds sample data

You will need to run these scripts accordingly.

2. Data source configuration
The project the data source configurations in the config file: spring-mvc-crud-demo-servlet. 
This file defines two datasources. One datasource for customerDataSource and another for employeeDataSource. 
For each datasource defined, you need to add
- session factory
- transaction manager
- tx:annotation driven

3. Java DAO code
The project includes DAOs for Customer and Employee. Make note of the @Autowired for the respective session factory. Also make note of the use of @Transacrtional with the name the of appropriate bean.

4. Java Service code
The service makes use of the customer and employee DAOs

5. Controller code
The controller used the server to retrieve the data. The data is placed in the model.

6. View page
File: display-results.jsp

This JSP page displays the results. It has an HTML table for Employee data and another HTML table for Customer data.

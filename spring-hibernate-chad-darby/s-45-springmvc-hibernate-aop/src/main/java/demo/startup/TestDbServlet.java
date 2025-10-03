package demo.startup;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;


@WebServlet("/TestDbServlet")
public class TestDbServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String user = "springstudent";
        String pass = "springstudent";
        String jdbcUrl = "jdbc:mysql://localhost:3306/web_customer_tracker?useSSL=false&serverTimezone=UTC";
        String driver = "com.mysql.cj.jdbc.Driver";

        try {

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            out.println("<h3>I'm connected to database: " + jdbcUrl + "</h3>");

            Class.forName(driver);

            Connection connection = DriverManager.getConnection(jdbcUrl, user, pass);

            out.println("<h1>CONNECTION SUCCESSFUL!</h1>");
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }

    }
}

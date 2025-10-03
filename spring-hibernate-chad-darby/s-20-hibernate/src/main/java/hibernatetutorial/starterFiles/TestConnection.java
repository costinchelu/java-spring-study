package hibernatetutorial.starterFiles;


import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection {

    private static final String JDBC_URL =  "jdbc:mysql://localhost:3306/hb-01-one-to-one-uni?useSSL=false";
    private static final String USER_ID = "hbstudent";
    private static final String PASS = "hbstudent";


    public static void main(String[] args) {

        try {
            System.out.println("Connecting to database: " + JDBC_URL);
            Connection conn = DriverManager.getConnection(JDBC_URL, USER_ID, PASS);
            System.out.println("Connection successful");
        } catch (Exception exc) {
            System.out.println("Connection failed");
            exc.printStackTrace();
        }
    }
}

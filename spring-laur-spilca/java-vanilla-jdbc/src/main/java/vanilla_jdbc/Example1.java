package vanilla_jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import static connections.ConnectionStrings.*;

/** statement */
public class Example1 {

    public static void main(String[] args) {

        String insert = "INSERT INTO products VALUES (NULL, 'Wine', 14)";

        try (
            Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement stmt = con.createStatement();
        ) {

            stmt.executeUpdate(insert);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

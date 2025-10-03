package vanilla_jdbc;

import java.sql.DriverManager;
import java.sql.SQLException;

import static connections.ConnectionStrings.*;

/**
 * prepared statement
 *
 * in case of prepared statement,
 * we are not sending the sql string with the execute method
 * the sql string is send as a parameter in prepareStatement method
 */
public class Example2 {

    public static void main(String[] args) {

        String productName = "Chocolate";
        int price = 4;
        String insert = "INSERT INTO products (name, price) VALUES (?, ?)";

        try (
            var connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            var statement = connection.prepareStatement(insert);
        ) {

            statement.setString(1, productName);
            statement.setDouble(2, price);
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

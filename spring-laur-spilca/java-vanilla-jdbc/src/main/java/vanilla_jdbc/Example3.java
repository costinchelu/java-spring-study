package vanilla_jdbc;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static connections.ConnectionStrings.*;

/**
 * execute()
 *  -> can be used for ANY SQL query (INSERT, UPDATE, SELECT, ALTER, CREATE, DROP, TRUNCATE)
 *  -> returns boolean -> true if it was a SELECT query and false otherwise
 *
 *  executeUpdate()
 *  -> ONLY able to send INSERT, UPDATE, DELETE
 *  -> returns int -> how many rows were affected
 *
 *  executeQuery()
 *  -> ONLY able to send SELECT
 *  -> returns ResultSet -> rows of data returned by the DB
 */
public class Example3 {

    public static void main(String[] args) {

        String selectSql = "SELECT * FROM products";

        try (
             var conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             var stmt = conn.prepareStatement(selectSql);
                ) {

            ResultSet rs = stmt.executeQuery();

            // rs.next() returns TRUE if a row was selected,
            // and false otherwise (we've arrived over the last row in the resultSet

            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");

                System.out.println(id + ") " + name + " = " + price + " lei");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}

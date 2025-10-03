package vanilla_jdbc;

import java.sql.*;
import static connections.ConnectionStrings.*;


public class Example5 {

    public static void main(String[] args) {

        String sqlDelete = "DELETE FROM products WHERE name = ?";
        String nameToDelete = "Wine";

        try (
                Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(sqlDelete);
        ) {

            stmt.setString(1, nameToDelete);
            int returnedInt = stmt.executeUpdate();
            System.out.println(returnedInt);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

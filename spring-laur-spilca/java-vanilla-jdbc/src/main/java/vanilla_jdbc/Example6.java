package vanilla_jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static connections.ConnectionStrings.*;

public class Example6 {

    public static void main(String[] args) {

        String sqlDelete = "DELETE FROM products WHERE name = ?";
        String nameToDelete = "Wine";

        try (
                Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(sqlDelete);
        ) {

            stmt.setString(1, nameToDelete);
            boolean isThisSelect = stmt.execute();
            System.out.println(isThisSelect);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}

package vanilla_jdbc;

import java.sql.*;

import static connections.ConnectionStrings.*;


public class Example4 {

    public static void main(String[] args) {

        String sqlQuery = "SELECT * FROM products WHERE price >= ?";
        double queryPrice = 5;

        try (
                Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement stmt = conn.prepareStatement(sqlQuery);
                ) {

            stmt.setDouble(1, queryPrice);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
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

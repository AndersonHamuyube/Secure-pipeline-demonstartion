import java.sql.*;

public class Vulnerable {
    public static ResultSet find(Connection c, String name) throws SQLException {
        Statement s = c.createStatement();
        return s.executeQuery("SELECT * FROM users WHERE name = '" + name + "'");
    }
}
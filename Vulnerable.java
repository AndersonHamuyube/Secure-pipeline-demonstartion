import java.sql.*;

public class Vulnerable {
    public static ResultSet find(Connection c, String name) throws SQLException {
       PreparedStatement ps = c.prepareStatement("SELECT * FROM users WHERE name = ?");
ps.setString(1, name);
return ps.executeQuery();
}
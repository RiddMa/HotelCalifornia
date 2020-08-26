import javax.xml.crypto.Data;
import java.sql.*;
import java.util.*;
import java.util.Date;


public class Database {
    String JDBC_URL = "jdbc:mysql://localhost:3306/HotelCalifornia?useSSL=false&characterEncoding=utf8";
    String JDBC_USER = "root";
    String JDBC_PASSWORD = "123456";
    Connection conn;

    public Database() {
        try{
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public int RETRIEVE(String username, String password) {
        try (PreparedStatement ps = conn.prepareStatement("SLEECT user_type FROM users WHERE user_name=? AND user_password=?")) {
            ps.setObject(1, username);
            ps.setObject(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                int usertype = -1;
                while (rs.next()) {
                    usertype = rs.getInt(1);
                }
                return usertype;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public int INSERT_RSVN(String guest, int room, Date startDate, Date endDate) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO reservations (user_id,room_id,start_date,end_date) VALUES (?,?,?,?)")) {
            ps.setObject(1, guest);
            ps.setObject(2, room);
            ps.setObject(3, startDate);
            ps.setObject(4, endDate);
            int n = ps.executeUpdate();
            return n;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int INSERT_USER(String username, String password, int type) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO users (user_name,user_password,user_type) VALUES (?,?,?)")) {
            ps.setObject(1, username);
            ps.setObject(2, password);
            ps.setObject(3, type);
            int n = ps.executeUpdate();
            return n;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }
}

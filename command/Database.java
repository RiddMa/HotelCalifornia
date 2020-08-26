package command;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.*;
import java.util.Date;


public class Database {
    String JDBC_URL = "jdbc:mysql://localhost:3306/hotelcalifornia?useSSL=false&characterEncoding=utf8&serverTimezone=UTC";
    String JDBC_USER = "root";
    String JDBC_PASSWORD = "123456";
    Connection conn;

    public Database() {
        try {
            this.conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public int RETRIEVE_USERTYPE(String username, String password) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT user_type FROM users WHERE user_name=? AND user_password=?")) {
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

    public int RETRIEVE_USERID(String username, String password) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT user_id FROM users WHERE user_name=? AND user_password=?")) {
            ps.setObject(1, username);
            ps.setObject(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                int userid = -1;
                while (rs.next()) {
                    userid = rs.getInt(1);
                }
                return userid;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public int GET_FREEROOM() {
        int roomId = -1;
        try (PreparedStatement ps = conn.prepareStatement("SELECT room_id FROM rooms WHERE room_id NOT IN (SELECT room_id FROM reservations)")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    roomId = rs.getInt(1);
                }
                return roomId;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public int INSERT_RSVN(int user, int room, Date startDate, Date endDate) {
        int rsvnId = -1;
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO reservations (user_id,room_id,start_date,end_date) VALUES (?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setObject(1, user);
            ps.setObject(2, room);
            ps.setObject(3, startDate);
            ps.setObject(4, endDate);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsvnId = rs.getInt(1);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return rsvnId;
    }

    public int INSERT_USER(String username, String password, int type) {
        int n = 0;
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO users (user_name,user_password,user_type) VALUES (?,?,?)")) {
            ps.setObject(1, username);
            ps.setObject(2, password);
            ps.setObject(3, type);
            n = ps.executeUpdate();
            return n;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return n;
    }
}

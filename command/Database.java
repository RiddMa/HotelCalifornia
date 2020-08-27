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
    ResultSet resultset;
    int orderId = 1;

    public Database() {
        try {
            //连接到数据库
            this.conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            //获取订单id
            try (PreparedStatement ps = conn.prepareStatement("SELECT order_id FROM reservations WHERE order_id=(select max(order_id) from reservations)")) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        orderId = rs.getInt(1);
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 超级管理员获取所有订单
     */
    public void RETRIEVE_ALL_RSVN() {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT rsvn_id,order_id,user_id,room_id,start_date,end_date FROM reservations WHERE rsvn_id>0");
            try {
                resultset = ps.executeQuery();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 用户获取订单
     */
    public void RETRIEVE_RSVN(int userId) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT rsvn_id,order_id,user_id,room_id,start_date,end_date FROM reservations WHERE user_id=?");
            ps.setObject(1, userId);
            try {
                resultset = ps.executeQuery();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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

    public String RETRIEVE_USERNAME(int userId) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT user_name FROM users WHERE user_id=?")) {
            ps.setObject(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                String userName = null;
                while (rs.next()) {
                    userName = rs.getString(1);
                }
                return userName;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void RETRIEVE_USER(int userId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("SELECT user_id,user_name FROM users WHERE user_id=?")) {
            ps.setObject(1, userId);
            try {
                resultset = ps.executeQuery();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
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
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO reservations (user_id,room_id,start_date,end_date,orderId) VALUES (?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setObject(1, user);
            ps.setObject(2, room);
            ps.setObject(3, startDate);
            ps.setObject(4, endDate);
            ps.setObject(5, ++orderId);
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

    public int DELETE_USER(String username, int type) {
        int n = 0;
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE user_name=? AND user_type=?")) {
            ps.setObject(1, username);
            ps.setObject(2, type);
            n = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return n;
    }
}

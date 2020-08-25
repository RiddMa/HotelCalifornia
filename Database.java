import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Database {
    String JDBC_URL = "jdbc:mysql://localhost:3306/HotelCalifornia?useSSL=false&characterEncoding=utf8";
    String JDBC_USER = "root";
    String JDBC_PASSWORD = "123456";
    public void RETRIEVE()
    {

        try(Connection conn = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD))
        {
            try(PreparedStatement ps = conn.prepareStatement("SLEECT id FROM ? WHERE xx=?"))
            {
                ps.setObject(1,"");
                ps.setObject(2,"");
                try(ResultSet rs = stmt.executeQuery(""))
                {
                    While(rs.next())
                    {

                    }
                }
            }
        }
    }

    public void INSERT()
    {
        try(Connection conn = DriverManager.getConnection(JDBC_URL,JDBC_USER,JDBC_PASSWORD))
        {
            try(PreparedStatement ps = conn.prepareStatement("INSERT INTO xx (x,y,z) VALUES (?,?,?)"))
            {
                ps.setObject(1,"");
                ps.setObject(2,"");
                ps.setObject(3,"");
                int n = ps.executeUpdate();
    }


}

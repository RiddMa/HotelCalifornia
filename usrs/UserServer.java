package usrs;

import java.util.Date;
import Transport.*;

/**
 * 用户类
 * @author Travis
 */
public class UserServer {
    protected String userName;
    protected String passWord;
    protected String type;
    protected TransportServer ts;
    // private TYPENAME list;//订单

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getType() {
        return type;
    }

    public String getUserName() {
        return userName;
    }

    /**
     * 构造函数
     * 
     * @param userName 用户名
     * @param passWord 密码
     * @param type     用户类型
     */
    public UserServer(String userName, String passWord, String type) {
        this.type = type;
        this.passWord = passWord;
        this.userName = userName;
        ts = new TransportServer();
    }

    /**
     * 登录
     * 
     * @param userName 用户名
     * @param passWord 密码
     * @return 登陆是否成功，成功返回{@code true}，否则返回{@code false}
     */
    public boolean login(String userName, String passWord) {

        return true;
    }

    /**
     * 登出
     * 
     * @return bool，已经登录返回{@code true}，未登录则无法登出返回{@code false}
     */
    public boolean logout() {
        return true;
    }

    /**
     * 预订房间
     * 
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param peopleNum 人数
     * @return 预定信息或预定失败信息的字符串
     */
    public String reserveRoom(Date startDate, Date endDate, int peopleNum) {
        return "";
    }

    /**
     * 显示个人预定信息
     * 
     * @return 预定信息的字符串
     */
    public String showReservation() {
        return "";
    }
}
package usrs;

/**
 * 管理员类 client端
 * @author  Travis
 */
public class AdminServer extends User {
    /**
     * 构造方法
     *
     * @param userName 用户名
     * @param passWord 密码
     * @param type     用户类型
     */
    public AdminServer(String userName, String passWord, String type) {
        super(userName, passWord, type);
    }

    /**
     * 管理员添加旅馆房间信息
     *
     * @param roomNumber 房间号
     * @return 房号是否重复，不重复返回{@code true},否则返回{@code false}
     */
    public boolean addRoom(int roomNumber) {
        return true;
    }

    /**
     * 管理员查看所有的预定信息
     *
     * @return 预定信息的字符串
     */
    public String showReservations() {
        return "";
    }
}
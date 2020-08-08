package usrs;

/**
 * 超级管理员类，继承Admin类
 * 超级管理员默认为root，密码root
 * @author Travis
 */
public class SuperAdminClient extends AdminServer {
    /**
     * 构造函数
     *
     * @param userName 用户名
     * @param passWord 密码
     * @param type     用户类型
     */
    public SuperAdminClient(String userName, String passWord, String type) {
        super(userName, passWord, type);
    }

    /**
     * 超级管理员创建管理员
     *
     * @param userName 用户名
     * @param passWord 密码
     */
    public void createAdmin(String userName, String passWord) {

    }

    /**
     * 超级管理员删除管理员
     *
     * @param adminUserName 管理员用户名
     * @return 删除成果返回true，失败（不存在此用户）返回false
     */
    public boolean delete(String adminUserName) {
        return true;
    }
}
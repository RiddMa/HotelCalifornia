package command;

import Transport.TransportClient;

import java.util.Arrays;
import java.util.List;

/**
 * 用于接收用户端的命令，并传输到传输层
 * 客户端使用时需要先建立TransportClient实例，再将其作为参数建立CommandClient实例
 * 使用时只需要使用parse()方法解析输入的命令
 * 具体命令的实现可以修改CommandClient的相关方法
 * @author Travis
 */
public class CommandClient extends Command {
    private final TransportClient tc;
    private String type;
    private String userName;
    private int id;//用户id
    private static final List<String> typeList = Arrays.asList("default","user", "admin", "superadmin");

    public CommandClient(String str, TransportClient tc) {
        super(str);
        this.tc = tc;
        type = "default";//默认为default，只有login权限
    }

    /**
     * 解析命令
     * @param str 命令
     * @return boolean 返回命令是否合法和是否成功
     */
    public boolean parse(String str) {
        setArgs(str);
        if(!isLegal()) {
            System.out.println("WRONG COMMAND");
            return false;
        }//命令名不合法则直接返回false
        if(!isFitType(type)) {
            System.out.println("WRONG type");
            return false;
        }
        switch (args[0]) {
            case "LOGIN":
                return login();
            case "LOGOUT":
                return logout();
            case "RESERVEROOM":
                return reserveRoom();
            case "SHOWRESERVATIONS":
                showReservations();
                return true;
            case "SHOWRESERVATION":
                showReservation();
                return true;
            case "CREATEADMIN":
                return createAdmin();
            case "ADDROOM":
                return addRoom();
            case "DELETE":
                return delete();
            case "CREATE":
                return create();
        }
        return false;
    }

    public String getType() {
        return type;
    }

    /**
     * 登录
     *
     * @return 接收到用户类型的字符串，成功返回{@code true}，否则返回{@code false}
     */
    boolean login() {
        //TODO: 需要增加用户名的正则检查
        //String pattern = "^[^0-9][\\w_]{5,9}$";
        if (args.length != 3) {
            System.out.println("WRONG PARAMETER");
            return false;
        }
        tc.transport(command + "\n");
        String str = tc.accept();
        id = Integer.parseInt(tc.accept());//获取用户id
        if(typeList.contains(str)){
            type = str;
            userName = args[1];
            return true;
        }
        else return false;
    }

    /**
     * 登出
     *
     * @return bool，已经登录返回{@code true}，未登录则无法登出返回{@code false}
     */
    boolean logout() {
        tc.transport("logout\n");

        if(tc.accept().equals("success")){
            type = "default";
            return true;
        }else return false;
    }

    /**
     * 预订房间 reserveRoom num startTime endTime
     * reserve_room 3 2020 3 5 2020 3 8
     *
     * @return 成功返回{@code true}，否则返回{@code false}
     */
    boolean reserveRoom() {
        String str;
        if(args.length != 8) {
            System.out.println("WRONG PARAMETER");
            return false;
        }
        tc.transport(command + " " + id + " " + userName + "\n");
        str = tc.accept();
        if(str.equals("failed")) return false;
        else if(str.equals("no enough rooms")) return false;
        else{
            System.out.println("> " + str);
            while (!(str = tc.accept()).equals("#")) {
                System.out.println("> " + str);
            }
            return true;
        }
    }

    /**
     * 直接打印个人预定信息
     */
    void showReservation() {
        String str;
        tc.transport(command + " " + id + "\n");
        while (!(str = tc.accept()).equals("#")) {
            System.out.println("> " + str);
        }
    }

    /**
     * 管理员添加旅馆房间信息
     *
     * @return 房号是否重复，不重复返回{@code true},否则返回{@code false}
     */
    boolean addRoom() {
        if (args.length != 2) {
            System.out.println("WRONG PARAMETER");
            return false;
        }
        tc.transport(command + "\n");
        return tc.accept().equals("success");
    }

    /**
     * 管理员查看所有的预定信息
     */
    void showReservations() {
        String str;
        tc.transport(command + "\n");
        while (!(str = tc.accept()).equals("#")) {
            System.out.println("> " + str);
        }
    }

    /**
     * 超级管理员创建管理员
     */
    boolean createAdmin() {
        if (args.length != 3) {
            System.out.println("WRONG PARAMETER");
            return false;
        }
        tc.transport(command + "\n");
        return tc.accept().equals("success");
    }

    /**
     * 超级管理员删除管理员
     *
     * @return 删除成果返回true，失败（不存在此用户）返回false
     */
    boolean delete() {
        if (args.length != 2) {
            System.out.println("WRONG PARAMETER");
            return false;
        }
        tc.transport(command + "\n");
        return tc.accept().equals("success");
    }

    /**
     * 创建用户
     *
     * @return 创建是否成功
     */
    boolean create() {
        if (args.length != 3) {
            System.out.println("WRONG PARAMETER");
            return false;
        }
        tc.transport(command + "\n");
        return tc.accept().equals("success");
    }


}

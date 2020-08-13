package command;

import Transport.TransportClient;

/**
 * 用于接收用户端的命令，并传输到传输层
 */
public class CommandClient extends Command {
    private final TransportClient tc;

    public CommandClient(String str, TransportClient tc) {
        super(str);
        this.tc = tc;
    }

    /**
     * 登录
     *
     * @return 登陆是否成功，成功返回{@code true}，否则返回{@code false}
     */
    boolean login() {
        //TODO: 需要增加用户名的正则检查
        //String pattern = "^[^0-9][\\w_]{5,9}$";
        if (args.length != 3) return false;
        tc.transport(command + "\n");
        return tc.accept().equals("success");
    }

    /**
     * 登出
     *
     * @return bool，已经登录返回{@code true}，未登录则无法登出返回{@code false}
     */
    boolean logout() {
        tc.transport("logout");
        return tc.accept().equals("success");
    }

    /**
     * 预订房间
     *
     * @return 成功返回{@code true}，否则返回{@code false}
     */
    boolean reserveRoom() {
        if (Integer.parseInt(args[1]) > Integer.parseInt(args[2])) return false;
        tc.transport(command);
        return tc.accept().equals("success");
    }

    /**
     * 直接打印个人预定信息
     */
    void showReservation() {
        String str;
        tc.transport(command);
        while ((str = tc.accept()) != null) {
            System.out.println("> " + str);
        }
    }

    /**
     * 管理员添加旅馆房间信息
     *
     * @return 房号是否重复，不重复返回{@code true},否则返回{@code false}
     */
    boolean addRoom() {
        if (args.length != 2) return false;
        tc.transport(command);
        return tc.accept().equals("success");
    }

    /**
     * 管理员查看所有的预定信息
     */
    void showReservations() {
        String str;
        tc.transport(command);
        while ((str = tc.accept()) != null) {
            System.out.println("> " + str);
        }
    }

    /**
     * 超级管理员创建管理员
     */
    boolean createAdmin() {
        if (args.length != 3) return false;
        tc.transport(command);
        return tc.accept().equals("success");
    }

    /**
     * 超级管理员删除管理员
     *
     * @return 删除成果返回true，失败（不存在此用户）返回false
     */
    boolean delete() {
        if (args.length != 2) return false;
        tc.transport(command);
        return tc.accept().equals("success");
    }

    /**
     * 创建用户
     *
     * @return 创建是否成功
     */
    boolean create() {
        if (args.length != 3) return false;
        tc.transport(command);
        return tc.accept().equals("success");
    }

    public boolean parse() {
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
}
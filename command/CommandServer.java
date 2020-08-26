package command;

import Transport.TransportServer;
import Database;
import java.sql.Date;
import java.util.Calendar;

/**
 * 用于服务器端的命令解析
 */
public class CommandServer extends Command {
    private final TransportServer ts;
    private final
    public CommandServer(String str, TransportServer ts) {
        super(str);
        this.ts = ts;
    }

    /**
     * 接收服务端的命令加以解析
     */
    public void parse() {
        setArgs(ts.accept());
        switch (args[0]) {
            case "LOGIN":
                login();
                break;
            case "LOGOUT":
                logout();
                break;
            case "RESERVEROOM":
                reserveRoom();
                break;
            case "SHOWRESERVATIONS":
                showReservations();
                break;
            case "SHOWRESERVATION":
                showReservation();
                break;
            case "CREATEADMIN":
                createAdmin();
                break;
            case "ADDROOM":
                addRoom();
                break;
            case "DELETE":
                delete();
                break;
            case "CREATE":
                create();
                break;
            default:
                System.out.println("unknown command:" + command + ".");
                break;
        }
    }

    private void delete() {
        //成功则向客户端发送“success\n”
        //ts.transport("success\n");
    }

    private void addRoom() {
        //成功则向客户端发送“success\n”
        //ts.transport("success\n");
    }

    private void createAdmin() {
        //成功则向客户端发送“success\n”
        //ts.transport("success\n");
    }

    /**
     * 向客户端发送要显示的内容，结束内容显示时应该发送"#"在最后一行作为结束标志
     */
    private void showReservation() {
        String str = "#";
        do {
            //对str的处理
            ts.transport(str);
        } while (!str.equals("#"));
    }

    /**
     * 向客户端发送要显示的内容，结束内容显示时应该发送"#"在最后一行作为结束标志
     */
    private void showReservations() {
        String str = "#";
        do {
            //对str的处理
            ts.transport(str);
        } while (!str.equals("#"));
    }

    private void reserveRoom() {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.set(Integer.parseInt(args[2]) - 1970, Integer.parseInt(args[3]), Integer.parseInt(args[4]));
        c2.set(Integer.parseInt(args[5]) - 1970, Integer.parseInt(args[6]), Integer.parseInt(args[7]));
        java.util.Date d1 = c1.getTime();
        java.util.Date d2 = c2.getTime();
        Date startDate = new Date(d1.getTime());
        Date endDate = new Date(d2.getTime());


        //成功则向客户端发送“success\n”
        //ts.transport("success\n");
    }

    private void logout() {
        //成功则向客户端发送“success\n”
        //ts.transport("success\n");
    }

    private void create() {
        //成功则向客户端发送“success\n”
        //ts.transport("success\n");

    }

    /**
     * 用于登录用户，成功则向客户端发送用户类型。
     * 用户类型如下："default","user", "admin", "superadmin"
     */
    private void login() {
        //ts.transport("user\n");
    }
}

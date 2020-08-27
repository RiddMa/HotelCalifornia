package command;

import Transport.TransportServer;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * 用于服务器端的命令解析
 */
public class CommandServer extends Command {
    private final TransportServer ts;
    private final Database db;

    public CommandServer(String str, TransportServer ts) {
        super(str);
        this.ts = ts;
        this.db = new Database();
    }

    /**
     * 接收服务端的命令加以解析
     */
    public void parse(String str){
        setArgs(str);
        switch (args[0]) {
            case "LOGIN" -> login();
            case "LOGOUT" -> logout();
            case "RESERVEROOM" -> reserveRoom();
            case "SHOWRESERVATIONS" -> showReservations();
            case "SHOWRESERVATION" -> showReservation();
            case "CREATEADMIN" -> createAdmin();
            case "ADDROOM" -> addRoom();
            case "DELETE" -> delete();
            case "CREATE" -> create();
            default -> System.out.println("unknown command:" + command + ".");
        }
    }

    private void delete() {

        //成功则向客户端发送“success\n”
        ts.transport("success\n");
    }

    private void addRoom() {
        //成功则向客户端发送“success\n”
        ts.transport("success\n");
    }

    private void createAdmin() {
        if (db.RETRIEVE_USERTYPE(args[1], args[2]) != -1) {
            //0:superadmin 1:admin 2:user
            if (db.INSERT_USER(args[1], args[2], 1) == 1) {
                ts.transport("success\n");
            } else {
                ts.transport("failed\n");
            }
        } else {
            ts.transport("failed\n");
        }
    }

    /**
     * 向客户端发送要显示的内容，结束内容显示时应该发送"#"在最后一行作为结束标志
     */
    private void showReservation(){
        //暂时存在这些变量里
        String usrName = "usrName";
        int usrId = Integer.parseInt(args[1]);//用户id
        int num = 0;//人数
        int rsvnId = 0;//订单id
        Date startDate = null;//开始时间
        Date endDate = null;//结束时间
        int roomId = 0;//房间id
        int orderId = 0;
        Reservations rs = new Reservations();
        try {
            db.RETRIEVE_USER(usrId);
            usrName = db.resultset.getString(2);
            db.RETRIEVE_RSVN(usrId);

            while (db.resultset.next()) {
                rsvnId = db.resultset.getInt(1);
                orderId = db.resultset.getInt(2);
                //usrId = db.resultset.getInt(3);
                roomId = db.resultset.getInt(4);
                startDate = db.resultset.getDate(5);
                endDate = db.resultset.getDate(6);
                Reservation r = new Reservation(usrName, usrId, num, rsvnId, startDate, endDate, roomId, orderId);
                rs.add(r);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //处理订单
        rs.transport(ts);
    }

    /**
     * 管理员查看所有订单
     * 命令格式：show_reservations
     * 没有预定信息，返回，none
     * 可能有多个有预定信息 可能有多个房间，返回：
     * （订单号）xxx （预定用户名） xxx （房间号） xxx xxx … xxx  （预定入住日期） 3个整数 （预定退房日期） 3个整数
     * 如：0001 Zhangsan 101 102 103 2020 3 4 2020 3 8
     * 0002 Lisi 104 2020 3 5 2020 3 7
     * 向客户端发送要显示的内容，结束内容显示时应该发送"#"在最后一行作为结束标志
     */
    private void showReservations() {
        //暂时存在这些变量里
        String usrName = "usrName";
        int usrId = 0;//用户id
        int num = 0;//人数
        int rsvnId = 0;//订单id
        Date startDate = null;//开始时间
        Date endDate = null;//结束时间
        int roomId = 0;//房间id
        int orderId = 0;
        ArrayList<Reservation> rsvnList = new ArrayList<>();//订单数组
        Reservations rs = new Reservations();

        db.RETRIEVE_ALL_RSVN();
        try {
            while (db.resultset.next()) {
                rsvnId = db.resultset.getInt(1);
                orderId = db.resultset.getInt(2);
                usrId = db.resultset.getInt(3);
                usrName = db.RETRIEVE_USERNAME(usrId);
                roomId = db.resultset.getInt(4);
                startDate = db.resultset.getDate(5);
                endDate = db.resultset.getDate(6);
                Reservation r = new Reservation(usrName, usrId, num, rsvnId, startDate, endDate, roomId, orderId);
                rs.add(r);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        rs.transport(ts);
    }

    /**
     * 如果房间足够，返回预定信息，如下：
     * 订单号xxx 预定旅客名xxx  预定人数 x 预定入住日期 x x x(年月日)  预定退房日期 x x x 预定房间号 xxx xxx … xxx （每个xxx表示一个被预定房间的房间号）
     * 如果房间不够或输入错误，预定失败，不产生订单数据，返回FAIL。每个成功预定，或者说订单都有编号，从1开始，然后每个预定自动加1。失败的预定不生成订单。
     */
    private void reserveRoom() {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.set(Integer.parseInt(args[2]), Integer.parseInt(args[3]) - 1, Integer.parseInt(args[4]));
        c2.set(Integer.parseInt(args[5]), Integer.parseInt(args[6]) - 1, Integer.parseInt(args[7]));
        java.util.Date d1 = c1.getTime();
        java.util.Date d2 = c2.getTime();

        Date startDate = new Date(d1.getTime());
        Date endDate = new Date(d2.getTime());
        int usrId = Integer.parseInt(args[8]);
        int num = Integer.parseInt(args[1]);
        int rsvnId = 0;
        ArrayList<Integer> roomId = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            int room = db.GET_FREEROOM();
            if (room != -1)
                rsvnId = db.INSERT_RSVN(usrId, room, startDate, endDate);
            else
                ts.transport("failed\n");
            if (rsvnId == -1)
                ts.transport("failed\n");
            else
                roomId.add(rsvnId);
        }
        //订单号xxx 预定旅客名xxx  预定人数 x 预定入住日期 x x x(年月日)
        //预定退房日期 x x x 预定房间号 xxx xxx … xxx （每个xxx表示一个被预定房间的房间号）
        ts.transport("--------------------------------------------------------------\n");
        ts.transport("订单号" + rsvnId + " 预定旅客名" + "usrName" + "预定人数" + num + "\n");
        ts.transport("预定入住日期" + startDate + "预定退房日期" + endDate + "\n");
        ts.transport("预定房间号：" + roomId + "\n");
        ts.transport("==============================================================\n");
        ts.transport("#\n");
    }

    private void logout() {
        //成功则向客户端发送“success\n”
        ts.transport("success\n");
    }

    private void create() {
        if (db.RETRIEVE_USERTYPE(args[1], args[2]) != -1) {
            //0:superadmin 1:admin 2:user
            if (db.INSERT_USER(args[1], args[2], 2) == 1) {
                ts.transport("success\n");
            } else {
                ts.transport("failed\n");
            }
        } else {
            ts.transport("failed\n");
        }
    }

    /**
     * 用于登录用户，成功则向客户端发送用户类型。
     * 用户类型如下："default","user", "admin", "superadmin"
     */
    private void login() {
        int usrType = db.RETRIEVE_USERTYPE(args[1], args[2]);
        int usrId = db.RETRIEVE_USERID(args[1], args[2]);
        switch (usrType) {
            case 0 -> ts.transport("superadmin\n");
            case 1 -> ts.transport("admin\n");
            case 2 -> ts.transport("user\n");
            default -> ts.transport("failed\n");
        }
        ts.transport(usrId + "\n");
    }
}

/**
 * 订单列，使用时要add订单
 *
 * @author Travis
 */
class Reservations {
    ArrayList<Reservation> rsvnList;

    public Reservations() {
        rsvnList = new ArrayList<>();
    }

    /**
     * 增加订单到订单列
     *
     * @param r 订单
     */
    void add(Reservation r) {
        rsvnList.add(r);
    }

    /**
     * 向客户端传送所需字符串
     *
     * @param ts TransportServer类
     */
    void transport(TransportServer ts) {
        if (rsvnList == null) return;
        Reservation reservation = rsvnList.get(0);//取第一个reservation
        int tempOrderID = reservation.orderId;//取第一个orderId
        for (Reservation rsvn : rsvnList) {//遍历
            if (tempOrderID != rsvn.orderId) {//当当前orderId与要发送的id不一致时发送并重置
                reservation.transport(ts);
                tempOrderID = rsvn.orderId;
                reservation = rsvn;
            }
            reservation.roomIdList.add(rsvn.roomId);
        }
        reservation.transport(ts);
        ts.transport("#\n");
    }
}

/**
 * 订单
 */
class Reservation {
    String usrName;
    int usrId;
    int num;
    int rsvnId;
    Date startDate, endDate;
    int roomId;
    int orderId;
    ArrayList<Integer> roomIdList;

    public Reservation(String usrName, int usrId, int num, int rsvnId, Date startDate, Date endDate, int roomId, int orderId) {
        this.usrId = usrId;
        this.usrName = usrName;
        this.num = num;
        this.rsvnId = rsvnId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.roomId = roomId;
        this.orderId = orderId;
        roomIdList = new ArrayList<>();
    }

    public void transport(TransportServer ts) {
        ts.transport("--------------------------------------------------------------\n");
        ts.transport("订单号" + rsvnId + " 预定旅客名" + usrName + "预定人数" + num + "\n");
        ts.transport("预定入住日期" + startDate + "预定退房日期" + endDate + "\n");
        ts.transport("预定房间号：" + roomIdList + "\n");
        ts.transport("==============================================================\n");
    }
}

import Transport.*;
import command.*;


import java.io.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Client {
    private static String command;
    private static String[] args;

    public static void main(String[] arg) {
        //TransportClient tc = new TransportClient("10.128.208.253",8088);
        TransportClient tc = new TransportClient();
        CommandClient cc = new CommandClient("null", tc);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));//接收命令行命令
        Time.print("请输入用户名和密码：" + "（新用户请先进行注册：create）");
        try {
            while (true) {
                String str = br.readLine();
                command = str;
                args = str.split(" ");
                args[0] = args[0].toUpperCase();
                switch (args[0]) {
                    case "LOGIN":
                        if (cc.parse(str)) {
                            Time.print("登录成功");
                            switch (cc.getType()) {
                                case "user":
                                    System.out.println("你可以进行以下操作：\n" + "注销：logout\n" + "预定房间：reserveRoom\n" + "显示个人预定信息：showReservation");
                                    break;
                                case "admin":
                                    System.out.println("你可以进行以下操作：\n" + "注销：logout\n" + "预定房间：reserveRoom\n" + "显示个人预定信息：showReservation\n" + "添加旅馆房间信息：addRoom\n" + "查看所有的预定信息：showReservations");
                                    break;
                                case "superadmin":
                                    System.out.println("你可以进行以下操作：\n" + "注销：logout\n" + "预定房间：reserveRoom\n" + "显示个人预定信息：showReservation\n" + "添加旅馆房间信息：addRoom\n" + "查看所有的预定信息：showReservations\n" + "创建管理员：createAdmin\n" + "删除管理员：delete");
                                    break;
                            }
                        } else {
                            Time.print("登录失败，请重新输入用户名和密码");
                        }
                        break;
                    case "LOGOUT":
                        if (cc.parse(str)) {
                            Time.print("已登出,请重新登录");
                        } else {
                            Time.print("登出失败");
                        }
                        break;
                    case "RESERVEROOM":
                        cc.parse(str);
                        break;
                    case "SHOWRESERVATIONS":
                        cc.parse(str);
                        break;
                    case "SHOWRESERVATION":
                        cc.parse(str);
                        break;
                    case "CREATEADMIN":
                        if (cc.parse(str)) {
                            Time.print("创建成功");
                        } else {
                            Time.print("创建失败");
                        }
                        break;
                    case "ADDROOM":
                        if (cc.parse(str)) {
                            Time.print("房间创建成功");
                        } else {
                            Time.print("房间创建失败");
                        }
                        break;
                    case "DELETE":
                        if (cc.parse(str)) {
                            Time.print("删除成功");
                        } else {
                            Time.print("失败，不存在此用户");
                        }
                        break;
                    case "CREATE":
                        if (cc.parse(str)) {
                            Time.print("注册成功，请登录:login");
                        } else {
                            Time.print("注册失败");
                        }
                        break;
                    default:
                        Time.print("unknown command:" + command + ".");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
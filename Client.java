import Transport.*;
import command.*;


import java.io.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Client {
    private static String command;
    private static String[] args;
    /**
    *String formatDate = DateUtil.timeStampToFormatDate(Calendar.getInstance().getTime(), "yyyy-MM-dd HH:mm:ss");
     *         System.out.println("formatDate3: " + String fo);
     */
    public static void main(String[] arg) {
        TransportClient tc = new TransportClient();
        CommandClient cc = new CommandClient("null", tc);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));//接收命令行命令
        String formatDate = Client.timeStampToFormatDate(Calendar.getInstance().getTime(), "yyyy-MM-dd HH:mm:ss");
        System.out.println(formatDate+"\n"+"请输入用户名和密码：\n" + "（新用户请先进行注册：create）");
        try {
            while (true) {
                String str = br.readLine();
                command = str;
                args = str.split(" ");
                args[0] = args[0].toUpperCase();
                switch (args[0]) {
                    case "LOGIN":
                        if (cc.parse(str)) {
                            System.out.println(formatDate+"\n"+"登录成功");
                            switch (cc.getType()) {
                                case "user":
                                    System.out.println(formatDate+"\n"+"你可以进行以下操作：\n" + "注销：logout\n" + "预定房间：reserveRoom\n" + "显示个人预定信息：showReservation");
                                    break;
                                case "admin":
                                    System.out.println(formatDate+"\n"+"你可以进行以下操作：\n" + "注销：logout\n" + "预定房间：reserveRoom\n" + "显示个人预定信息：showReservation\n" + "添加旅馆房间信息：addRoom\n" + "查看所有的预定信息：showReservations");
                                    break;
                                case "superadmin":
                                    System.out.println(formatDate+"\n"+"你可以进行以下操作：\n" + "注销：logout\n" + "预定房间：reserveRoom\n" + "显示个人预定信息：showReservation\n" + "添加旅馆房间信息：addRoom\n" + "查看所有的预定信息：showReservations\n" + "创建管理员：createAdmin\n" + "删除管理员：delete");
                                    break;
                            }
                        } else {
                            System.out.println(formatDate+"\n"+"登录失败，请重新输入用户名和密码");
                        }
                        break;
                    case "LOGOUT":
                        if (cc.parse(str)) {
                            System.out.println(formatDate+"\n"+"已登出");
                        } else {
                            System.out.println(formatDate+"\n"+"登出失败");
                        }
                        break;
                    case "RESERVEROOM":
                        if (cc.parse(str)) {
                            System.out.println(formatDate+"\n"+"预约成功，请按时入住");
                        } else {
                            System.out.println(formatDate+"\n"+"房间已满，预约失败");
                        }
                        break;
                    case "SHOWRESERVATIONS":
                        cc.parse(str);
                        break;
                    case "SHOWRESERVATION":
                        cc.parse(str);
                        break;
                    case "CREATEADMIN":
                        if (cc.parse(str)) {
                            System.out.println(formatDate+"\n"+"创建成功");
                        } else {
                            System.out.println(formatDate+"\n"+"创建失败");
                        }
                        break;
                    case "ADDROOM":
                        if (cc.parse(str)) {
                            System.out.println(formatDate+"\n"+"房间创建成功");
                        } else {
                            System.out.println(formatDate+"\n"+"房间创建失败");
                        }
                        break;
                    case "DELETE":
                        if (cc.parse(str)) {
                            System.out.println(formatDate+"\n"+"删除成功");
                        } else {
                            System.out.println(formatDate+"\n"+"失败，不存在此用户");
                        }
                        break;
                    case "CREATE":
                        if (cc.parse(str)) {
                            System.out.println(formatDate+"\n"+"注册成功，请登录:login");
                        } else {
                            System.out.println(formatDate+"\n"+"注册失败");
                        }
                        break;
                    default:
                        System.out.println(formatDate+"\n"+"unknown command:" + command + ".");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String timeStampToFormatDate(Object dateObj, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String formatDate = sdf.format(dateObj);
        return formatDate;
    }

}
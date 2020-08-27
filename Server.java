import Transport.TransportServer;
import command.CommandServer;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Server {
    public static void main(String[] arg) {
        TransportServer ts = new TransportServer();
        CommandServer cs = new CommandServer("null", ts);
        String formatDate = Server.timeStampToFormatDate(Calendar.getInstance().getTime(), "yyyy-MM-dd HH:mm:ss");
        String str;
        while (true) {
            while ((str = ts.accept()) != null) {
                System.out.println(formatDate+"\n"+"client:" + str);
                cs.parse(str);
            }
            ts.socketAccept();
        }
    }
    public static String timeStampToFormatDate(Object dateObj, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String formatDate = sdf.format(dateObj);
        return formatDate;
    }
}
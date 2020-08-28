import Transport.TransportServer;
import command.CommandServer;
import command.Time;

public class Server {
    public static void main(String[] arg) {
        TransportServer ts = new TransportServer();
        CommandServer cs = new CommandServer("null", ts);
        String str;
        while (true) {
            while ((str = ts.accept()) != null) {
                Time.print("client:" + str);
                cs.parse(str);
            }
            ts.socketAccept();
        }
    }
}
import Transport.TransportServer;
import command.CommandServer;

public class Server {
    public static void main(String[] arg) {
        TransportServer ts = new TransportServer();
        CommandServer cs = new CommandServer("null", ts);
        String str;
        while (true) {
            while ((str = ts.accept()) != null) {
                System.out.println("client:" + str);
                cs.parse(str);
            }
            ts.socketAccept();
        }
    }
}
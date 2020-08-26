import Transport.TransportServer;
import command.CommandServer;

public class Server {
    static void main(String[] arg){
        TransportServer ts = new TransportServer();
        CommandServer cs = new CommandServer("null",ts);
    }
}
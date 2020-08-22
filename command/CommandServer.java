package command;

import Transport.TransportServer;

public class CommandServer extends Command {
    private final TransportServer ts;

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
    }

    private void addRoom() {
    }

    private void createAdmin() {
    }

    private void showReservation() {
    }

    private void showReservations() {
    }

    private void reserveRoom() {
    }

    private void logout() {
    }

    private void create() {
    }

    private void login() {
    }
}

import Transport.*;
import command.Command;

import java.io.IOException;
import java.util.Scanner;

public class testServer {
    public static void main(String[] args) {
        TransportServer a = new TransportServer();
        Scanner sc = new Scanner(System.in);
        while (true) {
            String str;
            /*Command tmp = (Command) a.acceptObj();
            a.transport(tmp.command + "\n");*/
            while ((str = a.accept()) != null) {
                System.out.println("client:" + str);
                a.transport("success\n");
            }
            a.socketAccept();
        }

    }
}
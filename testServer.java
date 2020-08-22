import Transport.*;
import java.util.Scanner;

public class testServer {
    public static void main(String[] args) {
        while (true) {
            TransportServer a = new TransportServer(8088);
            Scanner sc = new Scanner(System.in);
            String str;
            while ((str = a.accept()) != null) {
                System.out.println("client:" + str);
                a.transport("success\n");
            }
            a.close();
        }

    }
}
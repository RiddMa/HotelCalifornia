import java.util.Scanner;

import Transport.*;

public class testClient {
    public static void main(String[] args) {
        TransportClient b = new TransportClient("10.122.238.116", 8088);
        Scanner sc = new Scanner(System.in);
        int i = 0;
        while(i != 5){
            i++;
            String str = sc.nextLine();
            b.transport(str + "\n");
            //System.out.println("server:" + b.accept());
        }
        b.close();
    }
}
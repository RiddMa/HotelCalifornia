import Transport.*;
import command.*;


import java.io.*;

public class Client {
    private static String command;
    private static String[] args;

    public static void main(String[] arg) {
        TransportClient tc = new TransportClient("192.168.142.1", 8088);
        //TransportClient tc = new TransportClient();
        CommandClient cc = new CommandClient("null", tc);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));//接收命令行命令
        System.out.println("Server Connected, please enter command:");
        try {
            while (true) {
                String str = br.readLine();
                cc.parse(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
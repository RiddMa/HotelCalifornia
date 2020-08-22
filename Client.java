import Transport.*;
import command.*;
import java.io.*;

public class Client {
    public static void main(String[] arg) {
        TransportClient tc = new TransportClient();
        CommandClient cc = new CommandClient("null", tc);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));//接收命令行命令
        System.out.print("hello");
        try {
            while (true) {
                String str = br.readLine();
                cc.parse(str);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
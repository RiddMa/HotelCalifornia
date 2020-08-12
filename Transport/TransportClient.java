package Transport;

import java.io.*;
import java.net.Socket;

/**
 * 客户端传输层
 * 
 * @author Travis
 */
public class TransportClient extends TransportLevel{

    public TransportClient() {
        this("localhost", 8088);
    }

    public TransportClient(String hostname, int port) {
        try {
            socket = new Socket(hostname, port);
            // initial I/O stream
            is = socket.getInputStream();
            os = socket.getOutputStream();
            bw = new BufferedWriter(new OutputStreamWriter(os));
            br = new BufferedReader(new InputStreamReader(is));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // set&get method
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }

    public BufferedReader getBr() {
        return br;
    }

}
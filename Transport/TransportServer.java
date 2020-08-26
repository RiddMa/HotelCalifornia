package Transport;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务器传输层 默认端口号为8088
 *
 * @author Travis
 */
public class TransportServer extends TransportLevel {
    private ServerSocket ss;

    public TransportServer() {
        this(8088);
    }

    public TransportServer(int port) {
        try {
            ss = new ServerSocket(port);
            System.out.println("启动服务器....端口号:" + port);
            socketAccept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        super.close();
        try {
            ss.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void socketAccept() {
        try {
            socket = ss.accept();
            addr = socket.getInetAddress().getLocalHost().toString();
            System.out.println("客户端:" + addr + "已连接到服务器");
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
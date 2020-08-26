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
            //System.out.println("本机" + addrHost + "已上线");
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
            addrHost = socket.getInetAddress().getLocalHost().toString();

            // initial I/O stream
            is = socket.getInputStream();
            os = socket.getOutputStream();
            bw = new BufferedWriter(new OutputStreamWriter(os));
            br = new BufferedReader(new InputStreamReader(is));

            addrClient = accept();

            System.out.println("客户端" + addrClient + "已连接");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用于接收对方传输的数据
     *
     * @return String
     */
    public String accept() {
        try {
            return br.readLine();
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("客户端:" + addrClient + "已失去连接");
            return null;
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
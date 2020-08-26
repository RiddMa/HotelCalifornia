package Transport;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 传输层抽象类，用于实现进程间通信。 其中的方法由TransportClient和TransportServer分别实现
 * 
 * @author Travis
 * @version 0.1
 */
public abstract class TransportLevel {
    protected Socket socket;
    protected InputStream is;
    protected OutputStream os;
    protected BufferedWriter bw;
    protected BufferedReader br;
    protected String addr;

    /**
     * 用于传输数据给对方的传输层
     * 
     * @param str
     */
    public void transport(String str) {
        // TODO
        try {
            bw.write(str);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用于接收对方传输的数据
     * 
     * @return String
     */
    public String accept(){
        try {
            return br.readLine();
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("客户端:" + addr + "已失去连接");
            return null;
        }
    }

    /**
     * 用于关闭链接以及输入输出流
     */
    public void close() {
        try {
            socket.shutdownOutput();
            bw.close();
            br.close();
            is.close();
            os.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
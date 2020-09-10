package Transport;

import command.Time;

import java.io.*;
import java.net.Socket;
import java.util.Calendar;
import java.text.SimpleDateFormat;

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
    protected ObjectInputStream oi;
    protected ObjectOutputStream oo;
    protected String addrHost;
    protected String addrClient;

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

    /*public void transportObj(Object obj) {
        try {
            bw.close();
            oo = new ObjectOutputStream(os);
            oo.writeObject(obj);
            oo.flush();
            oo.close();
            bw = new BufferedWriter(new OutputStreamWriter(os));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object acceptObj() {
        Object obj = null;
        try {
            br.close();
            oi = new ObjectInputStream(new BufferedInputStream(is));
            obj = oi.readObject();
            oi.close();
            br = new BufferedReader(new InputStreamReader(is));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            return obj;
        }
    }*/

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
//import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 处理命令
 *
 * @author Travis
 */
public class Command {
    private final String[] args;
    private static final List<String> commandset = Arrays.asList("LOGIN", "LOGOUT", "RESERVEROOM", "CREATE",
            "SHOWRESERVATION", "ADDROOM", "SHOWRESERVATIONS", "CREATEADMIN", "DELETE");

    /**
     * 接受命令，命令需要以空格隔开。 命令转化为大写形式，参数形式不变
     *
     * @param str
     */
    public Command(String str) {
        this.args = str.split(" ");
        args[0] = args[0].toUpperCase();
        // String[] commandlist = {"LOGIN","LOGOUT","RESERVEROOM","CREATE",
        // "SHOWRESERVATION","ADDROOM","SHOWRESERVATIONS","CREATEADMIN","DELETE"};
        // commandset = new ArrayList<String>();
        // commandset = Arrays.asList(commandlist);
    }

    /**
     * 判断命令是否合法
     *
     * @return 合法返回{@code true}
     */
    public boolean islegal() {
        String command = args[0];
        return commandset.contains(command);
    }

    /**
     * 功能测试
     */
    public static void main(String[] args) {
        Command a = new Command("login b c");
        Command b = new Command("loGout  b c");
        System.out.println("a->" + a.islegal());
        System.out.println("b->" + b.islegal());
    }
}
package command;

import java.util.Arrays;
import java.util.List;

/**
 * 处理命令
 *
 * @author Travis
 */
public class Command {
    protected String command;
    protected String[] args;
    private static final List<String> commandset = Arrays.asList("LOGIN", "LOGOUT", "RESERVEROOM", "CREATE",
            "SHOWRESERVATION", "ADDROOM", "SHOWRESERVATIONS", "CREATEADMIN", "DELETE");
    private static final List<String> userCommand = Arrays.asList("LOGIN", "LOGOUT", "RESERVEROOM", "CREATE",
            "SHOWRESERVATION");
    private static final List<String> adminCommand = Arrays.asList("ADDROOM", "SHOWRESERVATIONS");
    private static final List<String> superadminCommand = Arrays.asList("CREATEADMIN", "DELETE");

    /**
     * 接受命令，命令需要以空格隔开。 命令转化为大写形式，参数形式不变
     *
     * @param str
     */
    public Command(String str) {
        command = str;
        args = str.split(" ");
        args[0] = args[0].toUpperCase();
    }

    public void setArgs(String str) {
        this.args = str.split(" ");
        args[0] = args[0].toUpperCase();
    }
    /**
     * 判断命令是否合法
     *
     * @return 合法返回{@code true}
     */
    public boolean isLegal() {
        String command = args[0];
        return commandset.contains(command);
    }

    /**
     * 判断命令是否符合用户身份
     *
     * @return 符合返回{@code true}
     */
    public boolean isFitType(String type) {
        boolean flag = false;
        switch (args[0]){
            case "user":
                flag = userCommand.contains(args[0]);
                break;
            case "admin":
                flag = adminCommand.contains(args[0]);
                break;
            case "superadmin":
                flag = superadminCommand.contains(args[0]);
                break;
        }
        return flag;
    }

    /**
     * 功能测试
     */
    public static void main(String[] args) {
        Command a = new Command("login b c");
        Command b = new Command("loGout  b c");
        System.out.println("a->" + a.isLegal());
        System.out.println("b->" + b.isLegal());
    }
}
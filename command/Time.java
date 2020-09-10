package command;

import java.util.Calendar;
import java.text.SimpleDateFormat;


public class Time {

    public static void print(String str) {
        String formatDate = timeStampToFormatDate(Calendar.getInstance().getTime(), "yyyy-MM-dd HH:mm:ss");
        System.out.print(formatDate + " > ");
        System.out.println(str);
    }
    public static void printOK() { System.out.println("OK"); }
    public static void printFAIL() {
        System.out.println("FAIL");
    }
    private static String timeStampToFormatDate(Object dateObj, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String formatDate = sdf.format(dateObj);
        return formatDate;
    }

}


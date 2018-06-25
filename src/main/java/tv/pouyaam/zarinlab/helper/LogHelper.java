package tv.pouyaam.zarinlab.helper;

/**
 * Created by pouya on 6/22/18.
 */
public class LogHelper {
    public static void log(String classTag, String methodTag, String message) {
        System.out.println(String.format("[%s::methodTag]:: %s", classTag, methodTag, message != null ? message : ""));
    }
}

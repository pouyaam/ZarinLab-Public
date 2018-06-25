package tv.pouyaam.zarinlab.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by pouya on 6/22/18.
 */
public class TimeHelper {
    public static long convertDateToMsec(String dateStr, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = sdf.parse(dateStr);
        return date.getTime();
    }
}

package sk.c.urbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * app utils
 *
 * @author coon
 */
public class Utils {


    public static String getDateString(Date date, SimpleDateFormat dateFormat) {
        return date != null && dateFormat != null ? dateFormat.format(date) : null;
    }

    public static Date getDate(String date, SimpleDateFormat dateFormat) {
        Date retVal = null;

        if (date != null && dateFormat != null) {
            try {
                retVal = dateFormat.parse(date);
            } catch (ParseException e) {
                retVal = null;
            }
        }

        return retVal;
    }

}

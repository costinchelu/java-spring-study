package hibernatetutorial.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * handle parsing and formatting dates
 */
public class DateUtils {

    // The date formatter
    // - dd:   day in month (number)
    // - MM:   month in year (number)
    // - yyyy: year
    //
    // See this link for details: https://docs.oracle.com/javase/tutorial/i18n/format/simpleDateFormat.html
    //
    //

    private static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");


    // read a date string and parse/convert to a date
    public static Date parseDate(String dateStr) throws ParseException {

        return formatter.parse(dateStr);
    }

    // read a date and format/convert to a string
    public static String formatDate(Date theDate) {

        String result = null;

        if (theDate != null) {
            result = formatter.format(theDate);
        }

        return result;
    }
}

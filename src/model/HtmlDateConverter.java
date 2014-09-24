package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HtmlDateConverter {
    private final SimpleDateFormat oldFormat =
            new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
    private final SimpleDateFormat newFormat =
            new SimpleDateFormat("MM/dd/YYY @ hh:mm a");

    public String convert(final String htmlFormat) {
        String formattedDate = htmlFormat;
        try {
            Date parsed = oldFormat.parse(htmlFormat);
            formattedDate = newFormat.format(parsed);
        } catch (ParseException ignore) {
        } finally {
            return formattedDate;
        }
    }
}

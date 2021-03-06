package dev.coomware.libs.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateParser {

    private final String DATE_STORAGE_FORMAT;

    /**
     * Constructor. Friendly date storage for Spigot.
     */
    public DateParser() {
        DATE_STORAGE_FORMAT = "EEE, d MMM yyyy HH:mm:ss Z";
    }

    /**
     * Converts supplied date object to a String.
     * @param date A date object to convert.
     * @return A read friendly String containing the date, utilizing the DSF we detailed in the constructor.
     */
    public String dateToString(Date date) {
        return new SimpleDateFormat(DATE_STORAGE_FORMAT, Locale.ENGLISH).format(date);
    }

    /**
     * Gets the current unix time. Current system time in milliseconds,
     * System#currentTimeMillis() / 1000L converts the number to seconds.
     *
     * @return UNIX Epoch Second (current time in seconds since 01 JAN 1970)
     */
    public long getUnixTime() {
        return System.currentTimeMillis() / 1000L;
    }

    /**
     * Gets a new Date object created from UNIX time.
     *
     * @param unix The UNIX time (in seconds)
     * @return New date object created from UNIX Time.
     */
    public Date getUnixDate(long unix) {
        return new Date(unix * 1000);
    }

    /**
     *
     * @param date
     * @return
     */
    public long getUnixTime(Date date) {
        if (date == null) {
            return 0;
        }

        return date.getTime() / 1000L;
    }

    /**
     *
     * @param time
     * @return
     */
    public Date parseDateOffset(String time) {
        Pattern timePattern = Pattern.compile("(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?"
                + "(?:([0-9]+)\\s*mo[a-z]*[,\\s]*)?"
                + "(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?"
                + "(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?"
                + "(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?"
                + "(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?"
                + "(?:([0-9]+)\\s*(?:s[a-z]*)?)?", Pattern.CASE_INSENSITIVE);
        Matcher matcher = timePattern.matcher(time);
        int years = 0;
        int months = 0;
        int weeks = 0;
        int days = 0;
        int hours = 0;
        int minutes = 0;
        int seconds = 0;
        boolean found = false;
        while (matcher.find()) {
            if (matcher.group() == null || matcher.group().isEmpty()) {
                continue;
            }
            for (int i = 0; i < matcher.groupCount(); i++) {
                if (matcher.group(i) != null && !matcher.group(i).isEmpty()) {
                    found = true;
                    break;
                }
            }
            if (found) {
                if (matcher.group(1) != null && !matcher.group(1).isEmpty()) {
                    years = Integer.parseInt(matcher.group(1));
                }
                if (matcher.group(2) != null && !matcher.group(2).isEmpty()) {
                    months = Integer.parseInt(matcher.group(2));
                }
                if (matcher.group(3) != null && !matcher.group(3).isEmpty()) {
                    weeks = Integer.parseInt(matcher.group(3));
                }
                if (matcher.group(4) != null && !matcher.group(4).isEmpty()) {
                    days = Integer.parseInt(matcher.group(4));
                }
                if (matcher.group(5) != null && !matcher.group(5).isEmpty()) {
                    hours = Integer.parseInt(matcher.group(5));
                }
                if (matcher.group(6) != null && !matcher.group(6).isEmpty()) {
                    minutes = Integer.parseInt(matcher.group(6));
                }
                if (matcher.group(7) != null && !matcher.group(7).isEmpty()) {
                    seconds = Integer.parseInt(matcher.group(7));
                }
                break;
            }
        }
        if (!found) {
            return null;
        }

        Calendar c = new GregorianCalendar();

        if (years > 0) c.add(Calendar.YEAR, years);
        if (months > 0) c.add(Calendar.MONTH, months);
        if (weeks > 0) c.add(Calendar.WEEK_OF_YEAR, weeks);
        if (days > 0) c.add(Calendar.DAY_OF_MONTH, days);
        if (hours > 0) c.add(Calendar.HOUR_OF_DAY, hours);
        if (minutes > 0) c.add(Calendar.MINUTE, minutes);
        if (seconds > 0) c.add(Calendar.SECOND, seconds);

        return c.getTime();
    }
}

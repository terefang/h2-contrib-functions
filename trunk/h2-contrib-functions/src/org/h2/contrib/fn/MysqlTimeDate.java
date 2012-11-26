package org.h2.contrib.fn;

import java.sql.SQLException;
import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.Date;

import org.h2.util.StringUtils;

//-- CREATE ALIAS	
//--	
//-- IF NOT EXISTS
//-- newFunctionAliasName	
//-- 	
//-- DETERMINISTIC	
//--
//-- FOR classAndMethodName
//-- AS sourceCodeString

public abstract class MysqlTimeDate 
{
	// CREATE ALIAS DATE FOR org.h2.contrib.fn.MysqlTimeDate.date ;
	/**
	 * See
	 * http://dev.mysql.com/doc/refman/5.1/en/date-and-time-functions.html#function_date
	 * This function is dependent on the exact formatting of the MySQL date/time
	 * string.
	 * 
	 * @param dateTime The date/time String from which to extract just the date
	 *            part.
	 * @return the date part of the given date/time String argument.
	 */
	public static String date(String dateTime) 
	{
		if (dateTime == null) 
		{
			return null;
		}
		
		int index = dateTime.indexOf(' ');
		if (index != -1) 
		{
			return dateTime.substring(0, index);
		}
		return dateTime;
	}
	
	// CREATE ALIAS UNIX_TIMESTAMP FOR org.h2.contrib.fn.MysqlTimeDate.unixTimestamp ;
	/**
	 * Get the seconds since 1970-01-01 00:00:00 UTC.
	 * See 
	 * http://dev.mysql.com/doc/refman/5.1/en/date-and-time-functions.html#function_unix-timestamp
	 * 
	 * @return the current timestamp in seconds.
	 */
	public static int unixTimestamp() 
	{
		return (int) (System.currentTimeMillis() / 1000L);
	}
	
	/**
	 * Get the seconds since 1970-01-01 00:00:00 UTC of the given timestamp.
	 * See 
	 * http://dev.mysql.com/doc/refman/5.1/en/date-and-time-functions.html#function_unix-timestamp
	 * 
	 * @param timestamp the timestamp
	 * @return the current timestamp in seconds.
	 */
	public static int unixTimestamp(Timestamp timestamp) throws SQLException 
	{
		return (int) (timestamp.getTime() / 1000L);
	}

	// CREATE ALIAS FROM_UNIXTIME FOR org.h2.contrib.fn.MysqlTimeDate.fromUnixTime ;
	/**
	 * See 
	 * http://dev.mysql.com/doc/refman/5.1/en/date-and-time-functions.html#function_from-unixtime
	 * 
	 * @param seconds The current timestamp in seconds.
	 * @return a formatted date/time String in the format "yyyy-MM-dd HH:mm:ss".
	 */
	public static String fromUnixTime(int seconds) 
    {
    	SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
        return formatter.format(new Date(seconds * 1000L));
    }

	/**
	 * See 
	 * http://dev.mysql.com/doc/refman/5.1/en/date-and-time-functions.html#function_from-unixtime
	 * 
	 * @param seconds The current timestamp in seconds.
	 * @param format The format of the date/time String to return.
	 * @return a formatted date/time String in the given format.
	 */
    public static String fromUnixTime(int seconds, String format) 
    {
        format = convertToSimpleDateFormat(format);
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(new Date(seconds * 1000L));
    }

    private static String convertToSimpleDateFormat(String format) 
    {
    	for (int i = 0; i < FORMAT_REPLACE.length; i += 2) 
    	{
    		format = StringUtils.replaceAll(format, FORMAT_REPLACE[i], FORMAT_REPLACE[i + 1]);
    	}
    	return format;
	}
	
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
    private static final String[] FORMAT_REPLACE = new String[] {
             "%a", "EEE",
             "%b", "MMM",
             "%c", "MM",
             "%d", "dd",
             "%e", "d",
             "%H", "HH",
             "%h", "hh",
             "%I", "hh",
             "%i", "mm",
             "%j", "DDD",
             "%k", "H",
             "%l", "h",
             "%M", "MMMM",
             "%m", "MM",
             "%p", "a",
             "%r", "hh:mm:ss a",
             "%S", "ss",
             "%s", "ss",
             "%T", "HH:mm:ss",
             "%W", "EEEE",
             "%w", "F",
             "%Y", "yyyy",
             "%y", "yy",
             "%%", "%",
    };

}
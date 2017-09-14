package com.ddebbie.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;


/**
 * @author Ram
 * 13-Sep-2017
 */
public class DateUtils {
    public static final int DAY = 1;
    public static final int MONTH = 2;
    public static final int YEAR = 3;

    public static final long YEAR_IN_MILLISECONDS = 365 * 24 * 60 * 60 * 1000L;
    public static final long MONTH_IN_MILLISECONDS = 30 * 24 * 60 * 60 * 1000L;
    public static final long DAY_IN_MILLISECONDS = 24 * 60 * 60 * 1000L;
    public static final long HOUR_IN_MILLISECONDS = 60 * 60 * 1000L;
    public static final long MINUTE_IN_MILLISECONDS = 60 * 1000L;

    public static final TimeZone localTimeZone = TimeZone.getDefault();

    public static long getCurrentTime() {
        return getLocalCalendar().getTimeInMillis();
    }

    public static long getCurrentTimeInGMT() {
        return getGMTCalendar().getTimeInMillis();
    }

    public static long getTimeInGMT(int incrementerType, int incrementer) {
        Calendar cal = getGMTCalendar();
        switch (incrementerType) {
            case DateUtils.DAY:
                cal.add(Calendar.DAY_OF_MONTH, incrementer);
                break;
            case DateUtils.MONTH:
                cal.add(Calendar.MONTH, incrementer);
                break;
            case DateUtils.YEAR:
                cal.add(Calendar.YEAR, incrementer);
                break;
            default:
                break;
        }
        long time = cal.getTimeInMillis();
        return time;
    }

    public static Date getStartTimeInGMT(int month, int year) {
        Calendar cal = getGMTCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.AM_PM, Calendar.AM);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date time = cal.getTime();
        cal = null;
        return time;
    }

    public static Date getStartTimeOfDay(int days){
        Calendar cal = getGMTCalendar();
        cal.set(Calendar.DATE, cal.get(Calendar.DATE)+days);
        cal.set(Calendar.AM_PM, Calendar.AM);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date time = cal.getTime();
        cal = null;
        return time;
    }

    public static Date getEndTimeOfDay(int days){
        Calendar cal = getGMTCalendar();
        cal.set(Calendar.DATE, cal.get(Calendar.DATE)+days);
        cal.set(Calendar.AM_PM, Calendar.PM);
        cal.set(Calendar.HOUR, 11);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        Date time = cal.getTime();
        cal = null;
        return time;
    }

    public static Date getStartTime(int month, int year) {
        Calendar cal = getLocalCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.AM_PM, Calendar.AM);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date time = cal.getTime();
        cal = null;
        return time;
    }

    public static Calendar getGMTCalendar() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        return Calendar.getInstance();
    }

    public static Date getEndTimeInGMT(int month, int year) {
        Calendar cal = getGMTCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.AM_PM, Calendar.PM);
        cal.set(Calendar.HOUR, 11);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        Date time = cal.getTime();
        cal = null;
        return time;
    }

    public static Date getEndTime(int month, int year) {
        Calendar cal = getLocalCalendar();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.AM_PM, Calendar.PM);
        cal.set(Calendar.HOUR, 11);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        Date time = cal.getTime();
        cal = null;
        return time;
    }

    public static long getTimeFromMMDDYYYYInGMT(String input) throws ParseException {
        if (null != input) {
            Calendar gmtCalendar = getGMTCalendar();// dont remove this line
            SimpleDateFormat dateFormatInGMT = new SimpleDateFormat("MM/dd/yyyy");
            return dateFormatInGMT.parse(input).getTime();
        }
        return 0;
    }
    
    public static long getTimeFromMMDDYYYYHH24MMSSInGMT(String input) throws ParseException {
        if (null != input) {
            Calendar gmtCalendar = getGMTCalendar(); // dont remove this line
            SimpleDateFormat dateFormatInGMT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            return dateFormatInGMT.parse(input).getTime();
        }
        return 0;
    }

    public static long getEndTimeFromMMDDYYYYInGMT(String input) throws ParseException {
        if (null != input) {
            Calendar gmtCalendar = getGMTCalendar();
            SimpleDateFormat dateFormatGMT = new SimpleDateFormat("MM/dd/yyyy");
            Date parse = dateFormatGMT.parse(input);
            gmtCalendar.setTime(parse);
            gmtCalendar.add(Calendar.HOUR, 23);
            gmtCalendar.add(Calendar.MINUTE, 59);
            gmtCalendar.add(Calendar.SECOND, 59);
            return gmtCalendar.getTimeInMillis();
        }
        return 0;
    }
    
    public static long getStartTimeFromMMDDYYYYInGMT(String input) throws ParseException {
        if (null != input) {
            Calendar gmtCalendar = getGMTCalendar();
            SimpleDateFormat dateFormatGMT = new SimpleDateFormat("MM/dd/yyyy");
            Date parse = dateFormatGMT.parse(input);
            gmtCalendar.setTime(parse);
            gmtCalendar.set(Calendar.HOUR, 0);
            gmtCalendar.set(Calendar.MINUTE, 0);
            gmtCalendar.set(Calendar.SECOND, 0);
            return gmtCalendar.getTimeInMillis();
        }
        return 0;
    }

    // Not changed
    public static Date getDateForMMYYYYStr(String input) throws ParseException {
        return new SimpleDateFormat("MMMMM yyyy").parse(input);
    }

    // Not changed
    public static String getDateWithTimeInMMDDYYYYhmm(long time) {
        return new SimpleDateFormat("MM.dd.yyyy h:mm a").format(new Date(time));
    }

    public static String getDateWithLocalTimeInMMDDYYYYhmm(long gmtTime) {
        Calendar calendar = getLocalCalendar();
        calendar.setTimeInMillis(gmtTime);
        return new SimpleDateFormat("MM.dd.yyyy h:mm:ss a").format(calendar.getTime());
    }
    
    public static String getDateWithGMTTimeInMMDDYYYYhmma(long gmtTime) {
        Calendar calendar = getGMTCalendar();
        calendar.setTimeInMillis(gmtTime);
        return new SimpleDateFormat("MM.dd.yyyy h:mm a").format(calendar.getTime());
    }
    

    public static String getDateWithGMTTimeIndMMMYYYYHHMMSSz(long gmtTime) {
        Calendar calendar = getGMTCalendar();
        calendar.setTimeInMillis(gmtTime);
        return new SimpleDateFormat("E, d MMM yyyy HH:mm:ss z").format(calendar.getTime());
    }
    
    public static String getDateWithGMTTimeInMMDDYYYYhmmaWithSlash(long gmtTime) {
        Calendar calendar = getGMTCalendar();
        calendar.setTimeInMillis(gmtTime);
        return new SimpleDateFormat("MM/dd/yyyy h:mm a").format(calendar.getTime());
    }
    
    public static String getDateWithGMTTimeInMMDDYYYYhmm(long gmtTime) {
        Calendar calendar = getGMTCalendar();
        calendar.setTimeInMillis(gmtTime);
        return new SimpleDateFormat("MM.dd.yyyy h:mm:ss a").format(calendar.getTime());
    }

    public static String getLocalDateWithInMMMMMDDYYYY(long time) {
        return new SimpleDateFormat("MMMMM,dd,yyyy").format(new Date(time));
    }
    
    public static String getGMTDateWithInMMDDYYYY(long gmtTime) {
        Calendar calendar = getGMTCalendar();
        calendar.setTimeInMillis(gmtTime);
        return new SimpleDateFormat("MM/dd/yyyy").format(calendar.getTime());
    }
    
    public static String getGMTDateWithHashInMMDDYYYY(long gmtTime) {
        Calendar calendar = getGMTCalendar();
        calendar.setTimeInMillis(gmtTime);
        return new SimpleDateFormat("MM-dd-yyyy").format(calendar.getTime());
    }

    public static String getGMTDateWithInMMDDYYYYHHMMSS(long gmtTime) {
        Calendar calendar = getGMTCalendar();
        calendar.setTimeInMillis(gmtTime);
        return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(calendar.getTime());
    }


    public static String getGMTDateWithInMMDDYYYYHHMMSSMillis(long gmtTime) {
        Calendar calendar = getGMTCalendar();
        calendar.setTimeInMillis(gmtTime);
        return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS").format(calendar.getTime());
    }

    
    // Not changed
    public static Calendar getCalendar(long time) {
        Calendar instance = getLocalCalendar();
        instance.setTimeInMillis(time);
        return instance;
    }

    public static Calendar getLocalCalendar() {
        TimeZone.setDefault(localTimeZone);
        return Calendar.getInstance();
    }

    public static Calendar getPreviousMonthInGMT(int year, int month) {
        Calendar instance = getGMTCalendar();
        instance.set(Calendar.YEAR, year);
        instance.set(Calendar.MONTH, month);
        instance.add(Calendar.MONTH, -1);
        return instance;
    }

    public static int getYear(Calendar cal) {
        return cal.get(Calendar.YEAR);
    }

    public static int getMonth(Calendar cal) {
        return cal.get(Calendar.MONTH);
    }

    public static int getCurrentYearInGMT() {
        Calendar cal = getGMTCalendar();
        return cal.get(Calendar.YEAR);
    }

    public static int getCurrentMonthInGMT() {
        Calendar cal = getGMTCalendar();
        return cal.get(Calendar.MONTH);
    }

    public static String getDuration(long previousTime) {
        String duration = null;
        Calendar currentInstance = getLocalCalendar();
        long currentTime = currentInstance.getTimeInMillis();
        long difference = currentTime - previousTime;
        if ((difference / YEAR_IN_MILLISECONDS) > 0) {
            duration = (difference / YEAR_IN_MILLISECONDS) + " years ago";
        } else if ((difference / MONTH_IN_MILLISECONDS) > 0) {
            duration = (difference / MONTH_IN_MILLISECONDS) + " months ago";
        } else if ((difference / DAY_IN_MILLISECONDS) > 0) {
            duration = (difference / DAY_IN_MILLISECONDS) + " days ago";
        } else if ((difference / HOUR_IN_MILLISECONDS) > 0) {
            duration = (difference / HOUR_IN_MILLISECONDS) + " hours ago";
        } else if ((difference / MINUTE_IN_MILLISECONDS) > 10) {
            duration = (difference / MINUTE_IN_MILLISECONDS) + " minutes ago";
        } else if ((difference / MINUTE_IN_MILLISECONDS) <= 10) {
            duration = "just now";
        }
        return duration;
    }

    public static long getTimeByAddMin(int addMinutes) {
        Calendar cal = getLocalCalendar();
        cal.add(Calendar.MINUTE, addMinutes);
        return cal.getTimeInMillis();
    }

    public static String formatGMT(long inputGMT) {
        Calendar gmtCalendar = getGMTCalendar();
        gmtCalendar.setTimeInMillis(inputGMT);
        return new SimpleDateFormat("MM/dd/yyyy").format(gmtCalendar.getTime());
    }
    
    public static String formatGMTWithTime(long inputGMT) {
        Calendar gmtCalendar = getGMTCalendar();
        gmtCalendar.setTimeInMillis(inputGMT);
        return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(gmtCalendar.getTime());
    }
    
    public static boolean isFirstDayOfMonth(){
    	Calendar today = getGMTCalendar();
    	int todayDay = today.get(Calendar.DAY_OF_MONTH);
    	int minimumDay = today.getActualMinimum(Calendar.DAY_OF_MONTH);
    	
    	if (todayDay == minimumDay){
    		return true;
    	}
    	return false;
    }
    
    public static boolean isLastDayOfMonth(){
    	Calendar today = getGMTCalendar();
    	int todayDay = today.get(Calendar.DAY_OF_MONTH);
    	int maximumDay = today.getActualMaximum(Calendar.DAY_OF_MONTH);
    	
    	if (todayDay == maximumDay){
    		return true;
    	}
    	return false;
    }
    
    public static int getPreviousMonthNumberInGMT() {
        Calendar instance = getGMTCalendar();
        instance.add(Calendar.MONTH, -1);
        return instance.get(Calendar.MONTH);
    }

    public static int getYearNumberOfPreviousMonthInGMT() {
        Calendar instance = getGMTCalendar();
        instance.add(Calendar.MONTH, -1);
        return instance.get(Calendar.YEAR);
    }

	public static long getTimeTillLastDayOfMonthInGMT() {
        Calendar instance = getGMTCalendar();
        Calendar instanceLastDay = getGMTCalendar();
		instanceLastDay.set(Calendar.DAY_OF_MONTH, instance.getActualMaximum(Calendar.DAY_OF_MONTH));
		instanceLastDay.set(Calendar.AM_PM, Calendar.AM);
		instanceLastDay.set(Calendar.HOUR, 0);
		instanceLastDay.set(Calendar.MINUTE, 0);
		instanceLastDay.set(Calendar.SECOND, 1);
		return instanceLastDay.getTimeInMillis() - instance.getTimeInMillis();
	}
	
	public static long getTimeTillPenultimateHourOfFirstDayOfNextMonthInGMT() {
        Calendar instance = getGMTCalendar();
        Calendar instanceNextMonthFirstDay = getGMTCalendar();
        instanceNextMonthFirstDay.add(Calendar.MONTH, 1);
        instanceNextMonthFirstDay.set(Calendar.DAY_OF_MONTH, instance.getActualMinimum(Calendar.DAY_OF_MONTH));
        instanceNextMonthFirstDay.set(Calendar.AM_PM, Calendar.PM);
        instanceNextMonthFirstDay.set(Calendar.HOUR, 11);
        instanceNextMonthFirstDay.set(Calendar.MINUTE, 0);
        instanceNextMonthFirstDay.set(Calendar.SECOND, 0);
		return instanceNextMonthFirstDay.getTimeInMillis() - instance.getTimeInMillis();
	}

    public static long getLocalTimeFromMMDDYYYYHH24MMSS(String input) throws ParseException {
        Calendar localCalendar = getLocalCalendar();         // dont remove this line
        if (null != input) {
            return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(input).getTime();
        }
        return 0;
    }
    

    public static long getGMTTimeFromMMDDYYYYHH24MMSSMillis(String input) throws ParseException {
        Calendar localCalendar = getGMTCalendar();         // dont remove this line
        if (null != input) {
            return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS").parse(input).getTime();
        }
        return 0;
    }
    
    public static long getGMTTimeByAddingMinutes(long minutes) {
    	long time = getCurrentTimeInGMT()+(1000*60*minutes);
    	return time;
    }
    
    public static long getStartTimeOfCurrentMonthInGMT() {
    	Calendar today = getGMTCalendar();
    	today.set(Calendar.DAY_OF_MONTH, 1);
    	today.set(Calendar.HOUR, 0);
    	today.set(Calendar.MINUTE, 0);
    	today.set(Calendar.SECOND, 0);
    	today.set(Calendar.MILLISECOND, 0);
    	today.set(Calendar.AM_PM, Calendar.AM);
    	return today.getTimeInMillis();
    }

    public static long getEndTimeOfCurrentMonthInGMT()  {
    	Calendar today = getGMTCalendar();
    	today.set(Calendar.DAY_OF_MONTH, today.getActualMaximum(Calendar.DAY_OF_MONTH));
    	today.set(Calendar.HOUR, 23);
    	today.set(Calendar.MINUTE, 59);
    	today.set(Calendar.SECOND, 59);
    	today.set(Calendar.MILLISECOND, 999);
    	today.set(Calendar.AM_PM, Calendar.AM);
    	return today.getTimeInMillis();
    	
    }
    public static Calendar getSecondPreviousMonthInGMT(int year, int month) {
        Calendar instance = getGMTCalendar();
        instance.set(Calendar.YEAR, year);
        instance.set(Calendar.MONTH, month);
        instance.add(Calendar.MONTH, -2);
        return instance;
    }
    public static String getFormatMonthYearInGMT(long inputGMT){
    	Calendar gmtCalendar = getGMTCalendar();
        gmtCalendar.setTimeInMillis(inputGMT);
        return new SimpleDateFormat("MMMMM yyyy").format(gmtCalendar.getTime());
    }
    
    public static Date getStartTimeOfNextSundayInGMT(){
    	Calendar gmtCalendar = getGMTCalendar();
    	int weekday = gmtCalendar.get(Calendar.DAY_OF_WEEK);
    	int days = Calendar.SUNDAY - weekday;
    	if(days < 0){
    		days += 7;
    	}
    	gmtCalendar.add(Calendar.DAY_OF_YEAR, days);
    	gmtCalendar.set(Calendar.AM_PM, Calendar.AM);
    	gmtCalendar.set(Calendar.HOUR, 0);
    	gmtCalendar.set(Calendar.MINUTE, 0);
    	gmtCalendar.set(Calendar.SECOND, 0);
    	gmtCalendar.set(Calendar.MILLISECOND, 0);
    	return gmtCalendar.getTime();
    	 
    }
    public static long getStartTimeFromMMMYYYYInGMT(String input) throws ParseException {
        if (null != input) {
            Calendar gmtCalendar = getGMTCalendar();
            SimpleDateFormat dateFormatGMT = new SimpleDateFormat("MMMyyyy");
            Date parse = dateFormatGMT.parse(input);
            gmtCalendar.setTime(parse);
            gmtCalendar.set(Calendar.HOUR, 0);
            gmtCalendar.set(Calendar.MINUTE, 0);
            gmtCalendar.set(Calendar.SECOND, 0);
            return gmtCalendar.getTimeInMillis();
        }
        return 0;
    }
    public static long getEndTimeFromMMMYYYYInGMT(String input) throws ParseException {
        if (null != input) {
            Calendar gmtCalendar = getGMTCalendar();
            SimpleDateFormat dateFormatGMT = new SimpleDateFormat("MMMyyyy");
            Date parse = dateFormatGMT.parse(input);
            gmtCalendar.setTime(parse);
            gmtCalendar.add(Calendar.HOUR, 23);
            gmtCalendar.add(Calendar.MINUTE, 59);
            gmtCalendar.add(Calendar.SECOND, 59);
            return gmtCalendar.getTimeInMillis();
        }
        return 0;
    }
    public static Map<Integer, Integer[]> getArrayOfMonthsAndYearsBetweenDates(String from, String to ) throws ParseException{
        Calendar fromGmtCalendar = getGMTCalendar();
        fromGmtCalendar.setTimeInMillis(getStartTimeFromMMMYYYYInGMT(from));
        Calendar toGmtCalendar = getGMTCalendar();
        toGmtCalendar.setTimeInMillis(getEndTimeFromMMMYYYYInGMT(to));
        Map<Integer, Integer[]> monthYearMap = new HashMap<Integer, Integer[]>();
        int i = 1;
        while(fromGmtCalendar.before(toGmtCalendar)){
        	Integer[] monthYearArray = new Integer[2];
        	monthYearArray[0] = fromGmtCalendar.get(Calendar.MONTH);
        	monthYearArray[1] = fromGmtCalendar.get(Calendar.YEAR);
        	monthYearMap.put(i, monthYearArray);
        	i++;
            fromGmtCalendar.add(Calendar.MONTH,1);
        }
        return monthYearMap;
    }
    
    public static Date getPreviousDate(Date currentDate){
    	Calendar cal = getGMTCalendar();
    	cal.setTime(currentDate);
    	cal.add(Calendar.DAY_OF_YEAR, -1);
    	Date previousDate = cal.getTime();
    	return previousDate;
    }
    
    public static Date getPreviousNDaysDate(Date givenDate, int previousNDays){
    	Calendar cal = getGMTCalendar();
    	cal.setTime(givenDate);
    	cal.add(Calendar.DAY_OF_YEAR, -previousNDays);
    	Date previousNDaysDate = cal.getTime();
    	return previousNDaysDate;
    }
    
    public static Date getDateForMMDDYY(String date) throws ParseException{
    	return new SimpleDateFormat("MM/dd/yy").parse(date);
    }
    
    public static long getStartTimeOfADate(Date date){
    	Calendar cal = getGMTCalendar();
    	cal.setTime(date);
    	cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTimeInMillis();
    }
    
    public static long getEndTimeOfADate(Date date){
    	Calendar cal = getGMTCalendar();
    	cal.setTime(date);
    	cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTimeInMillis();
    }
    
    public static String getMMDDYYFromDate(Date date){
    	DateFormat format = new SimpleDateFormat("MM/dd/yy");
    	return format.format(date);
    }
    
    public static Date getNextDate(Date currentDate){
    	Calendar cal = getGMTCalendar();
    	cal.setTime(currentDate);
    	cal.add(Calendar.DAY_OF_YEAR, 1);
    	Date nextDate = cal.getTime();
    	return nextDate;
    }
    
    public static Date getCurrentGMTDate() throws ParseException{
    	Date currentGMTDate = new Date(getCurrentTimeInGMT());
    	Date formattedDate = new SimpleDateFormat("MM/dd/yy").parse(getMMDDYYFromDate(currentGMTDate));
    	return formattedDate;
    }
    
    public static Date getDateForNthDayOfAMonth(int year, int month, int day) throws ParseException{
    	Calendar cal = getGMTCalendar();
    	cal.set(year, month, day);
    	SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
    	String formattedDate = format.format(cal.getTime());
    	return format.parse(formattedDate);
    }
    
    public static Date getDateForFutureNDays(Date givenDate, int nextNDays){
    	Calendar cal = getGMTCalendar();
    	cal.setTime(givenDate);
    	cal.add(Calendar.DAY_OF_YEAR, nextNDays);
    	Date futureNDaysDate = cal.getTime();
    	return futureNDaysDate;
    }
    
    public static Date getDateFromMMDDYYYYString(String dateString) throws ParseException{
    	Calendar cal = getGMTCalendar();
    	SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
    	Date date = format.parse(dateString);
    	cal.setTime(date);
    	return cal.getTime();
    }
    
    public static long getDayTimeStampInMillSeconds(int days) throws ParseException{
    	Calendar cal = getGMTCalendar();
        cal.set(Calendar.DATE, cal.get(Calendar.DATE)+days);
        cal.set(Calendar.AM_PM, Calendar.AM);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long timeStamp = cal.getTimeInMillis();
        cal = null;
        return timeStamp;
    }
}
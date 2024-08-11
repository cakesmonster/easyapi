package com.cakemonster.easyapi.utils;

import com.cakemonster.easyapi.enums.TimeRangeType;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * TimeRangeUtil
 *
 * @author cakemonster
 * @date 2024/8/10
 */
public class TimeRangeUtil {

    public static Map<String, Object> generateTimeParams(TimeRangeType timeRangeType, Date startTime, Date endTime) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat hourFormatter = new SimpleDateFormat("HH");
        SimpleDateFormat minuteFormatter = new SimpleDateFormat("mm");
        SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat hourOnlyFormatter = new SimpleDateFormat("yyyy-MM-dd HH");

        Map<String, Object> timeParams = new HashMap<>();
        boolean isSameDate = dateFormatter.format(startTime).equals(dateFormatter.format(endTime));

        switch (timeRangeType) {
            case LAST_30_MINUTES:
                if (isSameDate) {
                    timeParams.put("dt", dateFormatter.format(startTime));
                    timeParams.put("hh", hourFormatter.format(startTime));
                    timeParams.put("startMm", minuteFormatter.format(startTime));
                    timeParams.put("endMm", minuteFormatter.format(endTime));
                } else {
                    timeParams.put("startDateTime", dateTimeFormatter.format(startTime));
                    timeParams.put("endDateTime", dateTimeFormatter.format(endTime));
                }
                break;
            case LAST_1_HOUR:
            case LAST_2_HOURS:
            case LAST_4_HOURS:
            case LAST_6_HOURS:
            case LAST_12_HOURS:
                if (isSameDate) {
                    timeParams.put("dt", dateFormatter.format(startTime));
                    timeParams.put("startHh", hourFormatter.format(startTime));
                    timeParams.put("endHh", hourFormatter.format(endTime));
                } else {
                    timeParams.put("startDateTime", hourOnlyFormatter.format(startTime));
                    timeParams.put("endDateTime", hourOnlyFormatter.format(endTime));
                }
                break;
            case YESTERDAY:
            case TODAY:
            case LAST_14_DAYS:
            case LAST_MONTH:
            case LAST_THREE_MONTH:
                timeParams.put("startDt", dateFormatter.format(startTime));
                timeParams.put("endDt", dateFormatter.format(endTime));
                break;
            default:
                throw new IllegalArgumentException(String.format("unsupported this timeRangeType: %s", timeRangeType));
        }
        return timeParams;
    }

    public static Map<String, Object> generateTimeParams(TimeRangeType timeRangeType) {
        Date[] timeRange = getTimeRange(timeRangeType);
        return generateTimeParams(timeRangeType, timeRange[0], timeRange[1]);
    }

    public static Date[] getTimeRange(TimeRangeType timeRangeType) {
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        Date startTime;
        Date endTime = now;

        switch (timeRangeType) {
            case LAST_30_MINUTES:
                calendar.add(Calendar.MINUTE, -30);
                startTime = calendar.getTime();
                break;
            case LAST_1_HOUR:
                calendar.add(Calendar.HOUR_OF_DAY, -1);
                startTime = calendar.getTime();
                break;
            case LAST_2_HOURS:
                calendar.add(Calendar.HOUR_OF_DAY, -2);
                startTime = calendar.getTime();
                break;
            case LAST_4_HOURS:
                calendar.add(Calendar.HOUR_OF_DAY, -4);
                startTime = calendar.getTime();
                break;
            case LAST_6_HOURS:
                calendar.add(Calendar.HOUR_OF_DAY, -6);
                startTime = calendar.getTime();
                break;
            case LAST_12_HOURS:
                calendar.add(Calendar.HOUR_OF_DAY, -12);
                startTime = calendar.getTime();
                break;
            case YESTERDAY:
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                startTime = calendar.getTime();
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                startTime = calendar.getTime();
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                endTime = calendar.getTime();
                break;
            case TODAY:
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                startTime = calendar.getTime();
                endTime = now;
                break;
            case LAST_14_DAYS:
                calendar.add(Calendar.DAY_OF_MONTH, -14);
                startTime = calendar.getTime();
                break;
            case LAST_MONTH:
                calendar.add(Calendar.MONTH, -1);
                startTime = calendar.getTime();
                break;
            case LAST_THREE_MONTH:
                calendar.add(Calendar.MONTH, -3);
                startTime = calendar.getTime();
                break;
            default:
                throw new IllegalArgumentException(String.format("unsupported this timeRangeType: %s", timeRangeType));
        }

        return new Date[] {startTime, endTime};
    }

}

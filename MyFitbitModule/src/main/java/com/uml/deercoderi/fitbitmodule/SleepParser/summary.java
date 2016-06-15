package com.uml.deercoderi.fitbitmodule.SleepParser;

/***
 *  Created by Chang Liu, for parsing of the sleep response(JSON-style)
 *
 *
 *  According to the official tutorial about Sleep information:
 *  (https://dev.fitbit.com/docs/sleep/):
 *
 *
 *  Request:
 *      GET https://api.fitbit.com/1/user/[user-id]/sleep/date/[date].json

 *  Response:
 *          summary Object is part of the response that itself is an json object
 *
 *
 *  History:
 *
 *  Chang Liu, v1.0, June 14th, 2016, Create first initial version and README
 *
 */

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cliu on 6/14/16.
 */
public class summary {
    // raw json object
    private JSONObject rawSummary;

    // member for summary
    private String totalMinutesAsleep;
    private String totalSleepRecords;
    private String totalTimeInBed;

    public void summary(JSONObject jb) {
        rawSummary = jb;
    }

    public void parse() throws JSONException {
        setTotalMinutesAsleep(rawSummary.getString("totalMinutesAsleep"));
        setTotalSleepRecords(rawSummary.getString("totalSleepRecords"));
        setTotalTimeInBed(rawSummary.getString("totalTimeInBed"));
    }

    public String getParsedString() {
        String header = "\n ***** Sleep Summary *****";
        String totalMinutes = "Total Minutes Asleep: " + getTotalMinutesAsleep() + "\n";
        String totalSleep = "Total Sleep Records: " + getTotalSleepRecords() + "\n";
        String totalTimeBed = "Total Time in Bed: " + getTotalTimeInBed() + "\n";

        return header + totalMinutes + totalSleep + totalTimeBed;
    }

    public void setTotalMinutesAsleep(String as) {
        totalMinutesAsleep = as;
    }

    public String getTotalMinutesAsleep() {
        return totalMinutesAsleep;
    }

    public void setTotalSleepRecords(String re) {
        totalSleepRecords = re;
    }

    public String getTotalSleepRecords() {
        return totalSleepRecords;
    }

    public void setTotalTimeInBed(String time) {
        totalTimeInBed = time;
    }

    public String getTotalTimeInBed() {
        return totalTimeInBed;
    }


}

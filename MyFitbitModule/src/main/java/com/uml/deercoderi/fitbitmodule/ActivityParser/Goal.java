package com.uml.deercoderi.fitbitmodule.ActivityParser;

/***
 *  Created by Chang Liu, for parsing of the activity response(JSON-style)
 *
 *  There are multiple GET request, so the response of activity also has multiple types, including
 *  non-TimeSeries and TimeSeries. We have to implemented all the interface and keyword for these
 *  response and save them.
 *
 *  According to the official tutorial about Activity and its Time Series:
 *  (https://dev.fitbit.com/docs/activity/):
 *
 *  Activity Summary:  https://api.fitbit.com/1/user/[user-id]/activities/date/[date].json

 *  We need to set the correct date, in the format of YYYY-MM-DD
 *  and the correct period, which could be 1d, 7d, 30d, 1w, 1m, 3m, 6m, 1y, or max.
 *
 *
 *  History:
 *
 *  Chang Liu, v1.0, June 9th, 2016, Create first initial version and README
 *
 */

/***
 * This is part of the ##Get Daily Activity Summary##, the response contains:
 * 1) activities List
 * 2) goals
 * 3) summary
 *
 * each of the above object contains many useful information, we construct three
 * sub-class to store all these objects and its member variables.
 *
 *
 * see details of Get Daily Activity Summary Section:
 *          https://dev.fitbit.com/docs/activity/#get-daily-activity-summary
 */
public class Goal {
    public String caloriesOut = "";
    public String distance = "";
    public String floors = "";
    public String steps = "";
}


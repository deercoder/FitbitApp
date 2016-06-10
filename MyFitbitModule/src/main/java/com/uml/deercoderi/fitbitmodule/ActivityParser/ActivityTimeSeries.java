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
 *  Activity Time series:  https://api.fitbit.com/1/user/[user-id]/activities/date/[date].json

 *  We need to set the correct date, in the format of YYYY-MM-DD
 *  and the correct period, which could be 1d, 7d, 30d, 1w, 1m, 3m, 6m, 1y, or max.
 *
 *
 *  History:
 *
 *  Chang Liu, v1.0, June 9th, 2016, Create first initial version and README
 *
 */


/**
 * Created by cliu on 6/9/16.
 */
public class ActivityTimeSeries {

    private String rawJsonString = "";


    public ActivityTimeSeries() {
        /// empty now, for constructing the time-series parser
    }


    public ActivityTimeSeries(String jsonString) {
        rawJsonString = jsonString;
    }


    public void parse() {

    }

}

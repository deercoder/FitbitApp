package com.uml.deercoderi.fitbitmodule;

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
 *  Activity FitbitSummary:  https://api.fitbit.com/1/user/[user-id]/activities/date/[date].json
 *
 *  Time Series:
 *  1. GET /1/user/[user-id]/[resource-path]/date/[date]/[period].json
 2. GET /1/user/[user-id]/[resource-path]/date/[base-date]/[end-date].json
 *
 *  For example: GET https://api.fitbit.com/1/user/-/activities/steps/date/today/1m.json

 *
 *  We need to set the correct date, in the format of YYYY-MM-DD
 *  and the correct period, which could be 1d, 7d, 30d, 1w, 1m, 3m, 6m, 1y, or max.
 *
 *
 *  History:
 *
 *  Chang Liu, v1.0, June 9th, 2016, Create first initial version and README
 *
 */

import android.util.Log;

import com.uml.deercoderi.fitbitmodule.ActivityParser.ActivityDailySummary.FitbitSummary;
import com.uml.deercoderi.fitbitmodule.ActivityParser.FitbitActivityLogging;
import com.uml.deercoderi.fitbitmodule.ActivityParser.FitbitActivityTimeSeries;
import com.uml.deercoderi.fitbitmodule.ActivityParser.FitbitDailyActivitySummary;
import com.uml.deercoderi.fitbitmodule.ActivityParser.ActivityDailySummary.FitbitGoal;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cliu on 6/9/16.
 */
public class FitbitActivityParser {

    /// define the types of parser, so that we can select which sub-parser to choose
    public static final int PARSER_DAILY_SUMMARY = 0;
    public static final int PARSER_ACTIVITY_TIME_SERIES = 1;
    public static final int PARSER_ACTIVITY_LOGGING = 2;
    public static final int PARSER_ACTIVITY_TYPES = 3;
    public static final int PARSER_ACTIVITY_GOALS = 4;
    public static final int PARSER_LIFETIME_STATS = 5;

    // parser for daily activity summary
    private FitbitDailyActivitySummary mDailySummary;

    // parser for time series activity
    private FitbitActivityTimeSeries mActivityTimeSeries;

    // parser for goals
    private FitbitGoal mGoals;

    // parser for Activity Logging List
    private FitbitActivityLogging mActivityLogging;

    public FitbitActivityParser() {
        /// empty constuctor, for future extension
    }


    public FitbitActivityParser(JSONObject json, int type) {
        switch(type) {
            case PARSER_DAILY_SUMMARY:
                mDailySummary = new FitbitDailyActivitySummary(json);
                break;
            case PARSER_ACTIVITY_TIME_SERIES:
                mActivityTimeSeries = new FitbitActivityTimeSeries(json);
                break;
            case PARSER_ACTIVITY_LOGGING:
                mActivityLogging = new FitbitActivityLogging(json);
                break;
            case PARSER_ACTIVITY_TYPES:
            case PARSER_ACTIVITY_GOALS:
                mGoals = new FitbitGoal(json);
                break;
            case PARSER_LIFETIME_STATS:
            default:
                Log.e("FitbitActivityParser", "other cases in FitbitActivityParser!");
        }

    }


    public String parseDailySummary() throws JSONException {
        if (mDailySummary != null) {
            String[] acts = mDailySummary.parseActivities();     // Cannot test as it's always empty
            String goals = mDailySummary.parseGoals();          // OK, single output
            String summary = mDailySummary.parseSummary();      // OK, single output
            String activityString = "";

            if (acts != null) {
                for(int i = 0; i < acts.length; i++) {
                    activityString += acts[i];
                }
            }
            return activityString + goals + summary;
        }
        else {
            Log.e("FitbitActivityParser", "NULL daily summary parser, error for parsing!");
            return "";
        }
    }

    public void parseActivityTimeSeries() {
        if (mActivityTimeSeries != null) {
            mActivityTimeSeries.parse();
        }
        else {
            Log.e("FitbitActivityParser", "NULL acitivity time series parser, error for parsing!");
        }
    }

    /// parse the activity logging
    public String parseActivityLogging() throws JSONException {
        if (mActivityLogging != null ) {
            String loggingText = mActivityLogging.getParsedString();
            return loggingText;
        }
        else {
            Log.e("FitbitActivityParser", "NULL activity Logging list, error for parsing!");
            return "";
        }
    }

    public FitbitSummary getSummary() {
        if (mDailySummary != null) {
            return mDailySummary.getSummary();
        }
        else {
            return null;
        }
    }

    public FitbitGoal getGoal() {
        if (mDailySummary != null) {
            return mDailySummary.getGoals();
        }
        else {
            return null;
        }
    }

}

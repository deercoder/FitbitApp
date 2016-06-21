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
 *  Activity FitbitSummary:  https://api.fitbit.com/1/user/[user-id]/activities/date/[date].json

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

import com.uml.deercoderi.fitbitmodule.ActivityParser.ActivityDailySummary.FitbitActivity;
import com.uml.deercoderi.fitbitmodule.ActivityParser.ActivityDailySummary.FitbitGoal;
import com.uml.deercoderi.fitbitmodule.ActivityParser.ActivityDailySummary.FitbitSummary;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/***
 * This is part of the ##Get Daily Activity FitbitSummary##, the response contains:
 * 1) activities List
 * 2) goals
 * 3) summary
 *
 * each of the above object contains many useful information, we construct three
 * sub-class to store all these objects and its member variables.
 *
 *
 * see details of Get Daily Activity FitbitSummary Section:
 *          https://dev.fitbit.com/docs/activity/#get-daily-activity-summary
 */
public class FitbitDailyActivitySummary {

    // object for using to parse sub-object
    private FitbitActivity mActivity;        // activity
    private FitbitGoal mGoals;           // goals
    private FitbitSummary mSummary;       // summary

    // raw response string in JSON-style
    private JSONObject rawResponse;

    /// pre-prasing stage, which will parse the raw JSON into several sub-object
    /// for example, activities, goals, summary
    private JSONArray activitiesJSONArray;
    private JSONObject goalJSONObject;
    private JSONObject summaryJSONObject;


    /// used the raw response string to construct parser
    public FitbitDailyActivitySummary(JSONObject json) {
        rawResponse = json;

        // do the pre-parsing when constructing the objects
        preParse();
    }

    /// using the raw json object, we get the activity/goal/summary sub-object for future parsing
    public void preParse() {
        try {
            activitiesJSONArray = rawResponse.getJSONArray("activities");
            goalJSONObject = rawResponse.getJSONObject("goals");
            summaryJSONObject = rawResponse.getJSONObject("summary");

            /// use these objects to construct the memeber object
            mGoals = new FitbitGoal(goalJSONObject);
            mSummary = new FitbitSummary(summaryJSONObject);

            mGoals.parse();
            mSummary.parse();
        } catch (JSONException e) {
            Log.e("DailyActivitySummary", "Exception when preparse the raw JSON Object!");
        }
    }




    /// for future extension, parse()
    public void parse() {
        //String activity = parseActivities();
        //String goal = parseGoals();
        //String summary = parseSummary();
    }

    /*
     *  Return all the activities in the daily activity summary
     */
    public String[] parseActivities() throws JSONException {

        /// parse the activities arrays if not null
        if (activitiesJSONArray != null) {
            final int len = activitiesJSONArray.length();
            String[] response = new String[len];
            for (int i = 0; i < len; i++) {
                /// get each of the activity
                final JSONObject oneActivity = activitiesJSONArray.getJSONObject(i);
                FitbitActivity fitAct = new FitbitActivity();
                fitAct.parse(oneActivity);
                response[i] = fitAct.getParsedString();
            }
            return response;
        }
        return null;
    }

    /// for each one activity, parse each of its field
    public String parseOneActivity(JSONObject json) throws JSONException {
        FitbitActivity fitAct = new FitbitActivity();
        fitAct.parse(json);
        String oneActResponse = fitAct.getParsedString();
        return oneActResponse;
    }


    public String parseGoals() throws JSONException {
        FitbitGoal fitGoal = new FitbitGoal(goalJSONObject);
        fitGoal.parse();
        String header = "********** Goals *********\n";
        return header + fitGoal.getParsedString();
    }

    public String parseSummary() throws JSONException {
        Log.e("aaaaa", summaryJSONObject.toString());
        FitbitSummary fitSum = new FitbitSummary(summaryJSONObject);
        fitSum.parse();
        String header = "********** Summary *********\n";
        return header + fitSum.getParsedString();
    }

    public FitbitSummary getSummary() {
        if (mSummary != null) {
            return mSummary;
        }
        else {
            return null;
        }
    }

    public FitbitGoal getGoals() {
        if (mGoals != null) {
            return mGoals;
        }
        else {
            return null;
        }
    }


} //// ENDS, for daily activity summary

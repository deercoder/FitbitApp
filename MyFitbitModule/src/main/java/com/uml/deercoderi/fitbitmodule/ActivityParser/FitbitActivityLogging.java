package com.uml.deercoderi.fitbitmodule.ActivityParser;

/***
 *  Created by Chang Liu, for parsing of the activity response(JSON-style)
 *
 *  There are multiple GET request, so the response of activity also has multiple types, including
 *  non-TimeSeries and TimeSeries. We have to implemented all the interface and keyword for these
 *  response and save them.
 *
 *  According to the official tutorial about Activity and its Time Series:
 *  (https://dev.fitbit.com/docs/activity/#get-activity-logs-list):
 *
 *  Activity Logging List: GET https://api.fitbit.com/1/user/-/activities/list.json
 *  for details about the parameters we can set, check the above URL.
 *
 *
 *  History:
 *
 *  Chang Liu, v1.0, June 13th, 2016, Create first initial version and README
 *
 */


import android.util.Log;

import com.uml.deercoderi.fitbitmodule.ActivityParser.ActivityLoggingList.FitbitLogActivity;
import com.uml.deercoderi.fitbitmodule.ActivityParser.ActivityLoggingList.FitbitLogPagination;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cliu on 6/13/16.
 */
public class FitbitActivityLogging {

    // raw json response object
    private JSONObject rawResponse;

    // member that contains the key point
    private JSONArray activities;
    private JSONObject pagination;


    public FitbitActivityLogging(JSONObject js) {
        rawResponse = js;

        // pre-parsing
        try {
            parse();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("ActivityLogging", "parse exception in consturctor!");
        }
    }


    public void parse() throws JSONException {
        activities = rawResponse.getJSONArray("activities");
        pagination = rawResponse.getJSONObject("pagination");
    }

    public String getParsedString() throws JSONException {

       // Log.e("aaaaaa", "bbbb");
        String act = "";
        String page = "";
        if (activities != null) {
            int len = activities.length();
            Log.e("aaaaaa", "bbbb" + len);
            for (int i = 0; i < len; i++) {
                FitbitLogActivity fa = new FitbitLogActivity(activities.getJSONObject(i));
                fa.parse();
                act += fa.getParsedString();
                Log.e("aaaaaa", "bbbbyyyyyy");
            }
        }
        if (pagination != null) {
            Log.e("aaaaaa", "bbbbxxxxx");
            FitbitLogPagination fp = new FitbitLogPagination(pagination);
            fp.parse();
            page = fp.getParsedString();
        }

        return act + page;
    }

    public String getOneActivityParsedString(JSONObject jsObject) throws JSONException {
        if (jsObject != null) {
            FitbitLogActivity fa = new FitbitLogActivity(jsObject);
            fa.parse();
            return fa.getParsedString();
        }
        else {
            Log.e("FitbitActivityLogging", "NULL json object in getOneActivityParsedString");
            return "";
        }
    }
}

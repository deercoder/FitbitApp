package com.uml.deercoderi.fitbitmodule.SleepParser;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cliu on 6/15/16.
 *
 * Parser of the response for Time Series of Sleep data, as the result, it may contain many keywords
 * that are included in the standard request
 *
 * We have to parse two ways of request:
 *
 *     1) GET https://api.fitbit.com/1/user/[user-id]/[resource-path]/date/[date]/[period].json
 *
 *     2) GET https://api.fitbit.com/1/user/[user-id]/[resource-path]/date/[base-date]/[end-date].json
 *
 * For the constructing of the request, we need to refer to the original ##FitbitSleepTimeSeries.
 *
 *
 */
public class SleepTimeSeries {

    /// raw response
    private JSONObject rawResponse;

    // member of the response json object
    private String logId;


    public void SleepGoal(JSONObject jb) {
        rawResponse = jb;

        try{
            parse();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("SleepTimeSeries", "error parsing in the constuctor!");
        }
    }

    public void parse() throws JSONException {
        setLogId(rawResponse.getString("logId"));
    }

    public String getParsedString() {
        String header = "\n**** Sleep Time Series Data: ******\n";
        String logId = "Log ID: " + getLogId();

        return header + logId;
    }

    public void setLogId(String id) {
        logId = id;
    }

    public String getLogId() {
        return logId;
    }


}

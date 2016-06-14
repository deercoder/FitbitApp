package com.uml.deercoderi.fitbitmodule.ActivityParser.ActivityDailySummary;

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
public class FitbitGoal {

    /// KEY field that stores the data
    private String caloriesOut = "";
    private String distance = "";
    private String floors = "";
    private String steps = "";

    // raw json objects
    private JSONObject json;

    public FitbitGoal(JSONObject js) {
        json = js;
        // just one object, no need to parse sub-object
    }

    public void parse() throws JSONException {
        if (json != null) {
            setCaloriesOut(json.getString("caloriesOut"));
            setDistance(json.getString("distance"));
            setFloors(json.getString("floors"));
            setSteps(json.getString("steps"));
        }
    }

    public String getParsedString() {
        String calOut = "Calorie Out: " + getCaloriesOut() + "\n";
        String distance = "Distance: " + getDistance() + "\n";
        String floors = "Floors: " + getFloors() + "\n";
        String steps = "Steps: " + getSteps() + "\n";
        return calOut + distance + floors + steps;
    }


    public void setCaloriesOut(String cal) {
        caloriesOut = cal;
    }

    public String getCaloriesOut() {
        return caloriesOut;
    }

    public void setDistance(String dis) {
        distance = dis;
    }

    public String getDistance() {
        return distance;
    }

    public void setFloors(String fl) {
        floors = fl;
    }

    public String getFloors() {
        return floors;
    }

    public void setSteps(String st) {
        steps = st;
    }

    public String getSteps() {
        return steps;
    }
}


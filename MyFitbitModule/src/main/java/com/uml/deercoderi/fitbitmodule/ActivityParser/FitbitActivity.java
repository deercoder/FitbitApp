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
public class FitbitActivity {
    private String activityId = "";
    private String activityParentId = "";
    private String calories = "";
    private String description = "";
    private String distance = "";
    private String duration = "";
    private String hasStartTime = "";
    private String isFavorite = "";
    private String logId = "";
    private String name = "";
    private String startTime = "";
    private String steps = "";

    public void parse(JSONObject json) throws JSONException {
        setActivityId(json.getString("activityId"));
        setActivityParentId(json.getString("activityParentId"));
        setCalories(json.getString("calories"));
        setDescription(json.getString("description"));
        setDistance(json.getString("distance"));
        setDuration(json.getString("duration"));
        setHasStartTime(json.getString("hasStartTime"));
        setIsFavorite(json.getString("isFavorite"));
        setLogId(json.getString("logId"));
        setName(json.getString("name"));
        setStartTime(json.getString("startTime"));
        setSteps(json.getString("steps"));
    }

    public String getParsedString() {

        String actId = "Activity ID: " + getActivityId() + "\n";
        String actParentId = "Activity Parent ID: " + getActivityParentId() + "\n";
        String cal = "Calorie: " + getCalories() + "\n";
        String des = "Description: " + getDescription() + "\n";
        String distance = "Distnce: " + getDistance() + "\n";
        String duration = "Duration: " + getDuration() + "\n";
        String hasStartTime = "HasStartTime: " + getHasStartTime() + "\n";
        String isFav = "IsFavorite: " + getIsFavorite() + "\n";
        String logID = "logID: " + getLogId() + "\n";
        String name = "Name: " + getName() + "\n";
        String startTime = "Start Time: " + getStartTime() + "\n";
        String steps = "Steps: " + getSteps() + "\n";

        return actId + actParentId + cal + des + distance + hasStartTime + duration +
                isFav + logID + name + startTime + steps;
    }

    public void setActivityId(String id) {
        activityId = id;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityParentId(String id) {
        activityParentId = id;
    }

    public String getActivityParentId() {
        return activityParentId;
    }

    public void setCalories(String cal) {
        calories = cal;
    }

    public String getCalories() {
        return calories;
    }

    public void setDescription(String des) {
        description = des;
    }

    public String getDescription() {
        return description;
    }

    public void setDistance(String dis) {
        distance = dis;
    }

    public String getDistance() {
        return distance;
    }

    public void setDuration(String dur) {
        duration = dur;
    }

    public String getDuration() {
        return duration;
    }

    public void setHasStartTime(String str) {
        hasStartTime = str;
    }

    public String getHasStartTime() {
        return hasStartTime;
    }

    public void setIsFavorite(String fav) {
        isFavorite = fav;
    }

    public String getIsFavorite() {
        return isFavorite;
    }

    public void setLogId(String id) {
        logId = id;
    }

    public String getLogId() {
        return logId;
    }

    public void setName(String name1) {
        name = name1;
    }

    public String getName() {
        return name;
    }

    public void setStartTime(String st) {
        startTime = st;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setSteps(String st) {
        steps = st;
    }

    public String getSteps() {
        return steps;
    }
    /// may be we need some getters in the future

}

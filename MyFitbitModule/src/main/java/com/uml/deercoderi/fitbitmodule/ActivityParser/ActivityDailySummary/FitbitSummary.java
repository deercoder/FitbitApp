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
public class FitbitSummary {
    private String activityCalories = "";
    private String caloriesBMR = "";
    private String caloriesOut = "";
    private JSONArray distance;
    private String elevation = "";
    private String fairlyActiveMinutes = "";
    private String floors = "";
    private String lightlyActiveMinutes = "";
    private String marginalCalories = "";
    private String sedentaryMinutes = "";
    private String steps = "";
    private String veryActiveMinutes = "";


    // private member of json object
    private JSONObject json;

    public FitbitSummary(JSONObject js) {
        //// empty, for future extension
        json = js;
    }

    public void parse() throws JSONException {
        setActivityCalories(json.getString("activityCalories"));
        setCaloriesBMR(json.getString("caloriesBMR"));
        setCaloriesOut(json.getString("caloriesOut"));
        setDistance(json.getJSONArray("distances"));
        setElevation(json.getString("elevation"));
        setFairlyActiveMinutes(json.getString("fairlyActiveMinutes"));
        setFloors(json.getString("floors"));
        setLightlyActiveMinutes(json.getString("lightlyActiveMinutes"));
        setMarginalCalories(json.getString("marginalCalories"));
        setSedentaryMinutes(json.getString("sedentaryMinutes"));
        setSteps(json.getString("steps"));
        setVeryActiveMinutes(json.getString("veryActiveMinutes"));
    }

    public String getParsedString() throws JSONException{
        String actCal = "activityCalories: " + getActivityCalories() + "\n";
        String calBMR = "caloriesBMR: " + getCaloriesBMR() + "\n";
        String calOut = "caloriesOut: " + getCaloriesOut() + "\n";
        String distance = "distance: {" + getDistance() + "}\n";
        String elevation = "elevation: " + getElevation() + "\n";
        String fairlyActMinute = "fairlyActiveMinutes: " + getFairlyActiveMinutes() + "\n";
        String floors = "floors: " + getFloors() + "\n";
        String lightlyActMinutes = "lightlyActiveMinutes: " + getLightlyActiveMinutes() + "\n";
        String margninalCalories = "marginalCalories: " + getMarginalCalories() + "\n";
        String sedentaryMinutes = "sedentaryMinutes: " + getSedentaryMinutes() + "\n";
        String steps = "steps: " + getSteps() + "\n";
        String veryActiveMinutes = "veryActiveMinutes: " + getVeryActiveMinutes() + "\n";
        return actCal + calBMR + calOut + distance + elevation + fairlyActMinute + floors
                + lightlyActMinutes + margninalCalories + sedentaryMinutes + steps
                + veryActiveMinutes;
    }

    public void setActivityCalories(String cal) {
        activityCalories = cal;
    }

    public String getActivityCalories() {
        return activityCalories;
    }

    public void setCaloriesBMR(String bmr) {
        caloriesBMR = bmr;
    }

    public String getCaloriesBMR() {
        return caloriesBMR;
    }

    public void setCaloriesOut(String cal) {
        caloriesOut = cal;
    }

    public String getCaloriesOut() {
        return caloriesOut;
    }

    /// may contain multiple instances of distances
    public void setDistance(JSONArray dis) {
        distance = dis;
    }

    public String getDistance() throws JSONException {
        int len = distance.length();
        String retString = "";
        for (int i = 0; i < len; i++) {
            JSONObject jo = distance.getJSONObject(i);
            String act = "Activity: " + jo.getString("activity") + "\n";
            String dist = "Distance: " + jo.getString("distance") + "\n";
            String oneList = act + dist;
            retString += oneList;
        }
        return retString;
    }

    public void setElevation(String elev) {
        elevation  = elev;
    }

    public String getElevation() {
        return elevation;
    }

    public void setFairlyActiveMinutes(String min) {
        fairlyActiveMinutes = min;
    }

    public String getFairlyActiveMinutes() {
        return fairlyActiveMinutes;
    }

    public void setFloors(String fl) {
        floors = fl;
    }

    public String getFloors() {
        return floors;
    }


    public void setLightlyActiveMinutes(String min) {
        lightlyActiveMinutes = min;
    }

    public String getLightlyActiveMinutes() {
        return lightlyActiveMinutes;
    }

    public void setMarginalCalories(String margCal) {
        marginalCalories = margCal;
    }

    public String getMarginalCalories() {
        return marginalCalories;
    }

    public void setSedentaryMinutes(String min) {
        sedentaryMinutes = min;
    }

    public String getSedentaryMinutes() {
        return sedentaryMinutes;
    }

    public void setSteps(String st) {
        steps = st;
    }

    public String getSteps() {
        return steps;
    }

    public void setVeryActiveMinutes(String min) {
        veryActiveMinutes = min;
    }

    public String getVeryActiveMinutes() {
        return veryActiveMinutes;
    }

}


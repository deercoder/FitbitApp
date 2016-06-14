package com.uml.deercoderi.fitbitmodule.ActivityParser.ActivityLoggingList;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cliu on 6/13/16.
 */
public class manualValuesSpecified {

    /// raw json
    private JSONObject json;

    /// private member
    private String calorie;     // true or false
    private String distance;    // same...
    private String steps;

    public manualValuesSpecified(JSONObject js) {
            json = js;
    }

    public void parse() throws JSONException {
        setCalorie(json.getString("calories"));
        setDistance(json.getString("distance"));
        setSteps(json.getString("steps"));
    }

    public String getParsedString() {
        String cal = "ManualValuesSpecific's calories: " + getCalorie() + "\n";
        String dis = "distance: " + getDistance() + "\n";
        String step = "steps: " + getSteps() + "\n";
        String header = "****** manualValueSpecific **********\n";
        return header + cal + dis + step;
    }


    public void setCalorie(String cal) {
        calorie = cal;
    }

    public String getCalorie() {
        return calorie;
    }

    public void setDistance(String dis) {
        distance = dis;
    }

    public String getDistance() {
        return distance;
    }

    public void setSteps(String st) {
        steps = st;
    }

    public String getSteps() {
        return steps;
    }

}

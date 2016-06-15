package com.uml.deercoderi.fitbitmodule.SleepParser;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cliu on 6/14/16.
 */
public class SleepGoal {

    /// json object
    private JSONObject rawResponse;

    /// member
    private String minDuration;
    private String updatedOn;

    //constructor
    public SleepGoal(JSONObject jb) {
        rawResponse = jb;

        /// parse when contructing the object
        try {
            parse();
        } catch(JSONException e) {
            e.printStackTrace();
            Log.e("SleepGoal", "Error when parsing in the constructor!");
        }
    }

    public void parse() throws JSONException {
        setMinDuration(rawResponse.getString("minDuration"));
        setUpdatedOn(rawResponse.getString("updatedOn"));
    }

    public String getParsedString() {
        String header = "\n***** Sleep Goal *****";
        String minDur = "minDuration: " + getMinDuration() + "\n";
        String updateOn = "updatedOn: " + getUpdatedOn() + "\n";

        return header + minDur + updateOn;
    }

    public void setMinDuration(String min) {
        minDuration = min;
    }

    public String getMinDuration() {
        return minDuration;
    }

    public void setUpdatedOn(String on) {
        updatedOn = on;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }
}

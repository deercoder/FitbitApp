package com.uml.deercoderi.fitbitmodule.ActivityParser.ActivityLoggingList;

/***
 *  Created by Chang Liu, for parsing of the activity response(JSON-style)
 *
 *  According to the official tutorial about Activity:
 *  (https://dev.fitbit.com/docs/activity/#get-activity-logs-list):
 *
 *  Activity Logging List: GET https://api.fitbit.com/1/user/-/activities/list.json
 *  for details about the parameters we can set, check the above URL.
 *
 *  subpart of the activities key field, that contained many sub-json-object, for each these
 *  arrays or complex structure, use a separate class to store for extension and clear logic.
 *  We can also use these classes later if other request also contain such classes(code reuse)
 *
 *  History:
 *
 *  Chang Liu, v1.0, June 13th, 2016, Create first initial version and README
 *
 */


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cliu on 6/13/16.
 */
public class activityLevel {

    /// raw jSON object
    private JSONObject json;

    /// memebers of each actvityLevel
    private String minutes;
    private String name;

    public activityLevel(JSONObject js) {
        json  = js;
    }

    public void parse() throws JSONException {
        setMinutes(json.getString("minutes"));
        setName(json.getString("name"));
    }

    public String getParsedString() {
        String minutes = "Minutes: " + getMinutes() + "\n";
        String name = "Name: " + getName() + "\n";
        String header = "****** activityLevel for this activity *******";
        return header + minutes + name;
    }

    public void setMinutes(String min) {
        minutes = min;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setName(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }
}

package com.uml.deercoderi.fitbitmodule.SleepParser;

/***
 *  Created by Chang Liu, for parsing of the sleep response(JSON-style)
 *
 *
 *  According to the official tutorial about Sleep information:
 *  (https://dev.fitbit.com/docs/sleep/):
 *
 *
 *  Request:
 *      GET https://api.fitbit.com/1/user/[user-id]/sleep/date/[date].json

 *  Response:
 *          minuteDate Object is part of the response that iteself is an json object
 *
 *
 *  History:
 *
 *  Chang Liu, v1.0, June 14th, 2016, Create first initial version and README
 *
 */

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cliu on 6/14/16.
 */
public class minuteData {

    /// raw json object
    private JSONObject rawObject;

    // memeber
    private String dateTime;
    private String value;

    public minuteData(JSONObject jb) {
        rawObject = jb;
    }

    public void parser() throws JSONException {
        setDateTime(rawObject.getString("dateTime"));
        setValue(rawObject.getString("value"));
    }

    public String getParsedString() {
        String header = "**** minute DATA info ***";
        String dateTime = "date time: " + getDateTime() + "\n";
        String value = "value: " + getValue() + "\n";
        return header + dateTime + value;
    }


    public void setDateTime(String t) {
        dateTime = t;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setValue(String v) {
        value = v;
    }

    public String getValue() {
        return value;
    }

}

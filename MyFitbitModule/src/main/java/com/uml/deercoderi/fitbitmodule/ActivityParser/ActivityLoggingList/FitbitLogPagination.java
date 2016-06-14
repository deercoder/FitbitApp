package com.uml.deercoderi.fitbitmodule.ActivityParser.ActivityLoggingList;
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
public class FitbitLogPagination {

    /// section of the "pagination", raw json object
    private JSONObject json;


    /// memeber of the "pagination" keyword
    private String beforeDate;
    private String limit;
    private String next;
    private String sort;

    public FitbitLogPagination(JSONObject js) {
        json = js;
    }

    public void parse() throws JSONException {
        setBeforeDate(json.getString("beforeDate"));
        setLimit(json.getString("limit"));
        setNext(json.getString("next"));
        setSort(json.getString("sort"));
    }

    public String getParsedString() {
        String befDate = "BeforeDate: " + getBeforeDate() + "\n";
        String limit = "Limit: " + getLimit() + "\n";
        String next =  "Next: " + getNext() + "\n";
        String sort = "Sort: " + getSort() + "\n";

        return befDate + limit + next + sort;
    }


    public void setBeforeDate(String bd) {
        beforeDate = bd;
    }

    public String getBeforeDate() {
        return beforeDate;
    }

    public void setLimit(String li) {
        limit = li;
    }

    public String getLimit() {
        return limit;
    }

    public void setNext(String n) {
        next = n;
    }

    public String getNext() {
        return next;
    }

    public void setSort(String s) {
        sort = s;
    }

    public String getSort() {
        return sort;
    }

}

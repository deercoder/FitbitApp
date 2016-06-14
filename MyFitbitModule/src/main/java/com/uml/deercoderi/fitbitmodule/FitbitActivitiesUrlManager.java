package com.uml.deercoderi.fitbitmodule;
/***
 *  Created by Chang Liu, for constructing the HTTP GET request, first construct the correct JSON post
 *
 *  According to the official tutorial about Activity and its Time Series:
 *  (https://dev.fitbit.com/docs/activity/):
 *
 *  Activity FitbitSummary:  https://api.fitbit.com/1/user/[user-id]/activities/date/[date].json
 *
 *  Time Series:
 *  1. GET /1/user/[user-id]/[resource-path]/date/[date]/[period].json
    2. GET /1/user/[user-id]/[resource-path]/date/[base-date]/[end-date].json
 *
 *  For example: GET https://api.fitbit.com/1/user/-/activities/steps/date/today/1m.json

 *
 *  We need to set the correct date, in the format of YYYY-MM-DD
 *  and the correct period, which could be 1d, 7d, 30d, 1w, 1m, 3m, 6m, 1y, or max.
 *
 *
 *  Now for demo I just the current date on device by default. I also created the interface to
 *  set period and start and end date for other calling for future extension.
 *
 *  History:
 *
 *  Chang Liu, v1.0, June 6th, 2016, Create first initial version and README
 *
 */

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by chang liu on 6/6/16.
 */
public class FitbitActivitiesUrlManager {

    /// this is just for the activity summary
    private static final String FITBIT_ACTIVITIES_URL = "https://api.fitbit.com/1/user/-/activities/date/";
    private static final String FITBIT_ACTIVITIES_TYPES = "https://api.fitbit.com/1/activities.json";
    private static final String FITBIT_ACTIVITY_LOGGING_LIST = "https://api.fitbit.com/1/user/-/activities/list.json?beforeDate=2016-05-14&sort=desc&limit=10&offset=0";
    // ends

    private static String mDate = "2015-05-14";
    private static String mPeriod = "1d";
    private static String mSartDate = "";
    private static String mEndDate = "";


    public FitbitActivitiesUrlManager() {
        //initDate();
    }


    /**
     * The following getter is just for using GET request to fetch information
     * for example, activities, sleep, heart rate, body, weight, etc...
     */
    public String  getActivitiesInfoUrl() {
        return FITBIT_ACTIVITIES_URL + mDate + ".json";
    }

    // browse all the activity types of Fitbit
    public String getBrowseActivityTypesUrl() {
        return FITBIT_ACTIVITIES_TYPES;
    }

    // get logging list of Fitbit
    public String getActvityLogging() {
        return FITBIT_ACTIVITY_LOGGING_LIST;
    }



    // check for date format correctness in the future...
    public void setSartDate(String sDate) {
        mSartDate = sDate;
    }

    public void setEndDate(String eDate) {
        mEndDate = eDate;
    }

    private void setPeriod(String period) {
        // may need to check for correctness, must be one of the following options:
        // 1d, 7d, 30d, 1w, 1m, 3m, 6m, 1y, or max.
        mPeriod = period;
    }

    /**
     * initDate and set the date to be the current time on device.
     */
    private void initDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(c.getTime());
        mDate = strDate;
        Log.e("FitbitUrlManager", "Setting the initial date" + mDate + " as current date on devices!");
    }


    /**
     * Get the current mDate that is initial as the current date on device
     * @return date of current time
     */
    private String getDate() {
        return mDate;
    }

    /**
     * Date should be in the format of YYYY-MM-DD
     * @param date
     */
    private void setDate(String date) {
        /// may need to check for correctness in the future, for example, number and dash...
        mDate = date;
    }
}

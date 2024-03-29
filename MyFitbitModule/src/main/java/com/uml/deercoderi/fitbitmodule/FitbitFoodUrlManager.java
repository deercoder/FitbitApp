package com.uml.deercoderi.fitbitmodule;
/***
 *  Created by Chang Liu, for constructing the HTTP GET request, first construct the correct JSON post
 *
 *  According to the official tutorial about Heart Rate Time Serias:
 *  (https://dev.fitbit.com/docs/food-logging/):
 *
 *  Get Food Goals: https://api.fitbit.com/1/user/[user-id]/foods/log/goal.json
 *  Get Food Logs: https://api.fitbit.com/1/user/[user-id]/foods/log/date/[date].json
 *  Get Water logs: https://api.fitbit.com/1/user/[user-id]/foods/log/water/date/[date].json
 *  Get Water Goals: https://api.fitbit.com/1/user/[user-id]/foods/log/water/goal.json
 *
 *  We need to set the correct date, in the format of YYYY-MM-DD
 *  and the correct period, which could be 1d, 7d, 30d, 1w, 1m.
 *
 *  Food or Water Time Series:
 *
 *  1. GET https://api.fitbit.com/1/user/[user-id]/[resource-path]/date/[date]/[period].json
 *  2. GET https://api.fitbit.com/1/user/[user-id]/[resource-path]/date/[base-date]/[end-date].json
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
public class FitbitFoodUrlManager {

    private static final String FITBIT_FOOD_URL = "https://api.fitbit.com/1/user/-/foods/log/goal.json";
    private static String mDate = "";
    private static String mPeriod = "1d";
    private static String mSartDate = "";
    private static String mEndDate = "";


    public FitbitFoodUrlManager() {
        initDate();
    }


    /**
     * The following getter is just for using GET request to fetch information
     * for example, activities, sleep, heart rate, body, weight, etc...
     */
    public String getFoodInfoUrl() {
        return FITBIT_FOOD_URL;
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
        // 1d, 7d, 30d, 1w, 1m
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
        Log.e("FitbitUrlManager", "Setting the initial date as current date on devices!");
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

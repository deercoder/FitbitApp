package com.uml.deercoderi.fitbitmodule;
/***
 *  Created by Chang Liu, for constructing the HTTP GET request, first construct the correct JSON post
 *
 *  According to the official tutorial about Heart Rate Time Serias:
 *  (https://dev.fitbit.com/docs/heart-rate/):
 *
 *  Two ways of fetching information:
 *
 *  1. GET https://api.fitbit.com/1/user/[user-id]/activities/heart/date/[date]/[period].json
 *  2. GET https://api.fitbit.com/1/user/[user-id]/activities/heart/date/[base-date]/[end-date].json
 *
 *  We need to set the correct date, in the format of YYYY-MM-DD
 *  and the correct period, which could be 1d, 7d, 30d, 1w, 1m.
 *
 *  Or use the 2nd way, set the start date and end date in the same date format(YYYY-MM-DD)
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
public class FitbitHeartRateUrlManager {

    private static final String FITBIT_HEART_RATE_URL = "https://api.fitbit.com/1/user/-/activities/heart/date/";
    private static String mDate = "";
    private static String mPeriod = "1d";
    private static String mSartDate = "";
    private static String mEndDate = "";


    public FitbitHeartRateUrlManager() {
        initDate();
    }


    /*
    *  By default, just use current date and 1 day period for demo, can be extended.
    */
    public String getHeartRateInfoUrl() {
        return FITBIT_HEART_RATE_URL + mDate + "/" + mPeriod + ".json";
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
        Log.e("FitbitUrlManager", "Setting the initial date " + mDate + " as current date on devices!");
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

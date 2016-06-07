package com.uml.deercoderi.fitbitmodule;
/***
 *  Created by Chang Liu, for constructing the HTTP GET request, first construct the correct JSON post
 *
 *  According to the official tutorial about Heart Rate Time Serias:
 *  (https://dev.fitbit.com/docs/friends/):
 *
 *  Get friends lists:
 *   GET https://api.fitbit.com/1/user/[user-id]/friends.json
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

/**
 * Created by chang liu on 6/6/16.
 */
public class FitbitFriendsUrlManager {

    private static final String FITBIT_FRIDENDS_URL = "https://api.fitbit.com/1/user/-/friends.json";

    public FitbitFriendsUrlManager() {
        /////
    }


    /**
     * The following getter is just for using GET request to fetch information
     * for example, activities, sleep, heart rate, body, weight, etc...
     */
    public String getFriendsInfoUrl() {
        return FITBIT_FRIDENDS_URL;
    }

}

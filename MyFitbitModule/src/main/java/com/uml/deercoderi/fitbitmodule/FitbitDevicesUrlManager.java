package com.uml.deercoderi.fitbitmodule;
/***
 *  Created by Chang Liu, for constructing the HTTP GET request, first construct the correct JSON post
 *
 *  According to the official tutorial about Devices List:
 *  (https://dev.fitbit.com/docs/devices/):
 *
 *
 * The Get Device endpoint returns a list of the Fitbit devices connected to a user's account.
        https://api.fitbit.com/1/user/-/devices.json
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
public class FitbitDevicesUrlManager {

    private static final String FITBIT_DEVICES_URL = "https://api.fitbit.com/1/user/-/devices.json";

    public FitbitDevicesUrlManager() {
        // empty, for non-parameter constructor
    }


    /**
     * The following getter is just for using GET request to fetch information
     * for example, activities, sleep, heart rate, body, weight, etc...
     */
    public String getDevicesInfoUrl() {
        return FITBIT_DEVICES_URL;
    }

}

package com.uml.deercoderi.fitbitmodule;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;


final class EasySocialFitbitAccessTokenParse {

    /**
     * This method is used to parse the AccessToken from a RawData
     * @param data Take an intent as a parameter.
     * @return AccessToken as String or null.
     */
    public static final String parseAccessToken(Intent data){
        String line = data.getStringExtra("data");
        Log.e("EasySocialFitbitApp", "" + line);


        int indexOfToken = line.indexOf("access_token");
        String code = line.substring(indexOfToken + 13, line.length());// add length of access_token=
        Log.e("EasyWebView", "the code/token is " + code);


        return code;
    }
}

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
        String url = "http://www.cs.uml.edu?"+line;
        Uri uri = Uri.parse(url);
        return uri.getQueryParameter("access_token");
    }
}

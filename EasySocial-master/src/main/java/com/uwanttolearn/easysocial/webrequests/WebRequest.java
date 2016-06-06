package com.uwanttolearn.easysocial.webrequests;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Hafiz Waleed Hussain on 12/8/2014.
 * This is the abstract class.
 */
public abstract class WebRequest {

    public interface Callback {
        void requestComplete(String data);
    }

    /**
     * This method is used to parse inputStream into String
     * @param inputStream Take as an argument.
     * @return String data or null.
     */
    protected  String inputStreamParse(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line = bufferedReader.readLine();
            Log.e("WebRequest", "WebRequest's inputStreamParse " + line);
            //return line;
            // for debugging, now let's check whether it crashes when returning the value
            return line;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("WebRequest", "Now crashes!");
            return null;
        }
    }
}

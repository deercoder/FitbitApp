package com.uwanttolearn.easysocial.webrequests;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Hafiz Waleed Hussain on 12/8/2014.
 * This is the Get WebRequest class which is extend from WebRequest.
 */
public class GetWebRequest extends WebRequest{


    private Callback _Callback = null;
    private String _Url = null;

    // Chang, fix the bug of GET request, which needs the access_token here, I passed it from MainActivity
    private String _AccessToken = null;

    /**
     * One argument constructor
     * @param callback Take Callback as parameter
     */
    public GetWebRequest(Callback callback) {
        _Callback = callback;
    }

    /**
     * This method start our Network communication.
     * @param url Take Url as parameter.
     */
    public void executeRequest(String url, String accToken) {
        _Url = url;
        _AccessToken = accToken;
        new LocalAsyncTask().execute();
    }


    /**
     * This class is used to do whole network processing in background.
     */
    private class LocalAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            try {
                URL url = new URL(_Url);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", "Bearer " + _AccessToken);
                Log.e("GetWebRequest", "GetWebRequest's doInBackground " + url.toString());
                /// Mark: It seems that it will call WebRequest's parse function

                // It seems here it get IOException, so return null
                String result = inputStreamParse(connection.getInputStream());
                return result;

            } catch (MalformedURLException e) {
                return null;
            } catch (IOException e) {
                Log.e("GetWebRequest", "GetWebRequest's doInBackground returns null, cause error!");
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            _Callback.requestComplete(result);
        }
    }

}


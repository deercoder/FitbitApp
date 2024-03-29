package com.uwanttolearn.easysocial;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
* Created by Hafiz Waleed Hussain on 12/6/2014.
 * Modified by Chang Liu on 6/2/2016, to fit for Fitbit Implicit Grant Flow(which don't need the
 * authorization code and access_token in two steps, we directly get it)
*/
class EasyWebViewClient extends WebViewClient {

    /** _EasySocialAuthActivity is a Reference of an {@link EasySocialAuthActivity} */
    private EasySocialAuthActivity _EasySocialAuthActivity;
    /** Dialog is shown when we are in between the procedure of getting code. */
    private ProgressDialog _Dialog;

    /**
     * One argument constructor
     * @param easySocialAuthActivity is a reference of a EasySocialAuthActivity.
     */
    public EasyWebViewClient(EasySocialAuthActivity easySocialAuthActivity) {
        this._EasySocialAuthActivity = easySocialAuthActivity;
        _Dialog = new ProgressDialog(_EasySocialAuthActivity);
        _Dialog.setCancelable(false);
        _Dialog.setCanceledOnTouchOutside(false);
        Log.e("LLL", "Start creating the EasyWebViewClient");

    }

    public ProgressDialog get_Dialog() {
        return _Dialog;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        Log.e("EasyWebViewClient", "shouldOverrideUrlLoading " + url);
        Uri uri = Uri.parse(url);
        String host = uri.getHost();
        Log.e("EasyWebViewClient", "Host: " + host);

        Uri callbackUri = Uri.parse(_EasySocialAuthActivity.getRedirectUrl());
        String callbackHost = callbackUri.getHost();

        Log.e("EasyWebViewClient", "callbackhost  " + callbackHost);

        if(host.equals(callbackHost)){
            Uri shortUri =  Uri.parse(url);
            String fragment = shortUri.getFragment();    // get only the string after `#`
            Log.e("ViewClient", "fragment " + fragment);
            // remaining part is like:
            // scope=activity&user_id=3CNXJX&token_type=Bearer&expires_in=86400&access_token=
            // eyJhbGciOiJIUzI1Ni.BZghP9uiOW_dfH0jE3SnoquG
            int indexOfToken = fragment.indexOf("access_token");
            String code = fragment.substring(indexOfToken + 13, fragment.length());// add length of access_token=
            Log.e("EasyWebView", "the code/token is " + code);

            // no longer needs this to get access_token as we have use implicit grant way to get it
            GetAccessToken getAccessToken = new GetAccessToken(_EasySocialAuthActivity);
            //Chang, locate the issue of fitbit's wrong url
            Log.e("EasyWebViewClient", "Creating the GetAccessToken object, " +url);

            //Chang, the following url(getAccessToken()+code) is what we think should be access_token
            // request, but in the code is null

            // Fix bug, only put the necessary access_token into this area, instead of the url which is
            // for 4-step authorization_code
            //getAccessToken.execute(_EasySocialAuthActivity.getAccessToken()+code);

            // this must be executed for dismissing the redirect_url's page, which is not necessary
            // and should use another activity
            getAccessToken.execute(code);
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if(_Dialog !=null) {
            _Dialog.show();
        }
        view.setVisibility(View.GONE);

    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if( _Dialog != null && _Dialog.isShowing())
        _Dialog.dismiss();
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        if( _Dialog != null && _Dialog.isShowing())
        _Dialog.dismiss();

        Intent data = new Intent();
        data.putExtra(_EasySocialAuthActivity.ERROR_CODE,errorCode);

        _EasySocialAuthActivity.setResult(Activity.RESULT_CANCELED, data);
        _EasySocialAuthActivity.finish();
    }
}

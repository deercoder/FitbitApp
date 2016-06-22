package com.uml.deercoderi.fitbitmodule;

import android.content.Context;
import android.util.Log;

import com.uwanttolearn.easysocial.EasySocialCredential;

import java.text.SimpleDateFormat;
import java.util.Calendar;

class EasySocialFitbitUrlManager {

    /** Constants */
    private static final String FITBIT_USER_INFO_URL =  "https://api.fitbit.com/1/user/-/profile.json";

    /** Constants for Fitbit URL, Chang */
    private static final String FITBIT_LOGIN_URL = "https://www.fitbit.com/oauth2/authorize?";

    /** Constants for Fitbit Login Key field */
    private static final String RESPONSE_TYPE = "response_type";
    private static final String CLIENT_ID = "client_id";
    private static final String REDIRECT_URL = "redirect_uri";
    private static final String SCOPE = "scope";

    /** Constatns for Fitbit Access Token Key Field */
    private static final String FITBIT_ACCESS_TOKEN_URL = "https://api.fitbit.com/oauth2/token?";
    private static final String AUTHORIZATION_CODE = "code";
    private static final String GRANT_TYPE = "grant_type";

    /** EasySocialCredential object containing info of Social Network credentials */
    private EasySocialCredential _EasySocialCredential;

    private EasySocialFitbitUrlManager(){
        // No Argument constructor
    }


    /**
     * One argument constructor. It take Credential as parameter.
     * @param easySocialCredential
     */
    public EasySocialFitbitUrlManager(EasySocialCredential easySocialCredential) {
        _EasySocialCredential = easySocialCredential;
    }

    /**
     * This method create a Fitbit Login Url and return as String.
     * @return
     *
     * Sample URL:
     *
     *
    String loginUrl = "https://www.fitbit.com/oauth2/authorize?" +
    "response_type=token&" +
    "client_id=227NCS&" +
    "redirect_uri=http://www.cs.uml.edu&" +
    "scope=activity&" +
    "expires_in=604800";
     */
    public String getLoginUrl() {

        /** get all necessary information for login */
        String responseType = _EasySocialCredential.getResponseType();
        String appId = _EasySocialCredential.getAppId();
        String redirectUrl = _EasySocialCredential.getRedirectUrl();
        String permissions = getPermissionsAsString(_EasySocialCredential.getPermissions());

        Log.e("FitbitUrlManager", "permissions = " + permissions);

        /** construct the String that will be used for login */
        return String.format(FITBIT_LOGIN_URL + RESPONSE_TYPE + "=%s&"
                + CLIENT_ID + "=%s&" + REDIRECT_URL + "=%s&" + SCOPE + "=%s&expires_in=31536000",
                responseType, appId, redirectUrl, permissions);
    }

    /**
     * This method create a Facebook AccessToken url and return as String.
     * @return
     *
     * Sample URL:
     *
    String accessTokenUrl = "https://api.fitbit.com/oauth2/token?" +
    "grant_type=authorization_code&" +
    "redirect_uri=http://www.cs.uml.edu&" +
    "client_id=227NCS&" +
    "client_secret=a6f5a2f9c4c8333609be680b7ee976be&" +
    "code=";
     */
    public String getAccessTokenUrl(){ //mark!!!

        /** This must be obtained from redirect_url tha contained the authorization code */
        String  authCode = "XXXXXXXXX";
        String  clientId = _EasySocialCredential.getAppId();
        String  grantType = "authorization_code";
        String  redirectUrl = _EasySocialCredential.getRedirectUrl();

        return String.format(FITBIT_ACCESS_TOKEN_URL + GRANT_TYPE + "=" + grantType + "&"
                + CLIENT_ID + "=%s&" + REDIRECT_URL + "=%s&" + AUTHORIZATION_CODE + "=" + authCode,
                clientId, redirectUrl, authCode);
    }

    /**
     * This method create response type and return as String.
     * @return
     */
    public String getResponseType(){
        return _EasySocialCredential.getResponseType();
    }


    /**
     * This method create UserInfo Url and return as String.
     * @param context Context take as a parameter.
     * @return
     */
    public String getUserInfoUrl(Context context){
        return FITBIT_USER_INFO_URL;
    }

    /**
     * This method only return a Redirect Url which user give when app created on a Social Network.
     * @return
     */
    public String getRedirectUrl(){
        return _EasySocialCredential.getRedirectUrl();
    }

    /** Private methods*/

    /**
     * This method is used to take the permissions of a SocialNetwork as String array and return as
     * a String appended with comma.
     * @param permissions
     * @return
     */
    private String getPermissionsAsString(String[] permissions) {
        if (permissions == null) return "";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < permissions.length; i++) {
            // Chang, fix the bug, here the scope must be concated with "+" instead of ","
            stringBuilder.append(permissions[i] + "+");
        }
        try {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        } catch (StringIndexOutOfBoundsException e) {
            return "";
        }
        return stringBuilder.toString();
    }

    private String getAccessTokenAsUrlParameter(Context context){
        return "access_token="+ EasySocialFitbitPreferenceUtility.getAccessToken(context);
    }

}



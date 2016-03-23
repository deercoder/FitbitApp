package com.uml.deercoderi.fitbitmodule;

import android.content.Context;

import com.uwanttolearn.easysocial.EasySocialCredential;

class EasySocialFitbitUrlManager {

    /** Constants */
    private static final String FITBIT_API_VERSION_NUMBER = "v2.0";
    private static final String FITBIT_GRAPH_SERVER = "https://graph.facebook.com/";
    private static final String FITBIT_REQUEST_URL =  FITBIT_GRAPH_SERVER+FITBIT_API_VERSION_NUMBER+"/";

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
        String url = "https://www.fitbit.com/oauth2/authorize?";
        return String.format(url + "response_type=%s&client_id=%s&redirect_uri=%s&scope=activity",
                _EasySocialCredential.getResponseType(),
                _EasySocialCredential.getAppId(),
                _EasySocialCredential.getRedirectUrl());
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
        String url = "https://api.fitbit.com/oauth2/token?";
        return String.format(url+"grant_type=authorization_code&client_id=%s&redirect_uri=%s&client_secret=%s",
                _EasySocialCredential.getAppId(),
                getRedirectUrl(),
                _EasySocialCredential.getAppSecretId());
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
        return FITBIT_REQUEST_URL+"me?"+getAccessTokenAsUrlParameter(context);
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
            stringBuilder.append(permissions[i] + ",");
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



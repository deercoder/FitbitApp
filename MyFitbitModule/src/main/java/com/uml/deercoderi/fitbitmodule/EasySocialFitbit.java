package com.uml.deercoderi.fitbitmodule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.uwanttolearn.easysocial.EasySocialAuthActivity;
import com.uwanttolearn.easysocial.EasySocialCredential;
import com.uwanttolearn.easysocial.webrequests.GetWebRequest;
import com.uwanttolearn.easysocial.webrequests.WebRequest;

import org.json.JSONException;
import org.json.JSONObject;


public class EasySocialFitbit {

    /** Class own reference*/
    private static EasySocialFitbit _EasySocialFitbit;

    /** EasySocialFacebookUrlManager is handle Fitbit API'S Url's*/
    private static EasySocialFitbitUrlManager _EasySocialFitbitUrlManager;

    /**
     * EasySocialFitbit instantiation method.
     * @param easySocialCredential Take Fitbit credential as a parameter.
     * @return EasySocialFitbit object.
     */
    public static final EasySocialFitbit getInstance(EasySocialCredential easySocialCredential){
        if(_EasySocialFitbit == null)
            _EasySocialFitbit = new EasySocialFitbit();
            _EasySocialFitbitUrlManager = new EasySocialFitbitUrlManager(easySocialCredential);
        return _EasySocialFitbit;
    }

    /**
     * No argument private constructor.
     */
    private EasySocialFitbit(){
    }

    /**
     * This method is used to check is user already login.
     * @param context Context of the calling component.
     * @return boolean
     */
    public boolean isLogin(Context context){
        return EasySocialFitbitPreferenceUtility.getAccessToken(context) != null;
    }

    /**
     * This method is used to authenticate user from Fitbit. To manage the response,
     * it is compulsory to override onActivityResult method.
     * @param activity It take activity as a reference.
     * @param requestCode requestCode is used to handle the response.
     *
     * NOTE: this will start the new activity of EasySocialAuthActivity(in easysocial package)
     */
    public void login(Activity activity, int requestCode){

        /* construct the intent for starting authentication */
        Intent intent = new Intent(activity, EasySocialAuthActivity.class);

        /* set the login url, */
        intent.putExtra(EasySocialAuthActivity.URL,_EasySocialFitbitUrlManager.getLoginUrl());

        /* set the redirect url */
        intent.putExtra(EasySocialAuthActivity.REDIRECT_URL, _EasySocialFitbitUrlManager.getRedirectUrl());

        /* set the access token url */
        intent.putExtra(EasySocialAuthActivity.ACCESS_TOKEN, _EasySocialFitbitUrlManager.getAccessTokenUrl()/**+EasySocialFitbitPreferenceUtility.getAccessToken(activity)*/);

        /* set the response type */
        intent.putExtra(EasySocialAuthActivity.RESPONSE_TYPE, _EasySocialFitbitUrlManager.getResponseType());

        Log.e("EasySocialFitbit", "login: " + _EasySocialFitbitUrlManager.getLoginUrl());
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * This method is used to handle the access token which we get in onActivityResult method.
     * @param context Context of Context of the calling component.
     * @param data Intent which get in onActivityResult method.
     */
    public void loginResponseHandler(Context context,Intent data){
        Log.e("EasySocialFitbit","loginResonseHandler!");
        String accessToken = EasySocialFitbitAccessTokenParse.parseAccessToken(data);
        Log.e("EasySocialFitbit", "access_token = " + accessToken);
        EasySocialFitbitPreferenceUtility.setAccessToken(context, accessToken);
    }


    /**
     * UserInfoCallback interface is used to get User Information from Fitbit. This
     * interface object send in getUserInfo method as parameter.
     */
    public static interface UserInfoCallback{ void onComplete(JSONObject jsonObject);}


    /***
     * The blew is for the call back of other fetching information, similar to UserInfoCallback
     */
    public static interface GeneralCallback {
        void onComplete(JSONObject jsonObject);
    }

    /*
    public static interface ActivitiesCallback {
        void onComplete(JSONObject jsonObject);
    }

    public static interface BodyWeightCallback {
        void onComplete(JSONObject jsonObject);
    }

    public static interface HeartRateCallback {
        void onComplete(JSONObject jsonObject);
    }

    public static interface FoodCallback {
        void onComplete(JSONObject jsonObject);
    }

    public void interface SleepCallback {
        void onComplete(JSONObject jsonObject);
    }
    */


    /**
     * This method get the User Information from Fitbit and send back to the caller
     * using Callback UserInfoCallback as JSONObject or null if error occur.
     * @param context Context of the calling component.
     * @param userInfoCallback Callback interface for User Information.
     */
    public void getUserInfo(Context context, final UserInfoCallback userInfoCallback){

        String accessToken = EasySocialFitbitPreferenceUtility.getAccessToken(context);

        GetWebRequest getWebRequest = new GetWebRequest(new WebRequest.Callback() {
            @Override
            public void requestComplete(String line) {
                try {
                    // Chang, to be fixed here, it's null!!
                    Log.e("EasySocialFitbit", "callback line " + line);
                    JSONObject jsonObject = new JSONObject(line); // Chang, if I use "aaa" value, it will pass, but wrong value
                    if (jsonObject == null) {
                        Log.e("EasySocialFitbit", "null json object");
                    }
                    userInfoCallback.onComplete(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("EasySocialFitbit", "Exception when consturction JSON Object");
                    userInfoCallback.onComplete(null);
                }
            }
        });
        getWebRequest.executeRequest(_EasySocialFitbitUrlManager.getUserInfoUrl(context), accessToken);
    }

    /*
    ***
    *           The blew code is for Fetching Fitbit's other information
    *           (Activity, body&weight, sleep, heart rate etc...)
    ***
    */
    public void getActivitiesInfo(Context context, final GeneralCallback fetchInfoCallback){
        String accessToken = EasySocialFitbitPreferenceUtility.getAccessToken(context);
        GetWebRequest getWebRequest = createGetWebRequest(fetchInfoCallback);
        getWebRequest.executeRequest(new FitbitActivitiesUrlManager().getActivitiesInfoUrl(), accessToken);

        // this is used for accessing the activity types that Fitbit supports
        //getWebRequest.executeRequest(new FitbitActivitiesUrlManager().getBrowseActivityTypesUrl(), accessToken);

    }

    public void getActivitiesLogging(Context context, final GeneralCallback fetchInfoCallback){
        String accessToken = EasySocialFitbitPreferenceUtility.getAccessToken(context);
        GetWebRequest getWebRequest = createGetWebRequest(fetchInfoCallback);
        getWebRequest.executeRequest(new FitbitActivitiesUrlManager().getActvityLogging(), accessToken);
    }

    public void getBodyWeightInfo(Context context, final GeneralCallback fetchInfoCallback){
        String accessToken = EasySocialFitbitPreferenceUtility.getAccessToken(context);
        GetWebRequest getWebRequest = createGetWebRequest(fetchInfoCallback);
        getWebRequest.executeRequest(new FitbitBodyWeightUrlManager().getBodyWeightInfoUrl(), accessToken);
    }

    public void getFoodInfo(Context context, final GeneralCallback fetchInfoCallback){
        String accessToken = EasySocialFitbitPreferenceUtility.getAccessToken(context);
        GetWebRequest getWebRequest = createGetWebRequest(fetchInfoCallback);
        getWebRequest.executeRequest(new FitbitFoodUrlManager().getFoodInfoUrl(), accessToken);
    }

    public void getHeartRateInfo(Context context, final GeneralCallback fetchInfoCallback){
        String accessToken = EasySocialFitbitPreferenceUtility.getAccessToken(context);
        GetWebRequest getWebRequest = createGetWebRequest(fetchInfoCallback);
        getWebRequest.executeRequest(new FitbitHeartRateUrlManager().getHeartRateInfoUrl(), accessToken);
    }

    public void getSleepInfo(Context context, final GeneralCallback fetchInfoCallback){
        String accessToken = EasySocialFitbitPreferenceUtility.getAccessToken(context);
        GetWebRequest getWebRequest = createGetWebRequest(fetchInfoCallback);
        getWebRequest.executeRequest(new FitbitSleepUrlManager().getSleepInfoUrl(), accessToken);
    }

    public void getFriendsInfo(Context context, final GeneralCallback fetchInfoCallback){
        String accessToken = EasySocialFitbitPreferenceUtility.getAccessToken(context);
        GetWebRequest getWebRequest = createGetWebRequest(fetchInfoCallback);
        getWebRequest.executeRequest(new FitbitFriendsUrlManager().getFriendsInfoUrl(), accessToken);
    }

    public void getDevicesInfo(Context context, final GeneralCallback fetchInfoCallback){
        String accessToken = EasySocialFitbitPreferenceUtility.getAccessToken(context);
        GetWebRequest getWebRequest = createGetWebRequest(fetchInfoCallback);
        getWebRequest.executeRequest(new FitbitDevicesUrlManager().getDevicesInfoUrl(), accessToken);
    }


    /*
    *  General constructor for creating the getWebRequest
    */
    public GetWebRequest createGetWebRequest(final GeneralCallback callback) {

        GetWebRequest getWebRequest = new GetWebRequest(new WebRequest.Callback() {
            @Override
            public void requestComplete(String line) {
                try {
                    // Chang, to be fixed here, it's null!!
                    Log.e("EasySocialFitbit", "callback line " + line);

                    // for response that contain [{}, {}...], have to reduce the "[", "]"
                    // to avoid crashes, or have to parse it seperately and concatenate for whole
                    if (line.charAt(0) == '[') {
                        line = line.substring(1, line.length()-1);
                    }
                    JSONObject jsonObject = new JSONObject(line); // Chang, if I use "aaa" value, it will pass, but wrong value
                    if (jsonObject == null) {
                        Log.e("EasySocialFitbit", "createGetWebRequest's jsonObject is null!");
                    }
                    callback.onComplete(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onComplete(null);
                }
            }
        });

        return getWebRequest;
    }


}
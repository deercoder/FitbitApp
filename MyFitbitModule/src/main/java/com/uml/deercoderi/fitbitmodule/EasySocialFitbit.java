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
     */
    public void login(Activity activity, int requestCode){
        Intent intent = new Intent(activity, EasySocialAuthActivity.class);
        intent.putExtra(EasySocialAuthActivity.URL,_EasySocialFitbitUrlManager.getLoginUrl());
        intent.putExtra(EasySocialAuthActivity.REDIRECT_URL, _EasySocialFitbitUrlManager.getRedirectUrl());
        intent.putExtra(EasySocialAuthActivity.ACCESS_TOKEN, _EasySocialFitbitUrlManager.getAccessTokenUrl()/**+EasySocialFitbitPreferenceUtility.getAccessToken(activity)*/);
        intent.putExtra(EasySocialAuthActivity.RESPONSE_TYPE, _EasySocialFitbitUrlManager.getResponseType());
        Log.e("EasySocialFitbit::login", _EasySocialFitbitUrlManager.getLoginUrl());
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * This method is used to handle the access token which we get in onActivityResult method.
     * @param context Context of Context of the calling component.
     * @param data Intent which get in onActivityResult method.
     */
    public void loginResponseHandler(Context context,Intent data){
        Log.e("EEE","Coming hereeeeeeeeeeeeeeeeee!");
        String accessToken = EasySocialFitbitAccessTokenParse.parseAccessToken(data);
        Log.e("EEE", accessToken);
        EasySocialFitbitPreferenceUtility.setAccessToken(context, accessToken);
    }


    /**
     * UserInfoCallback interface is used to get User Information from Fitbit. This
     * interface object send in getUserInfo method as parameter.
     */
    public static interface UserInfoCallback{ void onComplete(JSONObject jsonObject);}

    /**
     * This method get the User Information from Fitbit and send back to the caller
     * using Callback UserInfoCallback as JSONObject or null if error occur.
     * @param context Context of the calling component.
     * @param userInfoCallback Callback interface for User Information.
     */
    public void getUserInfo(Context context, final UserInfoCallback userInfoCallback){

        GetWebRequest getWebRequest = new GetWebRequest(new WebRequest.Callback() {
            @Override
            public void requestComplete(String line) {
                try {
                    JSONObject jsonObject = new JSONObject(line);
                    userInfoCallback.onComplete(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                    userInfoCallback.onComplete(null);
                }
            }
        });
        getWebRequest.executeRequest(_EasySocialFitbitUrlManager.getUserInfoUrl(context));
    }
}
package com.uml.deercoderi.fitbitmodule;

import android.content.Context;
import android.content.SharedPreferences;


final class EasySocialFitbitPreferenceUtility {

    private static final String EASY_SOCIAL_FITBIT_PREFERENCE_NAME = "easy_social_fitbit_preference";
    private static final String EASY_SOCIAL_FITBIT_ACCESS_TOKEN = "easy_social_fitbit_access_token";

    public static final void setAccessToken(Context context,String accessToken){
        getEditor(context).putString(EASY_SOCIAL_FITBIT_ACCESS_TOKEN, accessToken).commit();
    }

    public static final String getAccessToken(Context context){
        return getSharedPreference(context).getString(EASY_SOCIAL_FITBIT_ACCESS_TOKEN,null);
    }

    public static final void removeAccessTokenOnLogout(Context context){
        getEditor(context).remove(EASY_SOCIAL_FITBIT_ACCESS_TOKEN);
    }


    private static final SharedPreferences.Editor getEditor(Context context){
        SharedPreferences sharedPreferences = getSharedPreference(context);
        return sharedPreferences.edit();
    }

    private static final SharedPreferences getSharedPreference(Context context){
        return context.getSharedPreferences(EASY_SOCIAL_FITBIT_PREFERENCE_NAME,Context.MODE_PRIVATE);
    }
}

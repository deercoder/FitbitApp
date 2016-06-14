package com.uml.fitbit;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.uml.deercoderi.fitbitmodule.EasySocialFitbit;
import com.uml.deercoderi.fitbitmodule.FitbitActivityParser;
import com.uwanttolearn.easysocial.EasySocialAuthActivity;
import com.uwanttolearn.easysocial.EasySocialCredential;

import org.json.JSONException;
import org.json.JSONObject;



public class MainFragment extends Fragment {

    /** Buttons */
    private Button mLoginButton;
    private Button mGetUserInfoButton;
    private Button mGetFriendsButton;
    private Button mGetBodyWeightButton;
    private Button mGetActivitiesButton;
    private Button mGetSleepButton;
    private Button mGetDevicesButton;
    private Button mGetHeartRateButton;
    private Button mGetFoodButton;
    private TextView mResponseTextView;
    private ProgressDialog progressDialog;

    /** Objects */
    private EasySocialFitbit mEasySocialFitbit;
    private String mUserId = null;

    /** Constants */
    public static final int REQUEST_CODE = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        /** Init the button */
        mLoginButton = (Button) rootView.findViewById(R.id.MainFragment_login_button);
        mGetUserInfoButton = (Button) rootView.findViewById(R.id.MainFragment_get_user_info_button);
        mGetFriendsButton = (Button) rootView.findViewById(R.id.MainFragment_get_friends_button);
        mGetBodyWeightButton = (Button) rootView.findViewById(R.id.MainFragment_body_weight_button);
        mGetActivitiesButton = (Button) rootView.findViewById(R.id.MainFragment_get_activities_button);
        mGetSleepButton = (Button) rootView.findViewById(R.id.MainFragment_get_sleep_button);
        mGetDevicesButton = (Button) rootView.findViewById(R.id.MainFragment_get_devices_button);
        mGetHeartRateButton = (Button) rootView.findViewById(R.id.MainFragment_get_heart_rate_button);
        mGetFoodButton = (Button) rootView.findViewById(R.id.MainFragment_get_food_button);


        mResponseTextView = (TextView) rootView.findViewById(R.id.MainFragment_response_text_view);
        progressDialog = new ProgressDialog(getActivity());

        /** Setup listener for LOGIN button */
        mLoginButton.setOnClickListener(onLoginButtonClick);

        /* set up listener for Get USER INFO button */
        mGetUserInfoButton.setOnClickListener(onGetUserInfoClick);

        /** set up listener for fetching information */
        mGetActivitiesButton.setOnClickListener(onGetActivitiesClick);
        mGetBodyWeightButton.setOnClickListener(onGetBodyWeightClick);
        mGetFoodButton.setOnClickListener(onGetFoodClick);
        mGetHeartRateButton.setOnClickListener(onGetHeartRateClick);
        mGetFriendsButton.setOnClickListener(onGetFriendsClick);
        mGetSleepButton.setOnClickListener(onGetSleepClick);
        mGetDevicesButton.setOnClickListener(onGetDevicesClick);

        /** set the dialog status */
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);


        /** Initialize Objects, (AppId, secretId, redirect_url, responsetype) */
        EasySocialCredential mCredentials = new EasySocialCredential.Builder("227NCS",
                "a6f5a2f9c4c8333609be680b7ee976be",
                "http://www.cs.uml.edu",
                "token").
                setPermissions(
                        new String[]{"activity", "nutrition", "heartrate", "location","sleep", "profile", "settings", "social", "weight"})
                .build();

        mEasySocialFitbit = EasySocialFitbit.getInstance(mCredentials);

        /** GUI handling logic*/
        if(mEasySocialFitbit.isLogin(getActivity())){
            disableButton(mLoginButton);
            enableButton(mGetDevicesButton);
            enableButton(mGetUserInfoButton);
            if(mUserId != null){
                enableButton(mGetFriendsButton);
                enableButton(mGetActivitiesButton);
                enableButton(mGetFoodButton);
                enableButton(mGetSleepButton);
                enableButton(mGetBodyWeightButton);
                enableButton(mGetHeartRateButton);
            }
        }

        return rootView;
    }


    /**
     * Anonymous click listener for Login Button
     */
    private View.OnClickListener onLoginButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            mEasySocialFitbit.login(getActivity(), REQUEST_CODE);

        }
    };

    /***
     * click listener for GetInfo Button
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private View.OnClickListener onGetUserInfoClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Log.e("MainFragment", "GetUserInfo is clicked!");

            mEasySocialFitbit.getUserInfo(getActivity(),
                    new EasySocialFitbit.UserInfoCallback() {
                        @Override
                        public void onComplete(JSONObject jsonObject) {
                            Log.e("MainFragment", "onGetUserInfoClick's onComplete is clicked!");
                            if(jsonObject == null){
                                Log.e("MainFragment", "Here!!!!!");
                                mResponseTextView.setText("UserInfo null");
                            }else{
                                mResponseTextView.setText(jsonObject.toString());
                                mUserId = jsonObject.optString("encodedId");
                                if(mUserId != null){
                                    enableButton(mGetFriendsButton);
                                    enableButton(mGetActivitiesButton);
                                    enableButton(mGetFoodButton);
                                    enableButton(mGetSleepButton);
                                    enableButton(mGetBodyWeightButton);
                                    enableButton(mGetHeartRateButton);
                                }
                            }
                            progressDialog.dismiss();
                        }
                    });
            progressDialog.show();

        }
    };

    private View.OnClickListener onGetActivitiesClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("MainFragment", "Get activities is clicked!");

            mEasySocialFitbit.getActivitiesInfo(getActivity(),
                    new EasySocialFitbit.GeneralCallback() {
                        @Override
                        public void onComplete(JSONObject jsonObject) {
                            Log.e("MainFragment", "getActivityInfoCallback's onComplete is clicked!");
                            if(jsonObject == null){
                                Log.e("MainFragment", "Here!!!!!");
                                mResponseTextView.setText("Activity info is null");
                            }else{

                                FitbitActivityParser parser = new FitbitActivityParser(jsonObject, FitbitActivityParser.PARSER_DAILY_SUMMARY);
                                String textString = "";
                                try {
                                    textString += parser.parseDailySummary();
                                }
                                catch(JSONException e) {
                                    Log.e("MainFragment", "parseDailySummary error!");
                                    e.printStackTrace();
                                }
                                if (textString != null) {

                                    mResponseTextView.setText(textString);
                                }
                                else {
                                    mResponseTextView.setText("NULL info");
                                }
                            }
                            progressDialog.dismiss();
                        }
                    });

            mEasySocialFitbit.getActivitiesLogging(getActivity(),
                    new EasySocialFitbit.GeneralCallback() {
                        @Override
                        public void onComplete(JSONObject jsonObject) {
                            Log.e("MainFragment", "getActivitiesLoggingCallback's onComplete is clicked!");
                            if(jsonObject == null){
                                Log.e("MainFragment", "Here!!!!!");
                                mResponseTextView.setText("Activity Logging Info is null");
                            }else{

                                FitbitActivityParser parser = new FitbitActivityParser(jsonObject, FitbitActivityParser.PARSER_ACTIVITY_LOGGING);
                                String textString = "";
                                try {
                                    textString = parser.parseActivityLogging();
                                } catch(JSONException e) {
                                    Log.e("MainFragment", "parseActivityLogging error!");
                                    e.printStackTrace();
                                }
                                if (textString != null) {
                                    String beforeText = mResponseTextView.getText().toString();
                                    mResponseTextView.setText(beforeText + textString);
                                }
                                else {
                                    mResponseTextView.setText("NULL info");
                                }
                            }
                            progressDialog.dismiss();
                        }
                    });
            progressDialog.show();
        }

    };

    private View.OnClickListener onGetBodyWeightClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("MainFragment", "Get Body and Weight is clicked!");

            mEasySocialFitbit.getBodyWeightInfo(getActivity(),
                    new EasySocialFitbit.GeneralCallback() {
                        @Override
                        public void onComplete(JSONObject jsonObject) {
                            Log.e("MainFragment", "getBodyWeightInfoCallback's onComplete is clicked!");
                            if(jsonObject == null){
                                Log.e("MainFragment", "Here!!!!!");
                                mResponseTextView.setText("Body and Weight info is null");
                            }else{
                                mResponseTextView.setText(jsonObject.toString());
                            }
                            progressDialog.dismiss();
                        }
                    });
            progressDialog.show();
        }
    };

    private View.OnClickListener onGetFoodClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("MainFragment", "Get Food is clicked!");

            mEasySocialFitbit.getFoodInfo(getActivity(),
                    new EasySocialFitbit.GeneralCallback() {
                        @Override
                        public void onComplete(JSONObject jsonObject) {
                            Log.e("MainFragment", "getFoodInfoCallback's onComplete is clicked!");
                            if(jsonObject == null){
                                Log.e("MainFragment", "Here!!!!!");
                                mResponseTextView.setText("Food info is null");
                            }else{
                                mResponseTextView.setText(jsonObject.toString());
                            }
                            progressDialog.dismiss();
                        }
                    });
            progressDialog.show();
        }
    };

    private View.OnClickListener onGetHeartRateClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("MainFragment", "Get heart rate is clicked!");

            mEasySocialFitbit.getHeartRateInfo(getActivity(),
                    new EasySocialFitbit.GeneralCallback() {
                        @Override
                        public void onComplete(JSONObject jsonObject) {
                            Log.e("MainFragment", "getHeartRateInfoCallback's onComplete is clicked!");
                            if(jsonObject == null){
                                Log.e("MainFragment", "Here!!!!!");
                                mResponseTextView.setText("Heart Rate info is null");
                            }else{
                                mResponseTextView.setText(jsonObject.toString());
                            }
                            progressDialog.dismiss();
                        }
                    });
            progressDialog.show();
        }
    };

    private View.OnClickListener onGetSleepClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("MainFragment", "Get Sleep is clicked!");

            mEasySocialFitbit.getSleepInfo(getActivity(),
                    new EasySocialFitbit.GeneralCallback() {
                        @Override
                        public void onComplete(JSONObject jsonObject) {
                            Log.e("MainFragment", "getSleepInfoCallback's onComplete is clicked!");
                            if(jsonObject == null){
                                Log.e("MainFragment", "Here!!!!!");
                                mResponseTextView.setText("Sleep info is null");
                            }else{
                                mResponseTextView.setText(jsonObject.toString());
                            }
                            progressDialog.dismiss();
                        }
                    });
            progressDialog.show();
        }
    };

    private View.OnClickListener onGetFriendsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("MainFragment", "Get Friends is clicked!");

            mEasySocialFitbit.getFriendsInfo(getActivity(),
                    new EasySocialFitbit.GeneralCallback() {
                        @Override
                        public void onComplete(JSONObject jsonObject) {
                            Log.e("MainFragment", "getFriendsInfoCallback's onComplete is clicked!");
                            if(jsonObject == null){
                                Log.e("MainFragment", "Here!!!!!");
                                mResponseTextView.setText("Friends info is null");
                            }else{
                                mResponseTextView.setText(jsonObject.toString());
                            }
                            progressDialog.dismiss();
                        }
                    });
            progressDialog.show();
        }
    };

    private View.OnClickListener onGetDevicesClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("MainFragment", "Get Devices is clicked!");

            mEasySocialFitbit.getDevicesInfo(getActivity(),
                    new EasySocialFitbit.GeneralCallback() {
                        @Override
                        public void onComplete(JSONObject jsonObject) {
                            Log.e("MainFragment", "getDevicesInfoCallback's onComplete is clicked!");
                            if(jsonObject == null){
                                Log.e("MainFragment", "Here!!!!!");
                                mResponseTextView.setText("Devices info is null");
                            }else{
                                mResponseTextView.setText(jsonObject.toString());
                            }
                            progressDialog.dismiss();
                        }
                    });
            progressDialog.show();
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == REQUEST_CODE){
                Log.e("MainFragment", "Response OK!");
                mResponseTextView.setText("Login Successful");
                /** Handle the authentication response */
                mEasySocialFitbit.loginResponseHandler(getActivity(), data);
                enableButton(mGetUserInfoButton);
            }
        }else if(resultCode == Activity.RESULT_CANCELED){
            if(resultCode == REQUEST_CODE) {
                //Toast.makeText(getActivity(), data.getIntExtra(EasySocialAuthActivity.ERROR_CODE, 0)
                //        + "", Toast.LENGTH_LONG).show();
                String err = data.getIntExtra(EasySocialAuthActivity.ERROR_CODE, 0) + "";
                Log.e("MainFragment", "There is an error code = " + err );
                //These error codes are present in WebViewClient.
                //http://developer.android.com/reference/android/webkit/WebViewClient.html
            }
        }
    }

    /**
     * set button status
     * @param button
     */
    private void disableButton(Button button) {
        button.setEnabled(false);
    }

    private void enableButton(Button button){
        button.setEnabled(true);
    }
}

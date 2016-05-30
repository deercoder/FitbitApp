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
import com.uwanttolearn.easysocial.EasySocialAuthActivity;
import com.uwanttolearn.easysocial.EasySocialCredential;

import org.json.JSONObject;



public class MainFragment extends Fragment {

    /** Views */
    private Button mLoginButton;
    private Button mGetUserInfoButton;
    private Button mGetFriendsButton;
    private Button mPostMessageButton;
    private Button mGetUserImageButton;
    private TextView mResponseTextView;
    private ProgressDialog progressDialog;
    /** Objects */
    private EasySocialFitbit mEasySocialFacebook;
    private String mUserId = null;
    /** Constants */
    public static final int REQUEST_CODE = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        /** Initialize Views */
        mLoginButton = (Button) rootView.findViewById(R.id.MainFragment_login_button);
        mGetUserInfoButton = (Button) rootView.findViewById(R.id.MainFragment_get_user_info_button);
        mGetFriendsButton = (Button) rootView.findViewById(R.id.MainFragment_get_friends_button);
        mPostMessageButton = (Button) rootView.findViewById(R.id.MainFragment_post_message_button);
        mGetUserImageButton = (Button) rootView.findViewById(R.id.MainFragment_get_user_image_button);
        mResponseTextView = (TextView) rootView.findViewById(R.id.MainFragment_response_text_view);
        progressDialog = new ProgressDialog(getActivity());

        /** Setup views */
        mLoginButton.setOnClickListener(onLoginButtonClick);

        /* set up listener */
        mGetUserInfoButton.setOnClickListener(onGetUserInfoClick);

        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);


        /** Initialize Objects */
        EasySocialCredential mCredentials = new EasySocialCredential.Builder("227NCS",
                "a6f5a2f9c4c8333609be680b7ee976be",
                "http://www.cs.uml.edu",
                "token").
                setPermissions(
                        new String[]{"activity", "nutrition", "heartrate", "location","sleep", "profile", "setting", "social", "weight"})
                .build();

        mEasySocialFacebook = EasySocialFitbit.getInstance(mCredentials);

        /** GUI handling logic*/
        if(mEasySocialFacebook.isLogin(getActivity())){
            disableButton(mLoginButton);
            enableButton(mGetUserInfoButton);
            enableButton(mGetUserImageButton);
            if(mUserId != null){
                enableButton(mGetFriendsButton);
                enableButton(mPostMessageButton);
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

            mEasySocialFacebook.login(getActivity(), REQUEST_CODE);

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

            Log.e("LLL", "GetUserInfo is clicked!");

            mEasySocialFacebook.getUserInfo(getActivity(),
                    new EasySocialFitbit.UserInfoCallback() {
                        @Override
                        public void onComplete(JSONObject jsonObject) {
                            Log.e("LLL", "onGetUserInfoClick's onComplete is clicked!");
                            if(jsonObject == null){
                                mResponseTextView.setText("UserInfo null");
                            }else{
                                mResponseTextView.setText(jsonObject.toString());
                                mUserId = jsonObject.optString("id");
                                if(mUserId != null){
                                    enableButton(mGetFriendsButton);
                                    enableButton(mPostMessageButton);
                                }
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
                Log.e("Error", "Response OK!");
                mResponseTextView.setText("Login Successful");
                /** Handle the authentication response */
                mEasySocialFacebook.loginResponseHandler(getActivity(), data);
                enableButton(mGetUserInfoButton);
            }
        }else if(resultCode == Activity.RESULT_CANCELED){
            if(resultCode == REQUEST_CODE) {
                //Toast.makeText(getActivity(), data.getIntExtra(EasySocialAuthActivity.ERROR_CODE, 0)
                //        + "", Toast.LENGTH_LONG).show();
                String err = data.getIntExtra(EasySocialAuthActivity.ERROR_CODE, 0) + "";
                Log.e("Error", "There is an error code = " + err );
                //These error codes are present in WebViewClient.
                //http://developer.android.com/reference/android/webkit/WebViewClient.html
            }
        }
    }

    /**
     * Private methods
     * @param button
     */
    private void disableButton(Button button){button.setEnabled(false);}
    private void enableButton(Button button){button.setEnabled(true);}
}

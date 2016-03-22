package com.uwanttolearn.easysocialfacebooktesting;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.uwanttolearn.easysocial.EasySocialAuthActivity;
import com.uwanttolearn.easysocial.EasySocialCredential;
import com.uwanttolearn.easysocialfacebook.EasySocialFacebook;

import org.json.JSONObject;

/**
 * Created by Hafiz Waleed Hussain on 12/12/2014.
 * This fragment handle all Facebook app testing functionality.
 */
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
    private EasySocialFacebook mEasySocialFacebook;
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
        mGetUserInfoButton.setOnClickListener(onGetUserInfoClick);
        mGetFriendsButton.setOnClickListener(onGetFriendsClick);
        mPostMessageButton.setOnClickListener(onPostMessageClick);
        mGetUserImageButton.setOnClickListener(onGetUserImageClick);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);


        /** Initialize Objects */
        EasySocialCredential mCredentials = new EasySocialCredential.Builder("227NCS",
                "a6f5a2f9c4c8333609be680b7ee976be",
                "https://www.cs.uml.edu/").
                setPermissions(
                        new String[]{"activity"})
                .build();

        mEasySocialFacebook = EasySocialFacebook.getInstance(mCredentials);

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

            mEasySocialFacebook.login(getActivity(),REQUEST_CODE);

        }
    };

    /**
     * Anonymous click listener for Get User Info Button
     */
    private View.OnClickListener onGetUserInfoClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mEasySocialFacebook.getUserInfo(getActivity(),
                    new EasySocialFacebook.UserInfoCallback() {
                @Override
                public void onComplete(JSONObject jsonObject) {
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

    /**
     * Anonymous click listener for Get Friends Button
     */
    private View.OnClickListener onGetFriendsClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mEasySocialFacebook.getFriends(getActivity(),mUserId,new EasySocialFacebook.GetFriendsCallback() {
                @Override
                public void onComplete(JSONObject data) {
                    if(data == null){
                        mResponseTextView.setText("Friends Data null");
                    }else{
                        mResponseTextView.setText(data.toString());
                    }
                    progressDialog.dismiss();
                }
            });
            progressDialog.show();
        }

    };

    /**
     * Anonymous click listener for Post Message Button
     */
    private View.OnClickListener onPostMessageClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mEasySocialFacebook.sendPost(getActivity(),mUserId, "Testing Easy Social"
                    ,new EasySocialFacebook.PostInfoCallback() {
                @Override
                public void onComplete(String postId) {
                    if(postId == null){
                        mResponseTextView.setText("Post not send");
                    }else{
                        mResponseTextView.setText("Post successfully send: "+postId);
                    }
                    progressDialog.dismiss();
                }
            });
            progressDialog.show();
        }
    };

    /**
     * Anonymous click listener for Get User Image Button
     */
    private View.OnClickListener onGetUserImageClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mEasySocialFacebook.getUserImageAsUri(getActivity(), new EasySocialFacebook.GetUserImageAsUri() {
                @Override
                public void onComplete(JSONObject data) {
                    if(data == null){
                        mResponseTextView.setText("User Image data null");
                    }else{
                        String imageUrl = data.optJSONObject("data").optString("url");
                        mResponseTextView.setText("User Image Url: "+imageUrl);
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
                mResponseTextView.setText("Login Successful");
                /** Handle the authentication response */
                mEasySocialFacebook.loginResponseHandler(getActivity(), data);
                enableButton(mGetUserInfoButton);
            }
        }else if(resultCode == Activity.RESULT_CANCELED){
            if(resultCode == REQUEST_CODE) {
                Toast.makeText(getActivity(), data.getIntExtra(EasySocialAuthActivity.ERROR_CODE, 0)
                        + "", Toast.LENGTH_LONG).show();
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

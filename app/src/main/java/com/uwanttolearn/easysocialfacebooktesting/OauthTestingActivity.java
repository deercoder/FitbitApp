package com.uwanttolearn.oauthtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.uwanttolearn.easysocial.EasySocialAuthActivity;


public class OauthTestingActivity extends Activity {


    private int REQUEST_CODE = 0x1;

    private TextView _ResponseShowTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oauth_testing);
        _ResponseShowTextView = (TextView) findViewById(R.id.response_text_view);
    }


    public void onStartOauth(View v) {
        Intent intent = new Intent(this, EasySocialAuthActivity.class);

        String loginUrl = "https://www.fitbit.com/oauth2/authorize?" +
                "response_type=token&" +
                "client_id=227NCS&" +
                "redirect_uri=http://www.cs.uml.edu&" +
                "scope=activity&" +
                "expires_in=604800";
//        String loginUrl = "https://www.facebook.com/dialog/oauth?" +
//                "client_id=INSERT HERE &" +
//                "redirect_uri=INSERT HERE &" +
//                "scope=email,publish_actions";
        intent.putExtra(EasySocialAuthActivity.URL, loginUrl);

        intent.putExtra(EasySocialAuthActivity.REDIRECT_URL, "http://www.cs.uml.edu");

        String accessTokenUrl = "https://api.fitbit.com/oauth2/token?" +
                "grant_type=authorization_code&" +
                "redirect_uri=http://www.cs.uml.edu&" +
                "client_id=227NCS&" +
                "client_secret=a6f5a2f9c4c8333609be680b7ee976be&" +
                "code=";
//        String accessTokenUrl = "https://graph.facebook.com/oauth/access_token?" +
//                "client_id=INSERT HERE &" +
//                "redirect_uri=INSERT HERE &" +
//                "client_secret=INSERT HERE &" +
//                "code=";/media/changliu/Research/Dropbox/tmp/project
        intent.putExtra(EasySocialAuthActivity.ACCESS_TOKEN, accessTokenUrl);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                String accessToken = data.getStringExtra("data");
                _ResponseShowTextView.setText(accessToken);
                Log.e("aaa", "OKKKKKKKKKKKKKKKKKKKKKKKKKK!");
                Log.e("aaa", accessToken);
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (requestCode == REQUEST_CODE) {
                //Toast.makeText(this, data.getIntExtra(EasySocialAuthActivity.ERROR_CODE, 0) + "", Toast.LENGTH_LONG).show();
                //These error codes are present in WebViewClient.
                //http://developer.android.com/reference/android/webkit/WebViewClient.html
                Log.e("bbb", "Failllllllllllllllllllllllll!");
            }
        }

    }
}

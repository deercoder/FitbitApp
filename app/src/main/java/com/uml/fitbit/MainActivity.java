package com.uml.fitbit;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment(),MainFragment.class.getName())
                    .commit();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** Check If fragment added in stack then get that fragment and send the onActivityResult to that
         * fragment.*/
        Fragment fragment = getFragmentManager().findFragmentByTag(MainFragment.class.getName());
        if(fragment != null){
            fragment.onActivityResult(requestCode,resultCode,data);
        }
    }
}

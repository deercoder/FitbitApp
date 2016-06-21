package com.uml.fitbit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActivitySummary extends ActionBarActivity {

    // This is the Adapter being used to display the list's data.
    SimpleCursorAdapter mAdapter;

    String MESSAGE_GOAL = "com.uml.fitbit.EXTRA_GOAL";
    /*
    String GOAL = "CaloriesOut: " + goalCaloriesOut + "\n"
            + "Distance: " + goalDistance + "\n";
    */
    String MESSAGE_SUMMARY_FLOOR = "com.uml.fitbit.EXTRA_SUMMARY_FLOOR";
    /*
    double SUMMARY_FLOOR = summaryFloor / goalFloor * 1.0;
    */
    String MESSAGE_SUMMARY_STEP = "com.uml.fitbit.EXTRA_SUMMARY_STEP";

    /*
    double SUMMARY_STEP = summaryStep / goalStep * 1.0;
    */

    String MESSAGE_SED_PERCENT = "com.uml.fitbit.SED_PER";
    String MESSAGE_LIG_PERCENT = "com.uml.fitbit.LIG_PER";
    String MESSAGE_MOD_PERCENT = "com.uml.fitbit.MOD_PER";
    String MESSAGE_VERY_PERCENT = "com.uml.fitbit.VERY_PER";

    String TAG = "ActivitySummary";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_summary);



        Intent data = getIntent();
        String goal = data.getStringExtra(MESSAGE_GOAL);
        double summaryFloor = data.getDoubleExtra(MESSAGE_SUMMARY_FLOOR, 0.0);
        double summaryStep = data.getDoubleExtra(MESSAGE_SUMMARY_STEP, 0.0);

        float sedPercent = data.getFloatExtra(MESSAGE_SED_PERCENT, 0.0f);
        float ligPercent = data.getFloatExtra(MESSAGE_LIG_PERCENT, 0.0f);
        float modPercent = data.getFloatExtra(MESSAGE_MOD_PERCENT, 0.0f);
        float veryPercent = data.getFloatExtra(MESSAGE_VERY_PERCENT, 0.0f);

        Log.e(TAG, goal);
        Log.e(TAG, "" + summaryFloor);
        Log.e(TAG, "" + summaryStep);
        Log.e(TAG, "sed: " + sedPercent + " light:" + ligPercent + " moderate: " + modPercent + " very: " + veryPercent);

        String sumFloorString = "Summary Floors: \n" + summaryFloor;
        String sumStepString = "Summary Step: \n" + summaryStep;

        String percentSedentaryActive = "Sendentary Active: " + String.format("%.2f",sedPercent*100)+"%";
        String percentLightActive = "Light Active: " + String.format("%.2f",ligPercent*100)+"%";
        String percentModerateActive = "Moderate Active: " + String.format("%.2f",modPercent*100)+"%";
        String percentVeryActive = "Very Active: " + String.format("%.2f",veryPercent*100)+"%";

        final ListView listview = (ListView) findViewById(R.id.listview);
        String[] values = new String[] {goal, sumFloorString, sumStepString, percentSedentaryActive,
            percentLightActive, percentModerateActive, percentVeryActive};


        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);




    }


    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

}

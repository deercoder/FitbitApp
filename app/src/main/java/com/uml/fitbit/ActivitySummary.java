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

    String MESSAGE_GOAL_CAL = "com.uml.fitbit.EXTRA_GOAL_CALORIE_OUT";
    String MESSAGE_GOAL_DISTANCE = "com.uml.fitbit.EXTRA_GOAL_DISTANCE";

    String TAG = "ActivitySummary";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_summary);



        Intent data = getIntent();
        int goalCalorieOut = data.getIntExtra(MESSAGE_GOAL_CAL, 0);
        float goalDistance = data.getFloatExtra(MESSAGE_GOAL_DISTANCE, 0.0f);
        double summaryFloor = data.getDoubleExtra(MESSAGE_SUMMARY_FLOOR, 0.0);
        double summaryStep = data.getDoubleExtra(MESSAGE_SUMMARY_STEP, 0.0);

        float sedPercent = data.getFloatExtra(MESSAGE_SED_PERCENT, 0.0f);
        float ligPercent = data.getFloatExtra(MESSAGE_LIG_PERCENT, 0.0f);
        float modPercent = data.getFloatExtra(MESSAGE_MOD_PERCENT, 0.0f);
        float veryPercent = data.getFloatExtra(MESSAGE_VERY_PERCENT, 0.0f);

        Log.e(TAG, "goal calorie: " + goalCalorieOut + " goal distance: " + goalDistance);
        Log.e(TAG, "" + summaryFloor);
        Log.e(TAG, "" + summaryStep);
        Log.e(TAG, "sed: " + sedPercent + " light:" + ligPercent + " moderate: " + modPercent + " very: " + veryPercent);


        ///// for calorie goal
        String goalCalorieOutString = "(当日)卡路里消耗目标: " + goalCalorieOut + " kCal";

        ///// for distance goal
        String goalDistanceString = "(当日)运动距离目标: " + goalDistance + " km";


        //// for summary/goal, ** floors ***
        String sumFloorString = "(当日)上下楼梯统计比例: \n" +  String.format("%.2f",summaryFloor*100)+"%";

        if (summaryFloor > 1) {
            sumFloorString += "(非常好, 您已经完成预定的上下楼目标!)";
        }
        else if (summaryFloor > 0.5 && summaryFloor < 0.8) {
            sumFloorString += "(不错, 您接近完成目标,继续保持!)";
        }
        else if (summaryFloor < 0.5) {
            sumFloorString += "(加油, 离预定的上下楼目标还有一段距离, 建议增加此运动!)";
        }


        //// for summary/goal, ** steps ***
        String sumStepString = "(当日)步数统计比例: \n" + String.format("%.2f",summaryStep*100)+"%";
        if (summaryStep > 1) {
            sumStepString += "(非常好, 您已经完成预定的步数目标!)";
        }
        else if (summaryStep > 0.5) {
            sumStepString += "(不错, 您接近完成目标,继续保持!)";
        }
        else if (summaryStep < 0.5) {
            sumStepString += "(加油, 离预定的上下楼目标还有一段距离, 建议增加此运动!)";
        }


        /// for activity statistics, ** activity **
        String percentSedentaryActive = "静止活动(比例): " + String.format("%.2f",sedPercent*100)+"%";

        String percentLightActive = "轻微运动(比例): " + String.format("%.2f",ligPercent*100)+"%";
        String percentModerateActive = "适度活动(比例): " + String.format("%.2f",modPercent*100)+"%";
        String percentVeryActive = "剧烈运动(比例): " + String.format("%.2f",veryPercent*100)+"%";

        if (sedPercent > 0.5) {
            percentSedentaryActive += "(你静坐太多,需要增强锻炼!)";
        }

        if (ligPercent < 0.3) {
            percentLightActive += "(运动太少,增加适度运动!)";
        }
        else if (ligPercent > 0.4) {
            percentLightActive += "(轻微运动太多,请适当增加适度运动!)";
        }
        if (modPercent > 0.15) {
            percentModerateActive += "(运动过多,请注意休息!)";
        }
        else if (modPercent < 0.05) {
            percentModerateActive += "(适度运动太少,适当的活动有助于宝宝健康,建议增加活动!)";
        }

        if (veryPercent > 0.05) {
            percentVeryActive += "(剧烈运动太多,注意胎儿安全,请不要剧烈运动!)";
        }

        /// listview to show the content
        final ListView listview = (ListView) findViewById(R.id.listview);
        String[] values = new String[] {goalCalorieOutString, goalDistanceString, sumFloorString, sumStepString, percentSedentaryActive,
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

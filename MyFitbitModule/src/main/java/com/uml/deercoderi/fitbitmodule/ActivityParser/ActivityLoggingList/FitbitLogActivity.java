package com.uml.deercoderi.fitbitmodule.ActivityParser.ActivityLoggingList;

/***
 *  Created by Chang Liu, for parsing of the activity response(JSON-style)
 *
 *  There are multiple GET request, so the response of activity also has multiple types, including
 *  non-TimeSeries and TimeSeries. We have to implemented all the interface and keyword for these
 *  response and save them.
 *
 *  According to the official tutorial about Activity and its Time Series:
 *  (https://dev.fitbit.com/docs/activity/#get-activity-logs-list):
 *
 *  Activity Logging List: GET https://api.fitbit.com/1/user/-/activities/list.json
 *  for details about the parameters we can set, check the above URL.
 *
 *  History:
 *
 *  Chang Liu, v1.0, June 13th, 2016, Create first initial version and README
 *
 */


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cliu on 6/13/16.
 */
public class FitbitLogActivity {

    /// all the JSONObject activities, stored in JSONArray
    private JSONObject LogActivity;

    /// for each activity, contains all the necessary information
    private String mActiveDuration;
    private JSONArray mActivityLevel;       // stored in the array
    private String mActivityName;
    private String mActivityTypeId;
    private String mAverageHeartRate;
    private String mCalories;
    private String mCalorieLink;
    private String mDistance;
    private String mDistanceUnit;
    private String mDuration;
    private String mHeartRateLink;
    private JSONArray mHeartRateZones;      // stored in the array
    private String mLastModifiedTime;
    private String mLogId;
    private String mLogType;
    private JSONObject mManualValueSpecified;   // JSON object that contains sub-member
    private String mPace;
    private JSONObject mSource;                 // JSON object contains sub-memeber
    private String mSpeed;
    private String mStartTime;
    private String mSteps;
    private String mTcxLink;


    public FitbitLogActivity(JSONObject js) {
        LogActivity = js;
    }

    public void parse() throws JSONException {
        Log.e("aaaaa", "bbbbcccccc");
        setActiveDuration(LogActivity.getString("activeDuration"));
        setActivityLevel(LogActivity.getJSONArray("activityLevel"));
        Log.e("aaaaa", "bbbbcccccceeeeeeee");
        setActivityName(LogActivity.getString("activityName"));
        setActivityTypeId(LogActivity.getString("activityTypeId"));
        setAverageHeartRate(LogActivity.getString("averageHeartRate"));
        setCalorie(LogActivity.getString("calories"));
        Log.e("aaaaa", "bbbbccccccfffffffffff");
        setCalorieLink(LogActivity.getString("caloriesLink"));
        //setDistance(LogActivity.getString("distance"));
        //setDistanceUnit(LogActivity.getString("distanceUnit"));
        setDuration(LogActivity.getString("duration"));
        Log.e("aaaaa", "bbbbccccccdddddddd");
        setHeartRateLink(LogActivity.getString("heartRateLink"));
        setHeartRateZones(LogActivity.getJSONArray("heartRateZones"));
        setLastModifiedTime(LogActivity.getString("lastModified"));
        setLogId(LogActivity.getString("logId"));
        setLogType(LogActivity.getString("logType"));
        setmManualValueSpecified(LogActivity.getJSONObject("manualValuesSpecified"));
        //setPace(LogActivity.getString("pace"));
        //setSource(LogActivity.getJSONObject("source"));
        //setSpeed(LogActivity.getString("speed"));
        setStartTime(LogActivity.getString("startTime"));
        //setSteps(LogActivity.getString("steps"));
        //setTcxLink(LogActivity.getString("tcxLink"));
    }

    /// don't use pace, as some items of activity doesn't contain such field
    public String getParsedString() throws JSONException {
        String activeDuration = "active duration: " + getActiveDuration() + "\n";
        String activityLev = "activity level: " + getActivityLevel() + "\n";
        String activityName = "activity name: " + getActivityName() + "\n";
        String averageHeartRate = "average heart rate: " + getAverageHeartRate() + "\n";
        String calorie = "calories: " + getCalorie() + "\n";
        //String distance = "distance: " + getDistance() + " " + getDistanceUnit() + "\n";
        String duration = "duration: " + getDuration() + "\n";
        String heartrateZone = "heart rate zones: " + getHeartRateZones() + "\n";
        String logtype = "log type: " + getLogType() + "\n";
        String manualValuesSpecific = "manual values specified: " + getManualValueSpecified() + "\n";
        //String pace = "pace: " + getPace() + "\n";
        String source = "source: " + getSource() + "\n";
        String speed = "speed: " + getSpeed() + "\n";
        String steps = "steps: " + getSteps() + "\n";
        String header = "******** Logging activity Lists **********";
        return header + activeDuration + activityLev + activityName + averageHeartRate + calorie
                + /*distance +*/ duration + heartrateZone + logtype + manualValuesSpecific; //+ pace
                //+ source + speed + steps;
    }


    //// setter and getter of the field;
    public void setActiveDuration(String d) {
        mActiveDuration = d;
    }

    public String getActiveDuration() {
        return mActiveDuration;
    }

    public void setActivityLevel(JSONArray ja) {
        mActivityLevel = ja;
    }

    /// for activityLevel, it's array, a bit complex for full strings.
    public String getActivityLevel() throws JSONException{
        String levelString = "";

        if (mActivityLevel != null) {
            int len = mActivityLevel.length();
            for (int i = 0; i < len; i++) {
                JSONObject jb = mActivityLevel.getJSONObject(i);
                activityLevel fitActLevel = new activityLevel(jb);
                fitActLevel.parse();
                levelString += fitActLevel.getParsedString();
            }
        }

        return levelString;
    }

    public void setActivityName(String n) {
        mActivityName = n;
    }

    public String getActivityName() {
        return mActivityName;
    }

    public void setActivityTypeId(String i) {
        mActivityTypeId = i;
    }

    public String getActivityTypeId() {
        return mActivityTypeId;
    }

    public void setAverageHeartRate(String aRate) {
        mAverageHeartRate = aRate;
    }

    public String getAverageHeartRate() {
        return mAverageHeartRate;
    }

    public void setCalorie(String cal) {
        mCalories = cal;
    }

    public String getCalorie() {
        return mCalories;
    }

    public void setCalorieLink(String calLink) {
        mCalorieLink = calLink;
    }

    public String getCalorieLink() {
        return mCalorieLink;
    }

    public void setDistance(String dis) {
        mDistance = dis;
    }

    public String getDistance() {
        return mDistance;
    }

    public void setDistanceUnit(String unit) {
        mDistanceUnit = unit;
    }

    public String getDistanceUnit() {
        return mDistanceUnit;
    }

    public void setDuration(String dur) {
        mDuration = dur;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setHeartRateLink(String link) {
        mHeartRateLink = link;
    }

    public String getHeartRateLink() {
        return mHeartRateLink;
    }

    public void setHeartRateZones(JSONArray ja) {
        mHeartRateZones = ja;
    }

    public String getHeartRateZones() throws JSONException {
        String heartRateZoneString = "";

        if (mHeartRateZones != null) {
            int len = mHeartRateZones.length();
            for (int i = 0 ; i < len; i++) {
                JSONObject jb = mHeartRateZones.getJSONObject(i);
                heartRateZones hrz = new heartRateZones(jb);
                hrz.parse();
                heartRateZoneString += hrz.getParsedString();
            }
        }
        return heartRateZoneString;
    }

    public void setLastModifiedTime(String modify) {
        mLastModifiedTime = modify;
    }

    public String getLastModifiedTime() {
        return mLastModifiedTime;
    }

    public void setLogId(String li) {
        mLogId = li;
    }

    public String getLogId() {
        return mLogId;
    }

    public void setLogType(String lt) {
        mLogType = lt;
    }

    public String getLogType() {
        return mLogType;
    }

    public void setmManualValueSpecified(JSONObject jb) {
        mManualValueSpecified = jb;
    }

    public String getManualValueSpecified() throws JSONException {
        String manualString = "";
        if (mManualValueSpecified != null) {
            manualValuesSpecified ms = new manualValuesSpecified(mManualValueSpecified);
            ms.parse();
            manualString = ms.getParsedString();
        }
        return manualString;
    }

    public void setPace(String p) {
        mPace = p;
    }

    public String getPace() {
        return mPace;
    }

    public void setSource(JSONObject jb) {
        mSource = jb;
    }

    public String getSource() throws JSONException {
        String sourceString = "";

        if (mSource != null) {
            source s = new source(mSource);
            s.parse();
            sourceString = s.getParsedString();
        }
        return sourceString;
    }

    public void setSpeed(String sp) {
        mSpeed = sp;
    }

    public String getSpeed() {
        return mSpeed;
    }

    public void setStartTime(String st) {
        mStartTime = st;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setSteps(String s) {
        mSteps = s;
    }

    public String getSteps() {
        return mSteps;
    }

    public void setTcxLink(String link) {
        mTcxLink = link;
    }

    public String getTcxLink() {
        return mTcxLink;
    }
}

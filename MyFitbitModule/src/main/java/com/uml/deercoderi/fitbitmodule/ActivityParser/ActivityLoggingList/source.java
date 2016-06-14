package com.uml.deercoderi.fitbitmodule.ActivityParser.ActivityLoggingList;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cliu on 6/13/16.
 */
public class source {
    /// raw json object
    private JSONObject json;

    /// private memberp
    private String id;
    private String name;
    private String type;
    private String url;


    public source(JSONObject js) {
        json = js;
    }

    public void parse()  throws JSONException {
        setId(json.getString("id"));
        setName(json.getString("name"));
        setType(json.getString("type"));
        setUrl(json.getString("url"));
    }

    public String getParsedString() {
        String id = "ID: " + getId() + "\n";
        String name = "Name: " + getName() + "\n";
        String type = "Type: " + getType() + "\n";
        String url = "Url: " +  getUrl()  + "\n";
        String header = "******** soruces in activity *********\n";
        return header + id + name + type + url;
    }

    /// setter and getters
    public void setId(String i) {
        id = i;
    }

    public String getId() {
        return id;
    }

    public void setName(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public void setType(String t) {
        type = t;
    }

    public String getType() {
        return type;
    }

    public void setUrl(String u) {
        url = u;
    }

    public String getUrl() {
        return url;
    }
}

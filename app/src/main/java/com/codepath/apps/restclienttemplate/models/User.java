package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {

    public String name;
    public long uid;
    public  String screenname;
    public  String profileImgUrl;


    //get user information from the json
    public static User fromJson(JSONObject jsonObject) throws JSONException {
     User user = new User();
     user.name = jsonObject.getString("name");
     user.uid = jsonObject.getLong("id");
     user.screenname = jsonObject.getString("screen_name");
     user.profileImgUrl = jsonObject.getString("profile_image_url");

     return user;
    }
    //constructor for Parceler library
    public  User() {}
}

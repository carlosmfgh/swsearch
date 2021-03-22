package com.carlosmfgh.swsearch;

import org.json.JSONException;
import org.json.JSONObject;

public class MyCharacter {

    private String mName;
    private String mHeight;
    private String mMass;
    private String mHairColor;
    private String mSkinColor;
    private String mEyeColor;
    private String mBirthYear;
    private String mGender;

    private JSONObject mJsonObject;

    public MyCharacter (JSONObject jsonObject) {
        mJsonObject = jsonObject;
        try {
            mName = mJsonObject.getString("name");
            mHeight = mJsonObject.getString("height");
            mMass = mJsonObject.getString("mass");
            mHairColor = mJsonObject.getString("hair_color");
            mSkinColor = mJsonObject.getString("skin_color");
            mEyeColor = mJsonObject.getString("eye_color");
            mBirthYear = mJsonObject.getString("birth_year");
            mGender = mJsonObject.getString("gender");

        } catch(JSONException e) {
            e.printStackTrace();
        }

    }

    public String getmName() {
        return mName;
    }

    public String getmHeight() {
        return mHeight;
    }

    public String getmMass() {
        return mMass;
    }

    public String getmHairColor() {
        return mHairColor;
    }

    public String getmSkinColor() {
        return mSkinColor;
    }

    public String getmEyeColor() {
        return mEyeColor;
    }

    public String getmBirthYear() {
        return mBirthYear;
    }

    public String getmGender() {
        return mGender;
    }

    public JSONObject getmJsonObject() {
        return mJsonObject;
    }
}

package com.carlosmfgh.swsearch;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * MyCharacter holds the JSONObject that was retrieved of the character.
 */
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
            mName = mJsonObject.getString(Constants.name);
            mHeight = mJsonObject.getString(Constants.height);
            mMass = mJsonObject.getString(Constants.mass);
            mHairColor = mJsonObject.getString(Constants.hair_color);
            mSkinColor = mJsonObject.getString(Constants.skin_color);
            mEyeColor = mJsonObject.getString(Constants.eye_color);
            mBirthYear = mJsonObject.getString(Constants.birth_year);
            mGender = mJsonObject.getString(Constants.gender);

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

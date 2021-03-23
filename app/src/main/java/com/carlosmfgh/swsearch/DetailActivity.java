package com.carlosmfgh.swsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";

    private Context context;
    private ImageButton buttonFavorite;
    private TextView textViewCharacter;
    private TextView textViewHeight;
    private TextView textViewMass;
    private TextView textViewHairColor;
    private TextView textViewSkinColor;
    private TextView textViewEyeColor;
    private TextView textViewBirthYear;
    private TextView textViewGender;
    private JSONObject jsonObject;
    private String name;
    private String height;
    private String mass;
    private String hairColor;
    private String skinColor;
    private String eyeColor;
    private String birthYear;
    private String gender;
    private SharedPreferences shared_pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        context = this;

        buttonFavorite = findViewById(R.id.buttonFavorite);
        textViewCharacter = findViewById(R.id.textViewCharacter);
        textViewHeight = findViewById(R.id.textViewHeight);
        textViewMass = findViewById(R.id.textViewMass);
        textViewHairColor = findViewById(R.id.textViewHairColor);
        textViewSkinColor = findViewById(R.id.textViewSkinColor);
        textViewEyeColor = findViewById(R.id.textViewEyeColor);
        textViewBirthYear = findViewById(R.id.textViewBirthYear);
        textViewGender = findViewById(R.id.textViewGender);
        shared_pref = context.getApplicationContext().getSharedPreferences(Constants.SharedPref_favorites, MODE_PRIVATE);

        initFavoriteButtonClickListener();


        if (getIntent().hasExtra(Constants.characterJsonObject)) {

            String jsonObjectString = getIntent().getStringExtra(Constants.characterJsonObject);
            try {
                jsonObject = new JSONObject(jsonObjectString);
                name = jsonObject.getString(Constants.name);
                height = jsonObject.getString(Constants.height);
                mass = jsonObject.getString(Constants.mass);
                hairColor = jsonObject.getString(Constants.hair_color);
                skinColor = jsonObject.getString(Constants.skin_color);
                eyeColor = jsonObject.getString(Constants.eye_color);
                birthYear = jsonObject.getString(Constants.birth_year);
                gender = jsonObject.getString(Constants.gender);
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                textViewCharacter.setText(name);
                textViewHeight.setText(height);
                textViewMass.setText(mass);
                textViewHairColor.setText(hairColor);
                textViewSkinColor.setText(skinColor);
                textViewEyeColor.setText(eyeColor);
                textViewBirthYear.setText(birthYear);
                textViewGender.setText(gender);
            }
        } else {
            MyAlertDialog.OkMessageBox(this, getString(R.string.error), getString(R.string.error_initializing_detail_view));
        }

        if (shared_pref.contains(name)) {
            buttonFavorite.setImageResource(R.drawable.ic_favorite_large);
        } else {
            buttonFavorite.setImageResource(R.drawable.ic_not_favorite_large);
        }

    }  // onCreate

    /**
     *
     */
    private void initFavoriteButtonClickListener () {

        buttonFavorite.setOnClickListener(view -> {
            SharedPreferences.Editor editor = shared_pref.edit();

            if (shared_pref.contains(name)) {
                buttonFavorite.setImageResource(R.drawable.ic_not_favorite_large);
                editor.remove(name);
            } else {
                buttonFavorite.setImageResource(R.drawable.ic_favorite_large);
                editor.putString(name, name);
            }
            editor.apply();
        });
    }
}
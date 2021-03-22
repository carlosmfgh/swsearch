package com.carlosmfgh.swsearch;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    EditText editTextSearch;
    RecyclerView characterRecyclerView;
    CharactersAdapter charactersAdapter;
    List<MyCharacter> myCharacterList;
    JsonReaderUrl jsonReaderUrl;
    Observable<JSONObject> jsonObjectObservable;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {

        // upon returning to this MainActivity, update the RecyclerView, since there
        // could have been a change to the characters 'favorite' status.
        charactersAdapter.updateData(myCharacterList);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextSearch = findViewById(R.id.editTextSearch);
        characterRecyclerView = findViewById(R.id.characterRecyclerView);
        jsonReaderUrl = new JsonReaderUrl("https://swapi.dev/api/people/");

        initSearchBarListener();

        myCharacterList = new ArrayList<>();
        charactersAdapter = new CharactersAdapter(this, myCharacterList);
        characterRecyclerView.setAdapter(charactersAdapter);
        characterRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }  // onCreate

    /**
     * Initializes the EditText to listen to changes to its value for each character that is
     * entered or deleted.
     *
     */
    private void initSearchBarListener ( ) {
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length() > 0) {
                    search(charSequence.toString());
                } else {
                    clearAdapter();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }  // initSearchBarListener

    /**
     *
     * @param stringToSearch
     */
    private void search (String stringToSearch) {
        jsonObjectObservable = jsonReaderUrl.getJSONObject(stringToSearch);

        Disposable disp = jsonObjectObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        jsonObject -> {
                            clearAdapter();

                            JSONArray jsonObjectArray = jsonObject.getJSONArray("results");

                            Log.d(TAG, "length(): " + jsonObjectArray.length());
                            for (int i = 0 ;  i < jsonObjectArray.length(); i++) {

                                JSONObject jo = jsonObjectArray.getJSONObject(i);

                                MyCharacter myCharacter = new MyCharacter(jo);
                                myCharacterList.add(myCharacter);
                            }
                            charactersAdapter.updateData(myCharacterList);
                        },
                        error -> {
                            Log.d(TAG, "Error " + error.getMessage());
                        },
                        () -> {

                        });

    }  // search

    private void clearAdapter () {
        myCharacterList.clear();
        charactersAdapter.updateData(myCharacterList);
    }

}
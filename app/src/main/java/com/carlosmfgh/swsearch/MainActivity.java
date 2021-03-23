package com.carlosmfgh.swsearch;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

    Context context;
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

        context = this;
        editTextSearch = findViewById(R.id.editTextSearch);
        characterRecyclerView = findViewById(R.id.characterRecyclerView);
        jsonReaderUrl = new JsonReaderUrl(Constants.baseHttpUrlPeopleSearchUrl);

        // initialize the search edit text for every character change.
        initSearchBarListener();

        // Prepare the recyclerview of will display the character list.
        myCharacterList = new ArrayList<>();
        charactersAdapter = new CharactersAdapter(this, myCharacterList);
        characterRecyclerView.setAdapter(charactersAdapter);
        characterRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Warn if there is no internet connection.
        if (!isNetworkAvailable()) {
            MyAlertDialog.OkMessageBox(this, getString(R.string.warning), getString(R.string.no_internet_connection));
        }
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
     * Subscribes to the getJSONObject observable, and receives the json object.
     * @param stringToSearch
     */
    private void search (String stringToSearch) {
        jsonObjectObservable = jsonReaderUrl.getJSONObject(stringToSearch);

        Disposable disp = jsonObjectObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        jsonObject -> {
                            clearAdapter();

                            JSONArray jsonObjectArray = jsonObject.getJSONArray(Constants.results);

                            Log.d(TAG, "length(): " + jsonObjectArray.length());
                            for (int i = 0 ;  i < jsonObjectArray.length(); i++) {

                                JSONObject jo = jsonObjectArray.getJSONObject(i);

                                MyCharacter myCharacter = new MyCharacter(jo);
                                myCharacterList.add(myCharacter);
                            }
                            charactersAdapter.updateData(myCharacterList);
                        },
                        error -> {
                            Log.d(TAG, getString(R.string.error) + error.getMessage());
                            MyAlertDialog.OkMessageBox(context,
                                                       getString(R.string.error),
                                                       getString(R.string.error_retrieving_json));
                        },
                        () -> {

                        });

    }  // search

    /**
     * Clears the list of characters from the list and sets (updates) in the adapter,
     * essentially clears the recyclerview list.
     */
    private void clearAdapter () {
        myCharacterList.clear();
        charactersAdapter.updateData(myCharacterList);
    }  // clearAdapter

    /**
     * Checks if there is a network connection.
     * @return
     */
    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }  // isNetworkAvailable
}
package com.carlosmfgh.swsearch;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import io.reactivex.Observable;

public class JsonReaderUrl {
    String mBaseUrl;

    public JsonReaderUrl(String baseUrl) {
        mBaseUrl = baseUrl + "?search=";
    }

    // todo: test this.
    //private JsonReaderUrl() {};

    public Observable<JSONObject> getJSONObject(String searchString) {

        return Observable.create(subscriber -> {
            try {
                //String search = mBaseUrl + searchString;
                JSONObject jsonObject = readJsonFromUrl(searchString);

                subscriber.onNext(jsonObject);
                subscriber.onComplete();
            } catch (IOException ioe) {
                Log.d("JsonReaderUrl", "IOException: " + ioe.getMessage() );
            } catch (JSONException jsonException) {
                Log.d("JsonReaderUrl", "JSONException: " + jsonException.getMessage() );
            }
        });
    }

    /**
     *
     * @param stringToSearch
     * @return
     * @throws IOException
     * @throws JSONException
     */
    private JSONObject readJsonFromUrl(String stringToSearch) throws IOException, JSONException {

        String searchString = mBaseUrl + stringToSearch;

        InputStream is = new URL(searchString).openStream();

        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    /**
     *
     * @param rd
     * @return
     * @throws IOException
     */
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }



}

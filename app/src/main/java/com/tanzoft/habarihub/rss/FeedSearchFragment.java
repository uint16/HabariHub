package com.tanzoft.habarihub.rss;


import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tanzoft.habarihub.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class FeedSearchFragment extends Fragment {

    private final String LOG_TAG = FeedSearchFragment.class.getSimpleName();

    ArrayAdapter<String> adapter = null;

    public FeedSearchFragment() {

    }

    @Override
   public void onStart() {
        super.onStart();
        Intent intent = getActivity().getIntent();
        String searchString = intent.getStringExtra(Intent.EXTRA_TEXT);
        runSearch(searchString);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feed_search, container, false);

        adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_feed_result, R.id.list_item_feed_textview, new ArrayList<String>());
        ListView feed_list = (ListView) rootView.findViewById(R.id.listview_feed);
        feed_list.setAdapter(adapter);

        return rootView;
    }

    public void runSearch(String search){
        FetchFeedTask task = new FetchFeedTask();
        if(!search.isEmpty()) {
            task.execute(search);
        } else {
            task.execute("engadget");
        }
    }

    public class FetchFeedTask extends AsyncTask<String, Void, String[]>{

        @Override
        protected String[] doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String feedsJSON = null;

            try{
                final String BASE_URL = "http://ajax.googleapis.com/ajax/services/feed/find?v=1.0";
                final String QUERY = "q";

                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY, params[0])
                        .build();

                URL url = new URL(builtUri.toString());
                Log.v(LOG_TAG, "Feed URL: " + url);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream is = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if(is == null){
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(is));
                String line;
                while((line = reader.readLine()) != null){
                    buffer.append(line + "\n");
                }

                if(buffer.length() == 0){
                    return null;
                }

                feedsJSON = buffer.toString();
                Log.v(LOG_TAG, "Search Result JSON: " + feedsJSON);
                return getFeedSearchResults(feedsJSON);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }

                if(reader != null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null; //if parsing fails
        }

        public String[] getFeedSearchResults(String feedsJSON) throws JSONException {
            final String ENTRIES = "entries";
            final String URL = "url";
            final String TITLE = "title";
            final String RESPONSE_DATA = "responseData";
            final String SEPARATOR = "%";
            JSONObject feedObject;
            JSONArray feedResults;


                feedObject = new JSONObject(feedsJSON);
                feedObject = feedObject.getJSONObject(RESPONSE_DATA);
                feedResults = feedObject.getJSONArray(ENTRIES);
                String[] res = new String[feedResults.length()];

                for(int i = 0; i < feedResults.length(); i++){
                    JSONObject obj = feedResults.getJSONObject(i);
                  //  String url = obj.getString(URL);
                    String title = obj.getString(TITLE);

                    res[i] = title;
                }

            return res;
        }

        @Override
        protected void onPostExecute(String[] results) {
            if(results != null){
                adapter.clear();
                for(String feed: results){
                    SpannableString content = new SpannableString(feed);
                    adapter.add(Html.fromHtml(content.toString()).toString());
                    Log.i(LOG_TAG, "Searh Result: " + feed);
                }
            }

            ArrayList<String> feeds = new ArrayList<String>(Arrays.asList(results));
            adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_feed_result, R.id.list_item_feed_textview, feeds);

            Log.e(LOG_TAG, "Adapter Status: " + adapter.isEmpty());
        }
    }
}

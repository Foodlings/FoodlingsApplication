package com.example.sheharyararif.foodlings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sheharyararif.foodlings.DatabaseModel.Friend;
import com.example.sheharyararif.foodlings.DatabaseModel.Review;
import com.example.sheharyararif.foodlings.DatabaseModel.SearchResult;
import com.example.sheharyararif.foodlings.ParserPackage.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class FriendsScreen extends AppCompatActivity {

    ArrayList<Friend> friendsList;
    Intent intent;
    Bundle args;
    SearchResult searchResult;
    ListView FriendsListView;
    FriendsAdapter adapter;
    JSONArray jsonArray = null;
    TextView FriendsResultLabel;

    //URL to get JSON Array
    private static String getAllFriendsURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getAllFriends?SubscriberID=";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_screen);

        FriendsListView = (ListView) findViewById(R.id.Friends_ListView);
        FriendsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Friend friend = (Friend) parent.getItemAtPosition(position);
                searchResult = new SearchResult();
                searchResult.setSubscriberID(friend.FriendID);
                searchResult.setFriendCheck("Friend");
                searchResult.setType("Subscriber");
                intent = new Intent(FriendsScreen.this, SubscriberProfileScreen.class);
                args = new Bundle();
                args.putSerializable("searchResult",(Serializable)searchResult);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);
            }
        });

        FriendsResultLabel = (TextView) findViewById(R.id.FriendsResultLabel);

        friendsList = new ArrayList<>();

        try
        {
            intent = getIntent();
            args = intent.getBundleExtra("BUNDLE");
            searchResult = (SearchResult) args.getSerializable("searchResult");
            getAllFriendsURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getAllFriends?SubscriberID=" + searchResult.SubscriberID;
        }
        catch (Exception ex)
        {
            getAllFriendsURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getAllFriends?SubscriberID=" + GlobalData.SubscriberID;
        }

        new JSONParse().execute();
    }

    @Override
    public void onRestart()
    {
        super.onRestart();

        //Initializing Posts Array
        friendsList = new ArrayList<>();

        //Posts List Event Listener
        FriendsListView = (ListView) findViewById(R.id.Friends_ListView);

        if(adapter!=null)
        {
            adapter.clearData();
            adapter.notifyDataSetChanged();
        }

        new JSONParse().execute();
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject>
    {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(FriendsScreen.this);
            pDialog.setMessage("Loading Friend List");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(getAllFriendsURL, "GET", null, null, null, null, null, null, null);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                // Getting JSON Array
                jsonArray = json.getJSONArray("Friend");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject fetchedData = jsonArray.getJSONObject(i);

                    friendsList.add(new Friend("", GlobalData.SubscriberID, fetchedData.getString("FriendID"), fetchedData.getString("Since"), fetchedData.getString("SubscriberName"), fetchedData.getString("DisplayPicture")));
                }

                if(jsonArray.length() > 0)
                {
                    adapter = new FriendsAdapter(friendsList, FriendsScreen.this);
                    FriendsListView.setAdapter(adapter);
                    FriendsResultLabel.setText("");
                }
                else
                {
                    FriendsResultLabel.setText("No Friends Added Yet.");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.example.sheharyararif.foodlings;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sheharyararif.foodlings.DatabaseModel.Comment;
import com.example.sheharyararif.foodlings.DatabaseModel.SearchResult;
import com.example.sheharyararif.foodlings.ParserPackage.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class DashboardScreen extends AppCompatActivity {

    ImageButton SettingsButton;
    Button PortalButton, ProfileButton, NewsFeedButton, SearchButton;
    EditText SearchTextBox;
    JSONArray jsonArray = null;
    String searchText;
    ArrayList<SearchResult> searchResultsList;
    Intent intent;
    Bundle args;

    //URL to get JSON Array
    private static String searchURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/searchSubscribers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_screen);

        intent = new Intent(DashboardScreen.this, SearchScreen.class);
        args = new Bundle();

        //SettingsScreen Button's Click Event
        SettingsButton = (ImageButton)findViewById(R.id.SettingsButton);
        SettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            { startActivity(new Intent(DashboardScreen.this, SettingsScreen.class)); }
        });

        //Portal Button's Click Event
        PortalButton = (Button)findViewById(R.id.PortalButton);
        PortalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            { startActivity(new Intent(DashboardScreen.this, PortalScreen.class)); }
        });

        //Profile Button's Click Event
        ProfileButton = (Button)findViewById(R.id.ProfileButton);
        ProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(GlobalData.Type.equals("Subscriber"))
                {
                    startActivity(new Intent(DashboardScreen.this, SubscriberProfileScreen.class));
                }
                else
                {
                    startActivity(new Intent(DashboardScreen.this, RestaurantProfile.class));
                }
            }
        });

        searchResultsList = new ArrayList<>();

        //NewsFeed Button's Click Event
        NewsFeedButton = (Button)findViewById(R.id.NewsfeedButton);
        NewsFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            { startActivity(new Intent(DashboardScreen.this, NewsFeed.class)); }
        });
        SearchTextBox = (EditText) findViewById(R.id.SearchTextBox);

        SearchButton = (Button) findViewById(R.id.SearchButton);
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(SearchTextBox.getText().toString().equals(""))
                {
                    Toast.makeText(DashboardScreen.this, "Fill the text box to search.",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    searchText = SearchTextBox.getText().toString();
                    new JSONParse().execute();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Log-out Confirmation")
                .setMessage("Do you want to Log-out?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(DashboardScreen.this, "Logged-out", Toast.LENGTH_SHORT).show();
                        Intent intents = new Intent(DashboardScreen.this, HomeScreen.class);
                        intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                                | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intents);
                        finish();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject>
    {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DashboardScreen.this);
            pDialog.setMessage("Searching");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();

            searchResultsList = new ArrayList<>();
            SearchResult searchResult = new SearchResult(GlobalData.SubscriberID, "",searchText, "", "", "", "");

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(searchURL, "POST", null, null, null, null, searchResult, null, null);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json)
        {
            pDialog.dismiss();
            try
            {
                // Getting JSON Array
                jsonArray = json.getJSONArray("SearchResult");

                for (int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject fetchedData = jsonArray.getJSONObject(i);

                    String SubscriberID = fetchedData.getString("SubscriberID");
                    String RestaurantID = fetchedData.getString("RestaurantID");
                    String Name = fetchedData.getString("Name");
                    String Type = fetchedData.getString("Type");
                    String Email = fetchedData.getString("Email");
                    String DisplayPicture = fetchedData.getString("DisplayPicture");
                    String FriendCheck = fetchedData.getString("FriendCheck");

                    searchResultsList.add(new SearchResult(SubscriberID, RestaurantID ,Name, Type, Email, DisplayPicture, FriendCheck));
                }

                GlobalData.SearchedFrom = "Dashboard";
                args.putSerializable("searchResultsList",(Serializable)searchResultsList);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);
            }
            catch (JSONException e)
            {
                Toast.makeText(DashboardScreen.this, "Something went wrong.",
                        Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}

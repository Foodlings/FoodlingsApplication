package com.example.sheharyararif.foodlings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sheharyararif.foodlings.DatabaseModel.SearchResult;
import com.example.sheharyararif.foodlings.ParserPackage.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class PortalScreen extends AppCompatActivity {

    Button CategoryButton, SearchButton, NearbyButton, TopRestaurantButton;
    EditText SearchTextBox;
    JSONArray jsonArray = null;
    String searchText;
    ArrayList<SearchResult> searchResultsList;
    Intent intent;
    Bundle args;

    //URL to get JSON Array
    private static String searchURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/searchRestaurants";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.portal_screen);

        intent = new Intent(PortalScreen.this, SearchScreen.class);
        args = new Bundle();
        searchResultsList = new ArrayList<>();
        SearchTextBox = (EditText) findViewById(R.id.SearchTextBox);

        //Category Button's Click Event
        CategoryButton = (Button)findViewById(R.id.CategoryButton);
        CategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            { startActivity(new Intent(PortalScreen.this, CategoryScreen.class)); }
        });

        TopRestaurantButton = (Button)findViewById(R.id.TopRestaurantButton);
        TopRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(PortalScreen.this, TopRestaurantsScreen.class));
            }
        });

        NearbyButton = (Button)findViewById(R.id.NearbyButton);
        NearbyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!GlobalData.Type.equals("Visitor"))
                {
                    startActivity(new Intent(PortalScreen.this, NearByRestaurantsScreen.class));
                }
                else
                {
                    Toast.makeText(PortalScreen.this, "Register/Login to access it.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        SearchButton = (Button) findViewById(R.id.SearchButton);
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(SearchTextBox.getText().toString().equals(""))
                {
                    Toast.makeText(PortalScreen.this, "Fill the text box to search.",
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

    private class JSONParse extends AsyncTask<String, String, JSONObject>
    {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PortalScreen.this);
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

                GlobalData.SearchedFrom = "Portal";
                args.putSerializable("searchResultsList",(Serializable)searchResultsList);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);
            }
            catch (JSONException e)
            {
                Toast.makeText(PortalScreen.this, "Something went wrong.",
                        Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}

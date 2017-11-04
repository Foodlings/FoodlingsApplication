package com.example.sheharyararif.foodlings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.sheharyararif.foodlings.DatabaseModel.SearchResult;
import com.example.sheharyararif.foodlings.ParserPackage.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;

public class TopRestaurantsScreen extends AppCompatActivity
{
    ArrayList<SearchResult> searchResultsList;
    Intent intent;
    Bundle args;
    ListView TopRestaurants_ListView;
    SearchAdapter adapter;
    SearchResult searchResult;
    TextView TopRestaurantsResultLabel;
    JSONArray jsonArray = null;

    //URL to get JSON Array
    private static String searchURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/topRestaurants";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_restaurants_screen);

        TopRestaurants_ListView = (ListView) findViewById(R.id.TopRestaurants_ListView);
        TopRestaurantsResultLabel = (TextView) findViewById(R.id.TopRestaurantsResultLabel);

        TopRestaurants_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id)
            {
                SearchResult searchResult = (SearchResult) adapter.getItemAtPosition(position);
                if(searchResult.Type.equals("Subscriber"))
                {
                    intent = new Intent(TopRestaurantsScreen.this, SubscriberProfileScreen.class);
                }
                else
                {
                    intent = new Intent(TopRestaurantsScreen.this, RestaurantProfile.class);
                }

                args = new Bundle();
                args.putSerializable("searchResult",(Serializable)searchResult);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);
            }
        });

        new JSONParse().execute();
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject>
    {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(TopRestaurantsScreen.this);
            pDialog.setMessage("Searching Top Restaurants");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();

            searchResultsList = new ArrayList<>();

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(searchURL, "GET", null, null, null, null, null, null, null);
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
                    String ReviewsCount = fetchedData.getString("ReviewsCount");
                    String Scope = fetchedData.getString("Scope");

                    SearchResult searchResult = new SearchResult();
                    searchResult.setSubscriberID(SubscriberID);
                    searchResult.setRestaurantID(RestaurantID);
                    searchResult.setName(Name);
                    searchResult.setType(Type);
                    searchResult.setEmail(Email);
                    searchResult.setDisplayPicture(DisplayPicture);
                    searchResult.setFriendCheck(FriendCheck);
                    searchResult.setReviewsCount(ReviewsCount);
                    searchResult.setScope(Scope);

                    searchResultsList.add(searchResult);
                }

                if(jsonArray.length() > 0)
                {
                    TopRestaurantsResultLabel.setText("Search Results");
                    adapter = new SearchAdapter(searchResultsList, TopRestaurantsScreen.this);
                    TopRestaurants_ListView.setAdapter(adapter);
                }
                else
                {
                    TopRestaurantsResultLabel.setText("No Restaurants Founds with Reviews.");
                }
            }
            catch (JSONException e)
            { e.printStackTrace(); }
        }
    }
}

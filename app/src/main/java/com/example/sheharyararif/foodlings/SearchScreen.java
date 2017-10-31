package com.example.sheharyararif.foodlings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sheharyararif.foodlings.DatabaseModel.SearchResult;
import com.example.sheharyararif.foodlings.ParserPackage.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchScreen extends AppCompatActivity
{
    SearchAdapter adapter;
    TextView ResultLabel;
    ListView SearchListView;
    Button SearchButton;
    EditText SearchTextBox;
    Intent intent;
    Bundle args;
    JSONArray jsonArray = null;
    String searchText;
    ArrayList<SearchResult> searchResultsList;

    //URL to get JSON Array
    private static String searchURL;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_screen);

        SearchListView = (ListView) findViewById(R.id.Search_ListView);
        ResultLabel = (TextView) findViewById(R.id.ResultLabel);

        if(GlobalData.SearchedFrom.equals("Dashboard"))
        {
            searchURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/searchSubscribers";
        }
        else if(GlobalData.SearchedFrom.equals("Portal"))
        {
            searchURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/searchRestaurants";
        }


        try
        {
            intent = getIntent();
            args = intent.getBundleExtra("BUNDLE");
            ArrayList<SearchResult> searchResultsList = (ArrayList<SearchResult>) args.getSerializable("searchResultsList");
            adapter = new SearchAdapter(searchResultsList, SearchScreen.this);
            SearchListView.setAdapter(adapter);

            if(searchResultsList.size() > 0)
            {
                ResultLabel.setText("Search Results");
            }
            else
            {
                ResultLabel.setText("No Results Found.");
            }
        }
        catch (Exception ex)
        { ResultLabel.setText("No Results Found."); }


        SearchTextBox = (EditText) findViewById(R.id.SearchTextBox);

        SearchButton = (Button) findViewById(R.id.SearchButton);
        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(SearchTextBox.getText().toString().equals(""))
                {
                    Toast.makeText(SearchScreen.this, "Fill the text box to search.",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    searchText = SearchTextBox.getText().toString();
                    new JSONParse().execute();
                }
            }
        });

        SearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id)
            {
                SearchResult searchResult = (SearchResult) adapter.getItemAtPosition(position);
                if(searchResult.Type.equals("Subscriber"))
                {
                    intent = new Intent(SearchScreen.this, SubscriberProfileScreen.class);
                }
                else
                {
                    intent = new Intent(SearchScreen.this, RestaurantProfile.class);
                }

                args = new Bundle();
                args.putSerializable("searchResult",(Serializable)searchResult);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRestart()
    {
        super.onRestart();

        if(adapter!=null){
            adapter.clearData();
            adapter.notifyDataSetChanged();
        }

        SearchListView = (ListView) findViewById(R.id.Search_ListView);

        //Initializing Posts Array
        searchResultsList = new ArrayList<>();
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject>
    {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SearchScreen.this);
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

                    searchResultsList.add(new SearchResult(SubscriberID, RestaurantID,Name, Type, Email, DisplayPicture, FriendCheck));
                }

                if(jsonArray.length() > 0)
                {
                    ResultLabel.setText("Search Results");
                    adapter = new SearchAdapter(searchResultsList, SearchScreen.this);
                    SearchListView.setAdapter(adapter);
                }
                else
                {
                    ResultLabel.setText("No Results Found.");
                    adapter.clearData();
                    adapter.notifyDataSetChanged();
                }
            }
            catch (JSONException e)
            { e.printStackTrace(); }
        }
    }
}

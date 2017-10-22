package com.example.sheharyararif.foodlings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sheharyararif.foodlings.DatabaseModel.Comment;
import com.example.sheharyararif.foodlings.DatabaseModel.Review;
import com.example.sheharyararif.foodlings.DatabaseModel.SearchResult;
import com.example.sheharyararif.foodlings.ParserPackage.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class ReviewsScreen extends AppCompatActivity
{
    ArrayList<Review> reviewsList;
    Button AddReview;
    Intent intent;
    Bundle args;
    SearchResult searchResult;
    String RestaurantID;
    ListView ReviewsListView;
    ReviewsAdapter adapter;
    JSONArray jsonArray = null;
    TextView ReviewsResultLabel;

    //URL to get JSON Array
    private static String reviewsURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getAllReviews?RestaurantID=";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviews_screen);

        ReviewsListView = (ListView) findViewById(R.id.Reviews_ListView);
        ReviewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Review review = (Review) parent.getItemAtPosition(position);
                Intent intent = new Intent(ReviewsScreen.this, ReviewDetailScreen.class);
                args = new Bundle();
                args.putSerializable("reviewDetail",(Serializable)review);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);
            }
        });

        ReviewsResultLabel = (TextView) findViewById(R.id.ReviewsResultLabel);

        reviewsList = new ArrayList<>();

        AddReview = (Button)findViewById(R.id.AddReviewButton);
        AddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                intent = new Intent(ReviewsScreen.this, AddReviewScreen.class);
                args = new Bundle();
                args.putSerializable("searchResult", (Serializable) searchResult);
                intent.putExtra("BUNDLE", args);
                startActivity(intent);
            }
        });

        try
        {
            intent = getIntent();
            args = intent.getBundleExtra("BUNDLE");
            searchResult = (SearchResult) args.getSerializable("searchResult");
            RestaurantID = searchResult.getRestaurantID();
            reviewsURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getAllReviews?RestaurantID=" + RestaurantID + "&SubscriberID=0&Scope=Restaurant";

            if(searchResult.Type.equals("Subscriber")){
                reviewsURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getAllReviews?RestaurantID=0&SubscriberID=" + searchResult.getSubscriberID() + "&Scope=" + searchResult.Type;
                AddReview.setVisibility(View.GONE);
            }
        }
        catch (Exception ex)
        {
            reviewsURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getAllReviews?RestaurantID=0&SubscriberID=" + GlobalData.SubscriberID + "&Scope=" + GlobalData.Type;
            AddReview.setVisibility(View.GONE);
        }

        new JSONParse().execute();
    }

    @Override
    public void onRestart()
    {
        super.onRestart();

        //Initializing Posts Array
        reviewsList = new ArrayList<>();

        //Posts List Event Listener
        ReviewsListView = (ListView) findViewById(R.id.Reviews_ListView);

        new JSONParse().execute();
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject>
    {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ReviewsScreen.this);
            pDialog.setMessage("Loading Reviews");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(reviewsURL, "GET", null, null, null, null, null, null, null);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                // Getting JSON Array
                jsonArray = json.getJSONArray("Review");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject fetchedData = jsonArray.getJSONObject(i);

                    String ReviewID = fetchedData.getString("ReviewID");
                    String PostID = fetchedData.getString("PostID");
                    String SubscriberID = fetchedData.getString("SubscriberID");
                    String RestaurantID = fetchedData.getString("RestaurantID");
                    String ReviewText = fetchedData.getString("ReviewText");
                    String TimeStamp = fetchedData.getString("TimeStamp");
                    String Taste = fetchedData.getString("Taste");
                    String Ambience = fetchedData.getString("Ambience");
                    String Service = fetchedData.getString("Service");
                    String OrderTime = fetchedData.getString("OrderTime");
                    String Price = fetchedData.getString("Price");
                    String SubscriberName = fetchedData.getString("SubscriberName");
                    String DisplayPicture = fetchedData.getString("DisplayPicture");
                    String RestaurantName = fetchedData.getString("RestaurantName");

                    reviewsList.add(new Review(ReviewID, PostID, SubscriberID, RestaurantID, ReviewText, TimeStamp, Taste, Ambience, Service, OrderTime, Price, SubscriberName, DisplayPicture, RestaurantName));
                }

                if(jsonArray.length() > 0)
                {
                    adapter = new ReviewsAdapter(reviewsList, ReviewsScreen.this);
                    ReviewsListView.setAdapter(adapter);
                    ReviewsResultLabel.setText("");
                }
                else
                {
                    ReviewsResultLabel.setText("No Reviews Found");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

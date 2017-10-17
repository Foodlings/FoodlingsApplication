package com.example.sheharyararif.foodlings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.sheharyararif.foodlings.DatabaseModel.Post;
import com.example.sheharyararif.foodlings.DatabaseModel.Review;
import com.example.sheharyararif.foodlings.DatabaseModel.SearchResult;
import com.example.sheharyararif.foodlings.ParserPackage.JSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddReviewScreen extends AppCompatActivity {

    Button AddReview;
    RatingBar TasteRating, AmbienceRating, ServiceRating, OrderTimeRating, PriceRating;
    EditText ReviewCommentTextBox;
    String TimeStamp, ReviewText, TasteValue, AmbienceValue, ServiceValue, OrderTimeValue, PriceValue;
    Intent intent;
    Bundle args;
    SearchResult searchResult;
    String RestaurantID;

    //URL to get JSON Array
    private static String url = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/createReview";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_review_screen);

        intent = getIntent();
        args = intent.getBundleExtra("BUNDLE");
        searchResult = (SearchResult) args.getSerializable("searchResult");
        RestaurantID = searchResult.getRestaurantID();

        TasteRating = (RatingBar) findViewById(R.id.TasteRating);
        AmbienceRating = (RatingBar) findViewById(R.id.AmbienceRating);
        ServiceRating = (RatingBar) findViewById(R.id.ServiceRating);
        OrderTimeRating = (RatingBar) findViewById(R.id.OrderTimeRating);
        PriceRating = (RatingBar) findViewById(R.id.PriceRating);

        ReviewCommentTextBox = (EditText) findViewById(R.id.ReviewCommentTextBox);

        AddReview = (Button) findViewById(R.id.AddReview);
        AddReview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                TasteValue = String.valueOf(TasteRating.getRating());
                AmbienceValue = String.valueOf(AmbienceRating.getRating());
                ServiceValue = String.valueOf(ServiceRating.getRating());
                OrderTimeValue = String.valueOf(OrderTimeRating.getRating());
                PriceValue = String.valueOf(PriceRating.getRating());
                ReviewText = ReviewCommentTextBox.getText().toString();
                TimeStamp = new SimpleDateFormat("d-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime()).toString();
                new JSONParse().execute();
            }
        });
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject>
    {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddReviewScreen.this);
            pDialog.setMessage("Posting Review");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args)
        {
            JSONParser jParser = new JSONParser();

            Review review = new Review("0","0",GlobalData.SubscriberID,RestaurantID,ReviewText,TimeStamp,TasteValue,AmbienceValue,ServiceValue,OrderTimeValue,PriceValue, "", "");

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url, "POST", null, null, null, null, null, review);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json)
        {
            pDialog.dismiss();
            Toast.makeText(AddReviewScreen.this, "Review Posted",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }
}

package com.example.sheharyararif.foodlings;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sheharyararif.foodlings.DatabaseModel.Post;
import com.example.sheharyararif.foodlings.ParserPackage.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsFeed extends AppCompatActivity
{
    ImageView ProfileIcon, PostIcon;
    CustomAdapter adapter;
    ListView PostsList;
    ArrayList<Post> post;
    Post postData;
    Intent serializedIntent;
    JSONArray user = null;


    //URL to get JSON Array
    private static String url = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getAllPosts";

    //JSON Node Names
    private static final String TAG_PostID = "PostID";
    private static final String TAG_SubscriberID = "SubscriberID";
    private static final String TAG_ImagePresence = "ImagePresence";
    private static final String TAG_ImageAlbumID = "ImageAlbumID";
    private static final String TAG_ReviewPresence = "ReviewPresence";
    private static final String TAG_CheckinPresence = "CheckinPresence";
    private static final String TAG_Privacy = "Privacy";
    private static final String TAG_TimeStamp = "TimeStamp";
    private static final String TAG_PostDescription = "PostDescription";
    private static final String TAG_ImageString = "ImageString";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_feed_screen);

        //Profile Icon
        ProfileIcon = (ImageView) findViewById(R.id.ProfileIcon);
        ProfileIcon.setOnClickListener(clickListener);

        //Post Icon
        PostIcon = (ImageView) findViewById(R.id.PostIcon);
        PostIcon.setOnClickListener(clickListener);

        //Initializing Posts Array
        post = new ArrayList<>();

        //Initializing Serialzied Intent
        serializedIntent = new Intent(this, CommentsScreen.class);

        //Posts List Event Listener
        PostsList = (ListView) findViewById(R.id.Posts_ListView1);
        PostsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int position, long id) {
                //postData = new Post("1","1","1","1","1","1","Public","Time","Descripto","Image Stringer");
                postData = (Post) PostsList.getItemAtPosition(position);
                serializedIntent.putExtra("PostData", postData);
                startActivity(serializedIntent);
            }
        });

        new JSONParse().execute();
    }

    View.OnClickListener clickListener = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            if (v.equals(ProfileIcon))
            {
                startActivity(new Intent(NewsFeed.this, RestaurantProfile.class));
            }
            else if (v.equals(PostIcon))
            {
                startActivity(new Intent(NewsFeed.this, WriteTextPost.class));
            }
        }
    };

    private class JSONParse extends AsyncTask<String, String, JSONObject>
    {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewsFeed.this);
            pDialog.setMessage("Loading Posts...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args)
        {
            JSONParser jParser = new JSONParser();

            Post post = null;
            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url, "GET", post, null);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json)
        {
            pDialog.dismiss();
            try
            {
                // Getting JSON Array
                user = json.getJSONArray("Post");

                for(int i=0; i<user.length(); i++)
                {
                    JSONObject fetchedData = user.getJSONObject(i);

                    String PostID = fetchedData.getString(TAG_PostID);
                    String SubscriberID = fetchedData.getString(TAG_SubscriberID);
                    String ImagePresence = fetchedData.getString(TAG_ImagePresence);
                    String ImageAlbumID = "1000";
                    String ReviewPresence = fetchedData.getString(TAG_ReviewPresence);
                    String CheckinPresence = fetchedData.getString(TAG_CheckinPresence);
                    String Privacy = fetchedData.getString(TAG_Privacy);
                    String Timestamp = fetchedData.getString(TAG_TimeStamp);
                    String PostDescription = fetchedData.getString(TAG_PostDescription);
                    String ImageString = fetchedData.getString(TAG_ImageString);

                    post.add(new Post(PostID, SubscriberID, ImagePresence, ImageAlbumID, ReviewPresence, CheckinPresence, Privacy, Timestamp, PostDescription, ImageString));
                }

                //Adapter
                adapter = new CustomAdapter(post, NewsFeed.this);

                //ListView
                PostsList.setAdapter(adapter);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }
    }
}

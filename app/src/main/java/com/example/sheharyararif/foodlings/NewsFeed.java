package com.example.sheharyararif.foodlings;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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

import com.example.sheharyararif.foodlings.DatabaseModel.Like;
import com.example.sheharyararif.foodlings.DatabaseModel.Post;
import com.example.sheharyararif.foodlings.ParserPackage.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NewsFeed extends AppCompatActivity
{
    ImageView ProfileIcon, PostIcon;
    CustomAdapter adapter;
    ListView PostsList;
    ArrayList<Post> post;
    JSONArray user = null;
    String postID;

    //URL to get JSON Array
    private static String url = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getAllPosts?SubscriberID=";
    private static String likeURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/createLike";
    private static String likeDeleteURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/deleteLike?SubscriberID=";

    //JSON Node Names
    private static final String TAG_PostID = "PostID";
    private static final String TAG_SubscriberID = "SubscriberID";
    private static final String TAG_SubscriberName = "SubscriberName";
    private static final String TAG_ImagePresence = "ImagePresence";
    private static final String TAG_ImageAlbumID = "ImageAlbumID";
    private static final String TAG_ReviewPresence = "ReviewPresence";
    private static final String TAG_CheckinPresence = "CheckinPresence";
    private static final String TAG_Privacy = "Privacy";
    private static final String TAG_TimeStamp = "TimeStamp";
    private static final String TAG_PostDescription = "PostDescription";
    private static final String TAG_ImageString = "ImageString";
    private static final String TAG_CommentsCount = "CommentsCount";
    private static final String TAG_LikesCount = "LikesCount";
    private static final String TAG_DisplayPicture = "DisplayPicture";
    private static final String TAG_CurrentUsersLike = "CurrentUsersLike";

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

        //Posts List Event Listener
        PostsList = (ListView) findViewById(R.id.Posts_ListView);

        new JSONParse().execute();
    }

    @Override
    public void onRestart()
    {
        super.onRestart();

        //Initializing Posts Array
        post = new ArrayList<>();

        //Posts List Event Listener
        PostsList = (ListView) findViewById(R.id.Posts_ListView);

        new JSONParse().execute();
    }

    View.OnClickListener clickListener = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            if (v.equals(ProfileIcon))
            {
                if(GlobalData.Type.equals("Subscriber"))
                {
                    startActivity(new Intent(NewsFeed.this, SubscriberProfileScreen.class));
                }
                else
                {
                    startActivity(new Intent(NewsFeed.this, RestaurantProfile.class));
                }
            }
            else if (v.equals(PostIcon))
            {
                startActivity(new Intent(NewsFeed.this, WriteTextPost.class));
            }
        }
    };

    public void LikePost(String postID)
    {
        this.postID = postID;
        new JSONParseLikePost().execute();
    }

    public void LikeDelete(String postID)
    {
        likeDeleteURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/deleteLike?SubscriberID=" + GlobalData.SubscriberID + "&PostID=" + postID;
        new JSONParseLikeDelete().execute();
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject>
    {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewsFeed.this);
            pDialog.setMessage("Loading Posts");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args)
        {
            JSONParser jParser = new JSONParser();

            url = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getAllPosts?SubscriberID=" + GlobalData.SubscriberID + "&Scope=NewsFeed";
            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url, "GET", null, null, null, null, null);
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
                    String SubscriberName = fetchedData.getString(TAG_SubscriberName);
                    String ImagePresence = fetchedData.getString(TAG_ImagePresence);
                    String ImageAlbumID = fetchedData.getString(TAG_ImageAlbumID);
                    String ReviewPresence = fetchedData.getString(TAG_ReviewPresence);
                    String CheckinPresence = fetchedData.getString(TAG_CheckinPresence);
                    String Privacy = fetchedData.getString(TAG_Privacy);
                    String Timestamp = fetchedData.getString(TAG_TimeStamp);
                    String PostDescription = fetchedData.getString(TAG_PostDescription);
                    String ImageString = fetchedData.getString(TAG_ImageString);
                    String CommentsCount = fetchedData.getString(TAG_CommentsCount);
                    String LikesCount = fetchedData.getString(TAG_LikesCount);
                    String DisplayPicture = fetchedData.getString(TAG_DisplayPicture);
                    String CurrentUsersLike = fetchedData.getString(TAG_CurrentUsersLike);

                    post.add(new Post(PostID, SubscriberID, SubscriberName, ImagePresence, ImageAlbumID, ReviewPresence, CheckinPresence, Privacy, Timestamp, PostDescription, ImageString, CommentsCount, LikesCount, DisplayPicture, CurrentUsersLike));
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

    private class JSONParseLikePost extends AsyncTask<String, String, JSONObject>
    {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewsFeed.this);
            pDialog.setMessage("Liking Post");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args)
        {
            JSONParser jParser = new JSONParser();

            Like like = new Like();
            like.setSubscriberID(GlobalData.SubscriberID);
            like.setPostID(postID);
            like.setTimeStamp(new SimpleDateFormat("d-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime()).toString());

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(likeURL, "POST", null, null, null, like, null);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json)
        {
            pDialog.dismiss();
        }
    }

    private class JSONParseLikeDelete extends AsyncTask<String, String, JSONObject>
    {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewsFeed.this);
            pDialog.setMessage("Unliking Post");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args)
        {
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(likeDeleteURL, "POST", null, null, null, null, null);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json)
        {
            pDialog.dismiss();
        }
    }
}

package com.example.sheharyararif.foodlings;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import com.example.sheharyararif.foodlings.DatabaseModel.Post;
import com.example.sheharyararif.foodlings.ParserPackage.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommunityScreen extends AppCompatActivity
{
    CustomAdapter adapter;
    ListView PostsList;
    ArrayList<Post> post;
    JSONArray posts = null;

    //URL to get JSON Array
    private static String url = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getAllReviewsForCoummunity";

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
    private static final String TAG_MenuPresence = "MenuPresence";
    private static final String TAG_Taste = "Taste";
    private static final String TAG_Ambience = "Ambience";
    private static final String TAG_Service = "Service";
    private static final String TAG_OrderTime = "OrderTime";
    private static final String TAG_Price = "Price";
    private static final String TAG_RestaurantName = "RestaurantName";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_screen);

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

    private class JSONParse extends AsyncTask<String, String, JSONObject>
    {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(CommunityScreen.this);
            pDialog.setMessage("Loading Reviews");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args)
        {
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url, "GET", null, null, null, null, null, null, null);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json)
        {
            pDialog.dismiss();
            try
            {
                // Getting JSON Array
                posts = json.getJSONArray("Post");

                for(int i=0; i<posts.length(); i++)
                {
                    JSONObject fetchedData = posts.getJSONObject(i);

                    Post postObject = new Post();
                    postObject.setPostID(fetchedData.getString(TAG_PostID));
                    postObject.setSubscriberID(fetchedData.getString(TAG_SubscriberID));
                    postObject.setSubscriberName(fetchedData.getString(TAG_SubscriberName));
                    postObject.setImagePresence(fetchedData.getString(TAG_ImagePresence));
                    postObject.setImageAlbumID(fetchedData.getString(TAG_ImageAlbumID));
                    postObject.setReviewPresence(fetchedData.getString(TAG_ReviewPresence));
                    postObject.setCheckinPresence(fetchedData.getString(TAG_CheckinPresence));
                    postObject.setPrivacy(fetchedData.getString(TAG_Privacy));
                    postObject.setTimeStamp(fetchedData.getString(TAG_TimeStamp));
                    postObject.setPostDescription(fetchedData.getString(TAG_PostDescription));
                    postObject.setImageString(fetchedData.getString(TAG_ImageString));
                    postObject.setCommentsCount(fetchedData.getString(TAG_CommentsCount));
                    postObject.setLikesCount(fetchedData.getString(TAG_LikesCount));
                    postObject.setDisplayPicture(fetchedData.getString(TAG_DisplayPicture));
                    postObject.setCurrentUsersLike(fetchedData.getString(TAG_CurrentUsersLike));
                    postObject.setMenuPresence(fetchedData.getString(TAG_MenuPresence));
                    postObject.setTaste(fetchedData.getString(TAG_Taste));
                    postObject.setAmbience(fetchedData.getString(TAG_Ambience));
                    postObject.setService(fetchedData.getString(TAG_Service));
                    postObject.setOrderTime(fetchedData.getString(TAG_OrderTime));
                    postObject.setPrice(fetchedData.getString(TAG_Price));
                    postObject.setRestaurantName(fetchedData.getString(TAG_RestaurantName));

                    post.add(postObject);
                }

                //Adapter
                adapter = new CustomAdapter(post, CommunityScreen.this);

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

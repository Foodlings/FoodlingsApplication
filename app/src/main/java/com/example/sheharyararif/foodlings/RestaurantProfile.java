package com.example.sheharyararif.foodlings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sheharyararif.foodlings.DatabaseModel.Like;
import com.example.sheharyararif.foodlings.DatabaseModel.Post;
import com.example.sheharyararif.foodlings.DatabaseModel.SearchResult;
import com.example.sheharyararif.foodlings.DatabaseModel.Subscriber;
import com.example.sheharyararif.foodlings.ParserPackage.JSONParser;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RestaurantProfile extends AppCompatActivity
{
    CustomAdapter adapter;
    ListView PostsList;
    ArrayList<Post> post;
    JSONArray posts = null, dataArray = null;
    ImageView DisplayPictureImageView, CoverPhotoImageView;
    private ProgressDialog pDialog;
    TextView RestaurantName , AboutText, LocationText, TimingsText;
    String postID, SubscriberID;
    Intent intent;
    Bundle args;
    SearchResult searchResult;
    Button ReviewsButton, MenuButton;

    //URL to get JSON Array
    private static String url = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getAllPosts?SubscriberID=";
    private static String SubscriberURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getSubscriber?SubscriberID=";
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
        setContentView(R.layout.restaurant_profile_screen);

        //Initializing Posts Array
        post = new ArrayList<>();

        DisplayPictureImageView = (ImageView) findViewById(R.id.DisplayPictureImageView);
        CoverPhotoImageView = (ImageView) findViewById(R.id.CoverPhotoImageView);

        RestaurantName = (TextView) findViewById(R.id.RestaurantName);
        AboutText = (TextView) findViewById(R.id.AboutText);
        LocationText = (TextView) findViewById(R.id.LocationText);
        TimingsText = (TextView) findViewById(R.id.TimingsText);

        PostsList = (ListView) findViewById(R.id.Posts_ListView);

        try
        {
            intent = getIntent();
            args = intent.getBundleExtra("BUNDLE");
            searchResult = (SearchResult) args.getSerializable("searchResult");
            SubscriberID = "";
        }
        catch (Exception ex)
        { }


        if(searchResult != null)
        {
            SubscriberID = searchResult.getSubscriberID();
            SubscriberURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getSubscriber?SubscriberID=" + SubscriberID;
        }
        else
        {
            SubscriberURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getSubscriber?SubscriberID=" + GlobalData.SubscriberID;
        }

        ReviewsButton = (Button) findViewById(R.id.ReviewsButton);
        ReviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                intent = new Intent(RestaurantProfile.this, ReviewsScreen.class);
                args = new Bundle();
                args.putSerializable("searchResult", (Serializable) searchResult);
                intent.putExtra("BUNDLE", args);
                startActivity(intent);
            }
        });

        MenuButton = (Button) findViewById(R.id.MenuButton);
        MenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(searchResult != null)
                {
                    intent = new Intent(RestaurantProfile.this, MenuScreen.class);
                    args = new Bundle();
                    args.putSerializable("searchResult", (Serializable) searchResult);
                    intent.putExtra("BUNDLE", args);
                    startActivity(intent);
                }
                else
                {
                    startActivity(new Intent(RestaurantProfile.this, MenuScreen.class));
                }
            }
        });

        new JSONParseSubscriber().execute();
    }

//    @Override
//    public void onRestart()
//    {
//        super.onRestart();
//
//        //Initializing Posts Array
//        post = new ArrayList<>();
//
//        //Posts List Event Listener
//        PostsList = (ListView) findViewById(R.id.Posts_ListView);
//
//        if(!SubscriberID.equals(""))
//        {
//            SubscriberURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getSubscriber?SubscriberID=" + SubscriberID;
//        }
//        else
//        {
//            SubscriberURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getSubscriber?SubscriberID=" + GlobalData.SubscriberID;
//        }
//
//        new JSONParseSubscriber().execute();
//    }

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
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog.setMessage("Loading Posts");
        }

        @Override
        protected JSONObject doInBackground(String... args)
        {
            JSONParser jParser = new JSONParser();

            if(searchResult != null)
            {
                SubscriberID = searchResult.getSubscriberID();
                url = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getAllPosts?SubscriberID=" + SubscriberID + "&Scope=Profile";
            }
            else
            {
                url = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getAllPosts?SubscriberID=" + GlobalData.SubscriberID + "&Scope=Profile";
            }

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url, "GET", null, null, null, null, null, null);
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
                adapter = new CustomAdapter(post, RestaurantProfile.this);

                //ListView
                PostsList.setAdapter(adapter);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }
    }

    private class JSONParseSubscriber extends AsyncTask<String, String, JSONObject>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(RestaurantProfile.this);
            pDialog.setMessage("Loading Profile");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args)
        {
            JSONParser jsonParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jsonParser.getJSONFromUrl(SubscriberURL, "GET", null, null, null, null, null, null);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json)
        {
            try
            {
                // Getting JSON Array
                dataArray = json.getJSONArray("Subscriber");
                JSONObject c = dataArray.getJSONObject(0);

                // Storing  JSON item in a Variable
                String RestName = c.getString("SubscriberName");
                String About = c.getString("Bio");
                String Location = c.getString("Address");
                String Timing = c.getString("Timing");
                String DisplayPicture = c.getString("DisplayPicture");
                String CoverPhoto = c.getString("CoverPhoto");

                RestaurantName.setText(RestName);
                AboutText.setText(About);

                if(c.getString("Type").equals("Restaurant")){
                    LocationText.setText(Location);
                    TimingsText.setText(Timing);
                }

                if (!DisplayPicture.equals("none")) {
                    Picasso.with(RestaurantProfile.this).load(DisplayPicture).into(DisplayPictureImageView);
                }

                if (!CoverPhoto.equals("none")) {
                    Picasso.with(RestaurantProfile.this).load(CoverPhoto).into(CoverPhotoImageView);
                }

                new JSONParse().execute();
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
            pDialog = new ProgressDialog(RestaurantProfile.this);
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
            JSONObject json = jParser.getJSONFromUrl(likeURL, "POST", null, null, null, like, null, null);
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
            pDialog = new ProgressDialog(RestaurantProfile.this);
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
            JSONObject json = jParser.getJSONFromUrl(likeDeleteURL, "POST", null, null, null, null, null, null);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json)
        {
            pDialog.dismiss();
        }
    }
}



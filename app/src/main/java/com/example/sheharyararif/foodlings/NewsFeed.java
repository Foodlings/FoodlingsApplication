package com.example.sheharyararif.foodlings;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NewsFeed extends AppCompatActivity
{
    ImageView ProfileIcon, PostIcon, FriendsIcon, SearchIcon, NewsFeedIcon, PictureIcon;
    CustomAdapter adapter;
    ListView PostsList;
    ArrayList<Post> post;
    JSONArray posts = null;
    String postID;
    String encodedImage = "none", TimeStamp;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    boolean restartCheck = true;

    //URL to get JSON Array
    private static String url = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getAllPosts?SubscriberID=";
    private static String likeURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/createLike";
    private static String likeDeleteURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/deleteLike?SubscriberID=";
    private static String postURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/createPost/";

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
        setContentView(R.layout.news_feed_screen);

        FriendsIcon = (ImageView) findViewById(R.id.FriendsIcon);
        if(GlobalData.Type.equals("Restaurant")){
            FriendsIcon.setImageResource(R.drawable.reviews_icon);
        }
        FriendsIcon.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                if(GlobalData.Type.equals("Restaurant")){
                    startActivity(new Intent(NewsFeed.this, ReviewsScreen.class));
                }
                else
                {
                    startActivity(new Intent(NewsFeed.this, FriendsScreen.class));
                }
            }
        });

        //Profile Icon
        ProfileIcon = (ImageView) findViewById(R.id.ProfileIcon);
        ProfileIcon.setOnClickListener(clickListener);


        NewsFeedIcon = (ImageView) findViewById(R.id.NewsFeedIcon);
        NewsFeedIcon.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                onRestart();
            }
        });

        SearchIcon = (ImageView) findViewById(R.id.SearchIcon);
        SearchIcon.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(new Intent(NewsFeed.this, SearchScreen.class));
            }
        });

        PictureIcon = (ImageView) findViewById(R.id.PictureIcon);
        PictureIcon.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                TimeStamp = new SimpleDateFormat("d-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime()).toString();
                restartCheck = false;
                selectImage();
            }
        });


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

        if(restartCheck){
            //Initializing Posts Array
            post = new ArrayList<>();

            //Posts List Event Listener
            PostsList = (ListView) findViewById(R.id.Posts_ListView);

            new JSONParse().execute();
        }

        restartCheck = true;
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
            JSONObject json = jParser.getJSONFromUrl(likeURL, "POST", null, null, null, like, null, null, null);
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
            JSONObject json = jParser.getJSONFromUrl(likeDeleteURL, "POST", null, null, null, null, null, null, null);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json)
        {
            pDialog.dismiss();
        }
    }

    private class JSONParseImage extends AsyncTask<String, String, JSONObject>
    {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewsFeed.this);
            pDialog.setMessage("Publishing Post");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args)
        {
            JSONParser jParser = new JSONParser();

            Post postObject = new Post("0",GlobalData.SubscriberID, "SubscriberName","0","0","0","0","Public",TimeStamp,"none",encodedImage, "0", "0", "", "", "0");

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(postURL, "POST", postObject, null, null, null, null, null, null);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json)
        {
            pDialog.dismiss();
            Toast.makeText(NewsFeed.this, "Post Published",
                    Toast.LENGTH_SHORT).show();
            restartCheck = true;
            onRestart();
        }
    }

    private void selectImage()
    {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(NewsFeed.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(NewsFeed.this);
                if (items[item].equals("Take Photo")) {
                    userChoosenTask="Take Photo";
                    if(result)
                        cameraIntent();
                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask="Choose from Library";
                    if(result)
                        galleryIntent();
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data)
    {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] myByteArray = baos.toByteArray();
        encodedImage = Base64.encodeToString(myByteArray, Base64.DEFAULT);
        new JSONParseImage().execute();
    }

    private void onCaptureImageResult(Intent data)
    {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        byte[] b = bytes.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        new JSONParseImage().execute();
    }
}

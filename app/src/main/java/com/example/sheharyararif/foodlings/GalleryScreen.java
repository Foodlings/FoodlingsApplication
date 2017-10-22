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
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sheharyararif.foodlings.DatabaseModel.Post;
import com.example.sheharyararif.foodlings.DatabaseModel.SearchResult;
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

public class GalleryScreen extends AppCompatActivity
{
    GridView GalleryGridView;
    GalleryGridAdapter adapter;
    ArrayList<Post> post;
    JSONArray posts = null;
    Button AddGalleryButton;
    String encodedImage = "none", TimeStamp;
    Post postObject;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    String SubscriberID;
    Intent intent;
    Bundle args;
    SearchResult searchResult;
    TextView GalleryResultLabel;

    //URL to get JSON Array
    private static String url = "http://foodlingsapi.azurewebsites.net//api/FoodlingDatabase/getGallery?SubscriberID=";
    private static String postGalleryURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/createPost/";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_screen);

        //Initializing Posts Array
        post = new ArrayList<>();

        //Initializing GridView
        GalleryGridView = (GridView) findViewById(R.id.GalleryGridView);

        GalleryResultLabel = (TextView) findViewById(R.id.GalleryResultLabel);

        //AddMenuButton Event
        AddGalleryButton = (Button)findViewById(R.id.AddGalleryButton);
        AddGalleryButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                TimeStamp = new SimpleDateFormat("d-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime()).toString();
                selectImage();
            }
        });

        try
        {
            intent = getIntent();
            args = intent.getBundleExtra("BUNDLE");
            searchResult = (SearchResult) args.getSerializable("searchResult");
            SubscriberID = searchResult.getSubscriberID();
            url = "http://foodlingsapi.azurewebsites.net//api/FoodlingDatabase/getGallery?SubscriberID=" + SubscriberID;
            AddGalleryButton.setVisibility(View.GONE);
        }
        catch (Exception ex)
        {
            url = "http://foodlingsapi.azurewebsites.net//api/FoodlingDatabase/getGallery?SubscriberID=" + GlobalData.SubscriberID;
            AddGalleryButton.setVisibility(View.VISIBLE);
        }

        new JSONParse().execute();
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject>
    {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(GalleryScreen.this);
            pDialog.setMessage("Loading Gallery");
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
                //Initializing Posts Array
                post = new ArrayList<>();

                // Getting JSON Array
                posts = json.getJSONArray("Post");

                for(int i=0; i<posts.length(); i++) {
                    JSONObject fetchedData = posts.getJSONObject(i);

                    String ImageString = fetchedData.getString("ImageString");

                    Post postObject = new Post();
                    postObject.ImageString = ImageString;

                    post.add(postObject);
                }

                if(posts.length() > 0)
                {
                    if(adapter != null)
                    {
                        adapter.clearData();
                        adapter.notifyDataSetChanged();
                    }

                    adapter = new GalleryGridAdapter(GalleryScreen.this, post);
                    GalleryGridView.setAdapter(adapter);
                    GalleryResultLabel.setText("");
                }
                else
                {
                    GalleryResultLabel.setText("No Images Found in the Gallery.");
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    private class JSONParseGallery extends AsyncTask<String, String, JSONObject>
    {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(GalleryScreen.this);
            pDialog.setMessage("Adding Image");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args)
        {
            JSONParser jParser = new JSONParser();

            postObject = new Post("0",GlobalData.SubscriberID, "SubscriberName","0","0","0","0","Public",TimeStamp,"none",encodedImage, "0", "0", "", "", "0");

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(postGalleryURL, "POST", postObject, null, null, null, null, null, null);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json)
        {
            pDialog.dismiss();
            Toast.makeText(GalleryScreen.this, "Image Posted",
                    Toast.LENGTH_SHORT).show();

            new JSONParse().execute();
        }
    }

    private void selectImage()
    {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(GalleryScreen.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(GalleryScreen.this);
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
        new JSONParseGallery().execute();
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
        new JSONParseGallery().execute();
    }
}

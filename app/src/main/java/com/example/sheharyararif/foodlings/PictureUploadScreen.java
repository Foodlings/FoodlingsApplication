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
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sheharyararif.foodlings.DatabaseModel.Subscriber;
import com.example.sheharyararif.foodlings.ParserPackage.JSONParser;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PictureUploadScreen extends AppCompatActivity {

    Button NextButton;
    ImageButton ProfilePictureUpload, CoverPhotoUpload;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask, displayPicture = "none", coverPhoto = "none", buttonClicked = "none";
    Subscriber subscriberObject;

    //URL to get JSON Array
    private static String url = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/createDisplayPicture";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_upload_screen);

        ProfilePictureUpload = (ImageButton) findViewById(R.id.ProfilePictureUpload);
        ProfilePictureUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClicked = "Display Picture";
                selectImage();
            }
        });

        CoverPhotoUpload = (ImageButton) findViewById(R.id.CoverPhotoUpload);
        CoverPhotoUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClicked = "Cover Photo";
                selectImage();
            }
        });

        //Login Button's Click Event
        NextButton = (Button)findViewById(R.id.NextButton);
        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            { startActivity(new Intent(PictureUploadScreen.this, DashboardScreen.class)); }
        });
    }

    private void selectImage()
    {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(PictureUploadScreen.this);
        builder.setTitle("Add Display Picture");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(PictureUploadScreen.this);
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
            }
        }
        //ImageTextView.setImageBitmap(bm);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();

        if(buttonClicked.equals("Display Picture")){
            displayPicture = Base64.encodeToString(b, Base64.DEFAULT);
            coverPhoto = "none";
            ProfilePictureUpload.setImageBitmap(bm);
        }
        else{
            coverPhoto = Base64.encodeToString(b, Base64.DEFAULT);
            displayPicture = "none";
            CoverPhotoUpload.setImageBitmap(bm);
        }
        new JSONParse().execute();
    }

    private void onCaptureImageResult(Intent data)
    {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        //ImageTextView.setImageBitmap(thumbnail);

        byte[] b = bytes.toByteArray();

        if(buttonClicked.equals("Display Picture")){
            displayPicture = Base64.encodeToString(b, Base64.DEFAULT);
            coverPhoto = "none";
            ProfilePictureUpload.setImageBitmap(thumbnail);
        }
        else{
            coverPhoto = Base64.encodeToString(b, Base64.DEFAULT);
            displayPicture = "none";
            CoverPhotoUpload.setImageBitmap(thumbnail);
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
            pDialog = new ProgressDialog(PictureUploadScreen.this);
            if(buttonClicked.equals("Display Picture")){
                pDialog.setMessage("Setting Display Picture");
            }
            else{
                pDialog.setMessage("Setting Cover Photo");
            }
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args)
        {
            JSONParser jParser = new JSONParser();

            subscriberObject = new Subscriber();
            subscriberObject.setSubscriberID(GlobalData.SubscriberID);

            if(!displayPicture.equals("none")){
                subscriberObject.setDisplayPicture(displayPicture);
                subscriberObject.setCoverPhoto("DisplayPicture");
            }
            else if(!coverPhoto.equals("none")){
                subscriberObject.setCoverPhoto(coverPhoto);
                subscriberObject.setDisplayPicture("CoverPhoto");
            }

            //Posting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url, "POST", null, subscriberObject, null, null, null, null);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json)
        {
            pDialog.dismiss();
            displayPicture = "none";
            coverPhoto = "none";
        }
    }
}

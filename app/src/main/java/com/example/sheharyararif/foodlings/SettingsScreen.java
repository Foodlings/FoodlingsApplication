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
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sheharyararif.foodlings.DatabaseModel.Subscriber;
import com.example.sheharyararif.foodlings.ParserPackage.JSONParser;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SettingsScreen extends AppCompatActivity {

    Button LogOutButton;
    Subscriber subscriberObject;
    EditText NameTextBox, HomeTownTextBox;
    Spinner GenderDropDown, PostDropDown;
    ImageButton ProfilePictureButton, CoverPhotoButton;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask, displayPicture = "none", coverPhoto = "none", buttonClicked = "none";

    //URL to get JSON Array
    private static String urlSettings = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/updateSubscriber";
    private static String url = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/createDisplayPicture";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);

        NameTextBox = (EditText) findViewById(R.id.NameTextBox);
        HomeTownTextBox = (EditText) findViewById(R.id.HomeTownTextBox);

        GenderDropDown = (Spinner) findViewById(R.id.GenderDropDown);
        PostDropDown = (Spinner) findViewById(R.id.PostDropDown);

        ProfilePictureButton = (ImageButton) findViewById(R.id.ProfilePictureButton);
        ProfilePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClicked = "Display Picture";
                selectImage();
            }
        });

        CoverPhotoButton = (ImageButton) findViewById(R.id.CoverPhotoButton);
        CoverPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClicked = "Cover Photo";
                selectImage();
            }
        });

        LogOutButton = (Button) findViewById(R.id.LogOutButton);
        LogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new AlertDialog.Builder(SettingsScreen.this)
                        .setTitle("Log-out Confirmation")
                        .setMessage("Do you want to Log-out?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(SettingsScreen.this, "Logged-out", Toast.LENGTH_SHORT).show();
                                Intent intents = new Intent(SettingsScreen.this, HomeScreen.class);
                                intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
                                        | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intents);
                                finish();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        subscriberObject = new Subscriber();

        if(NameTextBox.getText().toString().equals("")){
            subscriberObject.setSubscriberName("");
        }
        else {
            subscriberObject.setSubscriberName(NameTextBox.getText().toString());
        }

        if(HomeTownTextBox.getText().toString().equals("")){
            subscriberObject.setAddress("");
        }
        else {
            subscriberObject.setAddress(HomeTownTextBox.getText().toString());
        }

        subscriberObject.setSubscriberID(GlobalData.SubscriberID);
        subscriberObject.setGender(GenderDropDown.getSelectedItem().toString());
        subscriberObject.setPassword("");
        subscriberObject.setType("");
        subscriberObject.setEmail("");
        subscriberObject.setPhoneNumber("");
        subscriberObject.setBio("");
        subscriberObject.setDoB("");
        new JSONParseSettings().execute();
    }

    private class JSONParseSettings extends AsyncTask<String, String, JSONObject>
    {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(SettingsScreen.this);
            pDialog.setMessage("Saving Settings");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args)
        {
            JSONParser jParser = new JSONParser();

            //Posting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(urlSettings, "POST", null, subscriberObject, null, null, null, null, null);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json)
        {
            pDialog.dismiss();
            Toast.makeText(SettingsScreen.this, "Settings Saved", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void selectImage()
    {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsScreen.this);
        builder.setTitle("Change Display Picture");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(SettingsScreen.this);
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

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();

        if(buttonClicked.equals("Display Picture")){
            displayPicture = Base64.encodeToString(b, Base64.DEFAULT);
            coverPhoto = "none";
        }
        else{
            coverPhoto = Base64.encodeToString(b, Base64.DEFAULT);
            displayPicture = "none";
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
        }
        else{
            coverPhoto = Base64.encodeToString(b, Base64.DEFAULT);
            displayPicture = "none";
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
            pDialog = new ProgressDialog(SettingsScreen.this);
            if(buttonClicked.equals("Display Picture")){
                pDialog.setMessage("Changing Display Picture");
            }
            else{
                pDialog.setMessage("Changing Cover Photo");
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
            JSONObject json = jParser.getJSONFromUrl(url, "POST", null, subscriberObject, null, null, null, null, null);
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

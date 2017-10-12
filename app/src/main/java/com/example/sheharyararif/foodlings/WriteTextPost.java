package com.example.sheharyararif.foodlings;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.sheharyararif.foodlings.DatabaseModel.Post;
import com.example.sheharyararif.foodlings.ParserPackage.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class WriteTextPost extends AppCompatActivity
{
    EditText PostTextBox;
    Button SubmitButton;
    String PostDescription, encodedImage = "none", TimeStamp;
    Post postObject;
    ImageView PostImage;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private ImageButton PostImageButton;
    private String userChoosenTask;

    //URL to get JSON Array
    private static String url = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/createPost/";

    JSONArray post = null;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_text_post_screen);

        //Post TextBox
        PostTextBox = (EditText) findViewById(R.id.PostTextBox);
        PostTextBox.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        //Post Image Button Event
        PostImageButton = (ImageButton) findViewById(R.id.PostImageButton);
        PostImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        PostImage = (ImageView) findViewById(R.id.PostImage);

        //Submit Button Event
        SubmitButton = (Button)findViewById(R.id.SubmitButton);
        SubmitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                PostDescription = PostTextBox.getText().toString();
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
            pDialog = new ProgressDialog(WriteTextPost.this);
            pDialog.setMessage("Publishing Post");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args)
        {
            JSONParser jParser = new JSONParser();

            postObject = new Post("0",GlobalData.SubscriberID, "SubscriberName","0","0","0","0","Public",TimeStamp,PostDescription,encodedImage, "0", "0", "", "");

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url, "POST", postObject, null, null, null, null);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json)
        {
            pDialog.dismiss();
            Toast.makeText(WriteTextPost.this, "Post Published",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void selectImage()
    {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(WriteTextPost.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(WriteTextPost.this);
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
        bm.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] myByteArray = baos.toByteArray();
        encodedImage = Base64.encodeToString(myByteArray, Base64.DEFAULT);
        PostImage.setImageBitmap(bm);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        //ImageTextView.setImageBitmap(thumbnail);

        byte[] b = bytes.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        PostImage.setImageBitmap(thumbnail);
    }
}






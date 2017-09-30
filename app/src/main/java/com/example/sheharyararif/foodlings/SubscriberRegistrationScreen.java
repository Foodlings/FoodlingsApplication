package com.example.sheharyararif.foodlings;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.sheharyararif.foodlings.ParserPackage.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SubscriberRegistrationScreen extends AppCompatActivity {

    Button FacebookRegisterButton, RegisterButton;
    EditText SubscriberNameTextBox, UserNameTextBox, EmailTextBox, PasswordTextBox, AboutTextBox;
    JSONArray post = null, dataArray = null;
    private ProgressDialog pDialog;

    //URL to get JSON Array
    private static String url = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/createSubscriber?SubscriberName=";
    private static String getSubscriberURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getSubscriber/?SubscriberName=EmailNone&SubscriberEmail=";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscriber_registration_screen);

        SubscriberNameTextBox = (EditText) findViewById(R.id.SubscriberNameTextBox);
        UserNameTextBox = (EditText) findViewById(R.id.UserNameTextBox);
        EmailTextBox = (EditText) findViewById(R.id.EmailTextBox);
        PasswordTextBox = (EditText) findViewById(R.id.PasswordTextBox);
        AboutTextBox = (EditText) findViewById(R.id.AboutTextBox);

        //Register Button's Click Event
        RegisterButton = (Button)findViewById(R.id.RegisterButton);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                url = url + SubscriberNameTextBox.getText().toString() + "&Password=" + PasswordTextBox.getText().toString() + "&Type=Subscriber&Email=" + EmailTextBox.getText().toString() + "&PhoneNumber=Phone&Bio=" + AboutTextBox.getText().toString() + "&Gender=Gender&DoB=Date";
                new JSONParse().execute();
            }
        });

        //Setting Facebook Button's Height
        FacebookRegisterButton = (Button)findViewById(R.id.FacebookRegisterButton);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) FacebookRegisterButton.getLayoutParams();
        if(getScreenHeight(this)>800)
        {
            params.height = 110;
        }
        else
        {
            params.height = 50;
        }
        FacebookRegisterButton.setLayoutParams(params);
    }

    private static int getScreenHeight(Context context)
    {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int height = metrics.heightPixels;
        return height;
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(SubscriberRegistrationScreen.this);
            pDialog.setMessage("Registering Subscriber");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args)
        {
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url, "POST", null, null);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json)
        {
            getSubscriberURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getSubscriber/?SubscriberName=EmailNone&SubscriberEmail=" + EmailTextBox.getText().toString();
            new JSONSubscriberParse().execute();
        }
    }

    private class JSONSubscriberParse extends AsyncTask<String, String, JSONObject>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args)
        {
            JSONParser jsonParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jsonParser.getJSONFromUrl(getSubscriberURL, "GET", null, null);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json)
        {
            pDialog.dismiss();
            getSubscriberURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getSubscriber/?SubscriberName=EmailNone&SubscriberEmail=";
            try
            {
                Toast.makeText(SubscriberRegistrationScreen.this, "Subscriber Registered Successfully.",
                        Toast.LENGTH_LONG).show();

                // Getting JSON Array
                dataArray = json.getJSONArray("Subscriber");
                JSONObject c = dataArray.getJSONObject(0);

                GlobalData.SubscriberID = c.getString("SubscriberID");
                startActivity(new Intent(SubscriberRegistrationScreen.this, PictureUploadScreen.class));
            }
            catch (JSONException e)
            {
                Toast.makeText(SubscriberRegistrationScreen.this, "Something went wrong..",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }
}

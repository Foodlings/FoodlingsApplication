package com.example.sheharyararif.foodlings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sheharyararif.foodlings.DatabaseModel.Subscriber;
import com.example.sheharyararif.foodlings.ParserPackage.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RestaurantRegistrationScreen extends AppCompatActivity {

    Button RegisterButton;
    EditText RestaurantNameTextBox, EmailTextBox, PasswordTextBox, LocationTextBox, AboutTextBox;
    JSONArray dataArray = null;
    private ProgressDialog pDialog;
    Subscriber restaurantProfile;

    //URL to get JSON Array
    private static String emailValidationURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/validateEmail?SubscriberEmail=";
    private static String url = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/createRestaurant";
    private static String getSubscriberURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getSubscriber/?SubscriberName=EmailNone&SubscriberEmail=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_registration_screen);

        RestaurantNameTextBox = (EditText) findViewById(R.id.RestaurantNameTextBox);
        EmailTextBox = (EditText) findViewById(R.id.EmailTextBox);
        PasswordTextBox = (EditText) findViewById(R.id.PasswordTextBox);
        LocationTextBox = (EditText) findViewById(R.id.LocationTextBox);
        AboutTextBox = (EditText) findViewById(R.id.AboutTextBox);

        //Register Button's Click Event
        RegisterButton = (Button)findViewById(R.id.RegisterButton);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                restaurantProfile = new Subscriber();
                restaurantProfile.setSubscriberName(RestaurantNameTextBox.getText().toString());
                restaurantProfile.setPassword(PasswordTextBox.getText().toString());
                restaurantProfile.setType("Restaurant");
                restaurantProfile.setEmail(EmailTextBox.getText().toString());
                restaurantProfile.setPhoneNumber("Phone");
                restaurantProfile.setBio(AboutTextBox.getText().toString());
                restaurantProfile.setAddress(LocationTextBox.getText().toString());
                restaurantProfile.setTiming("Time");
                restaurantProfile.setCategory("Category");

                emailValidationURL = emailValidationURL + EmailTextBox.getText().toString();
                new JSONEmailValidator().execute();
            }
        });
    }

    private class JSONEmailValidator extends AsyncTask<String, String, JSONObject>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(RestaurantRegistrationScreen.this);
            pDialog.setMessage("Registering Restaurant");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args)
        {
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(emailValidationURL, "GET", null, null, null, null, null);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json)
        {
            emailValidationURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/validateEmail?SubscriberEmail=";
            try
            {
                // Getting JSON Array
                dataArray = json.getJSONArray("Subscriber");
                JSONObject c = dataArray.getJSONObject(0);

                String emailValidator = c.getString("Email");

                if(emailValidator.equals("Available"))
                {
                    new JSONParse().execute();
                }
                else
                {
                    pDialog.dismiss();
                    Toast.makeText(RestaurantRegistrationScreen.this, "Email Address already registered.",
                            Toast.LENGTH_LONG).show();
                }
            }
            catch (JSONException e)
            {
                pDialog.dismiss();
                url = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/createRestaurant?SubscriberName=";
                e.printStackTrace();
            }
        }
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args)
        {
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url, "POST", null, restaurantProfile, null, null, null);
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
            JSONObject json = jsonParser.getJSONFromUrl(getSubscriberURL, "GET", null, null, null, null, null);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json)
        {
            pDialog.dismiss();
            getSubscriberURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getSubscriber/?SubscriberName=EmailNone&SubscriberEmail=";
            try
            {
                Toast.makeText(RestaurantRegistrationScreen.this, "Restaurant Registered Successfully.",
                        Toast.LENGTH_LONG).show();

                // Getting JSON Array
                dataArray = json.getJSONArray("Subscriber");
                JSONObject c = dataArray.getJSONObject(0);

                GlobalData.SubscriberID = c.getString("SubscriberID");
                GlobalData.Type = c.getString("Type");
                startActivity(new Intent(RestaurantRegistrationScreen.this, PictureUploadScreen.class));
            }
            catch (JSONException e)
            {
                Toast.makeText(RestaurantRegistrationScreen.this, "Something went wrong..",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }
}

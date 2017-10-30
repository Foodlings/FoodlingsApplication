package com.example.sheharyararif.foodlings;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sheharyararif.foodlings.DatabaseModel.Subscriber;
import com.example.sheharyararif.foodlings.ParserPackage.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SubscriberRegistrationScreen extends AppCompatActivity {

    Button FacebookRegisterButton, RegisterButton;
    EditText SubscriberNameTextBox, EmailTextBox, PasswordTextBox, AboutTextBox, DoBTextBox;
    Spinner GenderTextBox;
    JSONArray post = null, dataArray = null;
    private ProgressDialog pDialog;
    Subscriber subscriber;
    Calendar myCalendar;

    //URL to get JSON Array
    private static String emailValidationURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/validateEmail?SubscriberEmail=";
    private static String url = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/createSubscriber";
    private static String getSubscriberURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getSubscriber/?SubscriberName=EmailNone&SubscriberEmail=";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscriber_registration_screen);

        myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        SubscriberNameTextBox = (EditText) findViewById(R.id.SubscriberNameTextBox);
        GenderTextBox = (Spinner) findViewById(R.id.GenderTextBox);
        EmailTextBox = (EditText) findViewById(R.id.EmailTextBox);
        PasswordTextBox = (EditText) findViewById(R.id.PasswordTextBox);
        AboutTextBox = (EditText) findViewById(R.id.AboutTextBox);
        DoBTextBox = (EditText) findViewById(R.id.DoBTextBox);
        DoBTextBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SubscriberRegistrationScreen.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //Register Button's Click Event
        RegisterButton = (Button)findViewById(R.id.RegisterButton);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(!EmailTextBox.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+"))
                {
                    EmailTextBox.setError("Invalid Email Format");
                    return;
                }

                subscriber = new Subscriber();
                subscriber.setSubscriberName(SubscriberNameTextBox.getText().toString());
                subscriber.setPassword(PasswordTextBox.getText().toString());
                subscriber.setType("Subscriber");
                subscriber.setEmail(EmailTextBox.getText().toString());
                subscriber.setPhoneNumber("Phone");
                subscriber.setBio(AboutTextBox.getText().toString());
                subscriber.setGender(GenderTextBox.getSelectedItem().toString());
                subscriber.setDoB(DoBTextBox.getText().toString());

                emailValidationURL = emailValidationURL + EmailTextBox.getText().toString();
                new JSONEmailValidator().execute();
            }
        });
    }

    private void updateLabel()
    {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        DoBTextBox.setText(sdf.format(myCalendar.getTime()));
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

    private class JSONEmailValidator extends AsyncTask<String, String, JSONObject>
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
            JSONObject json = jParser.getJSONFromUrl(emailValidationURL, "GET", null, null, null, null, null, null, null);
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
                    Toast.makeText(SubscriberRegistrationScreen.this, "Email Address already registered.",
                            Toast.LENGTH_LONG).show();
                }
            }
            catch (JSONException e)
            {
                pDialog.dismiss();
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
            JSONObject json = jParser.getJSONFromUrl(url, "POST", null, subscriber, null, null, null, null, null);
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
            JSONObject json = jsonParser.getJSONFromUrl(getSubscriberURL, "GET", null, null, null, null, null, null, null);
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

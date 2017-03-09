package com.example.sheharyararif.foodlings;

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
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.sheharyararif.foodlings.ParserPackage.JSONParser;

public class LoginScreen extends AppCompatActivity {

    Button SigninButton;
    EditText EmailTextBox, PasswordTextBox;

    JSONArray dataArray = null;

    //URL to get JSON Array
    private static String url = "http://foodlingswebapi.azurewebsites.net/api/FoodlingDatabase/getSubscriber/?SubscriberName=EmailNone&SubscriberEmail=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);


        //Setting Main Block's Top Margin
        LinearLayout MainBlock = (LinearLayout) findViewById(R.id.MainBlock);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        if(getScreenHeight(this)>800)
        {
            params.setMargins(0,500,0,0);
        }
        else
        {
            params.setMargins(0,200,0,0);
        }
        MainBlock.setLayoutParams(params);


        //TextBox initialization
        EmailTextBox = (EditText) findViewById(R.id.EmailTextBox);
        PasswordTextBox = (EditText) findViewById(R.id.PasswordTextBox);

        //Signin Button's Click Event
        SigninButton = (Button)findViewById(R.id.SigninButton);
        SigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                url = url + EmailTextBox.getText();
                new JSONParse().execute();
            }
        });

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
        { super.onPreExecute(); }

        @Override
        protected JSONObject doInBackground(String... args)
        {
            JSONParser jsonParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jsonParser.getJSONFromUrl(url, "GET");
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
                String EmailAddress = c.getString("Email");
                String Password = c.getString("Password");


                if((EmailTextBox.getText().toString().equals(EmailAddress))&&(PasswordTextBox.getText().toString().equals(Password)))
                {
                    startActivity(new Intent(LoginScreen.this, DashboardScreen.class));
                }
            }
            catch (JSONException e)
            { e.printStackTrace(); }
        }
    }
}



package com.example.sheharyararif.foodlings;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SubscriberRegistrationScreen extends AppCompatActivity {

    Button FacebookRegisterButton, RegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subscriber_registration_screen);

        //Register Button's Click Event
        RegisterButton = (Button)findViewById(R.id.RegisterButton);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            { startActivity(new Intent(SubscriberRegistrationScreen.this, PictureUploadScreen.class)); }
        });

        //Setting Facebook Button's Height
        FacebookRegisterButton = (Button)findViewById(R.id.FacebookRegisterButton);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) FacebookRegisterButton.getLayoutParams();
        if(getScreenHeight(this)>800)
        {
            params.height = 150;
        }
        else
        {
            params.height = 80;
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
}

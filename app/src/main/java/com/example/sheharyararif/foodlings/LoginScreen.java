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
import android.widget.RelativeLayout.LayoutParams;

public class LoginScreen extends AppCompatActivity {

    Button SigninButton;

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


        //Signin Button's Click Event
        SigninButton = (Button)findViewById(R.id.SigninButton);
        SigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            { startActivity(new Intent(LoginScreen.this, DashboardScreen.class)); }
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
}

package com.example.sheharyararif.foodlings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PictureUploadScreen extends AppCompatActivity {

    Button NextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_upload_screen);

        //Login Button's Click Event
        NextButton = (Button)findViewById(R.id.NextButton);
        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            { startActivity(new Intent(PictureUploadScreen.this, DashboardScreen.class)); }
        });
    }
}

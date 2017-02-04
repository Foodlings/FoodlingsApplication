package com.example.sheharyararif.foodlings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RegistrationScreen extends AppCompatActivity {

    Button RestaurantButton, SubscriberButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_screen);

        //Restaurant Registration Button's Click Event
        RestaurantButton = (Button)findViewById(R.id.RestaurantButton);
        RestaurantButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            { startActivity(new Intent(RegistrationScreen.this, RestaurantRegistrationScreen.class)); }
        });

        //Subscriber Registration Button's Click Event
        SubscriberButton = (Button)findViewById(R.id.SubscriberButton);
        SubscriberButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            { startActivity(new Intent(RegistrationScreen.this, SubscriberRegistrationScreen.class)); }
        });

    }
}

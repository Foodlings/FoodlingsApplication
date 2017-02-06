package com.example.sheharyararif.foodlings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PortalScreen extends AppCompatActivity {

    Button AdvanceSearchButton, CategoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.portal_screen);

        //Advance Search Button's Click Event
        AdvanceSearchButton = (Button)findViewById(R.id.AdvanceSearchButton);
        AdvanceSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            { startActivity(new Intent(PortalScreen.this, AdvanceSearchScreen.class)); }
        });

        //Category Button's Click Event
        CategoryButton = (Button)findViewById(R.id.CategoryButton);
        CategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            { startActivity(new Intent(PortalScreen.this, CategoryScreen.class)); }
        });

    }
}

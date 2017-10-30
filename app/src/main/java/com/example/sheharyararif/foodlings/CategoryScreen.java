package com.example.sheharyararif.foodlings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sheharyararif.foodlings.DatabaseModel.SearchResult;

import java.io.Serializable;

public class CategoryScreen extends AppCompatActivity {

    Button FastFoodButton, ContinentalButton, CaribbeanButton;
    Intent intent;
    Bundle args;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_screen);

        FastFoodButton = (Button)findViewById(R.id.FastFoodButton);
        FastFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SearchResult searchResult = new SearchResult();
                searchResult.Name = "Fast Food";
                intent = new Intent(CategoryScreen.this, CategoryResults.class);
                args = new Bundle();
                args.putSerializable("searchResult",(Serializable)searchResult);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);
            }
        });

        ContinentalButton = (Button)findViewById(R.id.ContinentalButton);
        ContinentalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SearchResult searchResult = new SearchResult();
                searchResult.Name = "Continental";
                intent = new Intent(CategoryScreen.this, CategoryResults.class);
                args = new Bundle();
                args.putSerializable("searchResult",(Serializable)searchResult);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);
            }
        });

        CaribbeanButton = (Button)findViewById(R.id.CaribbeanButton);
        CaribbeanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SearchResult searchResult = new SearchResult();
                searchResult.Name = "Caribbean";
                intent = new Intent(CategoryScreen.this, CategoryResults.class);
                args = new Bundle();
                args.putSerializable("searchResult",(Serializable)searchResult);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);
            }
        });
    }
}

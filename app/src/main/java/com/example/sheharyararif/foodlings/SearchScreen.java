package com.example.sheharyararif.foodlings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.sheharyararif.foodlings.DatabaseModel.SearchResult;

import java.util.ArrayList;

public class SearchScreen extends AppCompatActivity
{
    SearchAdapter adapter;
    ListView SearchListView;
    Intent intent;
    Bundle args;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_screen);

        SearchListView = (ListView) findViewById(R.id.Search_ListView);

        intent = getIntent();
        args = intent.getBundleExtra("BUNDLE");
        ArrayList<SearchResult> searchResultsList = (ArrayList<SearchResult>) args.getSerializable("searchResultsList");

        if(searchResultsList.size() > 0)
        {
            adapter = new SearchAdapter(searchResultsList, SearchScreen.this);

            //ListView
            SearchListView.setAdapter(adapter);
        }

    }
}

package com.example.sheharyararif.foodlings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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

        SearchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                SearchResult searchResult = (SearchResult) adapter.getSelectedItem();
                Toast.makeText(SearchScreen.this, searchResult.getEmail(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}

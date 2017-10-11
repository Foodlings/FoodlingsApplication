package com.example.sheharyararif.foodlings;

/**
 * Created by Sheharyar Arif on 9/27/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.sheharyararif.foodlings.DatabaseModel.Comment;
import com.example.sheharyararif.foodlings.DatabaseModel.SearchResult;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import java.util.ArrayList;

public class SearchAdapter extends ArrayAdapter
{

    private ArrayList<SearchResult> dataSet;
    Context mContext;
    TextView NameTextView;
    ImageView DisplayPicture;

    public SearchAdapter(ArrayList<SearchResult> data, Context context)
    {
        super(context, R.layout.search_item, data);
        this.dataSet = data;
        this.mContext=context;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Object getItem(int i) {
        return dataSet.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.search_item, null, false);

        NameTextView = (TextView) row.findViewById(R.id.NameTextView);
        DisplayPicture = (ImageView) row.findViewById(R.id.DisplayPicture);

        NameTextView.setText(dataSet.get(i).getName());

        if (!dataSet.get(i).getDisplayPicture().equals("none"))
        {
            Picasso.with(mContext).load(dataSet.get(i).getDisplayPicture()).into(DisplayPicture);
        }

        return row;
    }
}

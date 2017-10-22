package com.example.sheharyararif.foodlings;

/**
 * Created by Sheharyar Arif on 10/22/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sheharyararif.foodlings.DatabaseModel.Comment;
import com.example.sheharyararif.foodlings.DatabaseModel.Friend;
import com.example.sheharyararif.foodlings.DatabaseModel.Subscriber;
import com.example.sheharyararif.foodlings.ParserPackage.JSONParser;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class FriendsAdapter extends ArrayAdapter
{
    private ArrayList<Friend> dataSet;
    Context mContext;
    TextView SubscriberName;
    ImageView DisplayPicture;

    public FriendsAdapter(ArrayList<Friend> data, Context context)
    {
        super(context, R.layout.friend_item, data);
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

    public void clearData() {
        dataSet.clear();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.friend_item, null, false);

        DisplayPicture = (ImageView) row.findViewById(R.id.DisplayPicture);
        SubscriberName = (TextView) row.findViewById(R.id.NameTextView);

        SubscriberName.setText(dataSet.get(i).getSubscriberName());

        if (!dataSet.get(i).getDisplayPicture().equals("none"))
        {
            Picasso.with(mContext).load(dataSet.get(i).getDisplayPicture()).into(DisplayPicture);
        }

        return row;
    }
}


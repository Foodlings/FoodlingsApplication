package com.example.sheharyararif.foodlings;

/**
 * Created by Sheharyar Arif on 9/27/2017.
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
import com.example.sheharyararif.foodlings.DatabaseModel.Subscriber;
import com.example.sheharyararif.foodlings.ParserPackage.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class CommentsAdapter extends ArrayAdapter
{

    private ArrayList<Comment> dataSet;
    Context mContext;
    TextView SubscriberName, CommentText, TimeStamp;
    JSONArray jsonArray = null;
    String Name;

    //URL to get JSON Array
    private static String subscriberURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getSubscriber?SubscriberID=";

    public CommentsAdapter(ArrayList<Comment> data, Context context)
    {
        super(context, R.layout.comment_item, data);
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.comment_item, null, false);

        SubscriberName = (TextView) row.findViewById(R.id.NameTextView);
        CommentText = (TextView) row.findViewById(R.id.CommentText);
        TimeStamp = (TextView) row.findViewById(R.id.CommentTime);

        SubscriberName.setText(dataSet.get(i).getSubscriberName());
        CommentText.setText(dataSet.get(i).getCommentText());
        TimeStamp.setText(dataSet.get(i).getTimeStamp());

        return row;
    }
}


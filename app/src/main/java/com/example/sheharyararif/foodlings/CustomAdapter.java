package com.example.sheharyararif.foodlings;

/**
 * Created by Sheharyar Arif on 6/30/2017.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sheharyararif.foodlings.DatabaseModel.Post;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter
{

    private ArrayList<Post> dataSet;
    Context mContext;
    TextView SubscriberName, Timestamp, PostDescription;
    ImageView DescriptionImage;

    public CustomAdapter(ArrayList<Post> data, Context context)
    {
        super(context, R.layout.post_item, data);
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
        View row = inflater.inflate(R.layout.post_item, null, false);

        SubscriberName = (TextView) row.findViewById(R.id.NameLabel);
        Timestamp = (TextView) row.findViewById(R.id.TimeLabel);
        PostDescription = (TextView) row.findViewById(R.id.DescriptionText);
        DescriptionImage = (ImageView) row.findViewById(R.id.DescriptionImage);

        SubscriberName.setText(dataSet.get(i).getPostID());
        Timestamp.setText(dataSet.get(i).getTimeStamp());
        PostDescription.setText(dataSet.get(i).getPostDescription());

        byte[] decodedString = Base64.decode(dataSet.get(i).getImageString(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        DescriptionImage.setImageBitmap(decodedByte);

        return row;
    }
}

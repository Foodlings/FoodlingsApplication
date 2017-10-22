package com.example.sheharyararif.foodlings;

/**
 * Created by Sheharyar Arif on 10/17/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sheharyararif.foodlings.DatabaseModel.*;

import java.io.Serializable;
import java.util.ArrayList;

public class ReviewsAdapter extends ArrayAdapter<Review> {

    private ArrayList<Review> dataSet;
    Context mContext;

    public ReviewsAdapter(ArrayList<Review> data, Context context) {
        super(context, R.layout.review_item, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Review getItem(int i) {
        return dataSet.get(i);
    }

    @Override
    public long getItemId(int i) {
        return dataSet.get(i).hashCode();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup)
    {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.review_item, null, false);

        try
        {
            TextView SubscriberName = (TextView) row.findViewById(R.id.NameTextView);
            TextView RestaurantName = (TextView) row.findViewById(R.id.RestaurantName);
            TextView Timestamp = (TextView) row.findViewById(R.id.TimeStamp);
            RatingBar RatingStars = (RatingBar) row.findViewById(R.id.RatingStars);
            final ImageView DisplayPicture = (ImageView) row.findViewById(R.id.DisplayPicture);

            SubscriberName.setText(dataSet.get(i).getSubscriberName());
            RestaurantName.setText("Restaurant: " + dataSet.get(i).getRestaurantName());
            Timestamp.setText(dataSet.get(i).getTimeStamp());

            Float averageRating = Math.round((((Float.parseFloat(dataSet.get(i).getTaste()) + Float.parseFloat(dataSet.get(i).getAmbience()) + Float.parseFloat(dataSet.get(i).getOrderTime()) + Float.parseFloat(dataSet.get(i).getService()) + Float.parseFloat(dataSet.get(i).getPrice()))/5)/0.5f))*0.5f;
            RatingStars.setRating(averageRating);

            try
            {
                if (!dataSet.get(i).DisplayPicture.equals("none"))
                {
                    Glide.with(mContext).load(dataSet.get(i).DisplayPicture).into(DisplayPicture);
                }
            }

            catch (Exception e)
            { System.out.print(e.getMessage()); }
        }
        catch (Exception e)
        { System.out.print(e.getMessage()); }

        return row;
    }
}

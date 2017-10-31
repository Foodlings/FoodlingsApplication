package com.example.sheharyararif.foodlings;

/**
 * Created by Sheharyar Arif on 6/30/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sheharyararif.foodlings.DatabaseModel.*;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Post> {

    private ArrayList<Post> dataSet;
    Context mContext;
    //TextView SubscriberName, Timestamp, PostDescription, CommentCount, LikesCount;
    //ImageView DescriptionImage, DisplayPicture;


    public CustomAdapter(ArrayList<Post> data, Context context) {
        super(context, R.layout.post_item, data);
        this.dataSet = data;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Post getItem(int i) {
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

    public void clearData() {
        dataSet.clear();
    }

//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View row = inflater.inflate(R.layout.post_item, null, false);
//
//        SubscriberName = (TextView) row.findViewById(R.id.NameLabel);
//        Timestamp = (TextView) row.findViewById(R.id.TimeLabel);
//        PostDescription = (TextView) row.findViewById(R.id.DescriptionText);
//        DescriptionImage = (ImageView) row.findViewById(R.id.DescriptionImage);
//        DisplayPicture = (ImageView) row.findViewById(R.id.DisplayPicture);
//        CommentCount = (TextView) row.findViewById(R.id.CommentCount);
//        LikesCount = (TextView) row.findViewById(R.id.LikesCount);
//
//        SubscriberName.setText(dataSet.get(i).getSubscriberName());
//        Timestamp.setText(dataSet.get(i).getTimeStamp());
//        PostDescription.setText(dataSet.get(i).getPostDescription());
//        CommentCount.setText(dataSet.get(i).getCommentsCount());
//        LikesCount.setText(dataSet.get(i).getLikesCount());
//
//        if (!dataSet.get(i).getDisplayPicture().equals("none"))
//        {
//            //decodedString = Base64.decode(dataSet.get(i).getDisplayPicture(), Base64.DEFAULT);
//            //decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//            //DisplayPicture.setImageBitmap(dataSet.get(i).getDisplayPictureBM());
//            Picasso.with(mContext).load(dataSet.get(i).getDisplayPicture()).into(DisplayPicture);
//        }
//
//        if (!dataSet.get(i).getImageString().equals("none"))
//        {
//            Picasso.with(mContext).load(dataSet.get(i).getImageString()).into(DescriptionImage);
//        }
//        else
//        {
//            DescriptionImage.getLayoutParams().height = 0;
//            DescriptionImage.requestLayout();
//        }
//
//        return row;
//    }

//    @Override
//    public View getView(final int i, View convertView, ViewGroup parent) {
//        final ViewHolder holder;
//        if (convertView == null) {
//            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.post_item, parent, false);
//            holder = new ViewHolder();
//            holder.NameLabel = (TextView) convertView.findViewById(R.id.NameLabel);
//            holder.TimeLabel = (TextView) convertView.findViewById(R.id.TimeLabel);
//            holder.DescriptionText = (TextView) convertView.findViewById(R.id.DescriptionText);
//            holder.DescriptionImage = (ImageView) convertView.findViewById(R.id.DescriptionImage);
//            holder.DisplayPicture = (ImageView) convertView.findViewById(R.id.DisplayPicture);
//            holder.CommentCount = (TextView) convertView.findViewById(R.id.CommentCount);
//            holder.LikesCount = (TextView) convertView.findViewById(R.id.LikesCount);
//            holder.CommentsImage = (ImageView) convertView.findViewById(R.id.CommentsImage);
//            holder.LikesImage = (ImageView) convertView.findViewById(R.id.LikesImage);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        final Post post = getItem(i);
//
//        TextView NameLabel = holder.NameLabel;
//        TextView TimeLabel = holder.TimeLabel;
//        TextView DescriptionText = holder.DescriptionText;
//        ImageView DescriptionImage = holder.DescriptionImage;
//        ImageView DisplayPicture = holder.DisplayPicture;
//        TextView CommentCount = holder.CommentCount;
//        TextView LikesCount = holder.LikesCount;
//        ImageView CommentsImage = holder.CommentsImage;
//        ImageView LikesImage = holder.LikesImage;
//
//        NameLabel.setText(post.getSubscriberName());
//        TimeLabel.setText(post.getTimeStamp());
//        DescriptionText.setText(post.getPostDescription());
//        CommentCount.setText(post.getCommentsCount());
//        LikesCount.setText(post.getLikesCount());
//        if (dataSet.get(i).getCurrentUsersLike().equals("Yes")) {
//            holder.LikesImage.setImageResource(R.drawable.like_red_icon);
//        } else {
//            holder.LikesImage.setImageResource(R.drawable.like_icon);
//        }
//
//        DisplayPicture.setVisibility(View.VISIBLE);
//        DescriptionImage.setVisibility(View.VISIBLE);
//
//        if (!post.getDisplayPicture().equals("none")) {
//            Glide.with(mContext).load(post.getDisplayPicture()).into(DisplayPicture);
//            //Picasso.with(mContext).load(post.getDisplayPicture()).into(DisplayPicture);
//        }
//
//        if (!post.getImageString().equals("none")) {
//            Glide.with(mContext).load(post.getImageString()).into(DescriptionImage);
//            //Picasso.with(mContext).load(post.getImageString()).into(DescriptionImage);
//        } else {
//            DescriptionImage.getLayoutParams().height = 0;
//            DescriptionImage.requestLayout();
//        }
//
//        holder.serializedIntent = new Intent(mContext, CommentsScreen.class);
//
//        CommentsImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.postData = new Post();
//                holder.postData.setPostID(dataSet.get(i).getPostID());
//                holder.serializedIntent.putExtra("PostData", holder.postData);
//                mContext.startActivity(holder.serializedIntent);
//            }
//        });
//
//        LikesImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//
//                    int likesCount;
//
//                    if (dataSet.get(i).getCurrentUsersLike().equals("Yes")) {
//                        dataSet.get(i).setCurrentUsersLike("No");
//                        holder.LikesImage.setImageResource(R.drawable.like_icon);
//                        likesCount = Integer.parseInt(holder.LikesCount.getText().toString()) - 1;
//                        if(mContext instanceof NewsFeed){
//                            ((NewsFeed)mContext).LikeDelete(dataSet.get(i).getPostID());
//                        } else if(mContext instanceof RestaurantProfile){
//                            ((RestaurantProfile)mContext).LikeDelete(dataSet.get(i).getPostID());
//                        }
//                    } else {
//                        dataSet.get(i).setCurrentUsersLike("Yes");
//                        holder.LikesImage.setImageResource(R.drawable.like_red_icon);
//                        likesCount = Integer.parseInt(holder.LikesCount.getText().toString()) + 1;
//                        if(mContext instanceof NewsFeed){
//                            ((NewsFeed)mContext).LikePost(dataSet.get(i).getPostID());
//                        } else if(mContext instanceof RestaurantProfile){
//                            ((RestaurantProfile)mContext).LikePost(dataSet.get(i).getPostID());
//                        }
//                    }
//
//                    holder.LikesCount.setText(Integer.toString(likesCount));
//                    dataSet.get(i).setLikesCount(Integer.toString(likesCount));
//            }
//        });
//
//        return convertView;
//    }

//    static class ViewHolder {
//        Post postData;
//        Intent serializedIntent;
//        public ImageView DescriptionImage, DisplayPicture, CommentsImage, LikesImage;
//        public TextView NameLabel, TimeLabel, DescriptionText, CommentCount, LikesCount;
//    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup)
    {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.post_item, null, false);

        try
        {
            TextView SubscriberName = (TextView) row.findViewById(R.id.NameLabel);
            TextView Timestamp = (TextView) row.findViewById(R.id.TimeLabel);
            TextView PostDescription = (TextView) row.findViewById(R.id.DescriptionText);
            final ImageView DescriptionImage = (ImageView) row.findViewById(R.id.DescriptionImage);
            final ImageView DisplayPicture = (ImageView) row.findViewById(R.id.DisplayPicture);
            final ImageView CommentsImage = (ImageView) row.findViewById(R.id.CommentsImage);
            final ImageView LikesImage = (ImageView) row.findViewById(R.id.LikesImage);
            final TextView CommentCount = (TextView) row.findViewById(R.id.CommentCount);
            final TextView LikesCount = (TextView) row.findViewById(R.id.LikesCount);
            RatingBar TasteRating = (RatingBar) row.findViewById(R.id.TasteRating);
            RatingBar AmbienceRating = (RatingBar) row.findViewById(R.id.AmbienceRating);
            RatingBar ServiceRating = (RatingBar) row.findViewById(R.id.ServiceRating);
            RatingBar OrderTimeRating = (RatingBar) row.findViewById(R.id.OrderTimeRating);
            RatingBar PriceRating = (RatingBar) row.findViewById(R.id.PriceRating);
            LinearLayout RatingsLayout = (LinearLayout) row.findViewById(R.id.RatingsLayout);
            RelativeLayout PostDescriptionLayout = (RelativeLayout) row.findViewById(R.id.PostDescriptionLayout);

            SubscriberName.setText(dataSet.get(i).getSubscriberName());
            Timestamp.setText(dataSet.get(i).getTimeStamp());

            if(!dataSet.get(i).getPostDescription().equals("none"))
            {
                PostDescription.setText(dataSet.get(i).getPostDescription());
            }
            else{
                PostDescription.setText("");
            }

            CommentCount.setText(dataSet.get(i).getCommentsCount());
            LikesCount.setText(dataSet.get(i).getLikesCount());
            if (dataSet.get(i).getCurrentUsersLike().equals("Yes"))
            {
                LikesImage.setImageResource(R.drawable.like_red_icon);
            }
            else
            {
                LikesImage.setImageResource(R.drawable.like_icon);
            }

            try
            {
                if (!dataSet.get(i).DisplayPicture.equals("none"))
                {
                    Glide.with(mContext).load(dataSet.get(i).DisplayPicture).into(DisplayPicture);
                }

                if(Integer.parseInt(dataSet.get(i).getReviewPresence()) == 1)
                {
                    TasteRating.setRating(Float.parseFloat(dataSet.get(i).getTaste()));
                    AmbienceRating.setRating(Float.parseFloat(dataSet.get(i).getAmbience()));
                    ServiceRating.setRating(Float.parseFloat(dataSet.get(i).getService()));
                    OrderTimeRating.setRating(Float.parseFloat(dataSet.get(i).getOrderTime()));
                    PriceRating.setRating(Float.parseFloat(dataSet.get(i).getPrice()));
                    DescriptionImage.setVisibility(View.GONE);
                    PostDescription.setGravity(Gravity.CENTER);
                    PostDescription.setTextSize(20);
                    PostDescription.setText("Restaurant: " + dataSet.get(i).getRestaurantName());
                }
                else
                {
                    RatingsLayout.setVisibility(View.GONE);

                    if (!dataSet.get(i).ImageString.equals("none"))
                    {
                        Glide.with(mContext).load(dataSet.get(i).ImageString).into(DescriptionImage);
                    }
                    else
                    {
                        PostDescriptionLayout.getLayoutParams().height = 0;
                        PostDescriptionLayout.requestLayout();
                    }
                }
            }
            catch (Exception e)
            {
                System.out.print(e.getMessage());
                System.out.print(e.getMessage());
            }


            if(!GlobalData.Type.equals("Visitor"))
            {
                CommentsImage.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        Intent serializedIntent = new Intent(mContext, CommentsScreen.class);
                        Post postData = new Post();
                        postData.setPostID(dataSet.get(i).getPostID());
                        serializedIntent.putExtra("PostData", postData);
                        mContext.startActivity(serializedIntent);
                    }
                });

                LikesImage.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        int likesCount;

                        if (dataSet.get(i).getCurrentUsersLike().equals("Yes"))
                        {
                            dataSet.get(i).setCurrentUsersLike("No");
                            LikesImage.setImageResource(R.drawable.like_icon);
                            likesCount = Integer.parseInt(LikesCount.getText().toString()) - 1;
                            if (mContext instanceof NewsFeed)
                            {
                                ((NewsFeed) mContext).LikeDelete(dataSet.get(i).getPostID());
                            }
                            else if (mContext instanceof RestaurantProfile)
                            {
                                ((RestaurantProfile) mContext).LikeDelete(dataSet.get(i).getPostID());
                            }
                            else if (mContext instanceof SubscriberProfileScreen)
                            {
                                ((SubscriberProfileScreen) mContext).LikeDelete(dataSet.get(i).getPostID());
                            }
                        }
                        else
                        {
                            dataSet.get(i).setCurrentUsersLike("Yes");
                            LikesImage.setImageResource(R.drawable.like_red_icon);
                            likesCount = Integer.parseInt(LikesCount.getText().toString()) + 1;
                            if (mContext instanceof NewsFeed)
                            {
                                ((NewsFeed) mContext).LikePost(dataSet.get(i).getPostID());
                            }
                            else if (mContext instanceof RestaurantProfile)
                            {
                                ((RestaurantProfile) mContext).LikePost(dataSet.get(i).getPostID());
                            }
                            else if (mContext instanceof SubscriberProfileScreen)
                            {
                                ((SubscriberProfileScreen) mContext).LikePost(dataSet.get(i).getPostID());
                            }
                        }

                        LikesCount.setText(Integer.toString(likesCount));
                        dataSet.get(i).setLikesCount(Integer.toString(likesCount));
                    }
                });
            }
        }
        catch (Exception e)
        {
            System.out.print(e.getMessage());
        }

        return row;
    }
}

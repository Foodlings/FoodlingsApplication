package com.example.sheharyararif.foodlings;

/**
 * Created by Sheharyar Arif on 6/30/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sheharyararif.foodlings.DatabaseModel.Like;
import com.example.sheharyararif.foodlings.DatabaseModel.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Post> {

    private ArrayList<Post> dataSet;
    Context mContext;
    TextView SubscriberName, Timestamp, PostDescription, CommentCount, LikesCount;
    ImageView DescriptionImage, DisplayPicture;


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

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.post_item, parent, false);
            holder = new ViewHolder();
            holder.NameLabel = (TextView) convertView.findViewById(R.id.NameLabel);
            holder.TimeLabel = (TextView) convertView.findViewById(R.id.TimeLabel);
            holder.DescriptionText = (TextView) convertView.findViewById(R.id.DescriptionText);
            holder.DescriptionImage = (ImageView) convertView.findViewById(R.id.DescriptionImage);
            holder.DisplayPicture = (ImageView) convertView.findViewById(R.id.DisplayPicture);
            holder.CommentCount = (TextView) convertView.findViewById(R.id.CommentCount);
            holder.LikesCount = (TextView) convertView.findViewById(R.id.LikesCount);
            holder.CommentsImage = (ImageView) convertView.findViewById(R.id.CommentsImage);
            holder.LikesImage = (ImageView) convertView.findViewById(R.id.LikesImage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Post post = getItem(i);

        TextView NameLabel = holder.NameLabel;
        TextView TimeLabel = holder.TimeLabel;
        TextView DescriptionText = holder.DescriptionText;
        ImageView DescriptionImage = holder.DescriptionImage;
        ImageView DisplayPicture = holder.DisplayPicture;
        TextView CommentCount = holder.CommentCount;
        TextView LikesCount = holder.LikesCount;
        ImageView CommentsImage = holder.CommentsImage;
        ImageView LikesImage = holder.LikesImage;

        NameLabel.setText(post.getSubscriberName());
        TimeLabel.setText(post.getTimeStamp());
        DescriptionText.setText(post.getPostDescription());
        CommentCount.setText(post.getCommentsCount());
        LikesCount.setText(post.getLikesCount());
        if (dataSet.get(i).getLikeCheck()) {
            holder.LikesImage.setImageResource(R.drawable.like_red_icon);
        } else {
            holder.LikesImage.setImageResource(R.drawable.like_icon);
        }

        if (!post.getDisplayPicture().equals("none")) {
            Picasso.with(mContext).load(post.getDisplayPicture()).into(DisplayPicture);
        }

        if (!post.getImageString().equals("none")) {
            Picasso.with(mContext).load(post.getImageString()).into(DescriptionImage);
        } else {
            DescriptionImage.getLayoutParams().height = 0;
            DescriptionImage.requestLayout();
        }

        holder.serializedIntent = new Intent(mContext, CommentsScreen.class);

        CommentsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.postData = new Post();
                holder.postData.setPostID(dataSet.get(i).getPostID());
                holder.serializedIntent.putExtra("PostData", holder.postData);
                mContext.startActivity(holder.serializedIntent);
            }
        });

        LikesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof NewsFeed) {
                    int likesCount;

                    if (dataSet.get(i).getLikeCheck()) {
                        dataSet.get(i).setLikeCheck(false);
                        holder.LikesImage.setImageResource(R.drawable.like_icon);
                        likesCount = Integer.parseInt(holder.LikesCount.getText().toString()) - 1;
                        ((NewsFeed)mContext).LikeDelete(dataSet.get(i).getPostID());
                    } else {
                        dataSet.get(i).setLikeCheck(true);
                        holder.LikesImage.setImageResource(R.drawable.like_red_icon);
                        likesCount = Integer.parseInt(holder.LikesCount.getText().toString()) + 1;
                        ((NewsFeed)mContext).LikePost(dataSet.get(i).getPostID());
                    }

                    holder.LikesCount.setText(Integer.toString(likesCount));
                    dataSet.get(i).setLikesCount(Integer.toString(likesCount));
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        Post postData;
        Intent serializedIntent;
        public ImageView DescriptionImage, DisplayPicture, CommentsImage, LikesImage;
        public TextView NameLabel, TimeLabel, DescriptionText, CommentCount, LikesCount;
    }
}

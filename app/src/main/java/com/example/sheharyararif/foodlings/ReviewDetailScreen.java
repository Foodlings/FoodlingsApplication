package com.example.sheharyararif.foodlings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.sheharyararif.foodlings.DatabaseModel.Review;
import com.example.sheharyararif.foodlings.DatabaseModel.SearchResult;

public class ReviewDetailScreen extends AppCompatActivity {

    Review reviewDetail;
    RatingBar TasteRating, AmbienceRating, ServiceRating, OrderTimeRating, PriceRating;
    TextView ReviewCommentTextBox, TitleLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_detail_screen);

        TasteRating = (RatingBar) findViewById(R.id.TasteRating);
        AmbienceRating = (RatingBar) findViewById(R.id.AmbienceRating);
        ServiceRating = (RatingBar) findViewById(R.id.ServiceRating);
        OrderTimeRating = (RatingBar) findViewById(R.id.OrderTimeRating);
        PriceRating = (RatingBar) findViewById(R.id.PriceRating);

        TitleLabel = (TextView) findViewById(R.id.TitleLabel);
        ReviewCommentTextBox = (TextView) findViewById(R.id.ReviewCommentTextBox);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        reviewDetail = (Review) args.getSerializable("reviewDetail");

        TasteRating.setRating(Float.parseFloat(reviewDetail.getTaste()));
        AmbienceRating.setRating(Float.parseFloat(reviewDetail.getAmbience()));
        ServiceRating.setRating(Float.parseFloat(reviewDetail.getService()));
        OrderTimeRating.setRating(Float.parseFloat(reviewDetail.getOrderTime()));
        PriceRating.setRating(Float.parseFloat(reviewDetail.getPrice()));
        ReviewCommentTextBox.setText(reviewDetail.getReviewText());
        TitleLabel.setText("Review By " + reviewDetail.getSubscriberName());
    }
}

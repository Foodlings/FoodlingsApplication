package com.example.sheharyararif.foodlings.DatabaseModel;

/**
 * Created by Sheharyar Arif on 2/21/2017.
 */

public class Review
{
    //Fields
    int ReviewID;
    int PostID;
    int RestaurantID;
    String ReviewText;
    String TimeStamp;


    // Constructor-1
    public Review()
    {}

    // Constructor-2
    public Review(int ReviewID, int PostID, int RestaurantID, String ReviewText, String TimeStamp)
    {
        this.ReviewID = ReviewID;
        this.PostID = PostID;
        this.RestaurantID = RestaurantID;
        this.ReviewText = ReviewText;
        this.TimeStamp = TimeStamp;
    }


    // Setters
    public void setReviewID(int ReviewID)
    {   this.ReviewID = ReviewID;    }

    public void setPostID(int PostID)
    {   this.PostID = PostID;    }

    public void setRestaurantID(int RestaurantID)
    {   this.RestaurantID = RestaurantID;    }

    public void setReviewText(String ReviewText)
    {   this.ReviewText = ReviewText;    }

    public void setTimeStamp(String TimeStamp)
    {   this.TimeStamp = TimeStamp;  }


    // Getters
    public int getReviewID()
    {   return ReviewID;    }

    public int getPostID()
    {   return PostID;  }

    public int getRestaurantID()
    {   return RestaurantID;    }

    public String getReviewText()
    {   return ReviewText;  }

    public String getTimeStamp()
    {   return TimeStamp;   }
}

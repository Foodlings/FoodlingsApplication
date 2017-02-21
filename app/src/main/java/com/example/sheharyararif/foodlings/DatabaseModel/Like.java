package com.example.sheharyararif.foodlings.DatabaseModel;

/**
 * Created by Sheharyar Arif on 2/21/2017.
 */

public class Like
{

    //Fields
    int LikeID;
    int SubscriberID;
    int PostID;
    String TimeStamp;


    //Constructor-1
    public Like()
    {}

    //Constructor-2
    public Like(int LikeID, int SubscriberID, int PostID, String TimeStamp)
    {
        this.LikeID = LikeID;
        this.SubscriberID = SubscriberID;
        this.PostID = PostID;
        this.TimeStamp = TimeStamp;
    }


    // Setters
    public void setLikeID(int LikeID)
    {  this.LikeID = LikeID;  }

    public void setSubscriberID(int SubscriberID)
    {  this.SubscriberID = SubscriberID;  }

    public void setPostID(int PostID)
    { this. PostID = PostID;  }

    public void setTimeStamp(String TimeStamp)
    {  TimeStamp = TimeStamp;  }


    // Getters
    public int getLikeID()
    {  return LikeID;  }

    public int getSubscriberID()
    {  return SubscriberID;  }

    public int getPostID()
    {  return PostID;  }

    public String getTimeStamp()
    {  return TimeStamp;  }
}

package com.example.sheharyararif.foodlings.DatabaseModel;

/**
 * Created by Sheharyar Arif on 2/21/2017.
 */

public class Like
{

    //Fields
    String LikeID;
    String SubscriberID;
    String PostID;
    String TimeStamp;


    //Constructor-1
    public Like()
    {}

    //Constructor-2
    public Like(String LikeID, String SubscriberID, String PostID, String TimeStamp)
    {
        this.LikeID = LikeID;
        this.SubscriberID = SubscriberID;
        this.PostID = PostID;
        this.TimeStamp = TimeStamp;
    }


    // Setters
    public void setLikeID(String LikeID)
    {  this.LikeID = LikeID;  }

    public void setSubscriberID(String SubscriberID)
    {  this.SubscriberID = SubscriberID;  }

    public void setPostID(String PostID)
    { this. PostID = PostID;  }

    public void setTimeStamp(String TimeStamp)
    {  this.TimeStamp = TimeStamp;  }


    // Getters
    public String getLikeID()
    {  return LikeID;  }

    public String getSubscriberID()
    {  return SubscriberID;  }

    public String getPostID()
    {  return PostID;  }

    public String getTimeStamp()
    {  return TimeStamp;  }
}

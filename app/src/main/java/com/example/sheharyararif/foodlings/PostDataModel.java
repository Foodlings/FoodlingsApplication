package com.example.sheharyararif.foodlings;

/**
 * Created by Sheharyar Arif on 6/30/2017.
 */

import java.io.Serializable;

@SuppressWarnings("serial")
public class PostDataModel implements Serializable
{

    String PostID;
    String SubscriberID;
    String ImagePresence;
    String ImageAlbumID;
    String ReviewPresence;
    String CheckinPresence;
    String Privacy;
    String Timestamp;
    String PostDescription;


    public PostDataModel(String PostID, String SubscriberID, String ImagePresence, String ImageAlbumID, String ReviewPresence, String CheckinPresence, String Privacy, String Timestamp, String PostDescription)
    {
        this.PostID = PostID;
        this.SubscriberID = SubscriberID;
        this.ImagePresence = ImagePresence;
        this.ImageAlbumID = ImageAlbumID;
        this.ReviewPresence = ReviewPresence;
        this.CheckinPresence = CheckinPresence;
        this.Privacy = Privacy;
        this.Timestamp = Timestamp;
        this.PostDescription = PostDescription;
    }


    public String getPostID()
    {
        return PostID;
    }

    public String getSubscriberID()
    {
        return SubscriberID;
    }

    public String getImagePresence()
    {
        return ImagePresence;
    }

    public String getImageAlbumID()
    {
        return ImageAlbumID;
    }

    public String getReviewPresence()
    {
        return ReviewPresence;
    }

    public String getCheckinPresence()
    {
        return CheckinPresence;
    }

    public String getPrivacy()
    {
        return Privacy;
    }

    public String getTimestamp()
    {
        return Timestamp;
    }

    public String getPostDescription()
    {
        return PostDescription;
    }
}


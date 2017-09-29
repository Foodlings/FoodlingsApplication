package com.example.sheharyararif.foodlings.DatabaseModel;

import java.io.Serializable;

/**
 * Created by Sheharyar Arif on 2/21/2017.
 */

@SuppressWarnings("serial")
public class Post implements Serializable
{

    // Fields
    String PostID;
    String SubscriberID;
    String ImagePresence;
    String ImageAlbumID;
    String ReviewPresence;
    String CheckinPresence;
    String Privacy;
    String TimeStamp;
    String PostDescription;
    String ImageString;


    // Constructor-1
    public Post()
    {}

    // Constructor-2
    public Post(String PostID, String SubscriberID, String ImagePresence, String ImageAlbumID, String ReviewPresence, String CheckinPresence, String Privacy, String TimeStamp, String PostDescription, String ImageString)
    {
        this.PostID = PostID;
        this.SubscriberID = SubscriberID;
        this.ImagePresence = ImagePresence;
        this.ImageAlbumID = ImageAlbumID;
        this.ReviewPresence = ReviewPresence;
        this.CheckinPresence = CheckinPresence;
        this.Privacy = Privacy;
        this.TimeStamp = TimeStamp;
        this.PostDescription = PostDescription;
        this.ImageString = ImageString;
    }


    // Setters
    public void setPostID(String PostID)
    {   this.PostID = PostID;   }

    public void setSubscriberID(String SubscriberID)
    {   this.SubscriberID = SubscriberID;   }

    public void setImagePresence(String ImagePresence)
    {   this.ImagePresence = ImagePresence;   }

    public void setImageAlbumID(String ImageAlbumID)
    {   this.ImageAlbumID = ImageAlbumID;   }

    public void setReviewPresence(String ReviewPresence)
    {   this.ReviewPresence = ReviewPresence;   }

    public void setCheckinPresence(String CheckinPresence)
    {   this.CheckinPresence = CheckinPresence;   }

    public void setPrivacy(String Privacy)
    {   this.Privacy = Privacy;   }

    public void setTimeStamp(String TimeStamp)
    {   this.TimeStamp = TimeStamp;   }

    public void setPostDescription(String PostDescription)
    {   this.PostDescription = PostDescription;   }

    public void setImageString(String ImageString)
    {   this.ImageString = ImageString;   }


    // Getters
    public String getPostID()
    {   return PostID; }

    public String getSubscriberID()
    {   return SubscriberID; }

    public String getImagePresence()
    {   return ImagePresence;   }

    public String getReviewPresence()
    {   return ReviewPresence;   }

    public String getCheckinPresence()
    {   return CheckinPresence;   }

    public String getImageAlbumID()
    {   return ImageAlbumID; }

    public String getPrivacy()
    {   return Privacy; }

    public String getTimeStamp()
    {   return TimeStamp; }

    public String getPostDescription()
    {   return PostDescription; }

    public String getImageString()
    {   return ImageString; }
}

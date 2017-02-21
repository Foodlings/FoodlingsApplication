package com.example.sheharyararif.foodlings.DatabaseModel;

/**
 * Created by Sheharyar Arif on 2/21/2017.
 */

public class Post
{

    // Fields
    int PostID;
    int SubscriberID;
    int ImagePresence;
    int ImageAlbumID;
    int ReviewPresence;
    int CheckinPresence;
    String Privacy;
    String TimeStamp;
    String PostDescription;


    // Constructor-1
    public Post()
    {}

    // Constructor-2
    public Post(int PostID, int SubscriberID, int ImagePresence, int ImageAlbumID, int ReviewPresence, int CheckinPresence, String Privacy, String TimeStamp, String PostDescription)
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
    }


    // Setters
    public void setPostID(int PostID)
    {   this.PostID = PostID;   }

    public void setSubscriberID(int SubscriberID)
    {   this.SubscriberID = SubscriberID;   }

    public void setImagePresence(int ImagePresence)
    {   this.ImagePresence = ImagePresence;   }

    public void setImageAlbumID(int ImageAlbumID)
    {   this.ImageAlbumID = ImageAlbumID;   }

    public void setReviewPresence(int ReviewPresence)
    {   this.ReviewPresence = ReviewPresence;   }

    public void setCheckinPresence(int CheckinPresence)
    {   this.CheckinPresence = CheckinPresence;   }

    public void setPrivacy(String Privacy)
    {   this.Privacy = Privacy;   }

    public void setTimeStamp(String TimeStamp)
    {   this.TimeStamp = TimeStamp;   }

    public void setPostDescription(String PostDescription)
    {   this.PostDescription = PostDescription;   }


    // Getters
    public int getPostID()
    {   return PostID; }

    public int getSubscriberID()
    {   return SubscriberID; }

    public int getImagePresence()
    {   return ImagePresence;   }

    public int getReviewPresence()
    {   return ReviewPresence;   }

    public int getCheckinPresence()
    {   return CheckinPresence;   }

    public int getImageAlbumID()
    {   return ImageAlbumID; }

    public String getPrivacy()
    {   return Privacy; }

    public String getTimeStamp()
    {   return TimeStamp; }

    public String getPostDescription()
    {   return PostDescription; }
}

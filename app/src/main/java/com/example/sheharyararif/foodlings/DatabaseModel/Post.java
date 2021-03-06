package com.example.sheharyararif.foodlings.DatabaseModel;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Sheharyar Arif on 2/21/2017.
 */

@SuppressWarnings("serial")
public class Post implements Serializable
{

    // Fields
    public String PostID;
    public String SubscriberID;
    public String SubscriberName;
    public String DisplayPicture;
    public String ImagePresence;
    public String ImageAlbumID;
    public String ReviewPresence;
    public String CheckinPresence;
    public String Privacy;
    public String TimeStamp;
    public String PostDescription;
    public String ImageString;
    public String CommentsCount;
    public String LikesCount;
    public String CurrentUsersLike;
    public boolean LikeCheck;
    public String MenuPresence;
    public String Taste;
    public String Ambience;
    public String Service;
    public String OrderTime;
    public String Price;
    public String RestaurantName;


    // Constructor-1
    public Post()
    {}

    // Constructor-2
    public Post(String PostID, String SubscriberID, String SubscriberName, String ImagePresence, String ImageAlbumID, String ReviewPresence, String CheckinPresence, String Privacy, String TimeStamp, String PostDescription, String ImageString, String CommentsCount, String LikesCount, String DisplayPicture, String CurrentUsersLike, String MenuPresence)
    {
        this.PostID = PostID;
        this.SubscriberID = SubscriberID;
        this.SubscriberName = SubscriberName;
        this.ImagePresence = ImagePresence;
        this.ImageAlbumID = ImageAlbumID;
        this.ReviewPresence = ReviewPresence;
        this.CheckinPresence = CheckinPresence;
        this.Privacy = Privacy;
        this.TimeStamp = TimeStamp;
        this.PostDescription = PostDescription;
        this.ImageString = ImageString;
        this.CommentsCount = CommentsCount;
        this.LikesCount = LikesCount;
        this.DisplayPicture = DisplayPicture;
        this.CurrentUsersLike = CurrentUsersLike;
        this.MenuPresence = MenuPresence;
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

    public void setSubscriberName(String SubscriberName)
    {   this.SubscriberName = SubscriberName;   }

    public void setCommentsCount(String CommentsCount)
    {   this.CommentsCount = CommentsCount;   }

    public void setLikesCount(String LikesCount)
    {   this.LikesCount = LikesCount;   }

    public void setDisplayPicture(String DisplayPicture)
    {   this.DisplayPicture = DisplayPicture;   }

    public void setLikeCheck(boolean LikeCheck)
    {   this.LikeCheck = LikeCheck;   }

    public void setCurrentUsersLike(String CurrentUsersLike)
    {   this.CurrentUsersLike = CurrentUsersLike;   }

    public void setMenuPresence(String MenuPresence)
    {   this.MenuPresence = MenuPresence;   }

    public void setTaste(String Taste)
    {   this.Taste = Taste;   }

    public void setAmbience(String Ambience)
    {   this.Ambience = Ambience;   }

    public void setService(String Service)
    {   this.Service = Service;   }

    public void setOrderTime(String OrderTime)
    {   this.OrderTime = OrderTime;   }

    public void setPrice(String Price)
    {   this.Price = Price;   }

    public void setRestaurantName(String RestaurantName)
    {   this.RestaurantName = RestaurantName;   }


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

    public String getSubscriberName()
    {   return SubscriberName; }

    public String getCommentsCount()
    {   return CommentsCount; }

    public String getLikesCount()
    {   return LikesCount; }

    public String getDisplayPicture()
    {   return DisplayPicture; }

    public boolean getLikeCheck()
    {   return LikeCheck; }

    public String getCurrentUsersLike()
    {   return CurrentUsersLike; }

    public String getMenuPresence()
    {   return MenuPresence; }

    public String getTaste()
    {   return Taste; }

    public String getAmbience()
    {   return Ambience; }

    public String getService()
    {   return Service; }

    public String getOrderTime()
    {   return OrderTime; }

    public String getPrice()
    {   return Price; }

    public String getRestaurantName()
    {   return RestaurantName; }
}

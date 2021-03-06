package com.example.sheharyararif.foodlings.DatabaseModel;

import java.io.Serializable;

/**
 * Created by Sheharyar Arif on 10/10/2017.
 */

public class SearchResult implements Serializable
{
    public String DisplayPicture, Name, Email, Type, SubscriberID, RestaurantID, FriendCheck, ReviewsCount, Scope;

    public SearchResult()
    {};

    public SearchResult(String SubscriberID, String RestaurantID, String Name, String Type, String Email, String DisplayPicture, String FriendCheck)
    {
        this.SubscriberID = SubscriberID;
        this.RestaurantID = RestaurantID;
        this.Name = Name;
        this.Type = Type;
        this.Email = Email;
        this.DisplayPicture = DisplayPicture;
        this.FriendCheck = FriendCheck;
    }

    public void setSubscriberID(String SubscriberID)
    {   this.SubscriberID = SubscriberID;   }

    public void setRestaurantID(String RestaurantID)
    {   this.RestaurantID = RestaurantID;   }

    public void setName(String Name)
    {   this.Name = Name;   }

    public void setType(String Type)
    {   this.Type = Type;   }

    public void setEmail(String Email)
    {   this.Email = Email;   }

    public void setDisplayPicture(String DisplayPicture)
    {   this.DisplayPicture = DisplayPicture;   }

    public void setFriendCheck(String FriendCheck)
    {   this.FriendCheck = FriendCheck;   }

    public void setReviewsCount(String ReviewsCount)
    {   this.ReviewsCount = ReviewsCount;   }

    public void setScope(String Scope)
    {   this.Scope = Scope;   }


    public String getSubscriberID()
    {   return SubscriberID; }

    public String getName()
    {   return Name; }

    public String getType()
    {   return Type; }

    public String getEmail()
    {   return Email; }

    public String getDisplayPicture()
    {   return DisplayPicture; }

    public String getRestaurantID()
    {   return RestaurantID; }

    public String getFriendCheck()
    {   return FriendCheck; }

    public String getReviewsCount()
    {   return ReviewsCount; }

    public String getScope()
    {   return Scope; }
}

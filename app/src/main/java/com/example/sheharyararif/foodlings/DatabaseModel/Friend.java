package com.example.sheharyararif.foodlings.DatabaseModel;

/**
 * Created by Sheharyar Arif on 3/5/2017.
 */

public class Friend
{
    //Fields
    public String ListID, SubscriberID, FriendID, Since, SubscriberName, DisplayPicture;


    //Constructor-1
    public Friend()
    {}

    //Constructor-2
    public Friend(String ListID, String SubscriberID, String FriendID, String Since, String SubscriberName, String DisplayPicture)
    {
        this.ListID = ListID;
        this.SubscriberID = SubscriberID;
        this.FriendID = FriendID;
        this.Since = Since;
        this.SubscriberName = SubscriberName;
        this.DisplayPicture = DisplayPicture;
    }


    //Setters
    public void setListID(String ListID)
    { this.ListID = ListID; }

    public void setSubscriberID(String SubscriberID)
    { this.SubscriberID = SubscriberID; }

    public void setFriendID(String FriendID)
    { this.FriendID = FriendID; }

    public void setSince(String Since)
    { this.Since = Since; }

    public void setSubscriberName(String SubscriberName)
    { this.SubscriberName = SubscriberName; }

    public void setDisplayPicture(String DisplayPicture)
    { this.DisplayPicture = DisplayPicture; }


    //Getters
    public String getListID()
    { return ListID; }

    public String getSubscriberID()
    { return SubscriberID; }

    public String getFriendID()
    { return FriendID; }

    public String getSince()
    { return Since; }

    public String getSubscriberName()
    { return SubscriberName; }

    public String getDisplayPicture()
    { return DisplayPicture; }
}

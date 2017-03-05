package com.example.sheharyararif.foodlings.DatabaseModel;

/**
 * Created by Sheharyar Arif on 3/5/2017.
 */

public class Friend
{
    //Fields
    int ListID;
    int SubscriberID;
    int FriendID;
    String Since;


    //Constructor-1
    public Friend()
    {}

    //Constructor-2
    public Friend(int ListID, int SubscriebrID, int FriendID, String Since)
    {
        this.ListID = ListID;
        this.SubscriberID = SubscriebrID;
        this.FriendID = FriendID;
        this.Since = Since;
    }


    //Setters
    public void setListID(int ListID)
    { this.ListID = ListID; }

    public void setSubscriberID(int SubscriberID)
    { this.SubscriberID = SubscriberID; }

    public void setFriendID(int FriendID)
    { this.FriendID = FriendID; }

    public void setSinsce(String Since)
    { this.Since = Since; }


    //Getters
    public int getListID()
    { return ListID; }

    public int getSubscriberID()
    { return SubscriberID; }

    public int setFriendID()
    { return FriendID; }

    public String setSince()
    { return Since; }

}

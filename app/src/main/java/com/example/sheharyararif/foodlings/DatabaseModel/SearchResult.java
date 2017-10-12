package com.example.sheharyararif.foodlings.DatabaseModel;

import java.io.Serializable;

/**
 * Created by Sheharyar Arif on 10/10/2017.
 */

public class SearchResult implements Serializable
{
    String DisplayPicture, Name, Email, Type, SubscriberID;

    public SearchResult(String SubscriberID, String Name, String Type, String Email, String DisplayPicture)
    {
        this.SubscriberID = SubscriberID;
        this.Name = Name;
        this.Type = Type;
        this.Email = Email;
        this.DisplayPicture = DisplayPicture;
    }

    public void setSubscriberID(String SubscriberID)
    {   this.SubscriberID = SubscriberID;   }

    public void setName(String Name)
    {   this.Name = Name;   }

    public void setType(String Type)
    {   this.Type = Type;   }

    public void setEmail(String Email)
    {   this.Email = Email;   }

    public void setDisplayPicture(String DisplayPicture)
    {   this.DisplayPicture = DisplayPicture;   }

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
}

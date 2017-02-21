package com.example.sheharyararif.foodlings.DatabaseModel;

/**
 * Created by Sheharyar Arif on 2/21/2017.
 */

public class ImageAlbum
{

    // Fields
    int ImageAlbumID;
    String AlbumName;
    int SubscriberID;


    // Constructor-1
    public ImageAlbum()
    {}

    // Constructor-2
    public ImageAlbum(int ImageAlbumID, String AlbumName, int SubscriberID)
    {
        this.ImageAlbumID = ImageAlbumID;
        this.AlbumName = AlbumName;
        this.SubscriberID = SubscriberID;
    }


    // Setters
    public void setImageAlbumID(int ImageAlbumID)
    {   this.ImageAlbumID = ImageAlbumID;   }

    public void setAlbumName(String AlbumName)
    {   this.AlbumName = AlbumName;   }

    public void setSubscriberID(int SubscriberID)
    {   this.SubscriberID = SubscriberID;   }


    // Getters
    public int getImageAlbumID()
    {   return this.ImageAlbumID; }

    public String getAlbumName()
    {   return this.AlbumName;   }

    public int getSubscriberID()
    {  return SubscriberID;  }
}
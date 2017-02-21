package com.example.sheharyararif.foodlings.DatabaseModel;

/**
 * Created by Sheharyar Arif on 2/21/2017.
 */

public class Images
{
    // Fields
    int ImageID;
    int ImageAlbumID;
    int PostID;


    // Constructor-1
    public Images()
    {}

    // Constructor-2
    public Images(int ImageID, int ImageAlbumID, int PostID)
    {
        this.ImageID = ImageID;
        this.ImageAlbumID = ImageAlbumID;
        this.PostID = PostID;
    }


    // Setters
    public void setImageID(int ImageID)
    {  this.ImageID = ImageID;  }

    public void setImageAlbumID(int ImageAlbumID)
    {  this.ImageAlbumID = ImageAlbumID; }

    public void setPostID(int PostID)
    {  this.PostID = PostID;  }


    // Getters
    public int getImageID()
    {  return ImageID;  }

    public int getImageAlbumID()
    {  return ImageAlbumID;  }

    public int getPostID()
    {  return PostID;  }
}

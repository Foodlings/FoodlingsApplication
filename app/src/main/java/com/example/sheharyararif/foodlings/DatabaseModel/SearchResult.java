package com.example.sheharyararif.foodlings.DatabaseModel;

/**
 * Created by Sheharyar Arif on 10/10/2017.
 */

public class SearchResult
{
    String DisplayPicture, Name, Email;

    public SearchResult(String Name, String Email, String DisplayPicture)
    {
        this.Name = Name;
        this.Email = Email;
        this.DisplayPicture = DisplayPicture;
    }

    public void setName(String Name)
    {   this.Name = Name;   }

    public void setEmail(String Email)
    {   this.Email = Email;   }

    public void setDisplayPicture(String DisplayPicture)
    {   this.DisplayPicture = DisplayPicture;   }

    public String getName()
    {   return Name; }

    public String getEmail()
    {   return Email; }

    public String getDisplayPicture()
    {   return DisplayPicture; }
}

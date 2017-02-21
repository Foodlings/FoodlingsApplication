package com.example.sheharyararif.foodlings.DatabaseModel;

/**
 * Created by Sheharyar Arif on 2/21/2017.
 */

public class Checkin
{

    //Fields
    int CheckinID;
    int PostID;
    int LocationID;
    int FranchiseID;
    String TimeStamp;


    //Constructor-1
    public Checkin()
    {}

    //Constructor-2
    public Checkin(int CheckinID, int PostID, int LocationID, int FranchiseID, String TimeStamp)
    {
        this.CheckinID = CheckinID;
        this.PostID = PostID;
        this.LocationID = LocationID;
        this.FranchiseID = FranchiseID;
        this.TimeStamp = TimeStamp;
    }



    // Setters
    public void setCheckinID(int CheckinID)
    {  this.CheckinID = CheckinID;  }

    public void setPostID(int PostID)
    {  this.PostID = PostID;  }

    public void setLocationID(int LocationID)
    {  this.LocationID = LocationID;  }

    public void setFranchiseID(int FranchiseID)
    {  this.FranchiseID = FranchiseID;  }

    public void setTimeStamp(String TimeStamp)
    {  this.TimeStamp = TimeStamp;  }


    // Getters
    public int getCheckinID()
    {  return CheckinID;  }

    public int getPostID()
    {  return PostID;  }

    public int getLocationID()
    {  return LocationID;  }

    public int getFranchiseID()
    {  return FranchiseID;  }

    public String getTimeStamp()
    {  return TimeStamp;  }
}

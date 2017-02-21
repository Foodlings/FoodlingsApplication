package com.example.sheharyararif.foodlings.DatabaseModel;

/**
 * Created by Sheharyar Arif on 2/21/2017.
 */

public class Franchise_Location
{

    //Fields
    int FranchiseID;
    int RestaurantID;
    int LocationID;


    //Constructor-1
    public Franchise_Location()
    {}

    //Constructor-2
    public Franchise_Location(int FranchiseID, int RestaurantID, int LocationID)
    {
        this.FranchiseID = FranchiseID;
        this.RestaurantID = RestaurantID;
        this.LocationID = LocationID;
    }


    // Setters
    public void setFranchiseID(int FranchiseID)
    {  this.FranchiseID = FranchiseID;  }

    public void setRestaurantID(int RestaurantID)
    {  this.RestaurantID = RestaurantID;  }

    public void setLocationID(int LocationID)
    {  this.LocationID = LocationID;  }


    // Getters
    public int getFranchiseID()
    {  return FranchiseID;  }

    public int getRestaurantID()
    {  return RestaurantID;  }

    public int getLocationID()
    {  return LocationID;  }
}


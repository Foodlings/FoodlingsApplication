package com.example.sheharyararif.foodlings.DatabaseModel;

/**
 * Created by Sheharyar Arif on 2/21/2017.
 */

public class Location
{
    int LocationID;
    String City;
    String Area;

    // Constructor-1
    public Location()
    {}

    // Constructor-2
    public Location(int LocationID, String City, String Area)
    {
        this.LocationID = LocationID;
        this.City = City;
        this.Area = Area;
    }


    // Setters
    public void setLocationID(int LocationID)
    {   this.LocationID = LocationID;    }

    public void setCity(String City)
    {   this.City = City;    }

    public void setArea(String Area)
    {   this.Area = Area;    }


    // Getters
    public int getLocationID()
    {   return LocationID;  }

    public String getCity()
    {   return City;    }

    public String getArea()
    {   return Area;    }
}

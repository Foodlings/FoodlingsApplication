package com.example.sheharyararif.foodlings.DatabaseModel;

/**
 * Created by Sheharyar Arif on 2/21/2017.
 */

public class RestaurantProfile
{
    // Fields
    int RestaurantID;
    int SubscriberID;
    String Address;
    String Timing;
    String Category;


    // Costructor-1
    public RestaurantProfile()
    {}

    // Constructor-2
    public RestaurantProfile(int RestaurantID, int SubscriberID, String Address, String Timing, String Category)
    {
        this.RestaurantID = RestaurantID;
        this.SubscriberID = SubscriberID;
        this.Address = Address;
        this.Timing = Timing;
        this.Category = Category;
    }


    // Setters
    public void setRestaurantID(int RestaurantID)
    {   this.RestaurantID = RestaurantID;    }

    public void setSubscriberID(int SubscriberID)
    {   this.SubscriberID = SubscriberID;    }

    public void setAddress(String Address)
    {   this.Address = Address;  }

    public void setTiming(String Timing)
    {   this.Timing = Timing;    }

    public void setCategory(String Category)
    {   this.Category = Category;    }


    // Getters
    public int getRestaurantID()
    {   return RestaurantID;    }

    public int getSubscriberID()
    {   return SubscriberID;    }

    public String getAddress()
    {   return Address; }

    public String getTiming()
    {   return Timing;  }

    public String getCategory()
    {   return Category;    }
}

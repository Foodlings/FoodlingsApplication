package com.example.sheharyararif.foodlings.DatabaseModel;

import java.io.Serializable;

/**
 * Created by Sheharyar Arif on 2/21/2017.
 */

public class Review implements Serializable
{
    //Fields
    public String ReviewID ;
    public String PostID ;
    public String SubscriberID ;
    public String RestaurantID ;
    public String ReviewText ;
    public String TimeStamp ;
    public String Taste ;
    public String Ambience ;
    public String Service ;
    public String OrderTime ;
    public String Price ;
    public String SubscriberName ;
    public String DisplayPicture ;


    // Constructor-1
    public Review()
    {}

    // Constructor-2
    public Review(String ReviewID, String PostID, String SubscriberID, String RestaurantID, String ReviewText, String TimeStamp, String Taste, String Ambience, String Service, String OrderTime, String Price, String SubsriberName, String DisplayPicture)
    {
        this.ReviewID = ReviewID;
        this.PostID = PostID;
        this.SubscriberID = SubscriberID;
        this.RestaurantID = RestaurantID;
        this.ReviewText = ReviewText;
        this.TimeStamp = TimeStamp;
        this.Taste = Taste;
        this.Ambience = Ambience;
        this.Service = Service;
        this.OrderTime = OrderTime;
        this.Price = Price;
        this.SubscriberName = SubsriberName;
        this.DisplayPicture = DisplayPicture;
    }


    // Setters
    public void setReviewID(String ReviewID)
    {   this.ReviewID = ReviewID;    }

    public void setPostID(String PostID)
    {   this.PostID = PostID;    }

    public void setSubscriberID(String SubscriberID)
    {   this.SubscriberID = SubscriberID;    }

    public void setRestaurantID(String RestaurantID)
    {   this.RestaurantID = RestaurantID;    }

    public void setReviewText(String ReviewText)
    {   this.ReviewText = ReviewText;    }

    public void setTimeStamp(String TimeStamp)
    {   this.TimeStamp = TimeStamp;  }

    public void setTaste(String Taste)
    {   this.Taste = Taste;  }

    public void setAmbience(String Ambience)
    {   this.Ambience = Ambience;  }

    public void setService(String Service)
    {   this.Service = Service;  }

    public void setOrderTime(String OrderTime)
    {   this.OrderTime = OrderTime;  }

    public void setPrice(String Price)
    {   this.Price = Price;  }

    public void setSubscriberName(String SubscriberName)
    {   this.SubscriberName = SubscriberName;  }

    public void setDisplayPicture(String DisplayPicture)
    {   this.DisplayPicture = DisplayPicture;  }


    // Getters
    public String getReviewID()
    {   return ReviewID;    }

    public String getPostID()
    {   return PostID;  }

    public String getSubscriberID()
    {   return SubscriberID;   }

    public String getRestaurantID()
    {   return RestaurantID;    }

    public String getReviewText()
    {   return ReviewText;  }

    public String getTimeStamp()
    {   return TimeStamp;   }

    public String getTaste()
    {   return Taste;   }

    public String getAmbience()
    {   return Ambience;   }

    public String getService()
    {   return Service;   }

    public String getOrderTime()
    {   return OrderTime;   }

    public String getPrice()
    {   return Price;   }

    public String getSubscriberName()
    {   return SubscriberName;   }

    public String getDisplayPicture()
    {   return DisplayPicture;   }
}

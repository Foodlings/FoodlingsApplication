package com.example.sheharyararif.foodlings.DatabaseModel;

/**
 * Created by Sheharyar Arif on 2/21/2017.
 */

public class Subscriber
{

    // Fields
    int SubscriberID;
    String SubscriberName;
    String Password;
    String Type;
    String Email;
    int DisplayPictureID;
    String PhoneNumber;
    String Bio;
    String Gender;
    String DoB;


    // Constructor-1
    public Subscriber()
    {}

    // Constructor-2
    public Subscriber(int SubscriberID, String SubscriberName, String Password, String type, String Email, int DisplayPictureID, String PhoneNumber, String Bio, String Gender, String DoB)
    {
        this.SubscriberID = SubscriberID;
        this.SubscriberName = SubscriberName;
        this.Password = Password;
        this.Type = type;
        this.Email = Email;
        this.DisplayPictureID = DisplayPictureID;
        this.PhoneNumber = PhoneNumber;
        this.Bio = Bio;
        this.Gender = Gender;
        this.DoB = DoB;
    }


    // Setters
    public void setSubscriberID(int SubscriberID)
    {   this.SubscriberID = SubscriberID;   }

    public void setSubscriberName(String SubscriberName)
    {   this.SubscriberName = SubscriberName;   }

    public void setPassword(String Password)
    {   this.Password = Password;   }

    public void setType(String type)
    {   this.Type = type;   }

    public void setEmail(String Email)
    {   this.Email = Email;   }

    public void setDisplayPictureID(int DisplayPictureID)
    {   this.DisplayPictureID = DisplayPictureID;   }

    public void setPhoneNumber(String PhoneNumber)
    {   this.PhoneNumber = PhoneNumber;   }

    public void setBio(String Bio)
    {   this.Bio = Bio;   }

    public void setGender(String Gender)
    {   this.Gender = Gender;   }

    public void setDoB(String DoB)
    {   this.DoB = DoB;   }


    // Getters
    public int getSubscriberID()
    {   return SubscriberID;    }

    public String getSubscriberName()
    {   return SubscriberName;  }

    public String getPassword()
    {   return Password;    }

    public String getType()
    {   return Type;    }

    public String getEmail()
    {   return Email;   }

    public int getDisplayPictureID()
    {   return DisplayPictureID;    }

    public String getPhoneNumber()
    {   return PhoneNumber; }

    public String getBio()
    {   return Bio; }

    public String getGender()
    {   return Gender;  }

    public String getDoB()
    {   return DoB; }
}

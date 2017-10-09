package com.example.sheharyararif.foodlings.DatabaseModel;

/**
 * Created by Sheharyar Arif on 2/21/2017.
 */

public class Subscriber
{

    // Fields
    String SubscriberID;
    String SubscriberName;
    String Password;
    String Type;
    String Email;
    String DisplayPictureID;
    String PhoneNumber;
    String Bio;
    String Gender;
    String DoB;
    String DisplayPicture;
    String CoverPhoto;
    String Address;
    String Timing;
    String Category;


    // Constructor-1
    public Subscriber()
    {}

    // Constructor-2
    public Subscriber(String SubscriberID, String SubscriberName, String Password, String type, String Email, String DisplayPictureID, String PhoneNumber, String Bio, String Gender, String DoB)
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
    public void setSubscriberID(String SubscriberID)
    {   this.SubscriberID = SubscriberID;   }

    public void setSubscriberName(String SubscriberName)
    {   this.SubscriberName = SubscriberName;   }

    public void setPassword(String Password)
    {   this.Password = Password;   }

    public void setType(String type)
    {   this.Type = type;   }

    public void setEmail(String Email)
    {   this.Email = Email;   }

    public void setDisplayPictureID(String DisplayPictureID)
    {   this.DisplayPictureID = DisplayPictureID;   }

    public void setPhoneNumber(String PhoneNumber)
    {   this.PhoneNumber = PhoneNumber;   }

    public void setBio(String Bio)
    {   this.Bio = Bio;   }

    public void setGender(String Gender)
    {   this.Gender = Gender;   }

    public void setDoB(String DoB)
    {   this.DoB = DoB;   }

    public void setDisplayPicture(String DisplayPicture)
    {   this.DisplayPicture = DisplayPicture;   }

    public void setCoverPhoto(String CoverPhoto)
    {   this.CoverPhoto = CoverPhoto;   }

    public void setAddress(String Address)
    {   this.Address = Address;   }

    public void setTiming(String Timing)
    {   this.Timing = Timing;   }

    public void setCategory(String Category)
    {   this.Category = Category;   }


    // Getters
    public String getSubscriberID()
    {   return SubscriberID;    }

    public String getSubscriberName()
    {   return SubscriberName;  }

    public String getPassword()
    {   return Password;    }

    public String getType()
    {   return Type;    }

    public String getEmail()
    {   return Email;   }

    public String getDisplayPictureID()
    {   return DisplayPictureID;    }

    public String getPhoneNumber()
    {   return PhoneNumber; }

    public String getBio()
    {   return Bio; }

    public String getGender()
    {   return Gender;  }

    public String getDoB()
    {   return DoB; }

    public String getDisplayPicture()
    {   return DisplayPicture; }

    public String getCoverPhoto()
    {   return CoverPhoto; }

    public String getAddress()
    {   return Address; }

    public String getTiming()
    {   return Timing; }

    public String getCategory()
    {   return Category; }
}

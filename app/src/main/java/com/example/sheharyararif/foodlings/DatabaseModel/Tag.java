package com.example.sheharyararif.foodlings.DatabaseModel;

/**
 * Created by Sheharyar Arif on 2/21/2017.
 */

public class Tag
{

    //Fields
    int TagID;
    int SubscriberID;
    int TaggedSubscriber;
    int PostID;
    String TimeStamp;

    //Constructor-1
    public Tag()
    {}

    //Constructor-2
    public Tag(int TagID, int SubscriberID, int TaggedSubscriber, int PostID, String TimeStamp)
    {
        this.TagID = TagID;
        this.SubscriberID = SubscriberID;
        this.TaggedSubscriber = TaggedSubscriber;
        this.PostID = PostID;
        this.TimeStamp = TimeStamp;
    }


    // Setters
    public void setTagID(int TagID)
    {  this.TagID = TagID;  }

    public void setSubscriberID(int SubscriberID)
    { this. SubscriberID = SubscriberID;  }

    public void setTaggedSubscriber(int TaggedSubscriber)
    {  this.TaggedSubscriber = TaggedSubscriber;  }

    public void setPostID(int PostID)
    {  this.PostID = PostID;  }

    public void setTimeStamp(String TimeStamp)
    {  this.TimeStamp = TimeStamp;  }


    // Getters
    public int getTagID()
    {  return TagID;  }

    public int getSubscriberID()
    {  return SubscriberID;  }

    public int getTaggedSubscriber()
    {  return TaggedSubscriber;  }

    public int getPostID()
    {  return PostID;  }

    public String getTimeStamp()
    {  return TimeStamp;  }
}

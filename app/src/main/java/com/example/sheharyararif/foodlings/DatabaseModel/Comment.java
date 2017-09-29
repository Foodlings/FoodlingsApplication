package com.example.sheharyararif.foodlings.DatabaseModel;

/**
 * Created by Sheharyar Arif on 2/21/2017.
 */

public class Comment
{

    // Fields
    String CommentID;
    String SubscriberID;
    String PostID;
    String CommentText;
    String TimeStamp;
    String SubscriberName;


    // Constructor-1
    public Comment()
    {}

    // Constructor-2
    public Comment(String commentID, String subscriberID, String SubscriberName, String postID, String commentText, String timeStamp)
    {
        this.CommentID = commentID;
        this.SubscriberID = subscriberID;
        this.SubscriberName = SubscriberName;
        this.PostID = postID;
        this.CommentText = commentText;
        this.TimeStamp = timeStamp;
    }


    // Setters
    public void setCommentID(String CommentID)
    {   this.CommentID = CommentID; }

    public void setSubscriberID(String SubscriberID)
    {   this.SubscriberID = SubscriberID;   }

    public void setPostID(String PostID)
    {   this.PostID = PostID;    }

    public void setCommentText(String CommentText)
    {   this.CommentText = CommentText;  }

    public void setTimeStamp(String TimeStamp)
    {   this.TimeStamp = TimeStamp;  }

    public void setSubscriberName(String SubscriberName)
    {   this.SubscriberName = SubscriberName;  }


    // Getters
    public String getTimeStamp()
    {   return TimeStamp;   }

    public String getCommentText()
    {   return CommentText; }

    public String getPostID()
    {   return PostID;  }

    public String getSubscriberID()
    {   return SubscriberID;    }

    public String getCommentID()
    {   return CommentID;   }

    public String getSubscriberName()
    {   return SubscriberName;   }
}

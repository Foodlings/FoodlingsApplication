package com.example.sheharyararif.foodlings.DatabaseModel;

/**
 * Created by Sheharyar Arif on 2/21/2017.
 */

public class Comment
{

    // Fields
    int CommentID;
    int SubscriberID;
    int PostID;
    String CommentText;
    String TimeStamp;


    // Constructor-1
    public Comment()
    {}

    // Constructor-2
    public Comment(int commentID, int subscriberID, int postID, String commentText, String timeStamp)
    {
        this.CommentID = commentID;
        this.SubscriberID = subscriberID;
        this.PostID = postID;
        this.CommentText = commentText;
        this.TimeStamp = timeStamp;
    }


    // Setters
    public void setCommentID(int CommentID)
    {   this.CommentID = CommentID; }

    public void setSubscriberID(int SubscriberID)
    {   this.SubscriberID = SubscriberID;   }

    public void setPostID(int PostID)
    {   this.PostID = PostID;    }

    public void setCommentText(String CommentText)
    {   this.CommentText = CommentText;  }

    public void setTimeStamp(String TimeStamp)
    {   this.TimeStamp = TimeStamp;  }


    // Getters
    public String getTimeStamp()
    {   return TimeStamp;   }

    public String getCommentText()
    {   return CommentText; }

    public int getPostID()
    {   return PostID;  }

    public int getSubscriberID()
    {   return SubscriberID;    }

    public int getCommentID()
    {   return CommentID;   }
}

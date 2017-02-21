package com.example.sheharyararif.foodlings.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.sheharyararif.foodlings.DatabaseModel.*;

/**
 * Created by Sheharyar Arif on 2/21/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper
{
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Foodlings";


    // =================================================== Table Names ===================================================

    // Table Names
    private static final String TABLE_FRIEND = "Friend";
    private static final String TABLE_SUBSCRIBER = "Subscriber";
    private static final String TABLE_POST = "Post";
    private static final String TABLE_IMAGES = "Images";
    private static final String TABLE_IMAGE_AlBUM = "ImageAlbum";
    private static final String TABLE_LIKE = "Like";
    private static final String TABLE_COMMENT = "Comment";
    private static final String TABLE_TAG = "Tag";
    private static final String TABLE_CHECKIN = "Checkin";
    private static final String TABLE_REVIEW = "Review";
    private static final String TABLE_RESTAURANT_PROFILE = "RestaurantProfile";
    private static final String TABLE_FRANCHISE_LOCATION = "Franchise_Location";
    private static final String TABLE_LOCATION = "Location";



    // ================================================= Table Column Names =================================================

    // Common column names
    private static final String KEY_SUBSCRIBER_ID = "SubscriberID";
    private static final String KEY_POST_ID = "PostID";
    private static final String KEY_IMAGE_ALBUM_ID = "ImageAlbumID";
    private static final String KEY_LOCATION_ID = "LocationID";
    private static final String KEY_FRANCHISE_ID = "FranchiseID";
    private static final String KEY_RESTAURANT_ID = "RestaurantID";
    private static final String KEY_TIMESTAMP = "TimeStamp";

    // Friend Table - column names
    private static final String KEY_LIST_ID = "ListID";
    private static final String KEY_FRIEND_ID = "FriendID";
    private static final String KEY_SINCE = "Since";

    // Subscriber Table - column names
    private static final String KEY_SUBSCRIBER_NAME = "SubscriberName";
    private static final String KEY_PASSWORD = "Password";
    private static final String KEY_TYPE = "Type";
    private static final String KEY_EMAIL_ADDRESS = "EmailAddress";
    private static final String KEY_DISPLAY_PICTURE_ID = "DisplayPictureID";
    private static final String KEY_PHONE_NUMBER = "PhoneNumber";
    private static final String KEY_BIO = "Bio";
    private static final String KEY_GENDER = "Gender";
    private static final String KEY_DOB = "DoB";

    // Post Table - column names
    private static final String KEY_IMAGE_PRESENCE = "ImagePresence";
    private static final String KEY_REVIEW_PRESENCE = "ReviewPresence";
    private static final String KEY_CHECKIN_PRESENCE = "CheckinPresence";
    private static final String KEY_PRIVACY = "Privacy";
    private static final String KEY_POST_DESCRIPTION = "PostDescription";

    // Images Table - column names
    private static final String KEY_IMAGE_ID = "ImageID";

    // ImageAlbum Table - column names
    private static final String KEY_ALBUM_NAME = "AlbumName";

    // Like Table - column names
    private static final String KEY_LIKE_ID = "LikeID";

    // Comment Table - column names
    private static final String KEY_COMMENT_ID = "CommentID";
    private static final String KEY_COMMENT_TEXT = "CommentText";

    // Tag Table - column names
    private static final String KEY_TAG_ID = "TagID";
    private static final String KEY_TAGGED_SUBSCRIBER = "TaggedSubscriber";

    // Checkin Table - column names
    private static final String KEY_CHECKIN_ID = "CheckinID";

    // Review Table - column names
    private static final String KEY_REVIEW_ID = "ReviewID";
    private static final String KEY_REVIEW_TEXT = "ReviewText";

    // RestaurantProfile Table - column names
    private static final String KEY_ADDRESS = "Address";
    private static final String KEY_TIMING = "Timing";
    private static final String KEY_CATEGORY = "Category";

    // Location Table - column names
    private static final String KEY_CITY = "City";
    private static final String KEY_AREA = "Area";



    // ================================================ Table Create Statements ================================================

    // Subscriber table create statement
    private static final String CREATE_TABLE_SUBSCRIBER = "CREATE TABLE " + TABLE_SUBSCRIBER +
            "("
            + KEY_SUBSCRIBER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_SUBSCRIBER_NAME + " TEXT NOT NULL,"
            + KEY_PASSWORD + " TEXT NOT NULL,"
            + KEY_TYPE + " TEXT NOT NULL,"
            + KEY_EMAIL_ADDRESS + " TEXT NOT NULL,"
            + KEY_DISPLAY_PICTURE_ID + " INTEGER NULL,"
            + KEY_PHONE_NUMBER + " TEXT NULL,"
            + KEY_BIO + " TEXT NULL,"
            + KEY_GENDER + " TEXT NULL,"
            + KEY_DOB  + " TEXT NULL" +
            ")";

    // Friend table create statement
    private static final String CREATE_TABLE_FRIEND = "CREATE TABLE " + TABLE_FRIEND +
            "("
            + KEY_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_SUBSCRIBER_ID  + " INTEGER NOT NULL,"
            + KEY_FRIEND_ID + " INTEGER NOT NULL,"
            + KEY_SINCE  + " TEXT NOT NULL,"
            + "FOREIGN KEY(" + KEY_SUBSCRIBER_ID + ") REFERENCES " + TABLE_SUBSCRIBER + "(" + KEY_SUBSCRIBER_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY(" + KEY_FRIEND_ID + ") REFERENCES " + TABLE_SUBSCRIBER + "(" + KEY_SUBSCRIBER_ID + ")" +
            ")";

    // ImageAlbum table create statement
    private static final String CREATE_TABLE_IMAGE_ALBUM = "CREATE TABLE " + TABLE_IMAGE_AlBUM +
            "("
            + KEY_IMAGE_ALBUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_ALBUM_NAME + " TEXT NOT NULL,"
            + KEY_SUBSCRIBER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY(" + KEY_SUBSCRIBER_ID + ") REFERENCES " + TABLE_SUBSCRIBER + "(" + KEY_SUBSCRIBER_ID + ") ON DELETE CASCADE" +
            ")";

    // Post table create statement
    private static final String CREATE_TABLE_POST = "CREATE TABLE " + TABLE_POST +
            "("
            + KEY_POST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_SUBSCRIBER_ID + " INTEGER NOT NULL,"
            + KEY_IMAGE_PRESENCE + " INTEGER NOT NULL,"
            + KEY_IMAGE_ALBUM_ID + " INTEGER NULL,"
            + KEY_REVIEW_PRESENCE + " INTEGER NOT NULL,"
            + KEY_CHECKIN_PRESENCE + " INTEGER NOT NULL,"
            + KEY_PRIVACY + " TEXT NOT NULL,"
            + KEY_TIMESTAMP  + " TEXT NOT NULL,"
            + KEY_POST_DESCRIPTION + " TEXT NULL,"
            + "FOREIGN KEY(" + KEY_SUBSCRIBER_ID + ") REFERENCES " + TABLE_SUBSCRIBER + "(" + KEY_SUBSCRIBER_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY(" + KEY_IMAGE_ALBUM_ID + ") REFERENCES " + TABLE_IMAGE_AlBUM + "(" + KEY_IMAGE_ALBUM_ID + ") ON DELETE CASCADE" +
            ")";

    // Image table create statement
    private static final String CREATE_TABLE_IMAGES = "CREATE TABLE " + TABLE_IMAGES +
            "("
            + KEY_IMAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_IMAGE_ALBUM_ID + " INTEGER NOT NULL,"
            + KEY_POST_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY(" + KEY_POST_ID + ") REFERENCES " + TABLE_POST + "(" + KEY_POST_ID + "),"
            + "FOREIGN KEY(" + KEY_IMAGE_ALBUM_ID + ") REFERENCES " + TABLE_IMAGE_AlBUM + "(" + KEY_IMAGE_ALBUM_ID + ") ON DELETE CASCADE" +
            ")";

    // Like table create statement
    private static final String CREATE_TABLE_LIKE = "CREATE TABLE " + TABLE_LIKE +
            "("
            + KEY_LIKE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_SUBSCRIBER_ID + " INTEGER NOT NULL,"
            + KEY_POST_ID + " INTEGER NOT NULL,"
            + KEY_TIMESTAMP + " TEXT NOT NULL,"
            + "CONSTRAINT Subscriber_Like_Post_Unique UNIQUE (" + KEY_SUBSCRIBER_ID + "," + KEY_POST_ID + "),"
            + "FOREIGN KEY(" + KEY_SUBSCRIBER_ID + ") REFERENCES " + TABLE_SUBSCRIBER + "(" + KEY_SUBSCRIBER_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY(" + KEY_POST_ID + ") REFERENCES " + TABLE_POST + "(" + KEY_POST_ID + ") ON DELETE CASCADE" +
            ")";

    // Comment table create statement
    private static final String CREATE_TABLE_COMMENT = "CREATE TABLE " + TABLE_COMMENT +
            "("
            + KEY_COMMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_SUBSCRIBER_ID + " INTEGER NOT NULL,"
            + KEY_POST_ID + " INTEGER NOT NULL,"
            + KEY_COMMENT_TEXT + " TEXT NOT NULL,"
            + KEY_TIMESTAMP + " TEXT NOT NULL,"
            + "FOREIGN KEY(" + KEY_SUBSCRIBER_ID + ") REFERENCES " + TABLE_SUBSCRIBER + "(" + KEY_SUBSCRIBER_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY(" + KEY_POST_ID + ") REFERENCES " + TABLE_POST + "(" + KEY_POST_ID + ") ON DELETE CASCADE" +
            ")";

    // Tag table create statement
    private static final String CREATE_TABLE_TAG = "CREATE TABLE " + TABLE_TAG +
            "("
            + KEY_TAG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_SUBSCRIBER_ID + " INTEGER NOT NULL,"
            + KEY_TAGGED_SUBSCRIBER + " INTEGER NOT NULL,"
            + KEY_POST_ID + " INTEGER NOT NULL,"
            + KEY_TIMESTAMP + " TEXT NOT NULL,"
            + "CONSTRAINT Tagged_Post UNIQUE (" + KEY_TAGGED_SUBSCRIBER + "," + KEY_POST_ID + "),"
            + "FOREIGN KEY(" + KEY_SUBSCRIBER_ID + ") REFERENCES " + TABLE_SUBSCRIBER + "(" + KEY_SUBSCRIBER_ID + "),"
            + "FOREIGN KEY(" + KEY_TAGGED_SUBSCRIBER + ") REFERENCES " + TABLE_SUBSCRIBER + "(" + KEY_SUBSCRIBER_ID + "),"
            + "FOREIGN KEY(" + KEY_POST_ID + ") REFERENCES " + TABLE_POST + "(" + KEY_POST_ID + ") ON DELETE CASCADE" +
            ")";

    // RestaurantProfile table create statement
    private static final String CREATE_TABLE_RESTAURANT_PROFILE = "CREATE TABLE " + TABLE_RESTAURANT_PROFILE +
            "("
            + KEY_RESTAURANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_SUBSCRIBER_ID + " INTEGER NOT NULL,"
            + KEY_ADDRESS + " TEXT NULL,"
            + KEY_TIMING + " TEXT NULL,"
            + KEY_CATEGORY + " TEXT NOT NULL,"
            + "FOREIGN KEY(" + KEY_SUBSCRIBER_ID + ") REFERENCES " + TABLE_SUBSCRIBER + "(" + KEY_SUBSCRIBER_ID + ") ON DELETE CASCADE" +
            ")";

    // Review table create statement
    private static final String CREATE_TABLE_REVIEW = "CREATE TABLE " + TABLE_REVIEW +
            "("
            + KEY_REVIEW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_POST_ID + " INTEGER NOT NULL,"
            + KEY_RESTAURANT_ID + " INTEGER NOT NULL,"
            + KEY_REVIEW_TEXT + " TEXT NOT NULL,"
            + KEY_TIMESTAMP + " TEXT NOT NULL,"
            + "FOREIGN KEY(" + KEY_POST_ID + ") REFERENCES " + TABLE_POST + "(" + KEY_POST_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY(" + KEY_RESTAURANT_ID + ") REFERENCES " + TABLE_RESTAURANT_PROFILE + "(" + KEY_RESTAURANT_ID + ") ON DELETE CASCADE" +
            ")";

    // Location table create statement
    private static final String CREATE_TABLE_LOCATION = "CREATE TABLE " + TABLE_LOCATION +
            "("
            + KEY_LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_CITY + " TEXT NOT NULL,"
            + KEY_AREA + " TEXT NULL" +
            ")";

    // Franchise_Location table create statement
    private static final String CREATE_TABLE_FRANCHISE_LOCATION = "CREATE TABLE " + TABLE_FRANCHISE_LOCATION +
            "("
            + KEY_FRANCHISE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_RESTAURANT_ID + " INTEGER NOT NULL,"
            + KEY_LOCATION_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY(" + KEY_LOCATION_ID + ") REFERENCES " + TABLE_LOCATION + "(" + KEY_LOCATION_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY(" + KEY_RESTAURANT_ID + ") REFERENCES " + TABLE_RESTAURANT_PROFILE + "(" + KEY_RESTAURANT_ID + ") ON DELETE CASCADE" +
            ")";

    // Checkin table create statement
    private static final String CREATE_TABLE_CHECKIN = "CREATE TABLE " + TABLE_CHECKIN +
            "("
            + KEY_CHECKIN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + KEY_POST_ID + " INTEGER NOT NULL,"
            + KEY_LOCATION_ID + " INTEGER NOT NULL,"
            + KEY_FRANCHISE_ID + " INTEGER NULL,"
            + KEY_TIMESTAMP + " TEXT NOT NULL,"
            + "CONSTRAINT Post_Checkin UNIQUE (" + KEY_POST_ID + "),"
            + "FOREIGN KEY(" + KEY_POST_ID + ") REFERENCES " + TABLE_POST + "(" + KEY_POST_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY(" + KEY_LOCATION_ID + ") REFERENCES " + TABLE_LOCATION + "(" + KEY_LOCATION_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY(" + KEY_FRANCHISE_ID + ") REFERENCES " + TABLE_FRANCHISE_LOCATION + "(" + KEY_FRANCHISE_ID + ") ON DELETE CASCADE" +
            ")";



    // ================================================= Tables CRUD Operations =================================================

    // Subscriber Table - CRUD Operations
    public boolean createSubscriber(Subscriber subscriber)
    {
        boolean status = false;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SUBSCRIBER_ID, subscriber.getSubscriberID());
        values.put(KEY_SUBSCRIBER_NAME, subscriber.getSubscriberName());
        values.put(KEY_PASSWORD, subscriber.getPassword());
        values.put(KEY_TYPE, subscriber.getType());
        values.put(KEY_EMAIL_ADDRESS, subscriber.getEmail());
        values.put(KEY_DISPLAY_PICTURE_ID, subscriber.getDisplayPictureID());
        values.put(KEY_PHONE_NUMBER, subscriber.getPhoneNumber());
        values.put(KEY_BIO, subscriber.getBio());
        values.put(KEY_GENDER, subscriber.getGender());
        values.put(KEY_DOB, subscriber.getDoB());

        // Insert row
        db.insert(TABLE_SUBSCRIBER, null, values);
        status = true;

        db.close();
        return status;
    }

    public Subscriber getSubscriber(String SubscriberName)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_SUBSCRIBER + " WHERE " + KEY_SUBSCRIBER_NAME + " = '" + SubscriberName + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
        { cursor.moveToFirst(); }

        Subscriber subscriber = new Subscriber();
        subscriber.setSubscriberID(cursor.getInt(cursor.getColumnIndex(KEY_SUBSCRIBER_ID)));
        subscriber.setSubscriberName(cursor.getString(cursor.getColumnIndex(KEY_SUBSCRIBER_NAME)));
        subscriber.setPassword(cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)));
        subscriber.setType(cursor.getString(cursor.getColumnIndex(KEY_TYPE)));
        subscriber.setEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL_ADDRESS)));
        subscriber.setDisplayPictureID(cursor.getInt(cursor.getColumnIndex(KEY_DISPLAY_PICTURE_ID)));
        subscriber.setPhoneNumber(cursor.getString(cursor.getColumnIndex(KEY_PHONE_NUMBER)));
        subscriber.setBio(cursor.getString(cursor.getColumnIndex(KEY_BIO)));
        subscriber.setGender(cursor.getString(cursor.getColumnIndex(KEY_GENDER)));
        subscriber.setDoB(cursor.getString(cursor.getColumnIndex(KEY_DOB)));

        db.close();
        return subscriber;
    }

    public List<Subscriber> getAllSubscribers()
    {
        List<Subscriber> subscriberList = new ArrayList<Subscriber>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_SUBSCRIBER;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to the list
        if (cursor.moveToFirst())
        {
            do
            {
                Subscriber subscriber = new Subscriber();
                subscriber.setSubscriberID(cursor.getInt(cursor.getColumnIndex(KEY_SUBSCRIBER_ID)));
                subscriber.setSubscriberName(cursor.getString(cursor.getColumnIndex(KEY_SUBSCRIBER_NAME)));
                subscriber.setPassword(cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)));
                subscriber.setType(cursor.getString(cursor.getColumnIndex(KEY_TYPE)));
                subscriber.setEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL_ADDRESS)));
                subscriber.setDisplayPictureID(cursor.getInt(cursor.getColumnIndex(KEY_DISPLAY_PICTURE_ID)));
                subscriber.setPhoneNumber(cursor.getString(cursor.getColumnIndex(KEY_PHONE_NUMBER)));
                subscriber.setBio(cursor.getString(cursor.getColumnIndex(KEY_BIO)));
                subscriber.setGender(cursor.getString(cursor.getColumnIndex(KEY_GENDER)));
                subscriber.setDoB(cursor.getString(cursor.getColumnIndex(KEY_DOB)));

                // Adding to the friendList
                subscriberList.add(subscriber);
            } while (cursor.moveToNext());
        }

        db.close();
        return subscriberList;
    }

    public boolean updateSubscriber(Subscriber subscriber)
    {
        boolean status = false;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SUBSCRIBER_NAME, subscriber.getSubscriberName());
        values.put(KEY_PASSWORD, subscriber.getPassword());
        values.put(KEY_TYPE, subscriber.getType());
        values.put(KEY_EMAIL_ADDRESS, subscriber.getEmail());
        values.put(KEY_DISPLAY_PICTURE_ID, subscriber.getDisplayPictureID());
        values.put(KEY_PHONE_NUMBER, subscriber.getPhoneNumber());
        values.put(KEY_BIO, subscriber.getBio());
        values.put(KEY_GENDER, subscriber.getGender());
        values.put(KEY_DOB, subscriber.getDoB());
        status = true;

        // updating row
        db.update(TABLE_SUBSCRIBER, values, KEY_SUBSCRIBER_ID + " = ?", new String[] { String.valueOf(subscriber.getSubscriberID()) });

        db.close();
        return status;
    }

    public boolean deleteSubscriber(String SubscriberName)
    {
        boolean status = false;
        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(TABLE_SUBSCRIBER, KEY_SUBSCRIBER_NAME + " = ?", new String[] { String.valueOf(SubscriberName) });
        status = true;

        db.close();
        return status;
    }

    public boolean deleteAllSubscriber()
    {
        boolean status = false;
        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(TABLE_SUBSCRIBER, null, null);
        status = true;

        db.close();
        return status;
    }



    // Friend Table - CRUD Operations
    public long createFriend(Subscriber Subscriber, Subscriber Friend)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SUBSCRIBER_ID, Subscriber.getSubscriberID());
        values.put(KEY_FRIEND_ID, Friend.getSubscriberID());
        values.put(KEY_SINCE, getDateTime());

        // Insert row
        long FriendID = db.insert(TABLE_FRIEND, null, values);

        db.close();
        return FriendID;
    }

    public Subscriber getFriend(long SubscriberID, String FriendName)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT " + KEY_LIST_ID + " FROM " + TABLE_FRIEND + " WHERE " + KEY_SUBSCRIBER_ID + " = " + SubscriberID + " AND " + KEY_FRIEND_ID + " = (SELECT " + TABLE_SUBSCRIBER + "." + KEY_SUBSCRIBER_ID + " FROM " + TABLE_SUBSCRIBER + " WHERE " + KEY_SUBSCRIBER_NAME + " = '" + FriendName + "')";

        Cursor cursor = null;
        int listID = -1;

        try
        {
            cursor = db.rawQuery(selectQuery, null);

            if (cursor != null)
            { cursor.moveToFirst(); }

            listID = cursor.getInt(cursor.getColumnIndex(KEY_LIST_ID));
        }
        catch (Exception e)
        {}

        Subscriber subscriber = new Subscriber();

        if(listID>=0)
        {
            subscriber = getSubscriber(FriendName);
        }

        db.close();
        return subscriber;
    }

    public List<Subscriber> getAllFriends(long SubscriberID)
    {
        List<Subscriber> friendsList = new ArrayList<Subscriber>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_SUBSCRIBER + " WHERE " + KEY_SUBSCRIBER_ID + " IN (SELECT " + KEY_FRIEND_ID + " FROM " + TABLE_FRIEND + " WHERE " + KEY_SUBSCRIBER_ID + " = " + SubscriberID + ")";

        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to the list
        if (cursor.moveToFirst())
        {
            do
            {
                Subscriber subscriber = new Subscriber();
                subscriber.setSubscriberID(cursor.getInt(cursor.getColumnIndex(KEY_SUBSCRIBER_ID)));
                subscriber.setSubscriberName(cursor.getString(cursor.getColumnIndex(KEY_SUBSCRIBER_NAME)));
                subscriber.setPassword(cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)));
                subscriber.setType(cursor.getString(cursor.getColumnIndex(KEY_TYPE)));
                subscriber.setEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL_ADDRESS)));
                subscriber.setDisplayPictureID(cursor.getInt(cursor.getColumnIndex(KEY_DISPLAY_PICTURE_ID)));
                subscriber.setPhoneNumber(cursor.getString(cursor.getColumnIndex(KEY_PHONE_NUMBER)));
                subscriber.setBio(cursor.getString(cursor.getColumnIndex(KEY_BIO)));
                subscriber.setGender(cursor.getString(cursor.getColumnIndex(KEY_GENDER)));
                subscriber.setDoB(cursor.getString(cursor.getColumnIndex(KEY_DOB)));

                // Adding to the friendList
                friendsList.add(subscriber);
            } while (cursor.moveToNext());
        }

        db.close();
        return friendsList;
    }

    public boolean deleteFriend(long SubscriberID, String FriendName)
    {
        boolean status = false;
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT " + KEY_SUBSCRIBER_ID + " FROM " + TABLE_SUBSCRIBER + " WHERE " + KEY_SUBSCRIBER_NAME + " = '" + FriendName + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
        { cursor.moveToFirst(); }

        db.delete(TABLE_FRIEND, KEY_SUBSCRIBER_ID + " = ? AND " + KEY_FRIEND_ID + " = ?", new String[] { String.valueOf(SubscriberID), cursor.getString(cursor.getColumnIndex(KEY_SUBSCRIBER_ID)) });
        status = true;

        db.close();
        return status;
    }


    // ImageAlbum Table - CRUD Operations
    public long createImageAlbum(ImageAlbum imageAlbum)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ALBUM_NAME, imageAlbum.getAlbumName());
        values.put(KEY_SUBSCRIBER_ID, imageAlbum.getSubscriberID());

        // Insert row
        long AlbumID = db.insert(TABLE_IMAGE_AlBUM, null, values);

        db.close();
        return AlbumID;
    }

    public ImageAlbum getImageAlbum(String AlbumName)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_IMAGE_AlBUM + " WHERE " + KEY_ALBUM_NAME + " = '" + AlbumName + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null)
        { cursor.moveToFirst(); }

        ImageAlbum imageAlbum = new ImageAlbum();
        imageAlbum.setImageAlbumID(cursor.getInt(cursor.getColumnIndex(KEY_IMAGE_ALBUM_ID)));
        imageAlbum.setAlbumName(cursor.getString(cursor.getColumnIndex(KEY_ALBUM_NAME)));
        imageAlbum.setSubscriberID(cursor.getInt(cursor.getColumnIndex(KEY_SUBSCRIBER_ID)));

        db.close();
        return imageAlbum;
    }

    public List<ImageAlbum> getAllImageAlbums(long SubscriberID)
    {
        List<ImageAlbum> imageAlbumList = new ArrayList<ImageAlbum>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_IMAGE_AlBUM + " WHERE " + KEY_SUBSCRIBER_ID + "=" + SubscriberID;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to the list
        if (cursor.moveToFirst())
        {
            do
            {
                ImageAlbum imageAlbum = new ImageAlbum();
                imageAlbum.setImageAlbumID(cursor.getInt(cursor.getColumnIndex(KEY_IMAGE_ALBUM_ID)));
                imageAlbum.setAlbumName(cursor.getString(cursor.getColumnIndex(KEY_ALBUM_NAME)));
                imageAlbum.setSubscriberID(cursor.getInt(cursor.getColumnIndex(KEY_SUBSCRIBER_ID)));

                // Adding to the friendList
                imageAlbumList.add(imageAlbum);
            } while (cursor.moveToNext());
        }

        db.close();
        return imageAlbumList;
    }

    public boolean updateImageAlbum(ImageAlbum imageAlbum)
    {
        boolean status = false;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ALBUM_NAME, imageAlbum.getAlbumName());
        values.put(KEY_SUBSCRIBER_ID, imageAlbum.getSubscriberID());

        // updating row
        db.update(TABLE_IMAGE_AlBUM, values, KEY_IMAGE_ALBUM_ID + " = ?", new String[] { String.valueOf(imageAlbum.getImageAlbumID()) });

        db.close();
        return status;
    }

    public boolean deleteImageAlbum(String AlbumName)
    {
        boolean status = false;
        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(TABLE_IMAGE_AlBUM, KEY_ALBUM_NAME + " = ?", new String[] { String.valueOf(AlbumName) });
        status = true;

        db.close();
        return status;
    }



    // Post Table - CRUD Operations
    public long createPost(Post post)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SUBSCRIBER_ID, post.getSubscriberID());
        values.put(KEY_IMAGE_PRESENCE, post.getImagePresence());
        values.put(KEY_IMAGE_ALBUM_ID, post.getImageAlbumID());
        values.put(KEY_REVIEW_PRESENCE, post.getReviewPresence());
        values.put(KEY_CHECKIN_PRESENCE, post.getCheckinPresence());
        values.put(KEY_PRIVACY, post.getPrivacy());
        values.put(KEY_TIMESTAMP, post.getTimeStamp());
        values.put(KEY_POST_DESCRIPTION, post.getPostDescription());

        // Insert row
        long PostID = db.insert(TABLE_POST, null, values);

        db.close();
        return PostID;
    }

    public Post getPost(long PostID)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_POST + " WHERE " + KEY_POST_ID + " = " + PostID;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor!=null)
        { cursor.moveToFirst(); }

        Post post = new Post();
        post.setPostID(cursor.getInt(cursor.getColumnIndex(KEY_POST_ID)));
        post.setSubscriberID(cursor.getInt(cursor.getColumnIndex(KEY_SUBSCRIBER_ID)));
        post.setImagePresence(cursor.getInt(cursor.getColumnIndex(KEY_IMAGE_PRESENCE)));
        post.setImageAlbumID(cursor.getInt(cursor.getColumnIndex(KEY_IMAGE_ALBUM_ID)));
        post.setReviewPresence(cursor.getInt(cursor.getColumnIndex(KEY_REVIEW_PRESENCE)));
        post.setCheckinPresence(cursor.getInt(cursor.getColumnIndex(KEY_CHECKIN_PRESENCE)));
        post.setPrivacy(cursor.getString(cursor.getColumnIndex(KEY_PRIVACY)));
        post.setTimeStamp(cursor.getString(cursor.getColumnIndex(KEY_TIMESTAMP)));
        post.setPostDescription(cursor.getString(cursor.getColumnIndex(KEY_POST_DESCRIPTION)));

        db.close();
        return post;
    }

    public List<Post> getAllPosts(long SubscriberID)
    {
        List<Post> postsList = new ArrayList<Post>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_POST + " WHERE " + KEY_SUBSCRIBER_ID + "=" + SubscriberID;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to the list
        if (cursor.moveToFirst())
        {
            do
            {
                Post post = new Post();
                post.setPostID(cursor.getInt(cursor.getColumnIndex(KEY_POST_ID)));
                post.setSubscriberID(cursor.getInt(cursor.getColumnIndex(KEY_SUBSCRIBER_ID)));
                post.setImagePresence(cursor.getInt(cursor.getColumnIndex(KEY_IMAGE_PRESENCE)));
                post.setImageAlbumID(cursor.getInt(cursor.getColumnIndex(KEY_IMAGE_ALBUM_ID)));
                post.setReviewPresence(cursor.getInt(cursor.getColumnIndex(KEY_REVIEW_PRESENCE)));
                post.setCheckinPresence(cursor.getInt(cursor.getColumnIndex(KEY_CHECKIN_PRESENCE)));
                post.setPrivacy(cursor.getString(cursor.getColumnIndex(KEY_PRIVACY)));
                post.setTimeStamp(cursor.getString(cursor.getColumnIndex(KEY_TIMESTAMP)));
                post.setPostDescription(cursor.getString(cursor.getColumnIndex(KEY_POST_DESCRIPTION)));

                // Adding to the friendList
                postsList.add(post);
            } while (cursor.moveToNext());
        }

        db.close();
        return postsList;
    }

    public boolean updatePost(Post post)
    {
        boolean status = false;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SUBSCRIBER_ID, post.getSubscriberID());
        values.put(KEY_IMAGE_PRESENCE, post.getImagePresence());
        values.put(KEY_IMAGE_ALBUM_ID, post.getImageAlbumID());
        values.put(KEY_REVIEW_PRESENCE, post.getReviewPresence());
        values.put(KEY_CHECKIN_PRESENCE, post.getCheckinPresence());
        values.put(KEY_PRIVACY, post.getPrivacy());
        values.put(KEY_TIMESTAMP, post.getTimeStamp());
        values.put(KEY_POST_DESCRIPTION, post.getPostDescription());
        status = true;

        // updating row
        db.update(TABLE_POST, values, KEY_POST_ID + " = ?", new String[] { String.valueOf(post.getPostID()) });

        db.close();
        return status;
    }

    public boolean deletePost(long PostID)
    {
        boolean status = false;
        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(TABLE_POST, KEY_POST_ID + " = ?", new String[] { String.valueOf(PostID) });
        status = true;

        db.close();
        return status;
    }



    // Images Table - CRUD Operations
    public long createImage(Images image)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IMAGE_ALBUM_ID, image.getImageAlbumID());
        values.put(KEY_POST_ID, image.getPostID());

        // Insert row
        long ImageID = db.insert(TABLE_IMAGES, null, values);

        db.close();
        return ImageID;
    }

    public Images getImage(long ImageID)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_IMAGES + " WHERE " + KEY_IMAGE_ID + " = " + ImageID;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor!=null)
        { cursor.moveToFirst(); }

        Images image = new Images();
        image.setImageID(cursor.getInt(cursor.getColumnIndex(KEY_IMAGE_ID)));
        image.setImageAlbumID(cursor.getInt(cursor.getColumnIndex(KEY_IMAGE_ALBUM_ID)));
        image.setPostID(cursor.getInt(cursor.getColumnIndex(KEY_POST_ID)));

        db.close();
        return image;
    }

    public List<Images> getAllImages(long SubscriberID)
    {
        List<Images> imageList = new ArrayList<Images>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_IMAGES + " WHERE " + KEY_IMAGE_ALBUM_ID + " IN ( SELECT " + KEY_IMAGE_ALBUM_ID + " FROM " + TABLE_IMAGE_AlBUM + " WHERE " + KEY_SUBSCRIBER_ID + "=" + SubscriberID + ")";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to the list
        if (cursor.moveToFirst())
        {
            do
            {
                Images image = new Images();
                image.setImageID(cursor.getInt(cursor.getColumnIndex(KEY_IMAGE_ID)));
                image.setImageAlbumID(cursor.getInt(cursor.getColumnIndex(KEY_IMAGE_ALBUM_ID)));
                image.setPostID(cursor.getInt(cursor.getColumnIndex(KEY_POST_ID)));

                // Adding to the friendList
                imageList.add(image);
            } while (cursor.moveToNext());
        }

        db.close();
        return imageList;
    }

    public boolean updateImage(Images image)
    {
        boolean status = false;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IMAGE_ALBUM_ID, image.getImageAlbumID());
        values.put(KEY_POST_ID, image.getPostID());
        status = true;

        // updating row
        db.update(TABLE_IMAGES, values, KEY_IMAGE_ID + " = ?", new String[] { String.valueOf(image.getImageID()) });

        db.close();
        return status;
    }

    public boolean deleteImage(long ImageID)
    {
        boolean status = false;
        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(TABLE_IMAGES, KEY_IMAGE_ID + " = ?", new String[] { String.valueOf(ImageID) });
        status = true;

        db.close();
        return status;
    }



    // Like Table - CRUD Operations
    public long createLike(Like like)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SUBSCRIBER_ID, like.getSubscriberID());
        values.put(KEY_POST_ID, like.getPostID());
        values.put(KEY_TIMESTAMP, like.getTimeStamp());

        // Insert row
        long LikeID = db.insert(TABLE_LIKE, null, values);

        db.close();
        return LikeID;
    }

    public Like getLike(long LikeID)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_LIKE + " WHERE " + KEY_LIST_ID + " = " + LikeID;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor!=null)
        { cursor.moveToFirst(); }

        Like like = new Like();
        like.setLikeID(cursor.getInt(cursor.getColumnIndex(KEY_LIKE_ID)));
        like.setSubscriberID(cursor.getInt(cursor.getColumnIndex(KEY_SUBSCRIBER_ID)));
        like.setPostID(cursor.getInt(cursor.getColumnIndex(KEY_IMAGE_PRESENCE)));
        like.setTimeStamp(cursor.getString(cursor.getColumnIndex(KEY_TIMESTAMP)));

        db.close();
        return like;
    }

    public List<Like> getAllLikes(long SubscriberID)
    {
        List<Like> likesList = new ArrayList<Like>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_LIKE + " WHERE " + KEY_SUBSCRIBER_ID + "=" + SubscriberID;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to the list
        if (cursor.moveToFirst())
        {
            do
            {
                Like like = new Like();
                like.setLikeID(cursor.getInt(cursor.getColumnIndex(KEY_LIKE_ID)));
                like.setSubscriberID(cursor.getInt(cursor.getColumnIndex(KEY_SUBSCRIBER_ID)));
                like.setPostID(cursor.getInt(cursor.getColumnIndex(KEY_IMAGE_PRESENCE)));
                like.setTimeStamp(cursor.getString(cursor.getColumnIndex(KEY_TIMESTAMP)));

                // Adding to the friendList
                likesList.add(like);
            } while (cursor.moveToNext());
        }

        db.close();
        return likesList;
    }

    public boolean deleteLike(long LikeID)
    {
        boolean status = false;
        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(TABLE_LIKE, KEY_LIKE_ID + " = ?", new String[] { String.valueOf(LikeID) });
        status = true;

        db.close();
        return status;
    }



    // Comment Table - CRUD Operations
    public long createComment(Comment comment)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SUBSCRIBER_ID, comment.getSubscriberID());
        values.put(KEY_POST_ID, comment.getPostID());
        values.put(KEY_COMMENT_TEXT, comment.getCommentText());
        values.put(KEY_TIMESTAMP, comment.getTimeStamp());

        // Insert row
        long CommentID = db.insert(TABLE_COMMENT, null, values);

        db.close();
        return CommentID;
    }

    public Comment getComment(long CommentID)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_COMMENT + " WHERE " + KEY_COMMENT_ID + " = " + CommentID;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor!=null)
        { cursor.moveToFirst(); }

        Comment comment = new Comment();
        comment.setCommentID(cursor.getInt(cursor.getColumnIndex(KEY_COMMENT_ID)));
        comment.setSubscriberID(cursor.getInt(cursor.getColumnIndex(KEY_SUBSCRIBER_ID)));
        comment.setPostID(cursor.getInt(cursor.getColumnIndex(KEY_POST_ID)));
        comment.setCommentText(cursor.getString(cursor.getColumnIndex(KEY_COMMENT_TEXT)));
        comment.setTimeStamp(cursor.getString(cursor.getColumnIndex(KEY_TIMESTAMP)));

        db.close();
        return comment;
    }

    public List<Comment> getAllComments(long SubscriberID)
    {
        List<Comment> commentsList = new ArrayList<Comment>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_COMMENT + " WHERE " + KEY_SUBSCRIBER_ID + "=" + SubscriberID;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to the list
        if (cursor.moveToFirst())
        {
            do
            {
                Comment comment = new Comment();
                comment.setCommentID(cursor.getInt(cursor.getColumnIndex(KEY_COMMENT_ID)));
                comment.setSubscriberID(cursor.getInt(cursor.getColumnIndex(KEY_SUBSCRIBER_ID)));
                comment.setPostID(cursor.getInt(cursor.getColumnIndex(KEY_POST_ID)));
                comment.setCommentText(cursor.getString(cursor.getColumnIndex(KEY_COMMENT_TEXT)));
                comment.setTimeStamp(cursor.getString(cursor.getColumnIndex(KEY_TIMESTAMP)));

                // Adding to the friendList
                commentsList.add(comment);
            } while (cursor.moveToNext());
        }

        db.close();
        return commentsList;
    }

    public boolean updateComment(Comment comment)
    {
        boolean status = false;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_COMMENT_ID, comment.getCommentID());
        values.put(KEY_SUBSCRIBER_ID, comment.getSubscriberID());
        values.put(KEY_POST_ID, comment.getPostID());
        values.put(KEY_COMMENT_TEXT, comment.getCommentText());
        values.put(KEY_TIMESTAMP, comment.getTimeStamp());
        status = true;

        // updating row
        db.update(TABLE_COMMENT, values, KEY_COMMENT_ID + " = ?", new String[] { String.valueOf(comment.getCommentID()) });

        db.close();
        return status;
    }

    public boolean deleteComment(long CommentID)
    {
        boolean status = false;
        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(TABLE_COMMENT, KEY_COMMENT_ID + " = ?", new String[] { String.valueOf(CommentID) });
        status = true;

        db.close();
        return status;
    }



    // Tag Table - CRUD Operations
    public long createTag(Tag tag)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SUBSCRIBER_ID, tag.getSubscriberID());
        values.put(KEY_TAGGED_SUBSCRIBER, tag.getTaggedSubscriber());
        values.put(KEY_POST_ID, tag.getPostID());
        values.put(KEY_TIMESTAMP, tag.getTimeStamp());

        // Insert row
        long TagID = db.insert(TABLE_TAG, null, values);

        db.close();
        return TagID;
    }

    public Tag getTag(long TagID)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_TAG + " WHERE " + KEY_TAG_ID + " = " + TagID;

        Cursor cursor = db.rawQuery(selectQuery, null);

        Tag tag = new Tag();
        tag.setTagID(cursor.getInt(cursor.getColumnIndex(KEY_TAG_ID)));
        tag.setSubscriberID(cursor.getInt(cursor.getColumnIndex(KEY_SUBSCRIBER_ID)));
        tag.setTaggedSubscriber(cursor.getInt(cursor.getColumnIndex(KEY_TAGGED_SUBSCRIBER)));
        tag.setPostID(cursor.getInt(cursor.getColumnIndex(KEY_POST_ID)));
        tag.setTimeStamp(cursor.getString(cursor.getColumnIndex(KEY_TIMESTAMP)));

        db.close();
        return tag;
    }

    public List<Tag> getAllTags(long SubscriberID)
    {
        List<Tag> tagsList = new ArrayList<Tag>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_TAG + " WHERE " + KEY_SUBSCRIBER_ID + "=" + SubscriberID;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to the list
        if (cursor.moveToFirst())
        {
            do
            {
                Tag tag = new Tag();
                tag.setTagID(cursor.getInt(cursor.getColumnIndex(KEY_TAG_ID)));
                tag.setSubscriberID(cursor.getInt(cursor.getColumnIndex(KEY_SUBSCRIBER_ID)));
                tag.setTaggedSubscriber(cursor.getInt(cursor.getColumnIndex(KEY_TAGGED_SUBSCRIBER)));
                tag.setPostID(cursor.getInt(cursor.getColumnIndex(KEY_POST_ID)));
                tag.setTimeStamp(cursor.getString(cursor.getColumnIndex(KEY_TIMESTAMP)));

                // Adding to the friendList
                tagsList.add(tag);
            } while (cursor.moveToNext());
        }

        db.close();
        return tagsList;
    }

    public boolean updateTag(Tag tag)
    {
        boolean status = false;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TAG_ID, tag.getTagID());
        values.put(KEY_SUBSCRIBER_ID, tag.getSubscriberID());
        values.put(KEY_TAGGED_SUBSCRIBER, tag.getTaggedSubscriber());
        values.put(KEY_POST_ID, tag.getPostID());
        values.put(KEY_TIMESTAMP, tag.getTimeStamp());
        status = true;

        // updating row
        db.update(TABLE_TAG, values, KEY_TAG_ID + " = ?", new String[] { String.valueOf(tag.getTagID()) });

        db.close();
        return status;
    }

    public boolean deleteTag(long TagID)
    {
        boolean status = false;
        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(TABLE_TAG, KEY_TAG_ID + " = ?", new String[] { String.valueOf(TagID) });
        status = true;

        db.close();
        return status;
    }



    // Restaurant Profile Table - CRUD Operations
    public long createRestaurantProfile(RestaurantProfile restaurantProfile)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SUBSCRIBER_ID, restaurantProfile.getSubscriberID());
        values.put(KEY_ADDRESS, restaurantProfile.getAddress());
        values.put(KEY_TIMING, restaurantProfile.getTiming());
        values.put(KEY_CATEGORY, restaurantProfile.getCategory());

        // Insert row
        long RestaurantProfileID = db.insert(TABLE_RESTAURANT_PROFILE, null, values);

        db.close();
        return RestaurantProfileID;
    }

    public RestaurantProfile getRestaurantProfile(long RestaurantProfileID)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_RESTAURANT_PROFILE + " WHERE " + KEY_RESTAURANT_ID + " = " + RestaurantProfileID;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor!=null)
        { cursor.moveToFirst(); }

        RestaurantProfile restaurantProfile = new RestaurantProfile();
        restaurantProfile.setRestaurantID(cursor.getInt(cursor.getColumnIndex(KEY_RESTAURANT_ID)));
        restaurantProfile.setSubscriberID(cursor.getInt(cursor.getColumnIndex(KEY_SUBSCRIBER_ID)));
        restaurantProfile.setAddress(cursor.getString(cursor.getColumnIndex(KEY_ADDRESS)));
        restaurantProfile.setTiming(cursor.getString(cursor.getColumnIndex(KEY_TIMING)));
        restaurantProfile.setCategory(cursor.getString(cursor.getColumnIndex(KEY_CATEGORY)));

        db.close();
        return restaurantProfile;
    }

    public List<RestaurantProfile> getAllRestaurantProfiles()
    {
        List<RestaurantProfile> restaurantProfilesList = new ArrayList<RestaurantProfile>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_RESTAURANT_PROFILE;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to the list
        if (cursor.moveToFirst())
        {
            do
            {
                RestaurantProfile restaurantProfile = new RestaurantProfile();
                restaurantProfile.setRestaurantID(cursor.getInt(cursor.getColumnIndex(KEY_RESTAURANT_ID)));
                restaurantProfile.setSubscriberID(cursor.getInt(cursor.getColumnIndex(KEY_SUBSCRIBER_ID)));
                restaurantProfile.setAddress(cursor.getString(cursor.getColumnIndex(KEY_ADDRESS)));
                restaurantProfile.setTiming(cursor.getString(cursor.getColumnIndex(KEY_TIMING)));
                restaurantProfile.setCategory(cursor.getString(cursor.getColumnIndex(KEY_CATEGORY)));

                // Adding to the friendList
                restaurantProfilesList.add(restaurantProfile);
            } while (cursor.moveToNext());
        }

        db.close();
        return restaurantProfilesList;
    }

    public boolean updateRestaurantProfile(RestaurantProfile restaurantProfile)
    {
        boolean status = false;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_RESTAURANT_ID, restaurantProfile.getRestaurantID());
        values.put(KEY_SUBSCRIBER_ID, restaurantProfile.getSubscriberID());
        values.put(KEY_ADDRESS, restaurantProfile.getAddress());
        values.put(KEY_TIMING, restaurantProfile.getTiming());
        values.put(KEY_CATEGORY, restaurantProfile.getCategory());
        status = true;

        // updating row
        db.update(TABLE_RESTAURANT_PROFILE, values, KEY_RESTAURANT_ID + " = ?", new String[] { String.valueOf(restaurantProfile.getRestaurantID()) });

        db.close();
        return status;
    }

    public boolean deleteRestaurantProfile(long RestaurantID)
    {
        boolean status = false;
        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(TABLE_RESTAURANT_PROFILE, KEY_RESTAURANT_ID + " = ?", new String[] { String.valueOf(RestaurantID) });
        status = true;

        db.close();
        return status;
    }




    // Review Table - CRUD Operations
    public long createReview(Review review)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_POST_ID, review.getPostID());
        values.put(KEY_RESTAURANT_ID, review.getRestaurantID());
        values.put(KEY_REVIEW_TEXT, review.getReviewText());
        values.put(KEY_TIMESTAMP, review.getTimeStamp());

        // Insert row
        long ReviewID = db.insert(TABLE_REVIEW, null, values);

        db.close();
        return ReviewID;
    }

    public Review getReivew(long ReviewID)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_REVIEW + " WHERE " + KEY_REVIEW_ID + " = " + ReviewID;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor!=null)
        { cursor.moveToFirst(); }

        Review review = new Review();
        review.setReviewID(cursor.getInt(cursor.getColumnIndex(KEY_REVIEW_ID)));
        review.setPostID(cursor.getInt(cursor.getColumnIndex(KEY_POST_ID)));
        review.setRestaurantID(cursor.getInt(cursor.getColumnIndex(KEY_RESTAURANT_ID)));
        review.setReviewText(cursor.getString(cursor.getColumnIndex(KEY_REVIEW_TEXT)));
        review.setTimeStamp(cursor.getString(cursor.getColumnIndex(KEY_TIMESTAMP)));

        db.close();
        return review;
    }

    public List<Review> getAllReviews(long SubscriberID, long RestaurantID)
    {
        List<Review> reviewsList = new ArrayList<Review>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "";

        if(SubscriberID!=-1)
        {
            selectQuery = "SELECT * FROM " + TABLE_REVIEW + " WHERE " + KEY_POST_ID + " IN(SELECT " + KEY_POST_ID + " FROM " + TABLE_POST + " WHERE " + KEY_SUBSCRIBER_ID + " = " + SubscriberID + " AND " + KEY_REVIEW_PRESENCE + " = 1)";
        }
        else if(RestaurantID!=-1)
        {
            selectQuery = "SELECT * FROM " + TABLE_REVIEW + " WHERE " + KEY_RESTAURANT_ID + "=" + RestaurantID;
        }

        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to the list
        if (cursor.moveToFirst())
        {
            do
            {
                Review review = new Review();
                review.setReviewID(cursor.getInt(cursor.getColumnIndex(KEY_REVIEW_ID)));
                review.setPostID(cursor.getInt(cursor.getColumnIndex(KEY_POST_ID)));
                review.setRestaurantID(cursor.getInt(cursor.getColumnIndex(KEY_RESTAURANT_ID)));
                review.setReviewText(cursor.getString(cursor.getColumnIndex(KEY_REVIEW_TEXT)));
                review.setTimeStamp(cursor.getString(cursor.getColumnIndex(KEY_TIMESTAMP)));

                // Adding to the friendList
                reviewsList.add(review);
            } while (cursor.moveToNext());
        }

        db.close();
        return reviewsList;
    }

    public boolean updateReview(Review review)
    {
        boolean status = false;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_REVIEW_ID, review.getReviewID());
        values.put(KEY_POST_ID, review.getPostID());
        values.put(KEY_RESTAURANT_ID, review.getRestaurantID());
        values.put(KEY_REVIEW_TEXT, review.getReviewText());
        values.put(KEY_TIMESTAMP, review.getTimeStamp());
        status = true;

        // updating row
        db.update(TABLE_REVIEW, values, KEY_REVIEW_ID + " = ?", new String[] { String.valueOf(review.getReviewID()) });

        db.close();
        return status;
    }

    public boolean deleteReview(long ReviewID)
    {
        boolean status = false;
        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(TABLE_REVIEW, KEY_REVIEW_ID + " = ?", new String[] { String.valueOf(ReviewID) });
        status = true;

        db.close();
        return status;
    }



    // Franchise_Location Table - CRUD Operations
    public long createFranchise_Location(Franchise_Location franchise_location)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_RESTAURANT_ID, franchise_location.getRestaurantID());
        values.put(KEY_LOCATION_ID, franchise_location.getLocationID());

        // Insert row
        long FranchiseID = db.insert(TABLE_FRANCHISE_LOCATION, null, values);

        db.close();
        return FranchiseID;
    }

    public Franchise_Location getFranchise(long FranchiseID)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_FRANCHISE_LOCATION + " WHERE " + KEY_FRANCHISE_ID + " = " + FranchiseID;

        Cursor cursor = db.rawQuery(selectQuery, null);

        Franchise_Location franchise_location = new Franchise_Location();
        franchise_location.setFranchiseID(cursor.getInt(cursor.getColumnIndex(KEY_FRANCHISE_ID)));
        franchise_location.setRestaurantID(cursor.getInt(cursor.getColumnIndex(KEY_RESTAURANT_ID)));
        franchise_location.setLocationID(cursor.getInt(cursor.getColumnIndex(KEY_LOCATION_ID)));

        db.close();
        return franchise_location;
    }

    public List<Franchise_Location> getAllFranchises(long RestaurantID)
    {
        List<Franchise_Location> franchisesList = new ArrayList<Franchise_Location>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_FRANCHISE_LOCATION + " WHERE " + KEY_RESTAURANT_ID + " = " + RestaurantID;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to the list
        if (cursor.moveToFirst())
        {
            do
            {
                Franchise_Location franchise_location = new Franchise_Location();
                franchise_location.setFranchiseID(cursor.getInt(cursor.getColumnIndex(KEY_FRANCHISE_ID)));
                franchise_location.setRestaurantID(cursor.getInt(cursor.getColumnIndex(KEY_RESTAURANT_ID)));
                franchise_location.setLocationID(cursor.getInt(cursor.getColumnIndex(KEY_LOCATION_ID)));

                // Adding to the friendList
                franchisesList.add(franchise_location);
            } while (cursor.moveToNext());
        }

        db.close();
        return franchisesList;
    }

    public boolean updateFranchise(Franchise_Location franchise_location)
    {
        boolean status = false;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FRANCHISE_ID, franchise_location.getLocationID());
        values.put(KEY_RESTAURANT_ID, franchise_location.getRestaurantID());
        values.put(KEY_LOCATION_ID, franchise_location.getLocationID());
        status = true;

        // updating row
        db.update(TABLE_FRANCHISE_LOCATION, values, KEY_FRANCHISE_ID + " = ?", new String[] { String.valueOf(franchise_location.getFranchiseID()) });

        db.close();
        return status;
    }

    public boolean deleteFranchise(long FranchiseID)
    {
        boolean status = false;
        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(TABLE_FRANCHISE_LOCATION, KEY_FRANCHISE_ID + " = ?", new String[] { String.valueOf(FranchiseID) });
        status = true;

        db.close();
        return status;
    }



    // Location Table - CRUD Operations
    public long createLocation(Location location)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CITY, location.getCity());
        values.put(KEY_AREA, location.getArea());

        // Insert row
        long LocationID = db.insert(TABLE_LOCATION, null, values);

        db.close();
        return LocationID;
    }

    public Location getLocation(long LocationID)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_LOCATION + " WHERE " + KEY_LOCATION_ID + " = " + LocationID;

        Cursor cursor = db.rawQuery(selectQuery, null);

        Location location = new Location();
        location.setLocationID(cursor.getInt(cursor.getColumnIndex(KEY_LOCATION_ID)));
        location.setCity(cursor.getString(cursor.getColumnIndex(KEY_CITY)));
        location.setArea(cursor.getString(cursor.getColumnIndex(KEY_AREA)));

        db.close();
        return location;
    }

    public List<Location> getAllLocations()
    {
        List<Location> locationsList = new ArrayList<Location>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_LOCATION;
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to the list
        if (cursor.moveToFirst())
        {
            do
            {
                Location location = new Location();
                location.setLocationID(cursor.getInt(cursor.getColumnIndex(KEY_LOCATION_ID)));
                location.setCity(cursor.getString(cursor.getColumnIndex(KEY_CITY)));
                location.setArea(cursor.getString(cursor.getColumnIndex(KEY_AREA)));

                // Adding to the friendList
                locationsList.add(location);
            } while (cursor.moveToNext());
        }

        db.close();
        return locationsList;
    }

    public boolean updateLocation(Location location)
    {
        boolean status = false;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LOCATION_ID, location.getLocationID());
        values.put(KEY_CITY, location.getCity());
        values.put(KEY_AREA, location.getArea());
        status = true;

        // updating row
        db.update(TABLE_LOCATION, values, KEY_LOCATION_ID + " = ?", new String[] { String.valueOf(location.getLocationID()) });

        db.close();
        return status;
    }

    public boolean deleteLocation(long LocationID)
    {
        boolean status = false;
        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(TABLE_LOCATION, KEY_LOCATION_ID + " = ?", new String[] { String.valueOf(LocationID) });
        status = true;

        db.close();
        return status;
    }



    // Checkin Table - CRUD Operations
    public long createCheckin(Checkin checkin)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_POST_ID, checkin.getPostID());
        values.put(KEY_LOCATION_ID, checkin.getLocationID());
        values.put(KEY_FRANCHISE_ID, checkin.getFranchiseID());
        values.put(KEY_TIMESTAMP, checkin.getTimeStamp());

        // Insert row
        long CheckinID = db.insert(TABLE_REVIEW, null, values);

        db.close();
        return CheckinID;
    }

    public Checkin getCheckin(long CheckID)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_CHECKIN + " WHERE " + KEY_CHECKIN_ID + " = " + CheckID;

        Cursor cursor = db.rawQuery(selectQuery, null);

        Checkin checkin = new Checkin();
        checkin.setCheckinID(cursor.getInt(cursor.getColumnIndex(KEY_CHECKIN_ID)));
        checkin.setPostID(cursor.getInt(cursor.getColumnIndex(KEY_POST_ID)));
        checkin.setLocationID(cursor.getInt(cursor.getColumnIndex(KEY_LOCATION_ID)));
        checkin.setFranchiseID(cursor.getInt(cursor.getColumnIndex(KEY_FRANCHISE_ID)));
        checkin.setTimeStamp(cursor.getString(cursor.getColumnIndex(KEY_TIMESTAMP)));

        db.close();
        return checkin;
    }

    public List<Checkin> getAllCheckins(long SubscriberID)
    {
        List<Checkin> checkinsList = new ArrayList<Checkin>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_CHECKIN + " WHERE " + KEY_POST_ID + " IN (SELECT " + KEY_POST_ID + " FROM " + TABLE_POST + " WHERE " + KEY_SUBSCRIBER_ID + " = " + SubscriberID + " AND" + KEY_CHECKIN_PRESENCE + "=1)";
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all rows and adding to the list
        if (cursor.moveToFirst())
        {
            do
            {
                Checkin checkin = new Checkin();
                checkin.setCheckinID(cursor.getInt(cursor.getColumnIndex(KEY_CHECKIN_ID)));
                checkin.setPostID(cursor.getInt(cursor.getColumnIndex(KEY_POST_ID)));
                checkin.setLocationID(cursor.getInt(cursor.getColumnIndex(KEY_LOCATION_ID)));
                checkin.setFranchiseID(cursor.getInt(cursor.getColumnIndex(KEY_FRANCHISE_ID)));
                checkin.setTimeStamp(cursor.getString(cursor.getColumnIndex(KEY_TIMESTAMP)));

                // Adding to the friendList
                checkinsList.add(checkin);
            } while (cursor.moveToNext());
        }

        db.close();
        return checkinsList;
    }

    public boolean updateCheckin(Checkin checkin)
    {
        boolean status = false;
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CHECKIN_ID, checkin.getCheckinID());
        values.put(KEY_POST_ID, checkin.getPostID());
        values.put(KEY_LOCATION_ID, checkin.getLocationID());
        values.put(KEY_FRANCHISE_ID, checkin.getFranchiseID());
        values.put(KEY_TIMESTAMP, checkin.getTimeStamp());
        status = true;

        // updating row
        db.update(TABLE_CHECKIN, values, KEY_CHECKIN_ID + " = ?", new String[] { String.valueOf(checkin.getCheckinID()) });

        db.close();
        return status;
    }

    public boolean deleteCheckin(long CheckinID)
    {
        boolean status = false;
        SQLiteDatabase db = this.getReadableDatabase();

        db.delete(TABLE_CHECKIN, KEY_CHECKIN_ID + " = ?", new String[] { String.valueOf(CheckinID) });
        status = true;

        db.close();
        return status;
    }

    // ============================================= com.example.sheharyararif.DatabaseHelper Class Functions =============================================


    // Constructor
    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db)
    {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON");
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // Creating Tables
        db.execSQL(CREATE_TABLE_FRIEND);
        db.execSQL(CREATE_TABLE_SUBSCRIBER);
        db.execSQL(CREATE_TABLE_POST);
        db.execSQL(CREATE_TABLE_IMAGES);
        db.execSQL(CREATE_TABLE_IMAGE_ALBUM);
        db.execSQL(CREATE_TABLE_LIKE);
        db.execSQL(CREATE_TABLE_COMMENT);
        db.execSQL(CREATE_TABLE_TAG);
        db.execSQL(CREATE_TABLE_CHECKIN);
        db.execSQL(CREATE_TABLE_REVIEW);
        db.execSQL(CREATE_TABLE_RESTAURANT_PROFILE);
        db.execSQL(CREATE_TABLE_FRANCHISE_LOCATION);
        db.execSQL(CREATE_TABLE_LOCATION);
    }

    // Dropping Older Tables upon every execution
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // On upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIEND);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBSCRIBER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE_AlBUM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIKE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHECKIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEW);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANT_PROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRANCHISE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);

        // Create new copies of these Tables
        onCreate(db);
    }


    // getDatetime()
    public String getDateTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
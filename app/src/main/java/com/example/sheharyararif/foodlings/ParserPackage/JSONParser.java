package com.example.sheharyararif.foodlings.ParserPackage;

/**
 * Created by Sheharyar Arif on 3/5/2017.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import android.widget.Toast;

import com.example.sheharyararif.foodlings.DatabaseModel.Comment;
import com.example.sheharyararif.foodlings.DatabaseModel.Friend;
import com.example.sheharyararif.foodlings.DatabaseModel.Like;
import com.example.sheharyararif.foodlings.DatabaseModel.Post;
import com.example.sheharyararif.foodlings.DatabaseModel.Review;
import com.example.sheharyararif.foodlings.DatabaseModel.SearchResult;
import com.example.sheharyararif.foodlings.DatabaseModel.Subscriber;

public class JSONParser
{

    static InputStream inputStream = null;
    static JSONObject jsonObject = null;
    static String json = "";

    // constructor
    public JSONParser()
    { }

    public JSONObject getJSONFromUrl(String url, String method, Post post, Subscriber subscriber, Comment comment, Like like, SearchResult searchResult, Review review, Friend friend)
    {
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = null;

            if (method == "POST") {
                HttpPost httpPost = new HttpPost(url);

                if (post != null) {
                    List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
                    nameValuePair.add(new BasicNameValuePair("PostID", post.getPostID()));
                    nameValuePair.add(new BasicNameValuePair("SubscriberID", post.getSubscriberID()));
                    nameValuePair.add(new BasicNameValuePair("ImagePresence", post.getImagePresence()));
                    nameValuePair.add(new BasicNameValuePair("ImageAlbumID", post.getImageAlbumID()));
                    nameValuePair.add(new BasicNameValuePair("ReviewPresence", post.getReviewPresence()));
                    nameValuePair.add(new BasicNameValuePair("CheckinPresence", post.getCheckinPresence()));
                    nameValuePair.add(new BasicNameValuePair("Privacy", post.getPrivacy()));
                    nameValuePair.add(new BasicNameValuePair("Timestamp", post.getTimeStamp()));
                    nameValuePair.add(new BasicNameValuePair("PostDescription", post.getPostDescription()));
                    nameValuePair.add(new BasicNameValuePair("ImageString", post.getImageString()));
                    nameValuePair.add(new BasicNameValuePair("MenuPresence", post.getMenuPresence()));
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                } else if (subscriber != null) {
                    List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
                    nameValuePair.add(new BasicNameValuePair("SubscriberID", subscriber.getSubscriberID()));
                    nameValuePair.add(new BasicNameValuePair("SubscriberName", subscriber.getSubscriberName()));
                    nameValuePair.add(new BasicNameValuePair("Password", subscriber.getPassword()));
                    nameValuePair.add(new BasicNameValuePair("Type", subscriber.getType()));
                    nameValuePair.add(new BasicNameValuePair("Email", subscriber.getEmail()));
                    nameValuePair.add(new BasicNameValuePair("DisplayPictureID", subscriber.getDisplayPictureID()));
                    nameValuePair.add(new BasicNameValuePair("PhoneNumber", subscriber.getPhoneNumber()));
                    nameValuePair.add(new BasicNameValuePair("Bio", subscriber.getBio()));
                    nameValuePair.add(new BasicNameValuePair("Gender", subscriber.getGender()));
                    nameValuePair.add(new BasicNameValuePair("DoB", subscriber.getDoB()));
                    nameValuePair.add(new BasicNameValuePair("DisplayPicture", subscriber.getDisplayPicture()));
                    nameValuePair.add(new BasicNameValuePair("CoverPhoto", subscriber.getCoverPhoto()));
                    nameValuePair.add(new BasicNameValuePair("Address", subscriber.getAddress()));
                    nameValuePair.add(new BasicNameValuePair("Timing", subscriber.getTiming()));
                    nameValuePair.add(new BasicNameValuePair("Category", subscriber.getCategory()));
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                } else if (comment != null) {
                    List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
                    nameValuePair.add(new BasicNameValuePair("SubscriberID", comment.getSubscriberID()));
                    nameValuePair.add(new BasicNameValuePair("PostID", comment.getPostID()));
                    nameValuePair.add(new BasicNameValuePair("CommentText", comment.getCommentText()));
                    nameValuePair.add(new BasicNameValuePair("Timestamp", comment.getTimeStamp()));
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                } else if (like != null) {
                    List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
                    nameValuePair.add(new BasicNameValuePair("LikeID", like.getLikeID()));
                    nameValuePair.add(new BasicNameValuePair("SubscriberID", like.getSubscriberID()));
                    nameValuePair.add(new BasicNameValuePair("PostID", like.getPostID()));
                    nameValuePair.add(new BasicNameValuePair("Timestamp", like.getTimeStamp()));
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                } else if(searchResult != null){
                    List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
                    nameValuePair.add(new BasicNameValuePair("SubscriberID", searchResult.getSubscriberID()));
                    nameValuePair.add(new BasicNameValuePair("Name", searchResult.getName()));
                    nameValuePair.add(new BasicNameValuePair("Type", searchResult.getType()));
                    nameValuePair.add(new BasicNameValuePair("Email", searchResult.getEmail()));
                    nameValuePair.add(new BasicNameValuePair("DisplayPicture", searchResult.getDisplayPicture()));
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                } else if(review != null){
                    List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
                    nameValuePair.add(new BasicNameValuePair("ReviewID", review.getReviewID()));
                    nameValuePair.add(new BasicNameValuePair("PostID", review.getPostID()));
                    nameValuePair.add(new BasicNameValuePair("SubscriberID", review.getSubscriberID()));
                    nameValuePair.add(new BasicNameValuePair("RestaurantID", review.getRestaurantID()));
                    nameValuePair.add(new BasicNameValuePair("ReviewText", review.getReviewText()));
                    nameValuePair.add(new BasicNameValuePair("Timestamp", review.getTimeStamp()));
                    nameValuePair.add(new BasicNameValuePair("Taste", review.getTaste()));
                    nameValuePair.add(new BasicNameValuePair("Ambience", review.getAmbience()));
                    nameValuePair.add(new BasicNameValuePair("Service", review.getService()));
                    nameValuePair.add(new BasicNameValuePair("OrderTime", review.getOrderTime()));
                    nameValuePair.add(new BasicNameValuePair("Price", review.getPrice()));
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                } else if(friend != null){
                    List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
                    nameValuePair.add(new BasicNameValuePair("ListID", friend.getListID()));
                    nameValuePair.add(new BasicNameValuePair("SubscriberID", friend.getSubscriberID()));
                    nameValuePair.add(new BasicNameValuePair("FriendID", friend.getFriendID()));
                    nameValuePair.add(new BasicNameValuePair("Since", friend.getSince()));
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
                }

                httpResponse = httpClient.execute(httpPost);
            } else if (method == "GET") {
                HttpGet httpGet = new HttpGet(url);
                httpResponse = httpClient.execute(httpGet);
            }

            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            inputStream.close();
            json = stringBuilder.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // Parsing the string to a JSON object
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jsonObject;
    }
}

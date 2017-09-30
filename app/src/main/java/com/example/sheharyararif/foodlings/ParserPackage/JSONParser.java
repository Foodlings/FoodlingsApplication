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
import com.example.sheharyararif.foodlings.DatabaseModel.Post;
import com.example.sheharyararif.foodlings.DatabaseModel.Subscriber;

public class JSONParser
{

    static InputStream inputStream = null;
    static JSONObject jsonObject = null;
    static String json = "";

    // constructor
    public JSONParser()
    { }

    public JSONObject getJSONFromUrl(String url, String method, Post post, Subscriber subscriber)
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

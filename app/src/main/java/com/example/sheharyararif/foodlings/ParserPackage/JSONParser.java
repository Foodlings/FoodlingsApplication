package com.example.sheharyararif.foodlings.ParserPackage;

/**
 * Created by Sheharyar Arif on 3/5/2017.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser
{

    static InputStream inputStream = null;
    static JSONObject jsonObject = null;
    static String json = "";

    // constructor
    public JSONParser()
    { }

    public JSONObject getJSONFromUrl(String url, String method)
    {
        try
        {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = null;

            if(method == "POST")
            {
                HttpPost httpPost = new HttpPost(url);
                httpResponse = httpClient.execute(httpPost);
            }
            else if(method == "GET")
            {
                HttpGet httpGet = new HttpGet(url);
                httpResponse = httpClient.execute(httpGet);
            }

            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();
        }
        catch (UnsupportedEncodingException e)
        {  e.printStackTrace(); }
        catch (ClientProtocolException e)
        { e.printStackTrace(); }
        catch (IOException e)
        { e.printStackTrace(); }

        try
        {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null)
            { stringBuilder.append(line + "\n"); }
            inputStream.close();
            json = stringBuilder.toString();
        }
        catch (Exception e)
        { Log.e("Buffer Error", "Error converting result " + e.toString()); }

        // Parsing the string to a JSON object
        try
        { jsonObject = new JSONObject(json); }
        catch (JSONException e)
        { Log.e("JSON Parser", "Error parsing data " + e.toString()); }

        // return JSON String
        return jsonObject;
    }
}

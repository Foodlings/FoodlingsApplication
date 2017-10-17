package com.example.sheharyararif.foodlings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sheharyararif.foodlings.DatabaseModel.Comment;
import com.example.sheharyararif.foodlings.DatabaseModel.Post;
import com.example.sheharyararif.foodlings.DatabaseModel.Subscriber;
import com.example.sheharyararif.foodlings.ParserPackage.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CommentsScreen extends AppCompatActivity {

    ArrayList<Comment> commentsList;
    JSONArray jsonArray = null;
    CommentsAdapter adapter;
    ListView CommentsListView;
    EditText CommentTextBox;
    Button ReplyButton;
    String CommentText, postID, TimeStamp;
    Comment commentObject;
    Intent serializedIntent;

    //URL to get JSON Array
    private static String commentsURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getAllComments?PostID=";
    private static String writeCommentURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/createComment";

    private static final String TAG_CommentID = "CommentID";
    private static final String TAG_SubscriberID = "SubscriberID";
    private static final String TAG_SubscriberName = "SubscriberName";
    private static final String TAG_PostID = "PostID";
    private static final String TAG_CommentText = "CommentText";
    private static final String TAG_TimeStamp = "TimeStamp";
    private static final String TAG_DisplayPicture = "DisplayPicture";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_screen);

        CommentsListView = (ListView) findViewById(R.id.Comments_ListView);

        Intent i = getIntent();
        Post postData = (Post) i.getSerializableExtra("PostData");

        serializedIntent = new Intent(this, CommentsScreen.class);

        //Initializing Comments Array
        commentsList = new ArrayList<>();

        CommentTextBox = (EditText) findViewById(R.id.CommentTextBox);
        CommentTextBox.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        ReplyButton = (Button)findViewById(R.id.ReplyButton);
        ReplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentText = CommentTextBox.getText().toString();
                TimeStamp = new SimpleDateFormat("d-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime()).toString();
                new JSONParseComment().execute();
            }
        });

        postID = postData.getPostID();
        commentsURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/getAllComments?PostID=";
        commentsURL = commentsURL + postID;
        writeCommentURL = "http://foodlingsapi.azurewebsites.net/api/FoodlingDatabase/createComment";
        new JSONParse().execute();
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject>
    {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CommentsScreen.this);
            pDialog.setMessage("Loading Comments");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();

            Comment comment = null;
            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(commentsURL, "GET", null, null, null, null, null, null);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                // Getting JSON Array
                jsonArray = json.getJSONArray("Comment");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject fetchedData = jsonArray.getJSONObject(i);

                    String CommentID = fetchedData.getString(TAG_CommentID);
                    String SubscriberID = fetchedData.getString(TAG_SubscriberID);
                    String SubscriberName = fetchedData.getString(TAG_SubscriberName);
                    String PostID = fetchedData.getString(TAG_PostID);
                    String CommentText = fetchedData.getString(TAG_CommentText);
                    String Timestamp = fetchedData.getString(TAG_TimeStamp);
                    String DisplayPicture = fetchedData.getString(TAG_DisplayPicture);

                    commentsList.add(new Comment(CommentID, SubscriberID, SubscriberName, PostID, CommentText, Timestamp, DisplayPicture));
                }

                //Adapter
                adapter = new CommentsAdapter(commentsList, CommentsScreen.this);

                //ListView
                CommentsListView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class JSONParseComment extends AsyncTask<String, String, JSONObject>
    {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            pDialog = new ProgressDialog(CommentsScreen.this);
            pDialog.setMessage("Posting Comment...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args)
        {
            JSONParser jParser = new JSONParser();

            commentObject = new Comment("IDplaceholder", GlobalData.SubscriberID, "NamePlaceholder", postID, CommentText, TimeStamp, "DPplaceholder");

            // Posting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(writeCommentURL, "POST", null, null, commentObject, null, null, null);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json)
        {
            pDialog.dismiss();
            Toast.makeText(CommentsScreen.this, "Comment Posted",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}


package com.example.anneh.trivia;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScoreSubmission implements Response.Listener<String>, Response.ErrorListener  {
    private Context instance;
    Callback callbackActivity;
    String username;
    String score;

    // Interface
    public interface Callback {
        void gotSubmitted();
        void gotSubmittedError(String message);
    }

    // Constructor
    public ScoreSubmission (Context context) {
        this.instance = context;
    }




    // Attempt to retrieve questions from the API + notify QuestionsActivity when done
    public void getSubmitted(Callback activity, String username, String score) {
        Log.d("ScoreSubmission.java", "getSubmitted");

        // save activity as instance variable
        callbackActivity = activity;
        username = username;
        score = score;


        // Create new request-que & add new request
        // http://www.itsalif.info/content/android-volley-tutorial-http-get-post-put
        // https://apps.mprog.nl/android-reference/volley
        RequestQueue queue = Volley.newRequestQueue(instance);
        String serverURL = "https://ide50-annehs.cs50.io:8080/list";

        Map<String, String> params = new HashMap<>();
        params.put("username", username); // private username = ?
        params.put("score", score);

        PostRequest request = new PostRequest(params, Request.Method.POST, serverURL, this, this);
        // request.getParams();
        queue.add(request);
    }


    @Override
    public void onResponse(String response) {
        Log.d("ScoreSubmission.java", "onResponse");

        // Pass questionItem's back to QuestionActivity
        try {
            callbackActivity.gotSubmitted();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Callback method: report to QuestionsActivity when JsonObjectRequest isn't successful
    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("ScoreSubmission.java", "onErrorResponse");
        callbackActivity.gotSubmittedError(error.getMessage());
    }


}

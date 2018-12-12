package com.example.anneh.trivia;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Dictionary;

public class ScoreRequest implements Response.Listener<JSONArray>, Response.ErrorListener {

    private Context instance;
    Callback callbackActivity;

    // Interface
    public interface Callback {
        void gotScore(ArrayList<LeaderboardItem> players);
        void gotScoreError(String message);
    }

    // Constructor
    public ScoreRequest (Context context) {

        this.instance = context;
    }

    // Attempt to retrieve questions from the API + notify QuestionsActivity when done
    public void getScore(Callback activity) {
        // save activity as instance variable
        callbackActivity = activity;

        // Create new request-que & add new request
        RequestQueue queue = Volley.newRequestQueue(instance);
        String URL = "https://ide50-annehs.cs50.io:8080/list";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null, this, this);
        queue.add(request);
    }

    @Override
    public void onResponse(JSONArray response) {
        Log.d("QuestionsRequest.java", "onResponse");

        LeaderboardItem player;
        ArrayList<LeaderboardItem> players = new ArrayList<>();


        try {
            for(int i=0; i<response.length(); i++) {
                JSONObject object = response.getJSONObject(i);
                String username = object.getString("username");
                String score = object.getString("score");

                player = new LeaderboardItem(username, score);
                players.add(player);

            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        // Pass LeaderboardItems back to LeaderboardActivity
        try {
            callbackActivity.gotScore(players);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("QuestionsRequest.java", "onErrorResponse");
        callbackActivity.gotScoreError(error.getMessage());
    }
}

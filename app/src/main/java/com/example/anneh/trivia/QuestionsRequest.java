package com.example.anneh.trivia;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class QuestionsRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    private Context instance;
    Callback callbackActivity;

    // Interface
    public interface Callback {
        void gotQuestions(ArrayList<QuestionItem> questions);
        void gotQuestionsError(String message);
    }

    // Constructor
    public QuestionsRequest (Context context) {

        this.instance = context;
    }

    // Attempt to retrieve questions from the API + notify QuestionsActivity when done
    public void getQuestions(Callback activity, String amount, String difficulty) {
        // save activity as instance variable
        callbackActivity = activity;

        // Create new request-que & add new request
        RequestQueue queue = Volley.newRequestQueue(instance);
        String URL = "https://opentdb.com/api.php?amount=" + amount + "&" + difficulty + "&type=multiple";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL, null, this, this);
        queue.add(jsonObjectRequest);
    }

    // Callback method: pass questions & answers to QuestionActivity if JsonObjectRequest successful
    @Override
    public void onResponse(JSONObject response) {
        Log.d("QuestionsRequest.java", "onResponse");

        JSONArray jsonQuestions;
        JSONArray jsonAllAnswers;
        String question;
        ArrayList <String> allAnswers = new ArrayList<>();
        ArrayList <QuestionItem> questionItems = new ArrayList<>();
        String anAnswer;
        String answer;


        // Get all questions & answers
        try {
            jsonQuestions = (JSONArray) response.get("results");

            for (int i = 0; i < jsonQuestions.length(); i++) {

                JSONObject result = (JSONObject) jsonQuestions.get(i);

                try {
                    // GET QUESTION
                    question = result.getString("question");

                    // GET RIGHT ANSWER
                    try {
                        answer = result.getString("correct_answer");
                        allAnswers.add(answer);

                        // GET WRONG ANSWERS
                        try {
                            jsonAllAnswers = result.getJSONArray("incorrect_answers");

                            for (int j = 0; j < jsonAllAnswers.length(); j++) {
                                try {
                                    anAnswer = jsonAllAnswers.getString(j);
                                    allAnswers.add(anAnswer);
                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            // Create new QuestionItem & add to item list
                            QuestionItem questionItem = new QuestionItem(question, answer, allAnswers);
                            questionItems.add(questionItem);

                            // Empty answer array for new question
                            allAnswers = new ArrayList<>();
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }


        // Pass questionItem's back to QuestionActivity
        try {
            callbackActivity.gotQuestions(questionItems);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Callback method: report to QuestionsActivity when JsonObjectRequest isn't successful
    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("QuestionsRequest.java", "onErrorResponse");
        callbackActivity.gotQuestionsError(error.getMessage());
    }
}



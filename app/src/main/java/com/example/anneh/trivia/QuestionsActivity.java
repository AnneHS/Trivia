package com.example.anneh.trivia;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class QuestionsActivity extends AppCompatActivity implements QuestionsRequest.Callback {
    Button ansOne;
    Button ansTwo;
    Button ansThree;
    Button ansFour;
    int questNum = 0;
    int score = 0;
    String amount;
    String difficulty;
    QuestionItem currentQuestion;
    ArrayList <String> allAnswers;
    ArrayList <QuestionItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        // get difficulty & amount
        Intent intent = getIntent();
        amount = intent.getStringExtra("amount");
        difficulty = intent.getStringExtra("difficulty");

        // Request for questions from API
        QuestionsRequest request = new QuestionsRequest(getApplicationContext());
        request.getQuestions(QuestionsActivity.this, amount, difficulty);

    }

    @Override
    public void gotQuestions(ArrayList<QuestionItem> questionItems) {

        // Update questions if request successful
        items = questionItems;
        updateQuestion();
    }

    public void updateQuestion() {

        // get next Question
        currentQuestion = items.get(questNum);

        // Set question in TextView
        TextView scoreTV = (TextView) findViewById(R.id.currentScore);
        TextView questionTV = (TextView) findViewById(R.id.question);
        String question = currentQuestion.getQuestion();

        // set text
        String scoreString = "Score: " + Integer.toString(score);
        scoreTV.setText(scoreString);
        questionTV.setText(Html.fromHtml(question));

        // Find views for multiple choice buttons
        ansOne = findViewById(R.id.A);
        ansTwo = findViewById(R.id.B);
        ansThree = findViewById(R.id.C);
        ansFour = findViewById(R.id.D);

        // Restore background colour for buttons
        ansOne.setBackground(this.getResources().getDrawable(R.drawable.buttonshape1));
        ansTwo.setBackground(this.getResources().getDrawable(R.drawable.buttonshape1));
        ansThree.setBackground(this.getResources().getDrawable(R.drawable.buttonshape1));
        ansFour.setBackground(this.getResources().getDrawable(R.drawable.buttonshape1));

        // get all answers for current question and shuffle
        allAnswers = currentQuestion.getAllAnswers();
        Collections.shuffle(allAnswers);

        // Set button text for multiple choice answers
        ansOne.setText(Html.fromHtml(allAnswers.get(0)));
        ansTwo.setText(Html.fromHtml(allAnswers.get(1)));
        ansThree.setText(Html.fromHtml(allAnswers.get(2)));
        ansFour.setText(Html.fromHtml(allAnswers.get(3)));

        // update questNum
        questNum += 1;
    }

    public void answerClicked(View view) {

        Button clickedBtn = null;
        String chosenAnswer = null;

        // Identify chosen answer
        switch(view.getId()) {
            case R.id.A:        clickedBtn = findViewById(R.id.A);
                                chosenAnswer = clickedBtn.getText().toString();
                                break;
            case R.id.B:        clickedBtn = findViewById(R.id.B);
                                chosenAnswer = clickedBtn.getText().toString();
                                break;
            case R.id.C:        clickedBtn = findViewById(R.id.C);
                                chosenAnswer = clickedBtn.getText().toString();
                                break;
            case R.id.D:        clickedBtn = findViewById(R.id.D);
                                chosenAnswer = clickedBtn.getText().toString();
                                break;
        }

        // Colour right answer green
        if (ansOne.getText().toString().equals(currentQuestion.getAnswer())) {
            ansOne.setBackground(this.getResources().getDrawable(R.drawable.right_button));
        }
        else if (ansTwo.getText().toString().equals(currentQuestion.getAnswer())) {
            ansTwo.setBackground(this.getResources().getDrawable(R.drawable.right_button));
        }
        else if (ansThree.getText().toString().equals(currentQuestion.getAnswer())) {
            ansThree.setBackground(this.getResources().getDrawable(R.drawable.right_button));
        }
        else if (ansFour.getText().toString().equals(currentQuestion.getAnswer())) {
            ansFour.setBackground(this.getResources().getDrawable(R.drawable.right_button));
        }

        // Updates score if answer is correct, else colors answer red
        if (currentQuestion.getAnswer().equals(chosenAnswer)) {
            score = score + 10;
        }
        else {
            clickedBtn.setBackground(this.getResources().getDrawable(R.drawable.wrong_button));
        }

        // Delays next next step in order to show wrong and right answer
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (questNum == items.size()) {
                    gameFinished();
                }
                else {
                    updateQuestion();
                }
            }
        }, 2000);

    }

    public void gameFinished () {

        // start ScoreActivity
        Intent intent = new Intent(QuestionsActivity.this, ScoreActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);

    }

    @Override
    public void gotQuestionsError(String message) {
        Toast.makeText(this, "Could not get questions", Toast.LENGTH_SHORT).show();
    }
}

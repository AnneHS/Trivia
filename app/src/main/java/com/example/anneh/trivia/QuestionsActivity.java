package com.example.anneh.trivia;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    int questNum = 0;
    int score = 0;
    String amount;
    String difficulty;
    QuestionItem currentQuestion;
    ArrayList <String> allAnswers;
    ArrayList <QuestionItem> items;
    String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        // Toast test
        Toast.makeText(this, "Started", Toast.LENGTH_SHORT).show();

        // get difficulty & amount
        Intent intent = getIntent();
        amount = intent.getStringExtra("amount");
        difficulty = intent.getStringExtra("difficulty");


        //
        QuestionsRequest request = new QuestionsRequest(getApplicationContext());
        request.getQuestions(QuestionsActivity.this, amount, difficulty);

    }

    @Override
    public void gotQuestions(ArrayList<QuestionItem> questionItems) {

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
        questionTV.setText(question);


        // Find views for multiple choice buttons
        Button ansOne = findViewById(R.id.A);
        Button ansTwo = findViewById(R.id.B);
        Button ansThree = findViewById(R.id.C);
        Button ansFour = findViewById(R.id.D);

        // set Backgroundcolour back to white
//        ansOne.setBackgroundColor(Color.WHITE);
//        ansTwo.setBackgroundColor(Color.WHITE);
//        ansThree.setBackgroundColor(Color.WHITE);
//        ansFour.setBackgroundColor(Color.WHITE);

        // get all answers for current question and shuffle
        allAnswers = currentQuestion.getAllAnswers();
        Collections.shuffle(allAnswers);

        // Set button text for multiple choice answers
        ansOne.setText(allAnswers.get(0));
        ansTwo.setText(allAnswers.get(1));
        ansThree.setText(allAnswers.get(2));
        ansFour.setText(allAnswers.get(3));

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

        // Colour button green/red if answer right/wrong and update score
        if (chosenAnswer == currentQuestion.getAnswer()) {
            // clickedBtn.setBackgroundColor(Color.GREEN);
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            score = score + 10;
            Log.d("Score", String.format("score = %d", score));
        }
        else {
            // clickedBtn.setBackgroundColor(Color.RED);
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
        }

        // TODO: sleep: delay next question

        // Go to next question if available, else game is finished
        if (questNum == items.size()) {
            gameFinished();
        }
        else {
            updateQuestion();
        }
    }

    public void gameFinished () {

        // start ScoreActivity
        Intent intent = new Intent(QuestionsActivity.this, ScoreActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);

    }

    @Override
    public void gotQuestionsError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}

package com.example.anneh.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ScoreActivity extends AppCompatActivity implements ScoreSubmission.Callback {
    String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        // Display score
        Intent intent = getIntent();
        int retrievedScore = intent.getIntExtra("score", 0);
        score = Integer.toString(retrievedScore);
        TextView scoreTV = (TextView) findViewById(R.id.score);
        scoreTV.setText(score);
    }

    public void resetClicked(View view) {

        // Start new game when clicked
        Intent intent = new Intent(ScoreActivity.this, StartActivity.class);
        startActivity(intent);
    }

    // Submit when submit button clicked and username entered
    public void submitClicked(View view) {

        // MAG DIT MET TRY/CATCH?
        EditText usernameTV = findViewById(R.id.username);
        String username = usernameTV.getText().toString();

        if (username.length() == 0) {
            Toast.makeText(this, "Enter username", Toast.LENGTH_LONG).show();
        }
        else {
            // Save score on server : KAN DIT HIER? --> gotSubmitted/gotSubmittedError?
            ScoreSubmission request = new ScoreSubmission(getApplicationContext());
            request.getSubmitted(ScoreActivity.this, username, score);
            Toast.makeText(this, "submit request", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void gotSubmitted() {
        Toast.makeText(this, "gotSubmitted", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ScoreActivity.this, LeaderboardActivity.class);
        startActivity(intent);
    }
    @Override
    public void gotSubmittedError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

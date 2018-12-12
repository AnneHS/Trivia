package com.example.anneh.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity implements ScoreRequest.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        // Toast test
        Toast.makeText(this, "Started", Toast.LENGTH_SHORT).show();

        ScoreRequest request = new ScoreRequest(getApplicationContext());
        request.getScore(LeaderboardActivity.this);

    }

    @Override
    public void gotScore(ArrayList<LeaderboardItem> players) {
        // Get ArrayList to leaderboard

        // TODO: Sort by score

        // Adapter
        LeaderboardAdapter adapter = new LeaderboardAdapter(this, R.layout.leaderboard_item, players);
        ListView listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);
    }

    @Override
    public void gotScoreError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void newGameClicked(View view) {

        // Start new game when clicked
        Intent intent = new Intent(LeaderboardActivity.this, StartActivity.class);
        startActivity(intent);
    }
}

package com.example.anneh.trivia;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class LeaderboardAdapter extends ArrayAdapter<LeaderboardItem> {

    // Instance variables
    private ArrayList players;
    private String username, score;
    Context context;

    // Constructor
    public LeaderboardAdapter(Context context, int resource, ArrayList<LeaderboardItem> objects) {
        super(context, resource, objects);

        // reference to list of friends = objects?
        players = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LeaderboardItem player;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.leaderboard_item, parent, false);
        }

        // get access to the layout's views
        TextView usernameTV = convertView.findViewById(R.id.username);
        TextView scoreTV = convertView.findViewById(R.id.score);

        // get players' score and username
        player = (LeaderboardItem) players.get(position);
        String username = player.getUsername();
        String score = player.getScore();

        // load into leaderboard
        usernameTV.setText(username);
        scoreTV.setText(score);

        return convertView;
    }
}

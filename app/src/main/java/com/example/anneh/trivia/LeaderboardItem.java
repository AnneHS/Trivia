package com.example.anneh.trivia;

import java.io.Serializable;
import java.util.ArrayList;

public class LeaderboardItem implements Serializable {
    public String username, score;

    // Constructor
    public LeaderboardItem(String username, String score) {
        this.username = username;
        this.score = score;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getScore() {
        return score;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(String score) {
        this.score = score;
    }

}
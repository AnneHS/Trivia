package com.example.anneh.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class StartActivity extends AppCompatActivity {
    String amountSelected = "10";
    String difficultySelected = "medium";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Spinner values: https://www.mkyong.com/android/android-spinner-drop-down-list-example/

        // Get reference to spinners and set adapters to spinner
        Spinner amountSpinner = (Spinner) findViewById(R.id.amount);
        amountSpinner.setOnItemSelectedListener(new onItemSelectedListener());

        // get difficulty
        Spinner difficultySpinner = (Spinner) findViewById(R.id.difficulty);
        difficultySpinner.setOnItemSelectedListener(new onItemSelectedListener());
    }

    private class onItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            switch (parent.getId()) {
                case R.id.amount:       amountSelected = (String) parent.getItemAtPosition(position);
                                        break;
                case R.id.difficulty:   difficultySelected = (String) parent.getItemAtPosition(position);
                                        break;
            }

        }
        public void onNothingSelected(AdapterView<?> parent) {
//            // DO NOTHING OF SWITCH?
//            // TODO: WERKT NIET?
//            switch (parent.getId()) {
//                case R.id.amount:       String amountSelected = "10";
//                                        break;
//                case R.id.difficulty:   String difficultySelected = "Medium";
//                                        break;
//            }
        }
    }


    public void startClicked(View view) {

        // Start new game when clicked
        Intent intent = new Intent(StartActivity.this, QuestionsActivity.class);

        intent.putExtra("amount", amountSelected);
        intent.putExtra("difficulty", difficultySelected);

        startActivity(intent);
    }
}

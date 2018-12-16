package com.example.anneh.trivia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

public class StartActivity extends AppCompatActivity {
    String amountSelected = "10";
    String difficultySelected = "medium";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Get reference to spinners and set adapters to spinner
        // Spinner values: https://www.mkyong.com/android/android-spinner-drop-down-list-example/
        Spinner amountSpinner = (Spinner) findViewById(R.id.amount);
        amountSpinner.setOnItemSelectedListener(new onItemSelectedListener());

        // get difficulty
        Spinner difficultySpinner = (Spinner) findViewById(R.id.difficulty);
        difficultySpinner.setOnItemSelectedListener(new onItemSelectedListener());

        // Set rounded corners imageView
        // https://www.viralandroid.com/2015/11/how-to-make-imageview-image-rounded-corner-in-android.html
        ImageView logo = (ImageView) findViewById(R.id.triviaImg);
        Bitmap mbitmap = ((BitmapDrawable) getResources().getDrawable(R.drawable.trivia)).getBitmap();
        Bitmap imageRounded = Bitmap.createBitmap(mbitmap.getWidth(), mbitmap.getHeight(), mbitmap.getConfig());
        Canvas canvas = new Canvas(imageRounded);
        Paint mpaint = new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setShader(new BitmapShader(mbitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        canvas.drawRoundRect((new RectF(0, 0, mbitmap.getWidth(), mbitmap.getHeight())), 25, 25, mpaint);
        logo.setImageBitmap(imageRounded);
    }

    // Check for the selected amount & difficulty
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
        // Do nothing (default = 10 questions, medium difficulty)
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    // Start new game when clicked, pass amount & difficulty to QuestionActivity
    public void startClicked(View view) {

        Intent intent = new Intent(StartActivity.this, QuestionsActivity.class);
        intent.putExtra("amount", amountSelected);
        intent.putExtra("difficulty", difficultySelected);
        startActivity(intent);
    }
}

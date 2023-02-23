package com.example.quizmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.color.utilities.Score;

public class ScoreActivity extends AppCompatActivity {

    private TextView scoreTextView;
    private Button playAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        ConstraintLayout constraintLayout = findViewById(R.id.mainLayout3);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        scoreTextView = findViewById(R.id.score_text_view);
        playAgain = findViewById(R.id.play_again_button);

        // Retrieve the user's score from the intent
        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);

        // Display the score to the user
        scoreTextView.setText("Your score: " + score);

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}


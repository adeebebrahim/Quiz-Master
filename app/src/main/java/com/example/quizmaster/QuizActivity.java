package com.example.quizmaster;

import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class QuizActivity extends AppCompatActivity {

    private TextView mQuestionTextView;
    private Button mOption1Button;
    private Button mOption2Button;
    private Button mOption3Button;
    private Button mOption4Button;
    private MediaPlayer mediaPlayer;
    private boolean soundOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ConstraintLayout constraintLayout = findViewById(R.id.mainLayout2);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        soundOn = getSoundState();

        if (soundOn) {
            mediaPlayer = MediaPlayer.create(QuizActivity.this, R.raw.theme);
            mediaPlayer.start();
        }

        mQuestionTextView = findViewById(R.id.question_text);
        mOption1Button = findViewById(R.id.option_1);
        mOption2Button = findViewById(R.id.option_2);
        mOption3Button = findViewById(R.id.option_3);
        mOption4Button = findViewById(R.id.option_4);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (soundOn && mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private boolean getSoundState() {
        SharedPreferences sharedPreferences = getSharedPreferences("SoundState", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isSoundOn", false);
    }
}

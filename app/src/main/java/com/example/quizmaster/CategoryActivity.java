package com.example.quizmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import androidx.annotation.NonNull;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private Button backButton;
    private Button codingButton;
    private Button sportsButton;
    private Button tvMoviesButton;
    private Button generalButton;
    private MediaPlayer mediaPlayer;
    private boolean soundOn;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        ConstraintLayout constraintLayout = findViewById(R.id.mainLayout1);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        soundOn = getSoundState();

        if (soundOn) {
            mediaPlayer = MediaPlayer.create(CategoryActivity.this, R.raw.theme);
            mediaPlayer.start();
        }

        backButton = findViewById(R.id.back_button);
        codingButton = findViewById(R.id.coding_btn);
        sportsButton = findViewById(R.id.sports_btn);
        tvMoviesButton = findViewById(R.id.tv_movies_btn);
        generalButton = findViewById(R.id.general_btn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        codingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, QuizActivity.class);
                intent.putExtra("category", "Coding");
                startActivity(intent);
            }
        });

        sportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, QuizActivity.class);
                intent.putExtra("category", "Sports");
                startActivity(intent);
            }
        });

        tvMoviesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, QuizActivity.class);
                intent.putExtra("category", "TV/Movies");
                startActivity(intent);
            }
        });

        generalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuizActivity("General");
            }
        });

    }

    private void startQuizActivity(String category) {
        // Query Firebase Realtime Database for all questions in the selected category
        Query query = mDatabase.child("questions").orderByChild("category").equalTo(category);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Question> questions = new ArrayList<>();
//                Log.d("question : " , String.valueOf(dataSnapshot.getChildren()));
                for (DataSnapshot questionSnapshot : dataSnapshot.getChildren()) {
//                    Log.d("question1 " , String.valueOf(questionSnapshot.getValue()));
                    Question question = questionSnapshot.getValue(Question.class);
//                    Log.d("qname", question.getQuestionText());
                    questions.add(question);
                }

                // Pass the selected category and list of questions to the QuizActivity
                Intent intent = new Intent(CategoryActivity.this, QuizActivity.class);
                intent.putExtra("category", category);
                intent.putExtra("questions", (Serializable) questions);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("CategoryActivity", "onCancelled", databaseError.toException());
            }
        });
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


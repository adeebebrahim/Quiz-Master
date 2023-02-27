package com.example.quizmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quizmaster.Question;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizActivity extends AppCompatActivity {

    private TextView scoreTextView;
    private TextView questionTextView;
    private Button option1Button;
    private Button option2Button;
    private Button option3Button;
    private Button option4Button;
    private MediaPlayer mediaPlayer;
    private boolean soundOn;

    private List<Question> questionList;
    private int currentQuestionIndex;
    private int score;
    private String category;

    private FirebaseDatabase database;
    private DatabaseReference questionsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

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

        scoreTextView = findViewById(R.id.score_text);
        questionTextView = findViewById(R.id.question_text);
        option1Button = findViewById(R.id.option_1);
        option2Button = findViewById(R.id.option_2);
        option3Button = findViewById(R.id.option_3);
        option4Button = findViewById(R.id.option_4);

        questionList = new ArrayList<>();
        currentQuestionIndex = 0;
        score = 0;

        scoreTextView.setText("Your score: " + score);

        // Retrieve the category selected by the user in CategoryActivity
        Intent intent = getIntent();
        category = intent.getStringExtra("category");

        database = FirebaseDatabase.getInstance();
        questionsRef = database.getReference("questions");

        // Retrieve the questions from Firebase, based on the selected category
        questionsRef.orderByChild("category").equalTo(category).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot questionSnapshot : dataSnapshot.getChildren()) {
                    Question question = questionSnapshot.getValue(Question.class);
                    questionList.add(question);
                }

                // Shuffle the question list so that the order of questions is random
                Collections.shuffle(questionList);

                // Display the first question
                displayQuestion(currentQuestionIndex);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
                Toast.makeText(QuizActivity.this, "Error retrieving questions", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up the click listeners for the answer buttons
        option1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(option1Button);
            }
        });

        option2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(option2Button);
            }
        });

        option3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(option3Button);
            }
        });

        option4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(option4Button);
            }
        });
    }

    // Display the current question and its answer options
    private void displayQuestion(int currentQuestionIndex) {
        Question currentQuestion = questionList.get(currentQuestionIndex);
        questionTextView.setText(currentQuestion.getQuestionText());
        option1Button.setText(currentQuestion.getOption1());
        option2Button.setText(currentQuestion.getOption2());
        option3Button.setText(currentQuestion.getOption3());
        option4Button.setText(currentQuestion.getOption4());

        // Set the background color of all buttons to the default color
        option1Button.setBackgroundColor(ContextCompat.getColor(this, R.color.pastel_turquoise));
        option2Button.setBackgroundColor(ContextCompat.getColor(this, R.color.pastel_turquoise));
        option3Button.setBackgroundColor(ContextCompat.getColor(this, R.color.pastel_turquoise));
        option4Button.setBackgroundColor(ContextCompat.getColor(this, R.color.pastel_turquoise));
    }

    private void checkAnswer(Button selectedButton) {
        Question currentQuestion = questionList.get(currentQuestionIndex);
        String correctAnswer = currentQuestion.getCorrectAnswer();

        if (selectedButton.getText().equals(correctAnswer)) {
            // The selected answer is correct
            selectedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
            score++;
        } else {
            // The selected answer is incorrect
            selectedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.red));

            // Highlight the correct answer in green
            if (option1Button.getText().equals(correctAnswer)) {
                option1Button.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
            } else if (option2Button.getText().equals(correctAnswer)) {
                option2Button.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
            } else if (option3Button.getText().equals(correctAnswer)) {
                option3Button.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
            } else if (option4Button.getText().equals(correctAnswer)) {
                option4Button.setBackgroundColor(ContextCompat.getColor(this, R.color.green));
            }
        }

        option1Button = findViewById(R.id.option_1);
        option2Button = findViewById(R.id.option_2);
        option3Button = findViewById(R.id.option_3);
        option4Button = findViewById(R.id.option_4);

//         Disable the answer buttons so the user cannot select another answer
        option1Button.setEnabled(false);
        option2Button.setEnabled(false);
        option3Button.setEnabled(false);
        option4Button.setEnabled(false);

        // Delay showing the next question for a short period of time, then display it
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // If there are more questions, display the next question
                        if (currentQuestionIndex < questionList.size() - 1) {
                            currentQuestionIndex++;
                            displayQuestion(currentQuestionIndex);
                            option1Button.setEnabled(true);
                            option2Button.setEnabled(true);
                            option3Button.setEnabled(true);
                            option4Button.setEnabled(true);
                        } else {
                            // If there are no more questions, end the quiz and show the score
                            endQuiz();
                        }
                    }
                },
                500 // Delay for 1 second
        );
        scoreTextView.setText("Your score: " + score);
    }

    private void endQuiz() {
        // Display the score to the user
        Toast.makeText(this, "Your score is " + score, Toast.LENGTH_SHORT).show();

        // Start the ScoreActivity and pass the score
        Intent intent = new Intent(QuizActivity.this, ScoreActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
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

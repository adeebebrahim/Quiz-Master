package com.example.quizmaster;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    private TextView mQuestionTextView;
    private Button mOption1Button;
    private Button mOption2Button;
    private Button mOption3Button;
    private Button mOption4Button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = findViewById(R.id.question_text);
        mOption1Button = findViewById(R.id.option_1);
        mOption2Button = findViewById(R.id.option_2);
        mOption3Button = findViewById(R.id.option_3);
        mOption4Button = findViewById(R.id.option_4);
    }
}

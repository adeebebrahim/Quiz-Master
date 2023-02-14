package com.example.quizmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CategoryActivity extends AppCompatActivity {

    private Button backButton;
    private Button codingButton;
    private Button sportsButton;
    private Button tvMoviesButton;
    private Button generalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

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
                intent.putExtra("category", "coding");
                startActivity(intent);
            }
        });

        sportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, QuizActivity.class);
                intent.putExtra("category", "sports");
                startActivity(intent);
            }
        });

        tvMoviesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, QuizActivity.class);
                intent.putExtra("category", "tv_movies");
                startActivity(intent);
            }
        });

        generalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this, QuizActivity.class);
                intent.putExtra("category", "general");
                startActivity(intent);
            }
        });
    }
}


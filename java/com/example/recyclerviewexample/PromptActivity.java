package com.example.recyclerviewexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PromptActivity extends AppCompatActivity {


    private boolean correctAnswer;
    private Button showCorrectAnswerButton;

    private TextView answerTextView;

    public static final String KEY_EXTRA_ANSWER_SHOWN = "answerShown";

    private void setAnswerShownResult(boolean answerWasShown) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EXTRA_ANSWER_SHOWN,answerWasShown);
        setResult(RESULT_OK, resultIntent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);

        correctAnswer = getIntent().getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER,true);

        showCorrectAnswerButton = findViewById(R.id.show_correct_answer_button);

        answerTextView = findViewById(R.id.answer_text_view);
        showCorrectAnswerButton.setOnClickListener(v -> {
            Log.d("PromptActivity", "button clicked. Correct ANswer: " + correctAnswer);
            int answer = correctAnswer ? R.string.button_true : R.string.button_false;
            answerTextView.setText(answer);
            setAnswerShownResult(true);
        });


    }


}
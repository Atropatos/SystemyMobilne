package com.example.recyclerviewexample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    private static final String QUIZ_TAG = "QuizApp";
    public static final String KEY_EXTRA_ANSWER = "pl.edu.pb.wi.quiz.correctAnswer";

    private static final int REQUEST_CODE_PROMPT =0;

    private RecyclerView contactsRecView;
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button podpowiedzButton;
    private TextView questionTextView;

    private boolean answerWasShown;

    private Question[] questions = new Question[] {
            new Question(R.string.q_activity,true),
            new Question(R.string.q_find_resources, false),
            new Question(R.string.q_listener,true),
            new Question(R.string.q_resources,true),
            new Question(R.string.q_version,false)
    };

    private int currentIndex = 0;
    private int ilePoprawnych = 0;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(QUIZ_TAG,"Wywołana została metoda: onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    private void checkAnswerCorrectness(boolean userAnswer) {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if(answerWasShown) {
            resultMessageId = R.string.answer_was_shown;
        } else {
            if( userAnswer == correctAnswer) {
                resultMessageId = R.string.correct_answer;
                ilePoprawnych++;
            } else {
                resultMessageId = R.string.incorrect_answer;
            }

        }

        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }

    private void setNextQuestion() {
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(QUIZ_TAG,"onPause() wywolane");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(QUIZ_TAG,"onStart() wywolane");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(QUIZ_TAG,"onStop() wywolane");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(QUIZ_TAG,"onDestroy() wywolane");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(QUIZ_TAG, "onResume() wywolane");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(QUIZ_TAG,"Wywołana została metoda cyklu życia: onCreate");
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }
       trueButton = findViewById(R.id.true_button);
       falseButton = findViewById(R.id.false_button);
       nextButton = findViewById(R.id.next_button);
       podpowiedzButton = findViewById(R.id.podpowiedz_button);
       questionTextView = findViewById(R.id.question_text_view);
        contactsRecView = findViewById(R.id.contactsRecView);



        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(true);
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(false);
            }
        });


        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                currentIndex = (currentIndex +1)%questions.length;
                answerWasShown = false;
                setNextQuestion();
                String wynik = "Twoj wynik to : " + ilePoprawnych;
                Toast.makeText(v.getContext(), wynik, Toast.LENGTH_SHORT).show();
            }


        });
        setNextQuestion();


//
//        podpowiedzButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                checkAnswerCorrectness(false);
//            }
//        });






    podpowiedzButton.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, PromptActivity.class);
                boolean correctAnswer = questions[currentIndex].isTrueAnswer();
                intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
                startActivityForResult(intent,REQUEST_CODE_PROMPT);
            });
        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK) { return;}
        if (requestCode == REQUEST_CODE_PROMPT) {
             if(data ==null) {return; }
             answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN,false);
        }
    }
}


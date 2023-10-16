package com.example.recyclerviewexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private RecyclerView contactsRecView ;
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private TextView questionTextView;

    private Question[] questions = new Question[] {
            new Question(R.string.q_activity,true),
            new Question(R.string.q_find_resources, false),
            new Question(R.string.q_listener,true),
            new Question(R.string.q_resources,true),
            new Question(R.string.q_version,false)
    };

    private int currentIndex = 0;
    private int ilePoprawnych = 0;

    private void checkAnswerCorrectness(boolean userAnswer) {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if( userAnswer == correctAnswer) {
            resultMessageId = R.string.correct_answer;
            ilePoprawnych++;
        } else {
            resultMessageId = R.string.incorrect_answer;
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }

    private void setNextQuestion() {
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       trueButton = findViewById(R.id.true_button);
       falseButton = findViewById(R.id.false_button);
       nextButton = findViewById(R.id.next_button);
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
                setNextQuestion();
                String wynik = "Twoj wynik to : " + ilePoprawnych;
                Toast.makeText(v.getContext(), wynik, Toast.LENGTH_SHORT).show();
            }


        });
        setNextQuestion();



    }

}
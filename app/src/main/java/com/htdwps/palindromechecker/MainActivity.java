package com.htdwps.palindromechecker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userInput;
    private TextView resultText;
    private Button submitButton;
    private ListView oldWordsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        userInput = findViewById(R.id.user_input);
        resultText = findViewById(R.id.textview_result);
        submitButton = findViewById(R.id.submit_button);
        oldWordsList = findViewById(R.id.listview_old_words);

        submitButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.submit_button:

                String userWord = userInput.getText().toString();

                if (userWord.length() > 0) {

                    boolean result = CheckPalindrome.reverseCheckWord(userWord);

                    resultText.setText(new StringBuilder().append(userWord).append(" ").append(Answer.result(MainActivity.this, result)).toString());

                    userInput.getText().clear();

                } else {

                    userInput.setError("This word field is currently blank.");

                }

                break;

            default:

                break;
        }

    }
}

package com.htdwps.palindromechecker;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.htdwps.palindromechecker.adapter.WordsListAdapter;
import com.htdwps.palindromechecker.utils.PalindromeDbHelper;
import com.htdwps.palindromechecker.utils.TestFakeData;
import com.htdwps.palindromechecker.utils.WordListContract;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Declare a SQLiteDatabase and the DatabaseHelper
    private SQLiteDatabase mDatabase;
    private WordsListAdapter mAdapter;

    private EditText userInput;
    private TextView resultText;
    private Button submitButton;
    private RecyclerView wordRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            getSupportActionBar().hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        setupLayout();

        wordRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        PalindromeDbHelper databaseHelper = new PalindromeDbHelper(this);

        mDatabase = databaseHelper.getWritableDatabase();

        // Insert test data to check that adapter is connected
        TestFakeData.insertStarterData(mDatabase);

        Cursor cursor = getAllGuests();

        mAdapter = new WordsListAdapter(this, cursor.getCount());

        Toast.makeText(this, "Count " + cursor.getCount(), Toast.LENGTH_SHORT).show();

        wordRecyclerView.setAdapter(mAdapter);

        submitButton.setOnClickListener(this);

    }

    // Setup the layout and tie the view by id
    public void setupLayout() {

        userInput = findViewById(R.id.user_input);
        resultText = findViewById(R.id.textview_result);
        submitButton = findViewById(R.id.submit_button);
        wordRecyclerView = findViewById(R.id.recyclerview_old_words);

    }

    private Cursor getAllGuests() {
        // COMPLETED (6) Inside, call query on mDb passing in the table name and projection String [] order by COLUMN_TIMESTAMP
        return mDatabase.query(
                WordListContract.WordSearchEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WordListContract.WordSearchEntry.COLUMN_TIMESTAMP
        );
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

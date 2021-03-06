package com.htdwps.palindromechecker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.htdwps.palindromechecker.adapter.WordsListAdapter;
import com.htdwps.palindromechecker.design.RecyclerDividerDecoration;
import com.htdwps.palindromechecker.utils.PalindromeDbHelper;
import com.htdwps.palindromechecker.utils.WordListContract;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, EditText.OnEditorActionListener {

    // Declare a SQLiteDatabase and the DatabaseHelper
    private SQLiteDatabase mDatabase;
    private WordsListAdapter mAdapter;

    private EditText userInput;
    private TextView resultText;
    private ImageButton submitButton;
    private RecyclerView wordRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        setupLayout();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        wordRecyclerView.setLayoutManager(linearLayoutManager);

        PalindromeDbHelper databaseHelper = new PalindromeDbHelper(this);

        mDatabase = databaseHelper.getWritableDatabase();

        // Insert test data to check that adapter is connected
        // Test data no longer needed, after database test written worked
//        TestFakeData.insertStarterData(mDatabase);

        Cursor cursor = getAllWordsList();

        mAdapter = new WordsListAdapter(this, cursor);

        wordRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long id = (long) viewHolder.itemView.getTag();
                removeGuest(id);
                mAdapter.swapCursor(getAllWordsList());
            }
        }).attachToRecyclerView(wordRecyclerView);

        userInput.setOnEditorActionListener(this);
        submitButton.setOnClickListener(this);

    }

    // Setup the layout and tie the view by id
    public void setupLayout() {

        userInput = findViewById(R.id.user_input);
        resultText = findViewById(R.id.textview_result);
        submitButton = findViewById(R.id.submit_button);
        wordRecyclerView = findViewById(R.id.recyclerview_old_words);
        wordRecyclerView.addItemDecoration(new RecyclerDividerDecoration(this));

    }

    private long addNewWord(String name, int isPalindrome) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(WordListContract.WordSearchEntry.COLUMN_WORD_NAME, name);
        contentValues.put(WordListContract.WordSearchEntry.COLUMN_PALINDROME, isPalindrome);

        return mDatabase.insert(WordListContract.WordSearchEntry.TABLE_NAME, null, contentValues);

    }

    private Cursor getAllWordsList() {
        // COMPLETED (6) Inside, call query on mDatabase passing in the table name and projection String [] order by COLUMN_TIMESTAMP
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

    private boolean removeGuest(long id) {
        return mDatabase.delete(WordListContract.WordSearchEntry.TABLE_NAME, WordListContract.WordSearchEntry._ID + "=" + id, null) > 0;
    }

    private void checkThisWord(View view) {

        String userWord = userInput.getText().toString();

        if (userWord.length() > 0) {

            int value;

            boolean result = CheckPalindrome.reverseCheckWord(userWord);

            if (result) {

                value = 1;
                resultText.setTextColor(getResources().getColor(R.color.result_true));

            } else {

                value = 0;
                resultText.setTextColor(getResources().getColor(R.color.result_false));

            }

            // Add the new word and it's result value into the database
            addNewWord(userWord, value);

            // Refresh the Cursor with new value to update the recyclerview list
            mAdapter.swapCursor(getAllWordsList());
            wordRecyclerView.smoothScrollToPosition(getAllWordsList().getCount());

            // Show user the result
            resultText.setText(new StringBuilder().append(userWord).append(" ").append(Answer.result(MainActivity.this, result)).toString());

            // Clear the UI such as edittext
            userInput.getText().clear();

            closeSoftKeyboard(view);

        } else {

            userInput.setError("This word field is currently blank.");

        }

    }

    // Close the soft keyboard when user is done using it
    public void closeSoftKeyboard(View view) {

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        try {

            if (inputMethodManager != null) {

                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

            }

        } catch (NullPointerException error) {

            error.printStackTrace();

        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.submit_button:

                checkThisWord(view);

                break;

            default:

                break;
        }

    }

    @Override
    public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent) {

        if (action == EditorInfo.IME_ACTION_DONE) {

            checkThisWord(textView);

            return true;

        }

        return false;

    }
}

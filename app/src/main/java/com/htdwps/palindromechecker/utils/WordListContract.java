package com.htdwps.palindromechecker.utils;

import android.provider.BaseColumns;

// Create a contract for the SQLite database defining table name and column names.
public class WordListContract {

    public static final class WordSearchEntry implements BaseColumns {

        //Table name
        public static final String TABLE_NAME = "wordlist";

        // Column names - Word user input, Result (True = 1 or False = 0), Timestamp
        public static final String COLUMN_WORD_NAME = "wordInput";
        public static final String COLUMN_PALINDROME = "result";
        public static final String COLUMN_TIMESTAMP = "timestamp";

    }
}

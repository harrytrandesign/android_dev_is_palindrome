package com.htdwps.palindromechecker.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.htdwps.palindromechecker.utils.WordListContract.*;

public class PalindromeDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "palindrome_words.db";
    // Version 1, if ever change the database columns than need to update this number so users get updated database.
    private static final int DATABASE_VERSION = 1;

    private static final String COLUMN_TEMP = "tempColumn";
    private static final String DATABASE_APPEND_NAME = "ALTER TABLE " + WordSearchEntry.TABLE_NAME + " ADD COLUMN " + COLUMN_TEMP + " string;";

    public PalindromeDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // COLUMN_WORD_NAME: Word that was entered
        // COLUMN_PALINDROME: 0 = FALSE; 1 = TRUE;
        // COLUMN_TIMESTAMP: Timestamp
        final String SQL_CREATE_WORDLIST_TABLE = "CREATE TABLE "
                + WordSearchEntry.TABLE_NAME + " ("
                + WordSearchEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + WordSearchEntry.COLUMN_WORD_NAME + " TEXT NOT NULL, "
                + WordSearchEntry.COLUMN_PALINDROME + " INTEGER NOT NULL, "
                + WordSearchEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                + "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_WORDLIST_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldDbVersion, int newDbVersion) {

        // To update or delete the database when database version is upgraded
        if (newDbVersion > oldDbVersion) {
            sqLiteDatabase.execSQL(DATABASE_APPEND_NAME);
        }

    }
}

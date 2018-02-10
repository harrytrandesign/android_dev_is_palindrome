package com.htdwps.palindromechecker.utils;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.htdwps.palindromechecker.utils.WordListContract.WordSearchEntry;

import java.util.ArrayList;
import java.util.List;


public class TestFakeData {

    public static void insertStarterData(SQLiteDatabase database) {

        if (database == null) {
            return;
        }

        // Create a list of fake word data for testing
        List<ContentValues> list = new ArrayList<ContentValues>();

        // Start inserting fake data into list
        ContentValues contentValues = new ContentValues();
        contentValues.put(WordSearchEntry.COLUMN_WORD_NAME, "Hello");
        contentValues.put(WordSearchEntry.COLUMN_PALINDROME, 0);
        list.add(contentValues);

//        contentValues = new ContentValues();
//        contentValues.put(WordSearchEntry.COLUMN_WORD_NAME, "racecar");
//        contentValues.put(WordSearchEntry.COLUMN_PALINDROME, 1);
//        list.add(contentValues);
//
//        contentValues = new ContentValues();
//        contentValues.put(WordSearchEntry.COLUMN_WORD_NAME, "Ace");
//        contentValues.put(WordSearchEntry.COLUMN_PALINDROME, 0);
//        list.add(contentValues);

        // Insert guest into the database
        try {

            // Begin the database transaction
            database.beginTransaction();
            // Make sure the database is clear
            database.delete(WordListContract.WordSearchEntry.TABLE_NAME, null, null);
            // Insert each contentValue from list into the database now
            for (ContentValues cv : list) {
                database.insert(WordListContract.WordSearchEntry.TABLE_NAME, null, cv);
            }
            database.setTransactionSuccessful();

        } catch (SQLException error) {

            error.printStackTrace();

        } finally {

            // Make sure to end the transaction afterwards
            database.endTransaction();

        }

    }

}

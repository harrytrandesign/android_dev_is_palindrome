package com.htdwps.palindromechecker.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.htdwps.palindromechecker.R;
import com.htdwps.palindromechecker.utils.WordListContract;


public class WordsListAdapter extends RecyclerView.Adapter<WordsListAdapter.WordViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public WordsListAdapter(Context mContext, Cursor mCursor) {
        this.mContext = mContext;
        this.mCursor = mCursor;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate the Recyclerview layout
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_word_result, parent, false);

        return new WordViewHolder(view);

    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {

        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String word = mCursor.getString(mCursor.getColumnIndex(WordListContract.WordSearchEntry.COLUMN_WORD_NAME));
        int isPalindrome = mCursor.getInt(mCursor.getColumnIndex(WordListContract.WordSearchEntry.COLUMN_PALINDROME));

        holder.wordTextView.setText(word);

        switch (isPalindrome) {

            case 0:
                holder.resultTextView.setText(R.string.palindrome_indicator_false);
                holder.resultTextView.setTextColor(mContext.getResources().getColor(R.color.result_false));
                break;

            case 1:
                holder.resultTextView.setText(R.string.palindrome_indicator_true);
                holder.resultTextView.setTextColor(mContext.getResources().getColor(R.color.result_true));
                break;

        }

    }

    @Override
    public int getItemCount() {

        return mCursor.getCount();

    }

    // Helps to switch the cursor after a new data inserted into the database, triggers the refresh of the recyclerview too
    public void swapCursor(Cursor newCursor) {

        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }

    }


    class WordViewHolder extends RecyclerView.ViewHolder {

        TextView wordTextView;
        TextView resultTextView;

        public WordViewHolder(View itemView) {
            super(itemView);

            wordTextView = itemView.findViewById(R.id.textview_word_checked);
            resultTextView = itemView.findViewById(R.id.textview_result);

        }
    }
}

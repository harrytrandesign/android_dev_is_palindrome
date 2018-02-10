package com.htdwps.palindromechecker.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.htdwps.palindromechecker.R;


public class WordsListAdapter extends RecyclerView.Adapter<WordsListAdapter.WordViewHolder> {

    private Context mContext;
    private int mCount;

    public WordsListAdapter(Context mContext, int mCount) {

        this.mContext = mContext;
        this.mCount = mCount;

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

    }

    @Override
    public int getItemCount() {

        return mCount;

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

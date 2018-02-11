package com.htdwps.palindromechecker.design;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.htdwps.palindromechecker.R;

/**
 * Created by HTDWPS on 2/10/18.
 */

public class RecyclerDividerDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;

    public RecyclerDividerDecoration(Context context) {

        mDivider = context.getResources().getDrawable(R.drawable.divider_line);

    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

        int left = parent.getPaddingLeft() + 20;
        int right = parent.getWidth() - parent.getPaddingRight() - 20;

        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }

    }

}

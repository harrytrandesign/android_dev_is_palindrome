package com.htdwps.palindromechecker;

import android.content.Context;

/**
 * Created by HTDWPS on 2/7/18.
 */

public class Answer {

    public static String result(Context context, Boolean bool) {

        Context mContext = context;

        String feedback = Boolean.toString(bool);

        String answer = "";

        switch (feedback) {

            case "true":

                answer = mContext.getResources().getString(R.string.palindrome_true);

                break;


            case "false":

                answer = mContext.getResources().getString(R.string.palindrome_false);

                break;

        }

        return answer;

    }

}

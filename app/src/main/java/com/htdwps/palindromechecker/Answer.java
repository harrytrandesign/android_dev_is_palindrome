package com.htdwps.palindromechecker;

/**
 * Created by HTDWPS on 2/7/18.
 */

public class Answer {

    public static String result(Boolean bool) {

        String feedback = Boolean.toString(bool);

        String answer = "";

        switch (feedback) {

            case "true":

                answer = " is a Palindrome.";

                break;


            case "false":

                answer = " is not a Palindrome.";

                break;

        }

        return answer;

    }

}

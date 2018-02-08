package com.htdwps.palindromechecker;

/**
 * Created by HTDWPS on 2/7/18.
 */

class CheckPalindrome {

    static boolean isWordGivenPalindrome(String word) {

        char[] array = word.replace(" ", "").toCharArray();

        // "racecar" --> ['r', 'a', 'c', 'e', 'c', 'a', 'r']; length = 7;
        // index position  0    1    2    3    4    5    6 aka array.length - 1;
        // "step on no pets" is also a palindrome

        int length = array.length;
        int start = 0;
        int end = array.length - 1;

        while (start < end) { // 0 < 6

            if (array[start] == array[end]) {

                start++;
                end--;

            } else {

                return false;

            }

        }

        return true;

    }

}
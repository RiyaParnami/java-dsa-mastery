package com.riya;

import java.util.Arrays;

public class SearchInStrings {
    public static void main(String[] args) {
        String name = "Riya";
        char target = 'i';
        System.out.println(search(name, target));

        System.out.println(Arrays.toString(name.toCharArray())); // makes character array
    }


    static boolean search2(String str, char target){
        if (str.length() == 0){ // here length have parentheses coz it is function(method) in string class
            return false;
        }

        // by for each loop
        for (char ch : str.toCharArray()){ // converts a string into a newly allocated array(char[]).
            // coz enhanced for-each enhanced loop only works on arrays or classes that implement the iterable interface.
            if (ch == target){
                return true;
            }
        }
        return false;
    }

    static boolean search(String str, char target){
        if (str.length() == 0){ // here length have parentheses coz it is function(method) in string class
            return false;
        }

        for (int i = 0; i < str.length(); i++) {
            if (target == str.charAt(i)){ // charAt returns the char value at a specified index.
                return true;
            }
        }
        return false;
    }
}

package com.riya;

import java.util.Scanner;

public class CaseCheck {
    public static void main(String[] args) {
        Scanner in  = new Scanner(System.in);
        char ch = in.next().trim().charAt(0); // next will print string ,trim will remove extra space , chatAt(0) will print first character

        if (ch >= 'a' && ch <= 'z') {
            System.out.println("Lowercase");
        }else{
            System.out.println("Uppercase");
        }
    }
}

package com.riya;

import java.util.Scanner;

public class Inputs {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("please enter some input: ");
        int rollno = input.nextInt();
        System.out.println("Your roll number is "+ rollno);

        String name = input.next();
        System.out.println(name);

        float  marks = input.nextFloat();
        System.out.println(marks);
        //Input Typed	Consumed By
        //65	         nextInt()
        //hi	         next()
        //riya	         waiting for nextFloat() ❌
        //Now Java tries to convert "riya" into a float.
        //Obviously impossible.
        //So:InputMismatchException
    }
}

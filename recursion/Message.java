package com.riya.intro;

public class Message {

    public static void main(String[] args) {
        // write a function that prints hello world
        message();
    }

    static void message() {
        System.out.println("Hello World");
        message1();
    }

    static void message1() {
        System.out.println("Hello World");
        message2();
    }

    static void message2() {
        System.out.println("Hello World");
        message3();
    }

    static void message3() {
        System.out.println("Hello World");
        message4(); // message 4 is executed it will come out of here,function will end from where it called
    }

    static void message4() {
        System.out.println("Hello World");
    }

}

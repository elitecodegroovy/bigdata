package com.api;

/**
 * @author JohnLiu
 * @version 0.1.0
 * @date 2014/1/24
 */
public class Greeting {
    public native void sayHi(String who, int times); //1

    static {
        System.loadLibrary("GreetingImpl");
    } //2

    public static void main(String[] args) {
        Greeting hello = new Greeting();
        hello.sayHi(args[0], Integer.parseInt(args[1])); //3
    }
}

package com.epam.rd.autotasks.meetanagent;

import java.util.Scanner;

public class MeetAnAgent {
    final static int PASSWORD = 133976; //You can change pass, but don't change the type

    public static void main(String[] args) {
        int pass = PASSWORD;
        Scanner scanner = new Scanner(System.in);
        int pw = scanner.nextInt();

        switch (pw) {
            case PASSWORD:
                System.out.println("Hello, Agent");
                break;
            default:
                System.out.println("Access denied");
        }
    }
}

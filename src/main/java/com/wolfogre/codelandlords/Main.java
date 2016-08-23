package com.wolfogre.codelandlords;

import java.util.Scanner;

/**
 * 裁判机执行入口，
 */
public class Main {
    public static void main(String[] args){
        System.out.print(
                "  ____          _        _                    _ _               _     \n"
                        + " / ___|___   __| | ___  | |    __ _ _ __   __| | | ___  _ __ __| |___ \n"
                        + "| |   / _ \\ / _` |/ _ \\ | |   / _` | '_ \\ / _` | |/ _ \\| '__/ _` / __|\n"
                        + "| |__| (_) | (_| |  __/ | |__| (_| | | | | (_| | | (_) | | | (_| \\__ \\\n"
                        + " \\____\\___/ \\__,_|\\___| |_____\\__,_|_| |_|\\__,_|_|\\___/|_|  \\__,_|___/\n"
                        + "              __        __   _                            _           \n"
                        + "              \\ \\      / /__| | ___ ___  _ __ ___   ___  | |          \n"
                        + "               \\ \\ /\\ / / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\ | |          \n"
                        + "                \\ V  V /  __/ | (_| (_) | | | | | |  __/ |_|          \n"
                        + "                 \\_/\\_/ \\___|_|\\___\\___/|_| |_| |_|\\___| (_)          \n"
        );
        Manager manager = new Manager();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please input the Gamblers, \n" +
                "empty for com.wolfogre.codelandlords.CoachGambler.\n");

        for(int i = 0; i < 3; ++i){
            String input;
            System.out.println("Please input the No." + (i + 1) + " Gamblers: ");
            input = scanner.nextLine();
            if(input.isEmpty())
                input = "com.wolfogre.codelandlords.CoachGambler";
            if(manager.loadGambler(input, i))
                System.out.println("Load " + input + " successfully!");
            else{
                System.out.println("Load " + input + " unsuccessfully, please try again!");
                --i;
            }
        }
        manager.start(5, 2, System.out);


    }
}

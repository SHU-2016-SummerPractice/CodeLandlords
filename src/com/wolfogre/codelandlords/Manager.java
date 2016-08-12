package com.wolfogre.codelandlords;

import java.util.Scanner;

/**
 * Created by wolfogre on 8/10/16.
 */
public class Manager {
    public static void main(String[] args){
        System.out.println("Begin!");
        WikipediaCardsChecker cardsChecker = new WikipediaCardsChecker();
        String input;
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            System.out.println(cardsChecker.getCardsType(scanner.nextLine()));
        }
    }
}

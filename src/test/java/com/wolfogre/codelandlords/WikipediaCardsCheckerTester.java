package com.wolfogre.codelandlords;

import org.junit.Test;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by wolfogre on 8/20/16.
 */
public class WikipediaCardsCheckerTester {
    @Test
    public void testGetCardsType() {
        String[] testData = {
                "A",
                "AA",
                "AAAA",
                "AAA2",
                "33445566",
                "3344556678",
                "3344"
        };
        WikipediaCardsChecker cardsChecker = new WikipediaCardsChecker();
        for(String str : testData)
            System.out.println(str + " : " + cardsChecker.getCardsType(str));
    }
}

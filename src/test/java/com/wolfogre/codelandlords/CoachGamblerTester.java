package com.wolfogre.codelandlords;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wolfogre on 8/21/16.
 */
public class CoachGamblerTester {
    @Test
    public void testPlay(){
        class TestData{
            public TestData(String preOutCards, String ownedCards, String result) {
                this.preOutCards = preOutCards;
                this.ownedCards = ownedCards;
                this.result = result;
            }
            String preOutCards;
            String ownedCards;
            String result;
        }

        List<TestData> testData = new ArrayList<>();

        testData.add(new TestData("A", "A2", "2"));
        testData.add(new TestData("A", "A", ""));
        testData.add(new TestData("A", "A2", "2"));
        testData.add(new TestData("SM", "2222", ""));
        testData.add(new TestData("2222", "AAAASM", "SM"));
        testData.add(new TestData("333444555778", "888999000JQK", "888999000JQK"));
        testData.add(new TestData("333444555777", "888999000JQK", "888999000JQK"));
        testData.add(new TestData("333444555666", "888999000JQK", ""));
        testData.add(new TestData("3334445556667778", "888999000JJJQQQ", ""));
        testData.add(new TestData("333444555666777", "888999000JJJQQQKKKK", "888999000JJJQQQ"));
        testData.add(new TestData("333444555666777", "88899000JJJQQQKKKK", "KKKK"));
        testData.add(new TestData("444555666888", "333999000JJJ", "333999000JJJ"));
        CoachGambler coachGambler = new CoachGambler();
        coachGambler.start(null, null, null);

        for(TestData data : testData)
            System.out.println(
                    data.result.equals(coachGambler.play("", data.preOutCards, data.ownedCards)) + " : "
                            + data.preOutCards + ","
                            + data.ownedCards + ","
                            + data.result + " : "
                            + coachGambler.play("", data.preOutCards, data.ownedCards));
    }
}

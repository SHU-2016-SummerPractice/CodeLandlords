package com.wolfogre.codelandlords;

import org.junit.Test;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wolfogre on 8/20/16.
 */
public class WikipediaCardsCheckerTester {
    @Test
    public void testGetCardsType() {
        List<Map.Entry<String, WikipediaCardsChecker.CardsType>> testData = new ArrayList<>();
        testData.add(new AbstractMap.SimpleEntry<>("A", WikipediaCardsChecker.CardsType.单张));
        testData.add(new AbstractMap.SimpleEntry<>("AA", WikipediaCardsChecker.CardsType.一对));
        testData.add(new AbstractMap.SimpleEntry<>("AAAA", WikipediaCardsChecker.CardsType.炸弹));
        testData.add(new AbstractMap.SimpleEntry<>("SM", WikipediaCardsChecker.CardsType.火箭));
        testData.add(new AbstractMap.SimpleEntry<>("33445566", WikipediaCardsChecker.CardsType.双顺));
        testData.add(new AbstractMap.SimpleEntry<>("33447788", WikipediaCardsChecker.CardsType.错误));
        testData.add(new AbstractMap.SimpleEntry<>("3344556678", WikipediaCardsChecker.CardsType.错误));
        testData.add(new AbstractMap.SimpleEntry<>("3344", WikipediaCardsChecker.CardsType.错误));
        testData.add(new AbstractMap.SimpleEntry<>("KKK", WikipediaCardsChecker.CardsType.三不带));
        testData.add(new AbstractMap.SimpleEntry<>("KKKA", WikipediaCardsChecker.CardsType.三带一));
        testData.add(new AbstractMap.SimpleEntry<>("3KKKA", WikipediaCardsChecker.CardsType.错误));
        testData.add(new AbstractMap.SimpleEntry<>("33KKK", WikipediaCardsChecker.CardsType.三带二));
        testData.add(new AbstractMap.SimpleEntry<>("333444555666", WikipediaCardsChecker.CardsType.飞机不带翼));
        testData.add(new AbstractMap.SimpleEntry<>("33344457", WikipediaCardsChecker.CardsType.飞机带小翼));
        testData.add(new AbstractMap.SimpleEntry<>("3334445577", WikipediaCardsChecker.CardsType.飞机带大翼));
        testData.add(new AbstractMap.SimpleEntry<>("333444555666789M", WikipediaCardsChecker.CardsType.飞机带小翼));
        testData.add(new AbstractMap.SimpleEntry<>("333444555666778800QQ", WikipediaCardsChecker.CardsType.飞机带大翼));
        testData.add(new AbstractMap.SimpleEntry<>("333444555666780Q", WikipediaCardsChecker.CardsType.飞机带小翼));
        testData.add(new AbstractMap.SimpleEntry<>("AAA22234", WikipediaCardsChecker.CardsType.错误));
        testData.add(new AbstractMap.SimpleEntry<>("333444555777680Q", WikipediaCardsChecker.CardsType.错误));
        testData.add(new AbstractMap.SimpleEntry<>("333444555666778800", WikipediaCardsChecker.CardsType.错误));
        testData.add(new AbstractMap.SimpleEntry<>("33334455", WikipediaCardsChecker.CardsType.四带二对));
        testData.add(new AbstractMap.SimpleEntry<>("333345", WikipediaCardsChecker.CardsType.四带二张));
        testData.add(new AbstractMap.SimpleEntry<>("3333455", WikipediaCardsChecker.CardsType.错误));

        WikipediaCardsChecker cardsChecker = new WikipediaCardsChecker();
        for(Map.Entry<String, WikipediaCardsChecker.CardsType> entry : testData)
            System.out.println(
                    entry.getValue().equals(cardsChecker.getCardsType(entry.getKey())) + " : "
                    + entry.getKey() + " : "
                    + cardsChecker.getCardsType(entry.getKey()) + " : "
                    + entry.getValue());
    }

    @Test
    public void testCheck() {
        class TestData{
            public TestData(String preOutCards, String ownedCards, String outCards, boolean result) {
                this.preOutCards = preOutCards;
                this.ownedCards = ownedCards;
                this.outCards = outCards;
                this.result = result;
            }

            String preOutCards;
            String ownedCards;
            String outCards;
            boolean result;
        }

        List<TestData> testData = new ArrayList<>();

        testData.add(new TestData("A", "A2", "2", true));
        testData.add(new TestData("A", "A", "2", false));
        testData.add(new TestData("A", "A2", "A", false));

        WikipediaCardsChecker cardsChecker = new WikipediaCardsChecker();
        for(TestData data : testData)
            System.out.println(
                    (data.result == cardsChecker.check(data.preOutCards, data.ownedCards, data.outCards)) + " : "
                            + data.ownedCards + ","
                            + data.ownedCards + ","
                            + data.ownedCards + ","
                            + data.result + " : "
                            + cardsChecker.check(data.preOutCards, data.ownedCards, data.outCards));
    }
}

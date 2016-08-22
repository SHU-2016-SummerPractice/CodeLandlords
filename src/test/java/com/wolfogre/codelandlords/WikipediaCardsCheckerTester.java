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
        List<Map.Entry<String, CardsType>> testData = new ArrayList<>();
        testData.add(new AbstractMap.SimpleEntry<>("A", CardsType.单张));
        testData.add(new AbstractMap.SimpleEntry<>("AA", CardsType.一对));
        testData.add(new AbstractMap.SimpleEntry<>("AAAA", CardsType.炸弹));
        testData.add(new AbstractMap.SimpleEntry<>("SM", CardsType.火箭));
        testData.add(new AbstractMap.SimpleEntry<>("33445566", CardsType.双顺));
        testData.add(new AbstractMap.SimpleEntry<>("33447788", CardsType.错误));
        testData.add(new AbstractMap.SimpleEntry<>("3344556678", CardsType.错误));
        testData.add(new AbstractMap.SimpleEntry<>("3344", CardsType.错误));
        testData.add(new AbstractMap.SimpleEntry<>("KKK", CardsType.三不带));
        testData.add(new AbstractMap.SimpleEntry<>("KKKA", CardsType.三带一));
        testData.add(new AbstractMap.SimpleEntry<>("3KKKA", CardsType.错误));
        testData.add(new AbstractMap.SimpleEntry<>("33KKK", CardsType.三带二));
        testData.add(new AbstractMap.SimpleEntry<>("333444555666", CardsType.飞机不带翼));
        testData.add(new AbstractMap.SimpleEntry<>("33344457", CardsType.飞机带小翼));
        testData.add(new AbstractMap.SimpleEntry<>("3334445577", CardsType.飞机带大翼));
        testData.add(new AbstractMap.SimpleEntry<>("333444555666789M", CardsType.飞机带小翼));
        testData.add(new AbstractMap.SimpleEntry<>("333444555666778800QQ", CardsType.飞机带大翼));
        testData.add(new AbstractMap.SimpleEntry<>("333444555666780Q", CardsType.飞机带小翼));
        testData.add(new AbstractMap.SimpleEntry<>("333444555666788Q", CardsType.飞机带小翼));
        testData.add(new AbstractMap.SimpleEntry<>("3334445556667888", CardsType.飞机带小翼));
        testData.add(new AbstractMap.SimpleEntry<>("AAA22234", CardsType.错误));
        testData.add(new AbstractMap.SimpleEntry<>("333444555777680Q", CardsType.错误));
        testData.add(new AbstractMap.SimpleEntry<>("333444555666778800", CardsType.错误));
        testData.add(new AbstractMap.SimpleEntry<>("33334455", CardsType.四带二对));
        testData.add(new AbstractMap.SimpleEntry<>("333345", CardsType.四带二张));
        testData.add(new AbstractMap.SimpleEntry<>("3333455", CardsType.错误));

        CardsChecker cardsChecker = new CardsChecker();
        for(Map.Entry<String, CardsType> entry : testData)
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
        testData.add(new TestData("SM", "2222", "2222", false));
        testData.add(new TestData("2222", "AAAASM", "SM", true));
        testData.add(new TestData("333444555778", "888999000JQK", "888999000JQK", true));
        testData.add(new TestData("333444555777", "888999000JQK", "888999000JQK", true));
        testData.add(new TestData("333444555666", "888999000JQK", "888999000JQK", false));
        testData.add(new TestData("3334445556667778", "888999000JJJQQQ", "888999000JJJQQQ", false));
        testData.add(new TestData("333444555666777", "888999000JJJQQQKKKK", "888999000JJJQQQKKKK", false));
        testData.add(new TestData("333444555666777", "888999000JJJQQQKKKK", "KKKK", true));
        testData.add(new TestData("444555666888", "333999000JJJ", "333999000JJJ", true));
        testData.add(new TestData("99", "MS", "MS", true));
        CardsChecker cardsChecker = new CardsChecker();
        for(TestData data : testData)
            System.out.println(
                    (data.result == cardsChecker.check("", data.preOutCards, data.ownedCards, data.outCards)) + " : "
                            + data.preOutCards + ","
                            + data.ownedCards + ","
                            + data.outCards + ","
                            + data.result + " : "
                            + cardsChecker.check("", data.preOutCards, data.ownedCards, data.outCards));
    }
}

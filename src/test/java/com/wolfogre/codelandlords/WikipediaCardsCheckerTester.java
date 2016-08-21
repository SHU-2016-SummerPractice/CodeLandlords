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
        List<Map.Entry<String, CardsChecker.CardsType>> testData = new ArrayList<>();
        testData.add(new AbstractMap.SimpleEntry<>("A", CardsChecker.CardsType.单张));
        testData.add(new AbstractMap.SimpleEntry<>("AA", CardsChecker.CardsType.一对));
        testData.add(new AbstractMap.SimpleEntry<>("AAAA", CardsChecker.CardsType.炸弹));
        testData.add(new AbstractMap.SimpleEntry<>("SM", CardsChecker.CardsType.火箭));
        testData.add(new AbstractMap.SimpleEntry<>("33445566", CardsChecker.CardsType.双顺));
        testData.add(new AbstractMap.SimpleEntry<>("33447788", CardsChecker.CardsType.错误));
        testData.add(new AbstractMap.SimpleEntry<>("3344556678", CardsChecker.CardsType.错误));
        testData.add(new AbstractMap.SimpleEntry<>("3344", CardsChecker.CardsType.错误));
        testData.add(new AbstractMap.SimpleEntry<>("KKK", CardsChecker.CardsType.三不带));
        testData.add(new AbstractMap.SimpleEntry<>("KKKA", CardsChecker.CardsType.三带一));
        testData.add(new AbstractMap.SimpleEntry<>("3KKKA", CardsChecker.CardsType.错误));
        testData.add(new AbstractMap.SimpleEntry<>("33KKK", CardsChecker.CardsType.三带二));
        testData.add(new AbstractMap.SimpleEntry<>("333444555666", CardsChecker.CardsType.飞机不带翼));
        testData.add(new AbstractMap.SimpleEntry<>("33344457", CardsChecker.CardsType.飞机带小翼));
        testData.add(new AbstractMap.SimpleEntry<>("3334445577", CardsChecker.CardsType.飞机带大翼));
        testData.add(new AbstractMap.SimpleEntry<>("333444555666789M", CardsChecker.CardsType.飞机带小翼));
        testData.add(new AbstractMap.SimpleEntry<>("333444555666778800QQ", CardsChecker.CardsType.飞机带大翼));
        testData.add(new AbstractMap.SimpleEntry<>("333444555666780Q", CardsChecker.CardsType.飞机带小翼));
        testData.add(new AbstractMap.SimpleEntry<>("333444555666788Q", CardsChecker.CardsType.飞机带小翼));
        testData.add(new AbstractMap.SimpleEntry<>("3334445556667888", CardsChecker.CardsType.飞机带小翼));
        testData.add(new AbstractMap.SimpleEntry<>("AAA22234", CardsChecker.CardsType.错误));
        testData.add(new AbstractMap.SimpleEntry<>("333444555777680Q", CardsChecker.CardsType.错误));
        testData.add(new AbstractMap.SimpleEntry<>("333444555666778800", CardsChecker.CardsType.错误));
        testData.add(new AbstractMap.SimpleEntry<>("33334455", CardsChecker.CardsType.四带二对));
        testData.add(new AbstractMap.SimpleEntry<>("333345", CardsChecker.CardsType.四带二张));
        testData.add(new AbstractMap.SimpleEntry<>("3333455", CardsChecker.CardsType.错误));

        CardsChecker cardsChecker = new CardsChecker();
        for(Map.Entry<String, CardsChecker.CardsType> entry : testData)
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
        testData.add(new TestData("444555666888", "333999000JJJ", "333999000JJJ", true));
        CardsChecker cardsChecker = new CardsChecker();
        for(TestData data : testData)
            System.out.println(
                    (data.result == cardsChecker.check(data.preOutCards, data.ownedCards, data.outCards)) + " : "
                            + data.preOutCards + ","
                            + data.ownedCards + ","
                            + data.outCards + ","
                            + data.result + " : "
                            + cardsChecker.check(data.preOutCards, data.ownedCards, data.outCards));
    }
}

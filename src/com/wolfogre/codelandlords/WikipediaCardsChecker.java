package com.wolfogre.codelandlords;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wolfogre on 8/10/16.
 */
public class WikipediaCardsChecker implements CardsChecker {
    @Override
    public boolean check(String preOutCards, String ownedCards, String outCards) {
        if(!contains(ownedCards, outCards))
            return false;
        ownedCards = sortCards(ownedCards);
        outCards = sortCards(outCards);
        //TODO
        return false;
    }

    private enum CardsType {
        错误,
        单张,
        一对,
        三不带,
        三带一,
        三带二,
        单顺,
        双顺,
        四带二,
        飞机,
        航天飞机,
        炸弹,
        火箭,
    }

    public CardsType getCardsType(String cards){
        //TODO:判断牌型的逻辑
        if(cards.isEmpty())
            return CardsType.错误;
        for(char ch : cards.toCharArray())
            if(FormatCards.getIndexByCard(ch) == -1)
                return CardsType.错误;
        FormatCards formatCards = new FormatCards(cards);
        switch (formatCards.getCounts()[0]){
            case 1:
                if(cards.length() == 1)
                    return CardsType.单张;
                if(cards.length() >= 5
                        && formatCards.getCards()[formatCards.size() - 1] <= 'A'
                        && formatCards.isContinuous(0, formatCards.size()))
                    return CardsType.单顺;
                if(cards.length() == 2
                        && formatCards.getCards()[0] == 'M'
                        && formatCards.getCards()[1] == 'S')
                    return CardsType.火箭;
                return CardsType.错误;
            case 2:
                if(cards.length() == 2)
                    return CardsType.一对;
                if(cards.length() == 3)
                    return CardsType.错误;
                if(cards.length() > 3
                        && judgeStraightPair(formatCards, 0))
//                        && formatCards.getCounts()[0] == 2
//                        && formatCards.getCounts()[formatCards.size() - 1] == 2
//                        && formatCards.getCards()[formatCards.size() - 1] <= 'A')
                    return CardsType.双顺;
                return CardsType.错误;
            case 3:
                if(cards.length() == 3)
                    return CardsType.三不带;
                if(cards.length() == 4)
                    return CardsType.三带一;
                if(cards.length() == 5 && formatCards.size() == 2)
                    return CardsType.三带二;
                int endOfThree;
                endOfThree = 0;
                while (formatCards.getCounts()[endOfThree] != 3 && endOfThree < formatCards.size()) {
                    ++endOfThree;
                }
                //TODO:注意 333 444 555 QQQ
                if(formatCards.isContinuous(0, endOfThree)){
                    if(formatCards.size() == endOfThree)
                        return CardsType.飞机;
                    //TODO:碰到问题了，333 444 555 666 JJJJ算不算？
                }
            case 4:
                //TODO:注意 JJJJ 333 444 555 666
            default:
                return null;
        }
    }

    private boolean contains(String ownedCards, String outCards){
        int[] record = new int[15];
        for(char ch : ownedCards.toCharArray()){
            ++record[FormatCards.getIndexByCard(ch)];
        }
        for(char ch : outCards.toCharArray()){
            if(FormatCards.getIndexByCard(ch) == -1 || record[FormatCards.getIndexByCard(ch)] == 0)
                return false;
            --record[FormatCards.getIndexByCard(ch)];
        }
        return true;
    }

    private String sortCards(String origin){
        int[] record = new int[15];
        for(char ch : origin.toCharArray()){
            ++record[FormatCards.getIndexByCard(ch)];
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 15; ++i){
            while(record[i]-- > 0)
                sb.append(FormatCards.getCardByIndex(i));
        }
        return sb.toString();
    }

    /**
     * 判断是不是连续的顺子对，传入0是从头开始判断，判断规则：最小为3，最大为A
     * @param formatCards 牌对象
     * @param index 当前是第几类排，如223355中，2是0，3是1，5是2
     * @return
     */
    private Boolean judgeStraightPair(FormatCards formatCards, int index){
        if(index == formatCards.size()){
            return true;
        }
        if(formatCards.getCounts()[index] == 2){
            if(index + 1 < formatCards.size()
                    && (FormatCards.getIndexByCard(formatCards.getCards()[index]) != FormatCards.getIndexByCard(formatCards.getCards()[index+1]) - 1
                    || formatCards.getCards()[index+1] == 'A') ){
                return false;
            }
            return judgeStraightPair(formatCards, index+1);
        }
        return false;
    }

    @Test
    public void testJSP(){
        String cards = "2233";
        FormatCards formatCards = new FormatCards(cards);
        System.out.println(judgeStraightPair(formatCards, 0));
    }

    /**
     * 判断是否成顺子的通用方法，在num为3的时候请注意，这里只能判断3是否成顺，如果带了对子，请判断对子的数量和仨子的数量是否相等
     * @param formatCards 牌对象
     * @param index 从第几类数字开始判断
     * @param num 单顺1，双顺2，三顺3
     * @return
     */
    private Boolean judgeStraight(FormatCards formatCards, int index, int num){
        if(index == formatCards.size()){
            return true;
        }
        if(formatCards.getCounts()[index] == num){
            if(index + 1 < formatCards.size()
                    && (FormatCards.getIndexByCard(formatCards.getCards()[index]) != FormatCards.getIndexByCard(formatCards.getCards()[index+1]) - 1
                    || formatCards.getCards()[index+1] == 'A') ){
                return false;
            }
            return judgeStraight(formatCards, index+1, num);
        }
        return false;
    }

    @Test
    public void testJS(){
        String cards = "333444555";
        FormatCards formatCards = new FormatCards(cards);
        System.out.println(judgeStraight(formatCards, 0, 3));
    }
}



package com.wolfogre.codelandlords;

import org.junit.Test;

/**
 * Created by Administrator on 2016/8/23.
 */
public class QjGambler extends Gambler {
    @Override
    public String getName() {
        return "QJ_1.0";
    }

    @Override
    public void start(Role landlord, String landlordExtraCards, String ownedCards) {

    }

    @Override
    public void over(Role winner, String prePreRemainCards, String preRemainCards, String remainCards) {

    }

    @Override
    public String play(String prePreCards, String preCards, String ownedCards) {
        String upCards = preCards;
        if(upCards.isEmpty())
            upCards = prePreCards;
        if(upCards.isEmpty()){
            int endIndex = ownedCards.length();
            while(!check(prePreCards, preCards, ownedCards, ownedCards.substring(0, endIndex--)));
            return ownedCards.substring(0, endIndex + 1);
        }
        CardsType type = getCardsType(upCards);
        int index = 0;
        String outCards = "";
        upCards = sort(upCards);
        switch(type){
            case 单张:
                index = 0;
                while(index < ownedCards.length()
                        && !isBigger(upCards, ownedCards.charAt(index)+""))
                    index++;
                if(index == ownedCards.length())
                    return "";
                outCards = ownedCards.charAt(index)+"";
                return outCards;
            case 一对:
                if(ownedCards.length() < 2)
                    return "";
                index = 0;
                outCards = ownedCards.substring(index, index + 2);
                while(index + 2 <= ownedCards.length()
                        && !isBigger(upCards, outCards)){
                    outCards = ownedCards.substring(index, index + 2);
                    index++;
                }
                if(index + 2 > ownedCards.length())
                    return "";
                return outCards;
            case 三不带:
                if(ownedCards.length() < 3)
                    return "";
                index = 0;
                outCards = ownedCards.substring(index, index + 3);
                while(index + 3 <= ownedCards.length()
                        && !isBigger(upCards, outCards)){
                    outCards = ownedCards.substring(index, index + 3);
                    index++;
                }
                if(index + 3 > ownedCards.length())
                    return "";
                return outCards;
            case 三带一:
                if(ownedCards.length() < 4)
                    return "";
                index = 0;
                outCards = ownedCards.substring(index, index + 3);
                while(index + 3 <= ownedCards.length()
                        && !isBigger(upCards.substring(0, 3), outCards)){
                    outCards = ownedCards.substring(index, index + 3);
                    index++;
                }
                if(index + 3 > ownedCards.length())
                    return "";
                if(index == 0)
                    return ownedCards.charAt(index + 3) + outCards;
                return ownedCards.charAt(0) + outCards;
            case 三带二:
                if(ownedCards.length() < 5)
                    return "";
                index = 0;
                outCards = ownedCards.substring(index, index + 3);
                while(index + 3 <= ownedCards.length()
                        &&!isBigger(upCards.substring(0, 3), outCards)){
                    outCards = ownedCards.substring(index, index+3);
                    index++;
                }
                if(index + 3 > ownedCards.length())
                    return "";
                String pairs = ownedCards.substring(0, 2);
                int index2 = 0;
                while(index2 + 2 <= ownedCards.length()
                        && (getCardsType(pairs) != CardsType.一对
                        || (index2 + 1 >= index && index2 < index + 3)
                        || (index2 < index + 2 && index2 + 1 >= index))){
                    pairs = ownedCards.substring(index2, index2 + 2);
                    index2++;
                }
                if(index2 + 1 > ownedCards.length()){
                    return "";
                }
                return outCards + pairs;
            case 单顺:
                return "";
            case 双顺:
                return "";
            case 四带二张:
                return "";
            case 四带二对:
                return "";
            case 飞机不带翼:
                return "";
            case 飞机带小翼:
                return "";
            case 飞机带大翼:
                return "";
            case 炸弹:
                return "";
            case 火箭:
                return "";
        }
        return "";
    }

    String sort(String s){
        return new FormatCards(s).toString();
    }

    @Test
    public void TestPlay(){
        System.out.println(play("", "3334", "34446789990JQAA2S"));
    }
}

package com.wolfogre.codelandlords;

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
        for(char ch : cards.toCharArray())
            if(FormatCards.getIndexByCard(ch) == -1)
                return CardsType.错误;
        FormatCards formatCards = new FormatCards(cards);
        switch (cards.length()){
            case 1:
                return CardsType.单张;
            case 2:
                if(formatCards.size() == 1)
                    return CardsType.一对;
                if(cards.charAt(0) == 'S' && cards.charAt(1) == 'M')
                    return CardsType.火箭;
                return CardsType.错误;
            case 3:
                if(formatCards.size() == 1)
                    return CardsType.三不带;
                return CardsType.错误;
            case 4:
                if(formatCards.size() == 1)
                    return CardsType.炸弹;
                if(formatCards.size() == 2 && formatCards.getCounts()[0] == 3)
                    return CardsType.三带一;
                return CardsType.错误;
            case 5:
                if(formatCards.size() == 2 && formatCards.getCounts()[0] == 3)
                    return CardsType.三带二;
                if(formatCards.isContinuous(0, formatCards.size())
                        && FormatCards.getIndexByCard(formatCards.getCards()[cards.length() - 1]) <= FormatCards.getIndexByCard('A'))
                    return CardsType.单顺;
                return CardsType.错误;
            case 6: //TODO
                if(cards.charAt(0) == cards.charAt(1) && cards.charAt(1) == cards.charAt(2) && cards.charAt(2) == cards.charAt(3)
                        || cards.charAt(1) == cards.charAt(2) && cards.charAt(2) == cards.charAt(3) && cards.charAt(3) == cards.charAt(4)
                        || cards.charAt(2) == cards.charAt(3) && cards.charAt(3) == cards.charAt(4) && cards.charAt(4) == cards.charAt(5))
                    return CardsType.四带二;
                if(formatCards.isContinuous(0, formatCards.size())
                        && FormatCards.getIndexByCard(formatCards.getCards()[cards.length() - 1]) <= FormatCards.getIndexByCard('A'))
                    return CardsType.单顺;
                return CardsType.错误;
            case 7: //TODO
                if(formatCards.isContinuous(0, formatCards.size())
                        && FormatCards.getIndexByCard(formatCards.getCards()[cards.length() - 1]) <= FormatCards.getIndexByCard('A'))
                    return CardsType.单顺;
                return CardsType.错误;
            case 8: //TODO
                if(formatCards.isContinuous(0, formatCards.size())
                        && FormatCards.getIndexByCard(formatCards.getCards()[cards.length() - 1]) <= FormatCards.getIndexByCard('A'))
                    return CardsType.单顺;
                return CardsType.错误;
            case 9: //TODO
                if(formatCards.isContinuous(0, formatCards.size())
                        && FormatCards.getIndexByCard(formatCards.getCards()[cards.length() - 1]) <= FormatCards.getIndexByCard('A'))
                    return CardsType.单顺;
                return CardsType.错误;
            case 10: //TODO
                if(formatCards.isContinuous(0, formatCards.size())
                        && FormatCards.getIndexByCard(formatCards.getCards()[cards.length() - 1]) <= FormatCards.getIndexByCard('A'))
                    return CardsType.单顺;
                return CardsType.错误;
            case 11: //TODO
                if(formatCards.isContinuous(0, formatCards.size())
                        && FormatCards.getIndexByCard(formatCards.getCards()[cards.length() - 1]) <= FormatCards.getIndexByCard('A'))
                    return CardsType.单顺;
                return CardsType.错误;
            case 12: //TODO
                if(formatCards.isContinuous(0, formatCards.size())
                        && FormatCards.getIndexByCard(formatCards.getCards()[cards.length() - 1]) <= FormatCards.getIndexByCard('A'))
                    return CardsType.单顺;
                return CardsType.错误;
            case 13: //TODO
            case 14: //TODO
            case 15: //TODO
            case 16: //TODO
            case 17: //TODO
            case 18: //TODO
            case 19: //TODO
            case 20: //TODO
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


}



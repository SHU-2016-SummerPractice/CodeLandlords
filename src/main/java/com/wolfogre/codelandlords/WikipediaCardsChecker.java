package com.wolfogre.codelandlords;

import org.junit.Test;

/**
 * 根据维基百科-斗地主的规则（有调整）实现的出牌检查器
 */
public class WikipediaCardsChecker implements CardsChecker {
    @Override
    public boolean check(String preOutCards, String ownedCards, String outCards) {
        if(!contains(ownedCards, outCards))
            return false;
        return false;
    }

    /**
     * 牌型
     */
    public enum CardsType {
        错误,
        单张,
        一对,
        三不带,
        三带一,
        三带二,
        单顺,
        双顺,
        四带二张,
        四带二对,
        飞机不带翼,
        飞机带小翼,
        飞机带大翼,
        炸弹,
        火箭,
    }

    /**
     * 获得牌型
     * @param cards 给定的牌
     * @return 牌型
     */
    public CardsType getCardsType(String cards){
        if(cards.isEmpty())
            return CardsType.错误;
        for(char ch : cards.toCharArray())
            if(FormatCards.getIndexByCard(ch) == -1)
                return CardsType.错误;

        FormatCards formatCards = new FormatCards(cards);

        switch (formatCards.getCounts()[0]){
            case 1:
                if(formatCards.size() == 1)
                    return CardsType.单张;
                if(formatCards.size() >= 5
                        && FormatCards.getIndexByCard(formatCards.getCards()[formatCards.size() - 1]) <= FormatCards.getIndexByCard('A')
                        && formatCards.isContinuous(0, formatCards.size()))
                    return CardsType.单顺;
                if(formatCards.size() == 2
                        && formatCards.getCards()[0] == 'M'
                        && formatCards.getCards()[1] == 'S')
                    return CardsType.火箭;
                return CardsType.错误;
            case 2:
                if(formatCards.size() == 1)
                    return CardsType.一对;
                if(formatCards.size() >= 3
                        && formatCards.getCounts()[0] == 2
                        && formatCards.getCounts()[formatCards.size() - 1] == 2
                        && FormatCards.getIndexByCard(formatCards.getCards()[formatCards.size() - 1]) <= FormatCards.getIndexByCard('A')
                        && formatCards.isContinuous(0, formatCards.size()))
                    return CardsType.双顺;
                return CardsType.错误;
            case 3:
                if(formatCards.size() == 1)
                    return CardsType.三不带;
                if(formatCards.size() == 2){
                    if(formatCards.getCounts()[1] == 1)
                        return CardsType.三带一;
                    if(formatCards.getCounts()[1] == 2)
                        return CardsType.三带二;
                }
                if(formatCards.size() >= 2){
                    if(formatCards.getCounts()[formatCards.size() - 1] == 3
                            && formatCards.isContinuous(0, formatCards.size())
                            && FormatCards.getIndexByCard(formatCards.getCards()[formatCards.size() - 1]) <= FormatCards.getIndexByCard('A')){
                        return CardsType.飞机不带翼;
                    }
                    if(formatCards.size() % 2 == 0){
                        if(formatCards.getCounts()[formatCards.size() / 2 - 1] == 3){
                            if(formatCards.getCounts()[formatCards.size() / 2] == 1 && formatCards.getCounts()[formatCards.size() - 1] == 1)
                                return CardsType.飞机带小翼;
                            if(formatCards.getCounts()[formatCards.size() / 2] == 2 && formatCards.getCounts()[formatCards.size() - 1] == 2)
                                return CardsType.飞机带大翼;
                        }
                    }
                }
                return CardsType.错误;
            case 4:
                if(formatCards.size() == 1){
                    return CardsType.炸弹;
                }
                if(formatCards.size() == 3){
                    if(formatCards.getCounts()[1] == 1 && formatCards.getCounts()[2] == 1)
                        return CardsType.四带二张;
                    if(formatCards.getCounts()[1] == 2 && formatCards.getCounts()[2] == 2)
                        return CardsType.四带二对;
                }
                // 44448888 这种情况不算四带二了吧，四带二的目的是牺牲炸来跑单只张或跑小对，但这个可是两个炸哇
                return CardsType.错误;
            default:
                return null;
        }
    }

    /**
     * 判读拥有的牌是否包含出的牌
     * @param ownedCards 拥有的牌
     * @param outCards 出的牌
     * @return 是否包含
     */
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
}



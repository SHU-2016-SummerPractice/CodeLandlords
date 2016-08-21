package com.wolfogre.codelandlords;

/**
 * 根据维基百科-斗地主的规则（有调整）实现的出牌检查器
 */

class CardsChecker {

    /**
     * 出牌检查
     * @param preOutCards 上家出的牌
     * @param ownedCards 玩家拥有的牌
     * @param outCards 玩家出的牌
     * @return 是否合法
     */
    boolean check(String preOutCards, String ownedCards, String outCards) {
        return contains(ownedCards, outCards)
                && isBigger(preOutCards, outCards);
    }

    /**
     * 获得牌型
     * @param cards 给定的牌
     * @return 牌型
     */
    CardsType getCardsType(String cards){
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

                //TODO:大 Bug，虽然不再讨论带四张相同单只的情况，却忽略了带两张或三张相同单只的情况 T_T
                if(formatCards.size() >= 2){
                    if(formatCards.getCounts()[formatCards.size() - 1] == 3
                            && formatCards.isContinuous(0, formatCards.size())
                            && FormatCards.getIndexByCard(formatCards.getCards()[formatCards.size() - 1]) <= FormatCards.getIndexByCard('A')){
                        return CardsType.飞机不带翼;
                    }
                    if(formatCards.size() % 2 == 0
                            && formatCards.getCounts()[formatCards.size() / 2 - 1] == 3
                            && FormatCards.getIndexByCard(formatCards.getCards()[formatCards.size() / 2 - 1]) <= FormatCards.getIndexByCard('A')
                            && formatCards.isContinuous(0, formatCards.size() / 2)
                            && formatCards.getCounts()[formatCards.size() / 2] == 2
                            && formatCards.getCounts()[formatCards.size() - 1] == 2)
                        return CardsType.飞机带大翼;
                    int[] result = splitAircraftWithWinglets(formatCards);
                    if(result[0] != 0)
                        return CardsType.飞机带小翼;
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
     * 判读自己出的牌是否比上家大
     * @param preOutCards 上家出的牌
     * @param outCards 自己出的牌
     * @return 是否比上家大
     */
    boolean isBigger(String preOutCards, String outCards){
        CardsType preOutCardsType = getCardsType(preOutCards);
        CardsType outCardsType = getCardsType(outCards);
        FormatCards preFormatCards = new FormatCards(preOutCards);
        FormatCards formatCards = new FormatCards(outCards);

        switch (preOutCardsType) {
            case 错误:
                throw new RuntimeException("PreOutCards is illegal");
            case 炸弹:
                return outCardsType.equals(CardsType.火箭)
                        || outCardsType.equals(CardsType.炸弹) && FormatCards.getIndexByCard(preFormatCards.getCards()[0]) < FormatCards.getIndexByCard(formatCards.getCards()[0]);
            case 火箭:
                return false;
            case 飞机带小翼:
                return outCardsType == CardsType.炸弹 ||
                        outCardsType.equals(preOutCardsType)
                                && splitAircraftWithWinglets(formatCards)[0] == splitAircraftWithWinglets(preFormatCards)[0]
                                && splitAircraftWithWinglets(formatCards)[1] > splitAircraftWithWinglets(preFormatCards)[1];
            default:
                return outCardsType == CardsType.炸弹 ||
                        outCardsType.equals(preOutCardsType)
                                && preFormatCards.size() == formatCards.size()
                                && FormatCards.getIndexByCard(preFormatCards.getCards()[0]) < FormatCards.getIndexByCard(formatCards.getCards()[0]);
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

    /**
     * 解析飞机带小翼的情况
     * @param formatCards 规整化后的牌
     * @return [飞机长度或不是飞机(0)，飞机最小端的牌的值]
     */
    private int[] splitAircraftWithWinglets(FormatCards formatCards){
        int endIndexOfThree = 0;
        while(endIndexOfThree < formatCards.size() && formatCards.getCounts()[endIndexOfThree] == 3)
            ++endIndexOfThree;
        if(endIndexOfThree - 0 >= 2
                && formatCards.isContinuous(0, endIndexOfThree)
                && FormatCards.getIndexByCard(formatCards.getCards()[endIndexOfThree - 1]) <= FormatCards.getIndexByCard('A')){
            int index = endIndexOfThree;
            int wingletsCount = 0;
            while(index < formatCards.size())
                wingletsCount += formatCards.getCounts()[index++];
            if(endIndexOfThree - 0 == wingletsCount)
                return new int[]{endIndexOfThree - 0, FormatCards.getIndexByCard(formatCards.getCards()[0])};
        }
        if(endIndexOfThree - 1 >= 2
                && formatCards.isContinuous(1, endIndexOfThree)
                && FormatCards.getIndexByCard(formatCards.getCards()[endIndexOfThree - 1]) <= FormatCards.getIndexByCard('A')){
            int index = endIndexOfThree;
            int wingletsCount = 3;
            while(index < formatCards.size())
                wingletsCount += formatCards.getCounts()[index++];
            if(endIndexOfThree - 1 == wingletsCount)
                return new int[]{endIndexOfThree - 1, FormatCards.getIndexByCard(formatCards.getCards()[1])};
        }
        if(endIndexOfThree - 1 >= 2
                && formatCards.isContinuous(0, endIndexOfThree - 1)
                && FormatCards.getIndexByCard(formatCards.getCards()[endIndexOfThree - 2]) <= FormatCards.getIndexByCard('A')){
            int index = endIndexOfThree - 1;
            int wingletsCount = 0;
            while(index < formatCards.size())
                wingletsCount += formatCards.getCounts()[index++];
            if(endIndexOfThree - 1 == wingletsCount)
                return new int[]{endIndexOfThree - 1, FormatCards.getIndexByCard(formatCards.getCards()[0])};
        }
        return new int[]{0, 0};
    }
}



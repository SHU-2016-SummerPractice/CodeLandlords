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
        四带二,
        飞机,
        飞机带翼,
        航天飞机,
        航天飞机带翼,
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
                if(cards.length() > 5
                        && judgeStraight(formatCards, 0, 3)){
                    return CardsType.飞机;
                }
                if(cards.length() > 7
                        && judgePlaneWithWing(formatCards, 0, 3, getPlaneSize(formatCards, 3))){
                    return CardsType.飞机带翼;
                }
                return CardsType.错误;
            case 4:
                if(formatCards.size() == 1){
                    return CardsType.炸弹;
                }
                if(judgeStraight(formatCards, 0, 4)){
                    return CardsType.航天飞机;
                }
                if(judgePlaneWithWing(formatCards, 0, 4, getPlaneSize(formatCards, 4))){
                    return CardsType.航天飞机带翼;
                }
                if(formatCards.size() == 3
                        && formatCards.getCounts()[1] == formatCards.getCounts()[2]
                        && formatCards.getCounts()[1] <= 2){
                    //TODO:比如44448888，算是4带二，但是是四个8带两个4，而不是四个4带两个8
                    return CardsType.四带二;
                }
                if(cards.length() == 5
                        || cards.length() == 7){
                    return CardsType.错误;
                }
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

    /**
     * 判断是否成飞机带翅膀
     * @param formatCards 牌对象
     * @param index 从第几类数字开始判断
     * @param num 三顺3，航天飞机4
     * @param planeSize 飞机数量。333444555，飞机数量3
     * @return
     */
    private Boolean judgePlaneWithWing(FormatCards formatCards, int index, int num, int planeSize){
        if(index == planeSize){
            //判断后面是对子或者别的
            if(formatCards.size() == (planeSize << 1)){
                for(int i = formatCards.size()-1; i > planeSize; i--){
                    if(formatCards.getCounts()[i] != formatCards.getCounts()[i-1]){
                        return false;
                    }
                }
                return true;
            }else{
                return false;
            }
        }
        if(formatCards.getCounts()[index] == num){
            if(index + 1 < planeSize
                    && (FormatCards.getIndexByCard(formatCards.getCards()[index]) != FormatCards.getIndexByCard(formatCards.getCards()[index+1]) - 1
                    || formatCards.getCards()[index+1] == 'A') ){
                return false;
            }
            return judgePlaneWithWing(formatCards, index+1, num, planeSize);
        }
        return false;
    }

    @Test
    public void TestJPWW(){
        String cards = "3333444478";
        FormatCards formatCards = new FormatCards(cards);
        System.out.println(judgePlaneWithWing(formatCards, 0, 4, 2));
    }

    /**获取同一长度牌的的数量。比如333444，返回2。注意这里不会报错，请先判断是否连续
     * @param formatCards 牌对象
     * @param num 三顺3
     * @return
     */
    private int getPlaneSize(FormatCards formatCards, int num){
        int i = 0, count = 0;
        while(formatCards.getCounts()[i++] == num){
            count ++;
        }
        return count;
    }

}



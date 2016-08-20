package com.wolfogre.codelandlords;

import java.util.HashMap;
import java.util.Map;

/**
 * 将牌规整化的工具
 * 方便牌型判断和输出
 */
public class FormatCards{

    private char [] cards;
    private int [] counts;

    /**
     * 构造方法
     * 它将对乱序的牌进行一次排序
     * 排序依据是，出现次数多的牌在前，若出现次数一样，小的牌在前
     * @param origin 乱序的牌
     */
    public FormatCards(String origin){
        HashMap<Character, Integer> record = new HashMap<Character, Integer>();
        for(char ch : origin.toCharArray()){
            record.put(ch, record.getOrDefault(ch, 0) + 1);
        }
        cards = new char[record.size()];
        counts = new int[record.size()];
        int index = 0;
        for(Map.Entry<Character, Integer> e : record.entrySet()){
            cards[index] = e.getKey();
            counts[index] = e.getValue();
            ++index;
        }
        for(int i = cards.length - 1; i >= 0; --i){
            for(int j = 0; j < i; ++j){
                if(!(counts[j] > counts[j + 1]
                        || counts[j] == counts[j + 1] && getIndexByCard(cards[j]) < getIndexByCard(cards[j + 1]))){
                    int temp1 = counts[j];
                    counts[j] = counts[j + 1];
                    counts[j + 1] = temp1;
                    char temp2 = cards[j];
                    cards[j] = cards[j + 1];
                    cards[j + 1] = temp2;
                }
            }
        }
    }

    /**
     * 获得规整化后的牌
     * 如 "3444555A"，则
     * getCards() = ['4','5','3','A']
     * @return 规整化后的牌
     */
    public char[] getCards() {
        return cards.clone();
    }

    /**
     * 获得规整化后的牌出现的次数
     * 如 "3444555A"，则
     * getCounts() = [3,3,1,1]
     * @return 规整化后的牌出现的次数
     */
    public int[] getCounts() {
        return counts.clone();
    }

    /**
     * 获得出现的牌的种数
     * 如 "3444555A"，则
     * size() = 4
     * @return 出现的牌的种数
     */
    public int size(){
        return cards.length;
    }

    /**
     * 判断规整化后的牌，从 getCards()[start] 到 getCards()[end] 是不是连续的
     * @param start 起始下标，含
     * @param end 结束下标，不含
     * @return 是否连续
     */
    public boolean isContinuous(int start, int end){
        for(int i = start; i < end - 1; ++i){
            if(getIndexByCard(cards[i]) != getIndexByCard(cards[i + 1]) - 1)
                return false;
        }
        return true;
    }

    /**
     * 获得规整化后的牌的输出形式
     * 如 "3444555A"，则
     * toString() = "4445553A"
     * @return 规整化后的牌的输出形式
     */
    @Override
    public String toString(){
        int [] tempCounts = counts.clone();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < tempCounts.length; ++i){
            while(tempCounts[i]-- > 0)
                sb.append(cards[i]);
        }
        return sb.toString();
    }

    /**
     * 获得一张牌的序号，牌越大序号越大
     * @param ch 单张牌
     * @return 序号，出错返回 -1
     */
    public static int getIndexByCard(char ch){
        switch (ch){
            case '3':
                return 0;
            case '4':
                return 1;
            case '5':
                return 2;
            case '6':
                return 3;
            case '7':
                return 4;
            case '8':
                return 5;
            case '9':
                return 6;
            case '0':
                return 7;
            case 'J':
                return 8;
            case 'Q':
                return 9;
            case 'K':
                return 10;
            case 'A':
                return 11;
            case '2':
                return 12;
            case 'M':
                return 13;
            case 'S':
                return 14;
            default:
                return -1;
        }
    }

    /**
     * 获得某个序号对应的牌
     * @param index 序号
     * @return 单张牌，出错返回 '\0'
     */
    public static char getCardByIndex(int index){
        switch (index){
            case 0:
                return '3';
            case 1:
                return '4';
            case 2:
                return '5';
            case 3:
                return '6';
            case 4:
                return '7';
            case 5:
                return '8';
            case 6:
                return '9';
            case 7:
                return '0';
            case 8:
                return 'J';
            case 9:
                return 'Q';
            case 10:
                return 'K';
            case 11:
                return 'A';
            case 12:
                return '2';
            case 13:
                return 'M';
            case 14:
                return 'S';
            default:
                return '\0';
        }
    }
}
package com.wolfogre.codelandlords;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wolfogre on 8/12/16.
 */
public class FormatCards{
    private char [] cards;
    private int [] counts;
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

    public char[] getCards() {
        return cards.clone();
    }

    public int[] getCounts() {
        return counts.clone();
    }

    public int size(){
        return counts.length;
    }

    public boolean isContinuous(int start, int end){
        for(int i = start; i < end - 1; ++i){
            if(getIndexByCard(cards[i]) != getIndexByCard(cards[i + 1]) - 1)
                return false;
        }
        return true;
    }

    public String toCards(){
        int [] tempCounts = counts.clone();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < tempCounts.length; ++i){
            while(tempCounts[i]-- > 0)
                sb.append(cards[i]);
        }
        return sb.toString();
    }

    static int getIndexByCard(char ch){
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

    static public char getCardByIndex(int index){
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
                return 0;
        }
    }
}
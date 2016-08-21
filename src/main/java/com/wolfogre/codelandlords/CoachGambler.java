package com.wolfogre.codelandlords;

/**
 * 陪练
 * 玩家要做的也是写一个这样的类，实现 Gambler 接口，
 * 为了让玩家在测试阶段有陪练，
 * CoachGambler 作为一个炮灰上场
 */
public class CoachGambler extends Gambler {

    @Override
    public String getName() {
        return "Coach_1.0";
        // 1.0 的基本逻辑就是管你三七二十一有的打就打
    }

    @Override
    public void start(Role landlord, String landlordExtraCards, String ownedCards) {

    }

    @Override
    public void over(Role winner, String prePreRemainCards, String preRemainCards, String remainCards) {

    }

    @Override
    public String play(String prePreCards, String preCards, String ownedCards) {
        this.ownedCards = ownedCards;
        this.preCards = preCards;
        this.ownedCards = ownedCards;
        searchResult = new StringBuilder();
        if(search(0))
            return searchResult.toString();
        return "";
    }

    String preCards;
    String ownedCards;
    StringBuilder searchResult;
    boolean search(int index){
        if(check(preCards, ownedCards, searchResult.toString()))
            return true;
        if(index == ownedCards.length())
            return false;
        searchResult.append(ownedCards.charAt(index));
        if(search(index + 1))
            return true;
        searchResult.deleteCharAt(searchResult.length() - 1);
        return search(index + 1);
    }
}

package com.wolfogre.codelandlords;

/**
 * 陪练
 * 玩家要做的也是写一个这样的类，实现 Gambler 接口，
 * 为了让玩家在测试阶段有陪练，
 * CoachGambler 作为一个炮灰上场，
 */
public class CoachGambler implements Gambler {
    @Override
    public String getName() {
        return "Coach_1.0";
    }

    @Override
    public void start(Role landlord, String landlordExtraCards, String ownedCards) {
        //TODO
    }

    @Override
    public void over(Role winner, String prePreRemainCards, String preRemainCards, String remainCards) {
        //TODO
    }

    @Override
    public String play(String prePreCards, String preCards, String ownedCards) {
        //TODO
        return null;
    }
}

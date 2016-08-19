package com.wolfogre.codelandlords;

/**
 * Created by wolfogre on 8/10/16.
 */
public class CoachGambler implements Gambler {
    @Override
    public String getName() {
        return "Coach_1.0";
    }

    @Override
    public void start(Role landlord, String landlordExtraCards, String ownedCards) {

    }

    @Override
    public void over(Role winner, String prePreRemainCards, String preRemainCards, String remainCards) {

    }

    @Override
    public String play(String prePreCards, String preCards, String ownedCards) {
        return null;
    }
}

package com.wolfogre.codelandlords;

/**
 * Created by wolfogre on 8/10/16.
 */
public class FoulException extends Exception {
    private String prePreOutCards;
    private String preOutCards;
    private String ownedCards;
    private String outCards;

    public FoulException(String message, String prePreOutCards, String preOutCards, String ownedCards, String outCards) {
        super(message);
        this.prePreOutCards = prePreOutCards;
        this.preOutCards = preOutCards;
        this.ownedCards = ownedCards;
        this.outCards = outCards;
    }

    public String getPrePreOutCards() {
        return prePreOutCards;
    }

    public void setPrePreOutCards(String prePreOutCards) {
        this.prePreOutCards = prePreOutCards;
    }

    public String getPreOutCards() {
        return preOutCards;
    }

    public void setPreOutCards(String preOutCards) {
        this.preOutCards = preOutCards;
    }

    public String getOwnedCards() {
        return ownedCards;
    }

    public void setOwnedCards(String ownedCards) {
        this.ownedCards = ownedCards;
    }

    public String getOutCards() {
        return outCards;
    }

    public void setOutCards(String outCards) {
        this.outCards = outCards;
    }
}

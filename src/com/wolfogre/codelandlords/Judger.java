package com.wolfogre.codelandlords;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by wolfogre on 8/9/16.
 */
public class Judger {

    private Gambler[] gambler;
    private CardsChecker cardsChecker;
    private Random random;

    public Judger(Gambler gambler1, Gambler gambler2, Gambler gambler3, CardsChecker cardsChecker){
        random = new Random();
        gambler = new Gambler[3];
        gambler[0] = gambler1;
        gambler[1] = gambler2;
        gambler[2] = gambler3;
        this.cardsChecker = cardsChecker;
    }

    public int[] judge() throws FoulException {
        ArrayList<String> outCardsQueue = new ArrayList<>();
        int landlord = getRandomLandlord();
        String[] cards = getRandomCards(landlord);
        int turn = landlord;
        for(int i = 0; i < 3; ++i){
            gambler[i].start(getRole(i, landlord), cards[3], cards[i]);
        }

        while(!(cards[0].isEmpty() || cards[1].isEmpty() || cards[2].isEmpty())){
            String prePreOutCards = outCardsQueue.size() >= 2 ? outCardsQueue.get(outCardsQueue.size() - 2) : "";
            String preOutCards = outCardsQueue.size() >= 1 ? outCardsQueue.get(outCardsQueue.size() - 1) : "";
            String outCards = gambler[turn].play(prePreOutCards, preOutCards, cards[turn]);
            if(!cardsChecker.check(prePreOutCards, preOutCards, cards[turn], outCards)){
                throw new FoulException(gambler[0].getName() + " faul",
                        prePreOutCards,
                        preOutCards,
                        cards[turn],
                        outCards);
            }
            cards[turn] = subtractCards(cards[turn], outCards);
            outCardsQueue.add(outCards);
            turn = (turn + 1) % 3;
        }

        int winner = -1;
        for(int i = 0; i < 3; ++i){
            if(cards[i].isEmpty()){
                winner = i;
                break;
            }
        }
        for(int i = 0; i < 3; ++i){
            //gambler[i].over(getRole(i, winner), cards[3], cards[i]);
            //TODO
        }



        return new int[3];
    }

    private String[] getRandomCards(int landlord){
        return null;
    }

    private int getRandomLandlord(){
        return random.nextInt(3);
    }

    private Gambler.Role getRole(int self, int other){
        switch ((other + 3 - self) % 3){
            case 0:
                return Gambler.Role.SELF;
            case 1:
                return Gambler.Role.PRE_PRE;
            case 2:
                return Gambler.Role.PRE;
        }
        return null;
    }

    private String subtractCards(String ownedCards, String outCards){
        return null;
    }
}

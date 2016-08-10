package com.wolfogre.codelandlords;

import com.wolfogre.codelandlords.Gambler.Role;

import java.util.ArrayList;
import java.util.Random;

import static com.wolfogre.codelandlords.Gambler.Role.*;

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

    public int[] judge() {
        ArrayList<String> outCardsQueue = new ArrayList<>();
        int landlord = getRandomLandlord();
        String[] cards = getRandomCards(landlord);
        int turn = landlord;
        int bet = 3;
        for(int i = 0; i < 3; ++i){
            gambler[i].start(getRoleByIndex(i, landlord), cards[3], cards[i]);
        }

        while(!(cards[0].isEmpty() || cards[1].isEmpty() || cards[2].isEmpty())){
            String prePreOutCards = outCardsQueue.size() >= 2 ? outCardsQueue.get(outCardsQueue.size() - 2) : "";
            String preOutCards = outCardsQueue.size() >= 1 ? outCardsQueue.get(outCardsQueue.size() - 1) : "";
            String outCards = gambler[turn].play(prePreOutCards, preOutCards, cards[turn]);
            if(!cardsChecker.check(preOutCards, cards[turn], outCards)){
                int[] result = new int[3];
                result[turn] = -10;
                return result;
            }
            //TODO:赌注加倍
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
            gambler[i].over(getRoleByIndex(i, winner),
                    cards[getIndexByRole(i, PRE_PRE)],
                    cards[getIndexByRole(i, PRE)], cards[i]);
        }

        if(winner == landlord){
            int[] result = {-bet, -bet, -bet};
            result[winner] = 2 * bet;
            return result;
        }
        int[] result = {bet, bet, bet};
        result[winner] = -2 * bet;
        return result;
    }

    private String[] getRandomCards(int landlord){
        return null;
    }

    private int getRandomLandlord(){
        return random.nextInt(3);
    }

    private Role getRoleByIndex(int self, int other){
        switch ((other + 3 - self) % 3){
            case 0:
                return SELF;
            case 1:
                return PRE_PRE;
            case 2:
                return PRE;
        }
        return null;
    }

    private int getIndexByRole(int self, Role other){
        switch (other){
            case PRE_PRE:
                return (self - 2 + 3) % 3;
            case PRE:
                return (self - 1 + 3) % 3;
            case SELF:
                return self;
            default:
                return -1;
        }
    }

    private String subtractCards(String ownedCards, String outCards){
        return null;
    }
}

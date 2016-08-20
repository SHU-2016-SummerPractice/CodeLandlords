package com.wolfogre.codelandlords;

import com.wolfogre.codelandlords.Gambler.Role;

import java.util.ArrayList;
import java.util.Random;

import static com.wolfogre.codelandlords.Gambler.Role.*;

/**
 * 裁判
 * 负责比赛过程的组织
 */
public class Judger {

    private Gambler[] gambler;
    private CardsChecker cardsChecker;
    private Random random;

    /**
     * 构造方法
     * 默认座次是 1->2->3->1
     * 若要调整座次，需重新构造
     * @param gambler1 玩家1
     * @param gambler2 玩家2
     * @param gambler3 玩家3
     * @param cardsChecker 出牌检查器
     */
    public Judger(Gambler gambler1, Gambler gambler2, Gambler gambler3, CardsChecker cardsChecker){
        random = new Random();
        gambler = new Gambler[3];
        gambler[0] = gambler1;
        gambler[1] = gambler2;
        gambler[2] = gambler3;
        this.cardsChecker = cardsChecker;
    }

    /**
     * 进行一场比赛
     * 可被反复调用完成多常比赛，但座次不变
     * @return 单场得分，依次是玩家1、2、3的得分，
     */
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

    /**
     * 发牌
     * @param landlord 地主的下标
     * @return 三家的牌
     */
    private String[] getRandomCards(int landlord){
        // TODO
        return null;
    }

    /**
     * 随机生成地主
     * @return 地主的下标
     */
    private int getRandomLandlord(){
        return random.nextInt(3);
    }

    /**
     * 根据下标获得彼此的相对角色
     * @param self 自己的下标
     * @param other 对方的下标
     * @return 相对角色
     */
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

    /**
     * 根据自己的下标和相对角色获得对方的下标
     * @param self 自己的下标
     * @param other 相对角色
     * @return 对方的下标
     */
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

    /**
     * 计算出牌后剩下的牌
     * @param ownedCards 拥有的牌
     * @param outCards 出掉的牌
     * @return 剩下的牌
     */
    private String subtractCards(String ownedCards, String outCards){
        //TODO
        return null;
    }
}
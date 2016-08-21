package com.wolfogre.codelandlords;

import com.wolfogre.codelandlords.Gambler.Role;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
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
    private PrintStream logOutput;

    /**
     * 构造方法
     * 默认座次是 1->2->3->1
     * 若要调整座次，需重新构造
     * @param gambler1 玩家1
     * @param gambler2 玩家2
     * @param gambler3 玩家3
     */
    public Judger(Gambler gambler1, Gambler gambler2, Gambler gambler3, PrintStream logOutput){
        random = new Random();
        gambler = new Gambler[3];
        gambler[0] = gambler1;
        gambler[1] = gambler2;
        gambler[2] = gambler3;
        this.cardsChecker = new CardsChecker();
        this.logOutput = logOutput;
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

        for(int i = 0; i < 3; ++i)
            logOutput.println("[gambler" + (i + 1) + "_start] " + cards[i]);

        for(int i = 0; i < 3; ++i){
            gambler[i].start(getRoleByIndex(i, landlord), cards[3], cards[i]);
        }

        while(!(cards[0].isEmpty() || cards[1].isEmpty() || cards[2].isEmpty())){
            String prePreOutCards = outCardsQueue.size() >= 2 ? outCardsQueue.get(outCardsQueue.size() - 2) : "";
            String preOutCards = outCardsQueue.size() >= 1 ? outCardsQueue.get(outCardsQueue.size() - 1) : "";
            String outCards = gambler[turn].play(prePreOutCards, preOutCards, cards[turn]);
            if(!cardsChecker.check(prePreOutCards, preOutCards, cards[turn], outCards)){
                logOutput.println("[gambler" + turn + "_break_rule] " + cards[turn] + "->" + outCards);
                int[] result = new int[3];
                result[turn] = -10;
                return result;
            }
            if(cardsChecker.getCardsType(outCards) == CardsType.炸弹
                    || cardsChecker.getCardsType(outCards) == CardsType.火箭)
                bet *= 2;
            logOutput.println("[gambler" + turn + "_out] " + cards[turn] + "->" + outCards);
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
        logOutput.println("[winner] " + winner);
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
        logOutput.println("[winner] " + Arrays.toString(result));
        return result;
    }

    /**
     * 发牌
     * @param landlord 地主的下标
     * @return [玩家1的牌，玩家2的牌，玩家3的牌，地主额外的牌（仅供展示，不需要再给玩家）]
     */
    private String[] getRandomCards(int landlord){
        StringBuilder stringBuilder =
                new StringBuilder("33334444555566667777888899990000JJJJQQQQKKKKAAAA2222MS");
        StringBuilder[] result = new StringBuilder[3];
        for(int i = 0; i < 3; ++i)
            result[i] = new StringBuilder();
        for(int i = 0; i < 17; ++i){
            for(int j = 0; j < 3; ++j){
                int randomIndex = random.nextInt(stringBuilder.length());
                result[j].append(stringBuilder.charAt(randomIndex));
                stringBuilder.deleteCharAt(randomIndex);
            }
        }
        String extraCards = stringBuilder.toString();
        result[landlord].append(extraCards);
        return new String[]{FormatCards.sort(result[0].toString()),
                FormatCards.sort(result[1].toString()),
                FormatCards.sort(result[2].toString()),
                extraCards};
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

        int[] record = new int[15];
        for(char ch : ownedCards.toCharArray()){
            ++record[FormatCards.getIndexByCard(ch)];
        }

        for(char ch : outCards.toCharArray()){
            --record[FormatCards.getIndexByCard(ch)];
        }

        StringBuilder result = new StringBuilder();
        for(int i = 0; i < record.length; ++i)
            for(int j = 0; j < record[i]; ++j)
                result.append(FormatCards.getCardByIndex(i));

        return FormatCards.sort(result.toString());
    }
}

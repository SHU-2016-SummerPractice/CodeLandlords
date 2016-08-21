package com.wolfogre.codelandlords;

/**
 * Created by wolfogre on 8/9/16.
 */

/**
 * 赌徒
 */
public abstract class Gambler {
    /**
     * 出牌检查器
     */
    private CardsChecker cardsChecker = new CardsChecker();

    protected boolean check(String prePreOutCards, String preOutCards, String ownedCards, String outCards){
        return cardsChecker.check(prePreOutCards, preOutCards, ownedCards, outCards);
    }

    protected CardsType getCardsType(String cards){
        return cardsChecker.getCardsType(cards);
    }

    protected boolean isBigger(String preOutCards, String outCards){
        return cardsChecker.isBigger(preOutCards, outCards);
    }

    /**
     * 相对角色
     * PRE_PRE 上上家
     * PRE 上家
     * SELF 自己
     */
    public enum Role{PRE_PRE, PRE, SELF}

    /**
     * 叫啥名字，建议格式：“名字_x.x”，不要有空格，如“Coach_1.0”
     * @return 名字
     */
    public abstract String getName();

    /**
     * 游戏开始
     * @param landlord 谁是地主
     * @param landlordExtraCards 地主额外拥有的牌
     * @param ownedCards 自己拥有的牌
     */
    public abstract void start(Role landlord, String landlordExtraCards, String ownedCards);

    /**
     * 游戏结束
     * @param winner 谁是赢家
     * @param prePreRemainCards 上上家剩的牌
     * @param preRemainCards 上家剩的牌
     * @param remainCards 剩的牌
     */
    public abstract void over(Role winner, String prePreRemainCards, String preRemainCards, String remainCards);

    /**
     * 出牌
     * @param prePreCards 上上家出的牌
     * @param preCards 上家出的牌
     * @param ownedCards 拥有的牌
     * @return 出的牌
     */
    public abstract String play(String prePreCards, String preCards, String ownedCards);
}

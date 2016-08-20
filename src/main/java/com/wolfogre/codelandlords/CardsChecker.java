package com.wolfogre.codelandlords;

/**
 * 出牌检查器
 * 为 Judger 提供服务，检查玩家出牌是否合法
 * 它是游戏规则的体现，为防止新的规则出现，故定义为接口
 */
public interface CardsChecker {
    /**
     * 检查出牌是否合法
     * @param preOutCards 上家的出牌
     * @param ownedCards 玩家拥有的牌
     * @param outCards 玩家出的牌
     * @return 是否合法
     */
    boolean check(String preOutCards, String ownedCards, String outCards);
}

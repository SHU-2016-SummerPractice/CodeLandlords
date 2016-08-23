package com.wolfogre.codelandlords;

import org.junit.Test;

import java.io.PrintStream;
import java.util.*;

/**
 * 管理者
 * 负责动态加载参加游戏的类，
 * 根据参数启动比赛，
 * 返回比赛统计结果和赛程记录
 */
class Manager {
    private Class[] classes;
    private Random random;

    Manager(){
        classes = new Class[3];
        random = new Random();
    }

    /**
     * 加载参加游戏的类
     * @param className 类名
     * @param index 第几个
     * @return 是否加载成功
     */
    boolean loadGambler(String className, int index){
        Class clazz;
        try {
            ClassLoader classLoader = Manager.class.getClassLoader();
            clazz = classLoader.loadClass(className);
            Gambler gambler = (Gambler)clazz.newInstance();// Don't delete
        } catch (Exception e) {
            return false;
        }
        classes[index] = clazz;
        return true;
    }

    /**
     * 开始游戏
     * TODO:这里注意：我对当前代码的理解是，玩家输入类进行对战以后，gambler就表示了座位，gambler1，2，3对应了各个座位
     * @param rounds 一共多少轮
     * @param inningsEachRound 每轮多少局
     * @param logOutput 日志输出流
     * @return [玩家1的总得分，玩家2的总得分，玩家3的总得分]
     */
    int[] start(int rounds, int inningsEachRound, PrintStream logOutput){
        int[] result = new int[3];

        for(int round = 1; round <= rounds; ++round){
            int[] order = randomOrder();
            Gambler[] gamblers = new Gambler[3];
            for(int i = 0; i < 3; ++i)
                try {
                    gamblers[i] = (Gambler)classes[order[i]].newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException("Fail to instance", e);
                }
            System.out.println(gamblers[0].getName() + " " + gamblers[1].getName() + " " + gamblers[2].getName());
            Judger judger = new Judger(gamblers[0], gamblers[1], gamblers[2], logOutput);
            for(int inning = 1; inning <= inningsEachRound; ++inning){
                logOutput.println("round " + round);
                logOutput.println("inning " + inning);

                logOutput.println("gambler1 " + gamblers[0].getName() + " " + gamblers[0]);
                logOutput.println("gambler2 " + gamblers[1].getName() + " " + gamblers[1]);
                logOutput.println("gambler3 " + gamblers[2].getName() + " " + gamblers[2]);
                int[] inningResult = judger.judge();
                for(int i = 0; i < 3; ++i)
                    result[order[i]] += inningResult[i];
                logOutput.println("[result] ");
                int index = 0;
                for(int x : result){
                    logOutput.println(classes[index++].getName() + ":" + x);
                }
            }
        }
        return result;
    }

    /**
     * 随机生成座次
     * @return [玩家1的座次，玩家2的座次，玩家3的座次]
     */
    int[] randomOrder(){
        List<int[]> choices = new ArrayList<>();
        choices.add(new int[]{0, 1, 2});
        choices.add(new int[]{0, 2, 1});
        choices.add(new int[]{1, 0, 2});
        choices.add(new int[]{1, 2, 0});
        choices.add(new int[]{2, 0, 1});
        choices.add(new int[]{2, 1, 0});
        return choices.get(random.nextInt(6));
    }
}

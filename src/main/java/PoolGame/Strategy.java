package PoolGame;

import PoolGame.items.Ball;
import PoolGame.items.Balls;
import PoolGame.items.Table;
import javafx.scene.shape.Circle;

import java.util.*;

public class Strategy {

    private static Map<Integer, Integer> account;


    public static void init(Balls balls)
    {
        //设置一下初始account
        account = new HashMap<>();
        for(Map.Entry<Integer, Ball> entry: balls.getBalls().entrySet())
        {
            if("white".equals(entry.getValue().getColour()))
            {
                account.put(entry.getKey(), 1);
//                System.out.println(account.get(entry.getKey()));
            }

            if("blue".equals(entry.getValue().getColour()))
            {
                account.put(entry.getKey(), 2);
//                System.out.println(account.get(entry.getKey()));
            }

            if("red".equals(entry.getValue().getColour()))
            {
                account.put(entry.getKey(), 1);
//                System.out.println(account.get(entry.getKey()));
            }
        }
    }

    public static void detectCollisionsOfBalls(Balls balls, Table table) {
        Map<Integer, Ball> map = balls.getBalls();
        for (Map.Entry<Integer, Ball> entry : map.entrySet()) {
            Ball nowBall = entry.getValue();
            for(Map.Entry<Integer, Ball> entryIner: map.entrySet()){
                Ball otherBall = entryIner.getValue();
                if( !Objects.equals(entryIner,entry)&& Math.sqrt( Math.pow(nowBall.getXPos()-otherBall.getXPos(),2) + Math.pow(nowBall.getYPos()-otherBall.getYPos(),2) ) <= nowBall.getShape().getRadius()*2 + 1){
                    double[] vectorA = new double[]{otherBall.getXPos()-nowBall.getXPos(), otherBall.getYPos()-nowBall.getYPos()};
                    double[] vectorB = new double[]{nowBall.getXVel(), nowBall.getYVel()};
                    double cosVal = calculateCosine(vectorA, vectorB);
                    double otherVal = Math.abs(cosVal*(Math.sqrt(Math.pow(nowBall.getXVel(),2)+Math.pow(nowBall.getYVel(),2))));
                    if(Objects.equals(entry.getValue().getColour(), "white")){
                        nowBall.setXVel(nowBall.getXVel()*0.05);
                        nowBall.setYVel(nowBall.getYVel()*0.05);
                    }
                    otherBall.setXVel(
                            ( otherBall.getXVel() + Math.pow(0.8, table.getFriction())*otherVal * (2*nowBall.getMass() / (nowBall.getMass()+otherBall.getMass())) )*
                                    ((otherBall.getXPos()-nowBall.getXPos())/
                                    (Math.sqrt(Math.pow(nowBall.getXPos()-otherBall.getXPos(),2)+Math.pow(nowBall.getYPos()-otherBall.getYPos(),2))))
                    );
                    otherBall.setYVel(
                            ( otherBall.getYVel() + Math.pow(0.8, table.getFriction())*otherVal * (2*nowBall.getMass() / (nowBall.getMass()+otherBall.getMass())) )*
                                    ((otherBall.getYPos()-nowBall.getYPos())/
                                    (Math.sqrt(Math.pow(nowBall.getXPos()-otherBall.getXPos(),2)+Math.pow(nowBall.getYPos()-otherBall.getYPos(),2))))
                    );
//                    nowBall.setXVel(
//                            nowBall.getXVel()-(2*otherVal * (otherBall.getMass() / (nowBall.getMass()+otherBall.getMass())))*
//                                    ((otherBall.getXPos()-nowBall.getXPos())/
//                                    (Math.sqrt(Math.pow(nowBall.getXPos()-otherBall.getXPos(),2)+Math.pow(nowBall.getYPos()-otherBall.getYPos(),2))))
//                    );
//                    nowBall.setYVel(
//                            nowBall.getYVel()-(2*otherVal * (otherBall.getMass() / (nowBall.getMass()+otherBall.getMass())))*
//                                    ((otherBall.getYPos()-nowBall.getYPos())/
//                                    (Math.sqrt(Math.pow(nowBall.getXPos()-otherBall.getXPos(),2)+Math.pow(nowBall.getYPos()-otherBall.getYPos(),2))))
//                    );
                }
            }
        }
    }
    // 计算两个向量的cosine值
    public static double calculateCosine(double[] vectorA, double[] vectorB) {
        // 计算点积
        double dotProduct = dotProduct(vectorA, vectorB);

        // 计算向量模
        double magnitudeA = magnitude(vectorA);
        double magnitudeB = magnitude(vectorB);
        if(magnitudeA * magnitudeB==0){
            return 1;
        }
        // 计算cosine值
        return dotProduct / (magnitudeA * magnitudeB);
    }

    // 计算两个向量的点积
    public static double dotProduct(double[] vectorA, double[] vectorB) {
        if (vectorA.length != vectorB.length) {
            throw new IllegalArgumentException("Vectors must have the same length");
        }

        double result = 0.0;
        for (int i = 0; i < vectorA.length; i++) {
            result += vectorA[i] * vectorB[i];
        }

        return result;
    }

    // 计算向量的模
    public static double magnitude(double[] vector) {
        double sumOfSquares = 0.0;
        for (double component : vector) {
            sumOfSquares += Math.pow(component, 2);
        }

        return Math.sqrt(sumOfSquares);
    }

    public static void detectCollisionsOfWall(Balls balls, Table table) {
        Map<Integer, Ball> map = balls.getBalls();
        for(Map.Entry<Integer, Ball> entry: map.entrySet()){//Integer key: balls.getBalls().keySet()
            Ball ball = entry.getValue();
//            System.out.println(ball);
            if(ball.getShape().getCenterX() < table.getLength() && ball.getShape().getCenterY() < table.getHeight())
            {
                // 检测小球是不是撞y轴, 如果撞上y轴, 那么y轴速度不变, x轴速度方向变反
//                System.out.println(ball.getShape().getCenterX());
                if(ball.getShape().getCenterX() <= ball.getShape().getRadius() + 1 || (ball.getShape().getCenterX() + ball.getShape().getRadius() + 1  ) >= table.getLength()){
                    ball.setXVel(- (Math.pow(0.8, table.getFriction())*ball.getXVel()));
                }

                // 检测小球是不是撞x轴, 如果撞上x轴, 那么x轴速度不变, y轴速度方向变反
                if(ball.getShape().getCenterY() <= ball.getShape().getRadius() + 1 || (ball.getShape().getCenterY() + ball.getShape().getRadius() + 1) >= table.getHeight()){
                    ball.setYVel(- (Math.pow(0.8, table.getFriction())*ball.getYVel()));
                }
            }
        }
    }


    public static int operation(Balls balls, List<Circle> list, Map<Integer, List<Double>> bluePos){
        Map<Integer, Ball> map = balls.getBalls();
        if(!map.isEmpty())
        {
//            System.out.println("当前还有球");
            if(map.containsKey(0))
            {
//                System.out.println("当前还有白球");
                if(map.size() == 1)
                {
//                    System.out.println("当前只有白球");
                    return 1;
                }
                else
                {
//                    System.out.println("当前除了白球, 还有其他球");
                    for(Map.Entry<Integer, Ball> entry: map.entrySet())
                    {
                        Integer index = entry.getKey();
                        Ball ball = entry.getValue();
                        for(Circle hole: list)
                        {
                            if((Math.sqrt(Math.pow(ball.getXPos() - hole.getCenterX(), 2) + Math.pow(ball.getYPos() - hole.getCenterY(), 2)) <= hole.getRadius()))
                            {
//                                System.out.println("进洞了");
                                if("blue".equals(ball.getColour()) && account.get(index) == 1)
                                {
                                    account.put(index, account.get(index) - 1);
//                                    System.out.println("我删了蓝色球");
                                    map.get(index).getShape().setVisible(false);
                                    map.remove(index);


                                }

                                if("blue".equals(ball.getColour()) && account.get(index) == 2)
                                {
                                    account.put(index, account.get(index) - 1);
                                    //更新蓝色小球到初始位置
                                    List<Double> bluePosList = bluePos.get(index);
                                    ball.setXPos(bluePosList.get(0));
                                    ball.setXVel(0);
                                    ball.setYVel(0);

                                    ball.setYPos(bluePosList.get(1));

                                }

                                if("red".equals(ball.getColour()) && account.get(index) == 1)
                                {
                                    account.put(index, account.get(index) - 1);
//                                    System.out.println("我删了红球");
                                    map.get(index).getShape().setVisible(false);
                                    map.remove(index);
                                }

                                if("white".equals(ball.getColour()) && account.get(index) == 1)
                                {
                                    account.put(index, account.get(index) - 1);
//                                    System.out.println("我删了白球");
                                    map.get(index).getShape().setVisible(false);
                                    map.remove(index);
                                }
                            }
                        }
                    }
                    return 2;
                }

            }
            else
            {
                return 0;
            }

        }
        else
        {
            return 0;
        }

    }

}
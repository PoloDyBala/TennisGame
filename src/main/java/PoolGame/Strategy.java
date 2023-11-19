package PoolGame;

import PoolGame.items.Ball;
import PoolGame.items.Balls;
import PoolGame.items.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Strategy {
    public static void detectCollisionsOfBalls(Balls balls, Table table) {
        Map<Integer, Ball> map = balls.getBalls();
        for (Map.Entry<Integer, Ball> entry : map.entrySet()) {
            Ball nowBall = entry.getValue();
            for(Map.Entry<Integer, Ball> entryIner: map.entrySet()){
                Ball otherBall = entryIner.getValue();
                if( !Objects.equals(entryIner,entry)&& Math.sqrt( Math.pow(nowBall.getXPos()-otherBall.getXPos(),2) + Math.pow(nowBall.getYPos()-otherBall.getYPos(),2) ) <= 43 ){
                    double[] vectorA = new double[]{otherBall.getXPos()-nowBall.getXPos(), otherBall.getYPos()-nowBall.getYPos()};
                    double[] vectorB = new double[]{nowBall.getXVel(), nowBall.getYVel()};
                    double cosVal = calculateCosine(vectorA, vectorB);
                    double otherVal = Math.abs(cosVal*(Math.sqrt(Math.pow(nowBall.getXVel(),2)+Math.pow(nowBall.getYVel(),2))));
                    otherBall.setXVel(
                            ( otherBall.getXVel() + Math.pow(0.6, table.getFriction())*otherVal * (2*nowBall.getMass() / (nowBall.getMass()+otherBall.getMass())) )*
                                    ((otherBall.getXPos()-nowBall.getXPos())/
                                    (Math.sqrt(Math.pow(nowBall.getXPos()-otherBall.getXPos(),2)+Math.pow(nowBall.getYPos()-otherBall.getYPos(),2))))
                    );
                    otherBall.setYVel(
                            ( otherBall.getYVel() + Math.pow(0.6, table.getFriction())*otherVal * (2*nowBall.getMass() / (nowBall.getMass()+otherBall.getMass())) )*
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
                if(ball.getShape().getCenterX() <= 23 || (ball.getShape().getCenterX() + 23 ) >= table.getLength()){
                    ball.setXVel(- (Math.pow(0.8, table.getFriction())*ball.getXVel()));
                }

                // 检测小球是不是撞x轴, 如果撞上x轴, 那么x轴速度不变, y轴速度方向变反
                if(ball.getShape().getCenterY() <= 23 || (ball.getShape().getCenterY() + 23) >= table.getHeight()){
                    ball.setYVel(- (Math.pow(0.8, table.getFriction())*ball.getYVel()));
                }
            }
        }
    }

}
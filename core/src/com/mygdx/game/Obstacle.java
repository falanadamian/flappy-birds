package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by damian on 23.05.2017.
 */

public class Obstacle {

    private static final int GAP=600;

    private Texture obstacleTop;
    private Texture obstacleBottom;
    private Vector2 positionObstacleTop;
    private Vector2 positionObstacleBottom;

    private Rectangle bordersTop, bordersBottom;

    private Random random;
    private int rangeBottom=FlappyBirds.HEIGHT/2+300;
    private int distanceTop=(FlappyBirds.HEIGHT-rangeBottom)/2;
    private int rangeTop=FlappyBirds.HEIGHT-distanceTop;
    private int range;

    public Obstacle(float x){
        obstacleTop= new Texture("TopTube.png");
        obstacleBottom= new Texture("BottomTube.png");
        random= new Random();
        range= random.nextInt(rangeTop-rangeBottom)+ rangeBottom;
        positionObstacleTop= new Vector2(x, range);
        positionObstacleBottom= new Vector2(x, positionObstacleTop.y - GAP);

        bordersTop= new Rectangle(positionObstacleTop.x, positionObstacleTop.y, obstacleTop.getWidth(), FlappyBirds.HEIGHT- positionObstacleTop.y);
        bordersBottom= new Rectangle(positionObstacleBottom.x, 0 , obstacleBottom.getWidth(), getPositionObstacleBottom().y);

    }

    public Texture getObstacleTop() {
        return obstacleTop;
    }

    public Texture getObstacleBottom() {
        return obstacleBottom;
    }

    public Vector2 getPositionObstacleTop() {
        return positionObstacleTop;
    }

    public Vector2 getPositionObstacleBottom() {
        return positionObstacleBottom;
    }

    public void reposition(float x){
        positionObstacleTop.set(x, range);
        positionObstacleBottom.set(x, positionObstacleTop.y - GAP);
/*        bordersTop.setPosition(positionObstacleTop.x, positionObstacleTop.y);
        bordersBottom.setPosition(positionObstacleBottom.x, positionObstacleBottom.y);*/
        bordersTop.set(positionObstacleTop.x, positionObstacleTop.y, obstacleTop.getWidth(), FlappyBirds.HEIGHT- positionObstacleTop.y);
        bordersBottom.set(positionObstacleBottom.x, 0 , obstacleBottom.getWidth(), getPositionObstacleBottom().y);
    }

    public Rectangle getBordersTop() {
        return bordersTop;
    }

    public Rectangle getBordersBottom() {
        return bordersBottom;
    }

    public void dispose(){
        obstacleBottom.dispose();
        obstacleTop.dispose();
    }
}
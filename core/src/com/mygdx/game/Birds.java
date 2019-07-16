package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by damian on 22.05.2017.
 */

public class Birds {

    private static final int MOVEMENT= 25;
    private Vector2 position;
    private Vector2 velocity;

    public Texture ping;
    public static final int GRAVITY=-8;

    private Circle bounds;

    public Sound wing;

    public Birds(int x, int y){
        position= new Vector2(x, y);
        velocity= new Vector2(0, 0);

        ping= new Texture("Black duck 1.png");

        bounds= new Circle(x,y, ping.getWidth()/2);

        wing=Gdx.audio.newSound(Gdx.files.internal("wing.wav"));
    }

    public void update(float deltaTime){
        velocity.add(0, GRAVITY);
        velocity.scl(deltaTime);
        position.add(MOVEMENT* deltaTime, velocity.y);
        velocity.scl(1/deltaTime);

        bounds.set(position.x+ ping.getWidth()/2, position.y+ ping.getHeight()/2 +10, 90);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Texture getPing() {return ping;}

    public void tap(){
        velocity.y= 100;
        wing.play();
    }

    public Circle getBounds(){
        return bounds;
    }

    public void dispose(){
        ping.dispose();
    }
}
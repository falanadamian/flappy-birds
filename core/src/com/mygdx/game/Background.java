package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by fala on 30.05.2017.
 */

public class Background {

    private Texture background;
    private Vector2 positionBackground;
    public float backgroundWidth;

    public Background(float x){
        background= new Texture("background.png");
        positionBackground= new Vector2(x, 0);
        backgroundWidth= background.getWidth();
    }


    public Texture getBackground() {
        return background;
    }

    public Vector2 getPositionBackground() {
        return positionBackground;
    }

    public void reposition(float x){
        positionBackground.set(x, 0);
    }

    public float getBackgroundWidth() {
        return backgroundWidth;
    }

    public void dispose(){
        background.dispose();
    }
}

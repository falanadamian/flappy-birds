package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.*;
import com.badlogic.gdx.Screen;

/**
 * Created by damian on 20.05.2017.
 */

public class MenuScreen implements Screen {

    FlappyBirds flappyBirds;

    private OrthographicCamera camera;
    private Viewport viewport;

    Background background;
    private Texture playMenuButton;
    private Texture menuScreenBackground;
    private Texture soundsButton;

    Rectangle playMenuButtonBounds;
    Rectangle soundsButtonBounds;

    boolean musicState=true;
    boolean soundState=true;
    //boolean soundButtonsState=true;
    int soundButtonsState;

    private Texture[] soundButtons;
    Preferences prefs;


    //Drugi argument byl gameScreen?
    public MenuScreen(FlappyBirds flappyBirds){
        this.flappyBirds= flappyBirds;
        camera=new OrthographicCamera();
        viewport =new StretchViewport(FlappyBirds.WIDTH, FlappyBirds.HEIGHT, camera);
        camera.setToOrtho(false, 1440, 2560 );
        camera.position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0);

        background= new Background(0);
        playMenuButton= new Texture("playMenuButton.png");
        menuScreenBackground= new Texture("menuScreenBackground.png");
        soundsButton= new Texture("soundOnButton.png");

        soundButtons= new Texture[2];
        soundButtons[0]= new Texture("soundOffButton.png");
        soundButtons[1]= new Texture("soundOnButton.png");

        playMenuButtonBounds= new Rectangle(FlappyBirds.WIDTH/2 - playMenuButton.getWidth()/2, FlappyBirds.HEIGHT/2- playMenuButton.getHeight()/2, playMenuButton.getWidth(), playMenuButton.getHeight());
        soundsButtonBounds= new Rectangle(FlappyBirds.WIDTH/2 - menuScreenBackground.getWidth()/2+ 100, FlappyBirds.HEIGHT/2 - menuScreenBackground.getHeight()/2 + 150, soundsButton.getWidth(), soundsButton.getHeight());

        prefs= Gdx.app.getPreferences("flappyBirds");
        this.soundButtonsState=prefs.getInteger("soundButtonsState", 1);
    }

    @Override
    public void show(){
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(1F, 1F, 1F, 1F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        flappyBirds.spriteBatch.setProjectionMatrix(camera.combined);

        flappyBirds.spriteBatch.begin();

        flappyBirds.spriteBatch.draw(background.getBackground() , background.getPositionBackground().x, 0, flappyBirds.WIDTH, flappyBirds.HEIGHT);
        flappyBirds.spriteBatch.draw(menuScreenBackground, FlappyBirds.WIDTH/2 - menuScreenBackground.getWidth()/2, FlappyBirds.HEIGHT/2 - menuScreenBackground.getHeight()/2);
        flappyBirds.spriteBatch.draw(playMenuButton, FlappyBirds.WIDTH/2 - playMenuButton.getWidth()/2, FlappyBirds.HEIGHT/2- playMenuButton.getHeight()/2 -50);
        //flappyBirds.spriteBatch.draw(soundsButton, FlappyBirds.WIDTH/2 - menuScreenBackground.getWidth()/2+ 100, FlappyBirds.HEIGHT/2 - menuScreenBackground.getHeight()/2 + 150);
        flappyBirds.spriteBatch.draw(soundButtons[soundButtonsState], FlappyBirds.WIDTH/2 - menuScreenBackground.getWidth()/2+ 100, FlappyBirds.HEIGHT/2 - menuScreenBackground.getHeight()/2 + 150);

        flappyBirds.spriteBatch.end();

        handleInput();
    }

    protected void handleInput(){
        if(Gdx.input.justTouched()){
            Vector3 tmp=new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0);
            camera.unproject(tmp);

            if(playMenuButtonBounds.contains(tmp.x,tmp.y)){
                flappyBirds.setScreen(new GameScreen(flappyBirds));
            }else if(soundsButtonBounds.contains(tmp.x,tmp.y)){
                if(musicState==true) {
                    flappyBirds.music.stop();
                    soundButtonsState=0;
                    //soundsButton= new Texture("soundOffButton.png");
                }else if(musicState==false){
                    flappyBirds.music.play();
                    soundButtonsState=1;
                    //soundsButton= new Texture("soundOnButton.png");
                }

                prefs.putInteger("soundButtonsState", soundButtonsState);
                prefs.flush();

                musicState=!musicState;
                soundState=!soundState;
            }else{
            }
        }
    }

    @Override
    public void resize(int width, int height){
    }

    @Override
    public void pause(){
    }

    @Override
    public void resume(){
    }

    @Override
    public void hide(){
    }

    @Override
    public void dispose(){
        background.dispose();
        playMenuButton.dispose();
        menuScreenBackground.dispose();
        soundsButton.dispose();

        soundButtons[0].dispose();
        soundButtons[1].dispose();
    }
}
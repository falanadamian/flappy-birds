package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.MenuScreen;

public class FlappyBirds extends Game {

    public SpriteBatch spriteBatch;

    public static final int WIDTH=1440;
    public static final int HEIGHT=2560;

    public Music music;

    @Override
    public void create () {
        spriteBatch= new SpriteBatch();

        this.setScreen(new MenuScreen(this));

        music= Gdx.audio.newMusic(Gdx.files.internal("song.mp3"));
        music.setLooping(true);
        music.setVolume(0.1f);
        music.play();
    }


    @Override
    public void render(){
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
	public void dispose () {
		spriteBatch.dispose();
        music.dispose();
	}

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }
}
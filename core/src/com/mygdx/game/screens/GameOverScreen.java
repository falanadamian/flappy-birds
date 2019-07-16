package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Background;
import com.mygdx.game.FlappyBirds;

/**
 * Created by fala on 30.05.2017.
 */

public class GameOverScreen implements Screen {


    private OrthographicCamera camera;
    private Viewport viewport;

    private FlappyBirds flappyBirds;
    private int score;
    private int highScore;
    private Texture gameOver;
    private Texture replayButton;
    private Texture homeButton;
    private Texture highScoreButton;
    private Background background;
    private BitmapFont scoreFont;

    private Rectangle replayButtonBounds;
    private Rectangle highScoreButtonBounds;
    private Rectangle homeButtonBounds;

    private GlyphLayout scoreLayout;
    private GlyphLayout highScoreLayout;

    public GameOverScreen(FlappyBirds flappyBirds, int score){
        this.flappyBirds= flappyBirds;
        camera=new OrthographicCamera();
        viewport =new StretchViewport(FlappyBirds.WIDTH, FlappyBirds.HEIGHT, camera);
        camera.setToOrtho(false, 1440, 2560 );
        camera.position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0);

        this.score=score;
        Preferences prefs= Gdx.app.getPreferences("flappyBirds");
        this.highScore=prefs.getInteger("highScore", 0);

        if(score > highScore){
            prefs.putInteger("highScore", score);
            prefs.flush();
            highScore=score;
        }

        gameOver= new Texture("gameOver.png");
        replayButton= new Texture("replayButton.png");
        homeButton= new Texture("homeButton.png");
        highScoreButton= new Texture("highScoreButton.png");

        replayButtonBounds= new Rectangle(FlappyBirds.WIDTH /2- replayButton.getWidth()/2, FlappyBirds.HEIGHT/2- gameOver.getHeight()/4, replayButton.getWidth(), replayButton.getHeight());
        highScoreButtonBounds= new Rectangle(FlappyBirds.WIDTH/2+ gameOver.getWidth()/3 - highScoreButton.getWidth(), FlappyBirds.HEIGHT/2- gameOver.getHeight()/3, highScoreButton.getWidth(), highScoreButton.getHeight());
        homeButtonBounds=new Rectangle(FlappyBirds.WIDTH/2- gameOver.getWidth()/3, FlappyBirds.HEIGHT/2- gameOver.getHeight()/3, homeButton.getWidth(), homeButton.getHeight());

        background= new Background(0);
        scoreFont= new BitmapFont();
        scoreFont.getData().setScale(5);
    }

    @Override
    public void show() {
    }

    private void handleInput(){
        if(Gdx.input.justTouched()){
            Vector3 tmp=new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0);
            camera.unproject(tmp);
            if(homeButtonBounds.contains(tmp.x,tmp.y))
            {
                flappyBirds.setScreen(new MenuScreen(flappyBirds));
            }else if(replayButtonBounds.contains(tmp.x,tmp.y))
            {
                flappyBirds.setScreen(new GameScreen(flappyBirds));
            }else if(highScoreButtonBounds.contains(tmp.x,tmp.y))
            {
                flappyBirds.setScreen(new MenuScreen(flappyBirds));
            }else{
            }
        }
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1F, 1F, 1F, 1F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        flappyBirds.spriteBatch.setProjectionMatrix(camera.combined);

        flappyBirds.spriteBatch.begin();

        flappyBirds.spriteBatch.draw(background.getBackground() , background.getPositionBackground().x, 0, FlappyBirds.WIDTH, FlappyBirds.HEIGHT);
        flappyBirds.spriteBatch.draw(gameOver, FlappyBirds.WIDTH/2- gameOver.getWidth()/2, FlappyBirds.HEIGHT/2- gameOver.getHeight()/2);
        flappyBirds.spriteBatch.draw(homeButton, FlappyBirds.WIDTH/2- gameOver.getWidth()/3, FlappyBirds.HEIGHT/2- gameOver.getHeight()/3);
        flappyBirds.spriteBatch.draw(highScoreButton, FlappyBirds.WIDTH/2+ gameOver.getWidth()/3 - highScoreButton.getWidth(), FlappyBirds.HEIGHT/2- gameOver.getHeight()/3);
        flappyBirds.spriteBatch.draw(replayButton, FlappyBirds.WIDTH/2- replayButton.getWidth()/2, FlappyBirds.HEIGHT/2- gameOver.getHeight()/4);

        scoreLayout= new GlyphLayout(scoreFont, "Score: " + score, Color.BLACK, 0, Align.left, false);
        highScoreLayout= new GlyphLayout(scoreFont, "HighScore: " + highScore, Color.BLACK, 0, Align.left, false);

        scoreFont.draw(flappyBirds.spriteBatch, scoreLayout, FlappyBirds.WIDTH/2- scoreLayout.width/2, FlappyBirds.HEIGHT/2+150);
        scoreFont.draw(flappyBirds.spriteBatch, highScoreLayout, FlappyBirds.WIDTH/2- highScoreLayout.width/2, FlappyBirds.HEIGHT/2 + 50);

        flappyBirds.spriteBatch.end();

        handleInput();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        gameOver.dispose();
        replayButton.dispose();
        homeButton.dispose();
        highScoreButton.dispose();
        background.dispose();
        scoreFont.dispose();
    }
}
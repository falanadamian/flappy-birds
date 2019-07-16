package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Background;
import com.mygdx.game.Birds;
import com.mygdx.game.FlappyBirds;
import com.mygdx.game.Obstacle;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

/**
 * Created by damian on 20.05.2017.
 */

public class GameScreen implements Screen {

    FlappyBirds flappyBirds;

    private OrthographicCamera camera;

    private Viewport viewport;

    private static final int OBSTACLE_DISTANCE=FlappyBirds.WIDTH/2;
    private static final int obstacleCount=3;
    private static final int backgroundCount=4;

    private Array<Obstacle> obstacles;
    private Array<Background> backgrounds;

    private Texture measureObstacle;
    private Texture measureBackground;
    private Texture homeButton;

    private ShapeRenderer shapeRenderer;

    private int score;
    private int scoringObstacle;
    private BitmapFont scoreFont;

    //private Sound hitSound;
    //private Sound scoreSound;

    Birds ping;
    int flapState=0;


    public GameScreen(FlappyBirds flappyBirds){

            this.flappyBirds=flappyBirds;

            camera= new OrthographicCamera();
            viewport= new StretchViewport(FlappyBirds.WIDTH, FlappyBirds.HEIGHT, camera);
            camera.setToOrtho(false, 1440, 2560 );
            camera.position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0);

            ping= new Birds(FlappyBirds.WIDTH/4, FlappyBirds.HEIGHT/2 );

            measureObstacle= new Texture("TopTube.png");
            measureBackground= new Texture("background.png");

            shapeRenderer= new ShapeRenderer();

            obstacles= new Array<Obstacle>();

        for(int i=1; i<=obstacleCount; i++){
            obstacles.add(new Obstacle(i*(OBSTACLE_DISTANCE + measureObstacle.getWidth())));
        }

        backgrounds= new Array<Background>();
        for(int i=1; i<=backgroundCount; i++){
            backgrounds.add(new Background((i-2)*measureBackground.getWidth()));
        }

        score=0;
        scoringObstacle=0;
        scoreFont= new BitmapFont();
        scoreFont.setColor(Color.BLUE);
        scoreFont.getData().setScale(10);

        //hitSound= Gdx.audio.newSound(Gdx.files.internal("hitSound.wav"));
        //scoreSound= Gdx.audio.newSound(Gdx.files.internal("scoreSound.wav"));

        homeButton= new Texture("homeButton.png");
    }

    @Override
    public void show(){
        Timer.schedule(new Timer.Task(){
            @Override
            public void run(){
                if(flapState>=0 && flapState<3){
                    flapState++;
                }else if(flapState==3){
                    flapState=0;
                }
            }
        },0,0.1f);
    }

    public void update(float deltaTime){
        handleInput();
        ping.update(deltaTime);
        camera.position.x= ping.getPosition().x + 80;

        for(Obstacle obstacle : obstacles){
            if(camera.position.x - (camera.viewportWidth/2) -80 > obstacle.getPositionObstacleTop().x + obstacle.getObstacleTop().getWidth()){
                //obstacle.reposition(obstacle.getPositionObstacleTop().x + ((Obstacle.OBSTACLE_WIDTH+ OBSTACLE_DISTANCE)*obstacleCount));
                obstacle.reposition(obstacle.getPositionObstacleTop().x + ((obstacle.getObstacleTop().getWidth() + OBSTACLE_DISTANCE)*obstacleCount));
/*                if(scoringObstacle<2){
                    scoringObstacle++;
                }else{
                    scoringObstacle=0;
                }*/
                score++;
                //scoreSound.play();

                Gdx.app.log("score", String.valueOf(score));
/*                if(ping.getPosition().x >= obstacles.get(scoringObstacle).getPositionObstacleTop().x + obstacles.get(scoringObstacle).getObstacleTop().getWidth()){
                    score++;
                    System.out.println("SCORED!");
                }*/
            }


            if(Intersector.overlaps(ping.getBounds(), obstacle.getBordersTop()) || Intersector.overlaps(ping.getBounds(), obstacle.getBordersBottom())) {
                flappyBirds.setScreen(new GameOverScreen(flappyBirds, score));
                //hitSound.play();
            }
        }

        for(Background background : backgrounds){
            if(camera.position.x - (camera.viewportWidth/2) -80 >= background.getPositionBackground().x+ background.getBackgroundWidth()){
                background.reposition(background.getPositionBackground().x + (backgroundCount* background.getBackground().getWidth()));
            }
        }

        if(ping.getPosition().y <=0){
            flappyBirds.setScreen(new MenuScreen(flappyBirds));
        }
        camera.update();
    }


    protected void handleInput(){
        if(Gdx.input.justTouched()){
            ping.tap();

            Vector3 tmp=new Vector3(Gdx.input.getX(),Gdx.input.getY(), 0);
            camera.unproject(tmp);

            Rectangle homeBounds=new Rectangle(ping.getPosition().x+ camera.viewportWidth/3, FlappyBirds.HEIGHT- 200, homeButton.getWidth(), homeButton.getHeight());

            if(homeBounds.contains(tmp.x,tmp.y))
            {
                flappyBirds.setScreen(new MenuScreen(flappyBirds));
            }
        }
    }

    @Override
    public void render(float delta){

        Gdx.gl.glClearColor(1F, 1F, 1F, 1F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        flappyBirds.spriteBatch.setProjectionMatrix(camera.combined);

        flappyBirds.spriteBatch.begin();

        for(Background background: backgrounds){
            flappyBirds.spriteBatch.draw(background.getBackground(), background.getPositionBackground().x, 0, background.getBackgroundWidth(), FlappyBirds.HEIGHT);
            flappyBirds.spriteBatch.draw(background.getBackground(), background.getPositionBackground().x, 0, background.getBackgroundWidth(), FlappyBirds.HEIGHT);
        }

        for(Obstacle obstacle: obstacles){
            flappyBirds.spriteBatch.draw(obstacle.getObstacleTop(), obstacle.getPositionObstacleTop().x, obstacle.getPositionObstacleTop().y, obstacle.getObstacleTop().getWidth(), FlappyBirds.HEIGHT- obstacle.getPositionObstacleTop().y);
            flappyBirds.spriteBatch.draw(obstacle.getObstacleBottom(), obstacle.getPositionObstacleBottom().x, 0, obstacle.getObstacleBottom().getWidth() , obstacle.getPositionObstacleBottom().y);
        }

        flappyBirds.spriteBatch.draw(ping.getPing(), ping.getPosition().x, ping.getPosition().y, 250, 250);

        scoreFont.draw(flappyBirds.spriteBatch, String.valueOf(score), ping.getPosition().x- camera.viewportWidth/3, camera.viewportHeight/10);

        flappyBirds.spriteBatch.draw(homeButton, ping.getPosition().x+ camera.viewportWidth/3, FlappyBirds.HEIGHT- 200);

        flappyBirds.spriteBatch.end();

        update(0.5f);
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width, height);
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
        for(Background background : backgrounds){
            background.dispose();
        }
        for(Obstacle obstacle : obstacles){
            obstacle.dispose();
        }
        ping.dispose();
        measureObstacle.dispose();
        measureBackground.dispose();
        homeButton.dispose();

        shapeRenderer.dispose();
    }
}
package screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.InputHandler;
import com.mygdx.game.JSGameStart;

import java.awt.event.InputMethodListener;

public class TitleScreen extends UIScreen {
    private ShapeRenderer test;
    private VerticalGroup ui;
    public TitleScreen(JSGameStart gameStart) {
        super(gameStart);
    }

    @Override
    public void show() {
        test = new ShapeRenderer();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        ui = new VerticalGroup();
        Color uiColor = Color.valueOf("#6f7bbf");
        ui.space(20);
        ui.center();

        Skin playButtonSkin = new Skin(Gdx.files.internal("skins/uiskin.json"));
        TextButton play = new TextButton("Play Game",playButtonSkin);
        play.setColor(uiColor);
        play.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x,float y,int pointer, int button) {

                gameStart.setScreen(new GameScreen(gameStart));

                return true;
            }
        });
        TextButton howToPlay = new TextButton("how to play",playButtonSkin);
        howToPlay.setColor(uiColor);
        howToPlay.addListener(new InputListener() {
           @Override
           public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)  {


               gameStart.setScreen(new HelpScreen(gameStart));

               return true;
           }

        });
        TextButton settings = new TextButton("settings",playButtonSkin);
        settings.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                gameStart.setScreen(new SettingsScreen(gameStart));

                return true;
            }

        });

        settings.setColor(uiColor);

        ui.addActor(play);
        ui.addActor(howToPlay);
        ui.addActor(settings);

        ui.setSize(play.getWidth(),play.getHeight()*3+ui.getSpace()*2);
        ui.setOrigin(ui.getWidth()/2, ui.getHeight()/2);
        ui.setPosition(Gdx.graphics.getWidth()/2f-ui.getOriginX(), Gdx.graphics.getHeight()/2f-ui.getOriginY());

        stage.addActor(ui);


    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height);
        backgroundStage.getViewport().update(width, height);
    }

    @Override
    public void render(float delta) {

        Gdx.gl20.glViewport(0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl20.glClearColor(0,0,0,0);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        backgroundStage.getViewport().apply();
        backgroundStage.act();
        backgroundStage.draw();

        stage.getViewport().apply();
        stage.act();
        stage.draw();

    }
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();

    }
}

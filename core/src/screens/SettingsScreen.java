package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.*;
import com.mygdx.game.JSGameStart;

public class SettingsScreen extends UIScreen {

    private VerticalGroup ui;
    private TextureRegion backgroundRegion;

    public SettingsScreen(JSGameStart gameStart) {
        super(gameStart);
    }

    @Override
    public void show() {

        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);

        Label label = new Label("Welcome to Settings", uiSkin);
        label.setAlignment(Align.center, Align.left);
        label.setPosition(stage.getViewport().getScreenWidth()/2f-(label.getWidth()/2), stage.getViewport().getScreenHeight()-label.getHeight()-50);

        TextButton back = new TextButton("back", uiSkin);
        back.setColor(uiColor);
        back.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                gameStart.setScreen(new TitleScreen(gameStart));
                dispose();
                System.out.println("clicked");
                return true;
            }

        });

        VerticalGroup ui = new VerticalGroup();
        ui.space(20);
        ui.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, Align.center);
        ui.addActor(back);


        stage.addActor(label);
        stage.addActor(ui);


    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        backgroundStage.getViewport().update(width, height);

    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl20.glClearColor(0, 0, 0, 0);
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
        backgroundStage.dispose();
    }


}


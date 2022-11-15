package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.JSGameStart;

import java.awt.*;

public class HelpScreen extends UIScreen {

    private VerticalGroup ui;
    public HelpScreen(JSGameStart gameStart) {
        super(gameStart);
    }

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Label label = new Label("JetStrike is a fighter game where you have to destroy enemy \nChoppas! Enemies will spawn near the Button left \nside of the map.\n"
                + "To click in the direction you want to fire in order\n to engage targets. Enemies will fight back \nand have a range at which they can see you", uiSkin);
        label.setAlignment(Align.left, Align.left);

        TextButton back = new TextButton("back", uiSkin);
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
        ui.space(40);
        ui.setPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, Align.center);
        ui.addActor(label);
        ui.addActor(back);

        stage.addActor(ui);

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        backgroundStage.getViewport().update(width,height);
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
    }


}

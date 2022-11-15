package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.mygdx.game.JSGameStart;

import java.util.logging.FileHandler;

public abstract class UIScreen extends ScreenAdapter {

    protected JSGameStart gameStart;
    protected Stage stage;
    protected Stage backgroundStage;
    protected FileHandle background = Gdx.files.internal("otherimages/background.png");
    protected Color uiColor = Color.valueOf("#6f7bbf");
    protected final Skin uiSkin = new Skin(Gdx.files.internal("skins/uiskin.json"));

    public UIScreen(JSGameStart gameStart) {
        this.gameStart = gameStart;
        backgroundStage = new Stage(new FillViewport(1280,720));
        backgroundStage.addActor(new Image(new Texture(background)));

    }
    @Override
    public abstract void show();
    @Override
    public abstract void render(float delta);

    @Override
    public abstract void dispose();

    public JSGameStart getGameStart() {
        return gameStart;
    }

    public void setGameStart(JSGameStart gameStart) {
        this.gameStart = gameStart;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}

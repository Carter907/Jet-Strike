package screens;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.JSGameStart;

public class PauseScreen extends UIScreen {

    public PauseScreen(JSGameStart gameStart) {
        super(gameStart);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());


    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void dispose() {

    }
}

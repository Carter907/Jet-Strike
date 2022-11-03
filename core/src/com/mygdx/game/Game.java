package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class Game extends ApplicationAdapter {

    private BitmapFont font;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private InputHandler inputHandler;
    private Player jet;
    Stage display;

    Viewport view;

    @Override
    public void create() {
        view = new ScreenViewport();
        display = new Stage(view);

        inputHandler = this.new InputHandler();
        Gdx.input.setInputProcessor(inputHandler);

        font = new BitmapFont(Gdx.files.internal("dontWorry.fnt"));
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        jet = new Player();
        jet.setSize(75, 40);
        jet.setPosition(100, 100);
        jet.setAlign(Align.center);
        jet.setOrigin(Align.center);

        display.addActor(jet);
        display.addActor(jet.new ForceField());

    }
    @Override
    public void resize (int width, int height) {
        // See below for what true means.
        display.getViewport().update(width, height, true);
    }

    @Override
    public void render() {

        Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        view.getCamera().position.set(jet.getX()+jet.getOriginX(), jet.getY()+jet.getOriginY(), 0);
        inputHandler.handleInput();
        display.act();
        display.draw();
        view.apply();
        batch.setProjectionMatrix(view.getCamera().combined);
        batch.begin();
        font.draw(batch, "Welcome to Jet Strike", 100, 100);
        batch.end();

    }

    @Override
    public void dispose() {

        display.dispose();
        batch.dispose();
        shapeRenderer.dispose();
    }


    private class InputHandler implements InputProcessor {
        private boolean keyPressed;
        private int keycode;
        private float mouseX;
        private float mouseY;
        private float mouseDirection;

        @Override
        public boolean keyDown(int keycode) {
            System.out.println(keycode + " pressed");
            this.keycode = keycode;
            return keyPressed = true;
        }

        @Override
        public boolean keyUp(int keycode) {
            System.out.println(keycode + " released");
            this.keycode = keycode;
            return keyPressed = false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            return false;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {

            return false;
        }

        @Override
        public boolean scrolled(float amountX, float amountY) {
            return false;
        }

        public void handleInput() {
            Vector2 mousePosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            mousePosition = view.unproject(mousePosition);
            mouseX = mousePosition.x;
            mouseY = mousePosition.y;
            setJetDirection();
            updateProjectiles();
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                System.out.println("left mouse clicked");
                Projectile bullet = new Projectile(jet.getX() + jet.getOriginX(), jet.getY() + jet.getOriginY(), mouseDirection);
                display.addActor(bullet);
                System.out.println(display.getActors());
            }

            if (keyPressed) {

                switch (keycode) {

                    case Input.Keys.W:
                        moveJet(4.5f);
                        break;
                    default:

                        break;
                }
            }
        }

        private void updateProjectiles() {

            for (Actor actor : display.getActors()) {
                if (actor instanceof Projectile) {
                    if (!((Projectile) actor).sendProjectile())
                        display.getActors().removeValue(actor, false);
                }

            }
        }

        public void moveJet(float speed) {
            jet.setX((float) (jet.getX() + speed * Math.cos(Math.toRadians(mouseDirection))));
            jet.setY((float) (jet.getY() + speed * Math.sin(Math.toRadians(mouseDirection))));
            jet.setRotation(mouseDirection);
        }

        public void setJetDirection() {
            mouseDirection = (float) Math.toDegrees(Math.atan2(
                    mouseY - (jet.getY() + jet.getOriginY()),
                    mouseX - (jet.getX() + jet.getOriginX())
            ));
            jet.setRotation(mouseDirection);
        }
    }

    public BitmapFont getFont() {
        return font;
    }

    public void setFont(BitmapFont font) {
        this.font = font;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public void setInputHandler(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

    public Player getJet() {
        return jet;
    }

    public void setJet(Player jet) {
        this.jet = jet;
    }

    public Stage getDisplay() {
        return display;
    }

    public void setDisplay(Stage display) {
        this.display = display;
    }
}

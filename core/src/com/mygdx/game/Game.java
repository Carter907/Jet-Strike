package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Arrays;


public class Game extends ApplicationAdapter {
    public static Game game;
    private BitmapFont font;
    private BitmapFont font2;
    private SpriteBatch batch;
    private float stateTime;
    private ShapeRenderer shapeRenderer;
    private InputHandler inputHandler;
    private Player jet;
    private Enemy enemyJet;
    private TextureAtlas atlas;
    private Stage display;

    private Viewport view;

    @Override
    public void create() {
        game = this;
        stateTime = 0;
        view = new ScreenViewport();
        display = new Stage(view);

        inputHandler = this.new InputHandler();
        Gdx.input.setInputProcessor(inputHandler);

        font = new BitmapFont(Gdx.files.internal("Fonts/dontWorry.fnt"));
        font2 = new BitmapFont(Gdx.files.internal("Fonts/font2.fnt"));
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        atlas = new TextureAtlas(Gdx.files.internal("SpriteAtlas/SpriteSheet.atlas"));

        jet = new Player();
        jet.setSize(140, 60);
        jet.setPosition(100, 100);
        jet.setOrigin(Align.center);
        jet.setForceField(new ForceField(jet));

        enemyJet = new Enemy();
        enemyJet.setSize(75, 40);
        enemyJet.setPosition(MathUtils.random(500), MathUtils.random(500));
        enemyJet.setOrigin(Align.center);
        enemyJet.setForceField(new ForceField(enemyJet));

        display.addActor(jet);
        display.addActor(enemyJet);

    }

    @Override
    public void resize(int width, int height) {
        // See below for what true means.
        display.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        stateTime += Gdx.graphics.getDeltaTime();
        Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        view.getCamera().position.set(jet.getX() + jet.getOriginX(), jet.getY() + jet.getOriginY(), 0);
        inputHandler.handleInput();
        display.act();
        display.draw();
        view.apply();
        batch.setProjectionMatrix(view.getCamera().combined);
        batch.begin();
        font.draw(batch, "Welcome to JetStrike", 100, 100);
        font2.draw(batch, "JetStrike is a jet fighter game where you destroy enemy jets!", 100, 60);
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
        private Ship[] ships;
        private Projectile[] projectiles;
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
            projectiles = getProjectiles();
            ships = getShips();
            setJetDirection();
            updateProjectiles();
            checkProjectiles();
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                System.out.println(mouseDirection);

                Projectile bullet = new Projectile(

                        (float) ((jet.getX() + jet.getOriginX()) + ((jet.getWidth() / 2 + 10) * Math.cos(Math.toRadians(mouseDirection)))),
                        (float) ((jet.getY() + jet.getOriginY()) + ((jet.getWidth() / 2 + 10) * Math.sin(Math.toRadians(mouseDirection)))),
                        mouseDirection,
                        Projectile.ProjectileTypes.ROCKET
                );

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

        private Ship[] getShips() {
            Ship[] ships = new Ship[0];
            for (Actor actor : display.getActors()) {
                if (actor instanceof Ship) {
                    ships = Arrays.copyOf(ships, ships.length + 1);
                    ships[ships.length - 1] = (Ship) actor;
                }
            }
            return ships;
        }

        private Projectile[] getProjectiles() {
            Projectile[] projectiles = new Projectile[0];
            for (Actor actor : display.getActors()) {
                if (actor instanceof Projectile) {
                    projectiles = Arrays.copyOf(projectiles, projectiles.length + 1);
                    projectiles[projectiles.length - 1] = (Projectile) actor;
                }
            }
            return projectiles;
        }

        private void checkProjectiles() {
            for (Ship ship : ships)
                for (Projectile projectile : projectiles) {
                    if (projectile.isExploded()) {
                        display.getActors().removeValue(projectile, false);
                        continue;
                    }
                    if (ship.contains(projectile.getX(), projectile.getY())) {

                        display.getActors().removeValue(ship, false);
                        projectile.setAnimation(Projectile.ProjectileAnimations.ROCKET_EXPLOSION);
                        break;
                    }
                }

        }


        private void updateProjectiles() {

            for (Actor actor : display.getActors()) {
                if (actor instanceof Projectile) {
                    Projectile projectile = (Projectile) actor;
                    if (projectile.getAnimation() == null)
                    if (!projectile.sendProjectile())
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

    public BitmapFont getFont2() {
        return font2;
    }

    public void setFont2(BitmapFont font2) {
        this.font2 = font2;
    }

    public Enemy getEnemyJet() {
        return enemyJet;
    }

    public void setEnemyJet(Enemy enemyJet) {
        this.enemyJet = enemyJet;
    }

    public float getStateTime() {
        return stateTime;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public void setAtlas(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    public Viewport getView() {
        return view;
    }

    public void setView(Viewport view) {
        this.view = view;
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

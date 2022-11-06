package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.*;
import com.sun.org.apache.xpath.internal.operations.Or;

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
    private TextureAtlas sprites;
    private Stage display;
    private Stage ui;

    private TiledMapRenderer mapRenderer;
    private Viewport viewDisplay;
    private Viewport viewUI;
    private Label killCount;

    private OrthographicCamera camera;

    @Override
    public void create() {

        game = this;
        stateTime = 0;
        camera = new OrthographicCamera();
        viewDisplay = new ScreenViewport(camera);
        display = new Stage(viewDisplay);
        viewUI = new ExtendViewport(800,600);
        ui = new Stage(viewUI);

        inputHandler = this.new InputHandler();
        Gdx.input.setInputProcessor(inputHandler);

        font = new BitmapFont(Gdx.files.internal("Fonts/dontWorry.fnt"));
        font2 = new BitmapFont(Gdx.files.internal("Fonts/font2.fnt"));
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        sprites = new TextureAtlas(Gdx.files.internal("SpriteAtlas/Sprites.atlas"));

        TiledMap map = new TmxMapLoader().load("Maps/map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1f, batch);

        jet = new Player();
        jet.setPosition(100, 100);
        jet.setOrigin(Align.center);
        jet.setForceField(new ForceField(jet));

        display.addActor(jet);

        Label.LabelStyle font1 = new Label.LabelStyle(font, Color.BLUE);
        killCount = new Label("Kill Count: ", font1);
        killCount.setPosition(20, ui.getViewport().getWorldHeight()-killCount.getHeight());

        ui.getActors().add(killCount);


    }

    @Override
    public void resize(int width, int height) {
        // See below for what true means.
        display.getViewport().update(width, height, true);
        ui.getViewport().update(width,height,true);
        camera.update();
    }

    @Override
    public void render() {

        stateTime += Gdx.graphics.getDeltaTime();
        Gdx.gl20.glViewport(0, 0, (int)camera.viewportWidth, (int)camera.viewportHeight);
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        viewDisplay.setCamera(camera);
        camera.position.set(jet.getX()+jet.getOriginX(), jet.getY()+jet.getOriginY(), 0);
        camera.update();
        mapRenderer.setView(camera);
        mapRenderer.render();
        inputHandler.handleInput();
        viewDisplay.apply();
        display.act();
        display.draw();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Welcome to JetStrike", 100, 100);
        font2.draw(batch, "JetStrike is a jet fighter game where you destroy enemy jets!", 100, 60);
        batch.end();

        viewUI.apply();
        ui.act();
        ui.draw();

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
        private boolean mousePressing;
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
            System.out.println("down");
            mousePressing = true;
            return true;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            System.out.println("up");
            mousePressing = false;
            return true;
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

            Vector3 mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            mousePosition = camera.unproject(mousePosition);
            mouseX = mousePosition.x;
            mouseY = mousePosition.y;
            projectiles = getProjectiles();
            ships = getShips();
            setJetDirection();
            updateProjectiles();
            checkProjectiles();

            if (mousePressing) {
                Projectile.shootProjectile(jet);
            }

            if (keyPressed) {

                switch (keycode) {

                    case Input.Keys.W:
                        moveJet(4.5f);
                        break;
                    case Input.Keys.R:
                        enemyJet = new Enemy();
                        enemyJet.setPosition(mouseX,  mouseY);
                        enemyJet.setOrigin(Align.center);
                        enemyJet.setForceField(new ForceField(enemyJet));

                        display.addActor(enemyJet);
                        break;
                    default:

                        break;
                }
                if (stateTime % 10 > -.2 && stateTime % 10 < .2) {
                    enemyJet = new Enemy();
                    enemyJet.setPosition(MathUtils.random(3000), MathUtils.random(3000));
                    enemyJet.setOrigin(Align.center);
                    enemyJet.setForceField(new ForceField(enemyJet));

                    display.addActor(enemyJet);
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
                    if (ship.contains(projectile.getX(), projectile.getY()) && ship instanceof Enemy) {

                        projectile.onShipCollision(ship);
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

    public Stage getUi() {
        return ui;
    }

    public void setUi(Stage ui) {
        this.ui = ui;
    }

    public Viewport getViewDisplay() {
        return viewDisplay;
    }

    public void setViewDisplay(Viewport viewDisplay) {
        this.viewDisplay = viewDisplay;
    }

    public Viewport getViewUI() {
        return viewUI;
    }

    public void setViewUI(Viewport viewUI) {
        this.viewUI = viewUI;
    }

    public Label getKillCount() {
        return killCount;
    }

    public void setKillCount(Label killCount) {
        this.killCount = killCount;
    }

    public void setEnemyJet(Enemy enemyJet) {
        this.enemyJet = enemyJet;
    }

    public float getStateTime() {
        return stateTime;
    }

    public TextureAtlas getSprites() {
        return sprites;
    }

    public void setSprites(TextureAtlas sprites) {
        this.sprites = sprites;
    }

    public Viewport getView() {
        return viewDisplay;
    }

    public void setView(Viewport view) {
        this.viewDisplay = view;
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

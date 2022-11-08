package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.actors.Enemy;
import com.mygdx.game.actors.Player;
import com.mygdx.game.actors.Ship;
import com.mygdx.game.item.ForceField;
import com.mygdx.game.item.projectiles.Projectile;
import com.mygdx.game.item.projectiles.Rocket;

import java.util.Arrays;


public class Game extends ApplicationAdapter {

    public static Game game;
    private BitmapFont font;
    private BitmapFont font2;
    private SpriteBatch batch;
    private float stateTime;
    private ShapeRenderer shapeRenderer;
    private InputHandler inputHandler;
    private Player player;
    private Enemy enemyJet;
    private TextureAtlas sprites;
    private Stage display;
    private Stage ui;
    private GameMapRenderer mapRenderer;
    private Viewport viewDisplay;
    private Viewport viewUI;
    private Label killCountLabel;
    private OrthographicCamera camera;

    @Override
    public void create() {

        game = this;
        stateTime = 0;
        camera = new OrthographicCamera();
        camera.zoom = .5f;
        viewDisplay = new ScreenViewport(camera);
        display = new Stage(viewDisplay);
        viewUI = new ExtendViewport(800, 600);
        ui = new Stage(viewUI);

        inputHandler = new InputHandler(this);
        Gdx.input.setInputProcessor(inputHandler);

        font = new BitmapFont(Gdx.files.internal("Fonts/dontWorry.fnt"));
        font2 = new BitmapFont(Gdx.files.internal("Fonts/font2.fnt"));
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        sprites = new TextureAtlas(Gdx.files.internal("SpriteAtlas/Sprites.atlas"));


        mapRenderer = new GameMapRenderer(GameMapRenderer.Maps.AT_SEA.getMap(), 1f, batch);
        Vector3 spawnPoint = new Vector3(((int) mapRenderer.getMap().getProperties().get("width")) * 16 / 2f,
                ((int) mapRenderer.getMap().getProperties().get("height")) * 16 / 2f,
                0);
        mapRenderer.setSpawnPoint(spawnPoint);

        player = new Player();
        player.setPosition(spawnPoint.x, spawnPoint.y);
        player.setOrigin(Align.center);
        player.setForceField(new ForceField(player));

        display.addActor(player);


        Label.LabelStyle font1 = new Label.LabelStyle(font, Color.BLUE);
        killCountLabel = new Label("Kill Count: " + Enemy.killCount, font1);
        killCountLabel.setPosition(20, ui.getViewport().getWorldHeight() - killCountLabel.getHeight());

        ui.getActors().add(killCountLabel);


    }

    @Override
    public void resize(int width, int height) {
        // See below for what true means.
        display.getViewport().update(width, height, true);
        ui.getViewport().update(width, height, true);
        camera.update();
    }

    @Override
    public void render() {

        stateTime += Gdx.graphics.getDeltaTime();
        Gdx.gl20.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        viewDisplay.setCamera(camera);
        camera.position.set(player.getX() + player.getOriginX(), player.getY() + player.getOriginY(), 0);
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
        font2.draw(batch, "JetStrike is a player fighter game where you destroy enemy jets!", 100, 60);
        batch.end();

        viewDisplay.apply();
        display.act();
        display.draw();

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


    public GameMapRenderer getMapRenderer() {
        return mapRenderer;
    }

    public Label getKillCountLabel() {
        return killCountLabel;
    }

    public OrthographicCamera getCamera() {
        return camera;
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
        return killCountLabel;
    }

    public void setKillCount(Label killCount) {
        this.killCountLabel = killCount;
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Stage getDisplay() {
        return display;
    }

    public void setDisplay(Stage display) {
        this.display = display;
    }
}

package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.*;
import com.mygdx.game.GameMapRenderer;
import com.mygdx.game.InputHandler;
import com.mygdx.game.JSGameStart;
import com.mygdx.game.actors.Enemy;
import com.mygdx.game.actors.EnemyHandler;
import com.mygdx.game.actors.Player;
import com.mygdx.game.item.ForceField;


public class GameScreen extends UIScreen {

    public static GameScreen game;
    private SpriteBatch batch;
    private float stateTime;
    private ShapeRenderer shapeRenderer;
    private InputHandler inputHandler;
    private Player player;
    private Enemy enemyJet;
    private Stage display;
    private Stage ui;
    private GameMapRenderer mapRenderer;
    private Label coinLabel;
    private Viewport viewDisplay;
    private Viewport viewUI;
    private Label killCountLabel;
    private Label deathLabel;
    private Dialog dialog;
    private OrthographicCamera camera;

    public GameScreen(JSGameStart gameStart) {
        super(gameStart);
        if (GameScreen.game == null)
        GameScreen.game = this;
    }

    @Override
    public void show() {

        stateTime = 0;
        camera = new OrthographicCamera();
        camera.zoom = 1f;
        viewDisplay = new ScreenViewport(camera);
        display = new Stage(viewDisplay);
        viewUI = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        ui = new Stage(viewUI);

        inputHandler = new InputHandler(this);
        Gdx.input.setInputProcessor(inputHandler);

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();



        mapRenderer = new GameMapRenderer(GameMapRenderer.Maps.ELECTROCRONIC.getMap(), 1f, batch);
        Vector3 spawnPoint = new Vector3(((int) mapRenderer.getMap().getProperties().get("width")) * 16 / 2f,
                ((int) mapRenderer.getMap().getProperties().get("height")) * 16 / 2f,
                0);
        mapRenderer.setSpawnPoint(spawnPoint);

        player = new Player();
        player.setPosition(spawnPoint.x, spawnPoint.y);
        player.setOrigin(Align.center);
        player.setForceField(new ForceField(player));

        display.addActor(player);


        coinLabel = new Label("Coins Collected: " + player.getCoins(), uiSkin);
        coinLabel.setPosition(ui.getViewport().getScreenWidth()/2f+150, ui.getViewport().getScreenHeight()-coinLabel.getHeight()-15);
        coinLabel.setColor(Color.GOLD);
        Image coinPng = new Image(new Texture(Gdx.files.internal("images/sprites/coin.png")));
        coinPng.setPosition(ui.getViewport().getScreenWidth()/2f+coinLabel.getWidth()+10+150, ui.getViewport().getScreenHeight()-coinLabel.getHeight()-10);

        killCountLabel = new Label("Kill Count: " + Enemy.killCount, uiSkin);
        killCountLabel.setPosition(20, ui.getViewport().getWorldHeight() - killCountLabel.getHeight()-10);
        killCountLabel.setColor(Color.RED);


        deathLabel = new Label("you have died\n please press 'g' to restart", uiSkin);
        deathLabel.setAlignment(Align.center, Align.center);
        deathLabel.setOrigin(deathLabel.getWidth()/2f, deathLabel.getHeight()/2);

        deathLabel.setPosition(ui.getViewport().getScreenWidth()/2f-(deathLabel.getWidth()/2f), ui.getViewport().getScreenHeight()/2f-(deathLabel.getHeight()/2));
        deathLabel.setVisible(false);


        ui.getActors().add(coinLabel);
        ui.getActors().add(coinPng);
        ui.getActors().add(killCountLabel);
        ui.getActors().add(deathLabel);




    }

    @Override
    public void resize(int width, int height) {
        // See below for what true means.
        display.getViewport().update(width, height, true);
        ui.getViewport().update(width,height,true);
        camera.update();
    }

    @Override
    public void render(float delta) {

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

    public void setKillCountLabel(Label killCountLabel) {
        this.killCountLabel = killCountLabel;
    }

    public Label getDeathLabel() {
        return deathLabel;
    }

    public void setDeathLabel(Label deathLabel) {
        this.deathLabel = deathLabel;
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

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
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

    public Label getCoinLabel() {
        return coinLabel;
    }

    public void setCoinLabel(Label coinLabel) {
        this.coinLabel = coinLabel;
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


    public Viewport getView() {
        return viewDisplay;
    }

    public void setView(Viewport view) {
        this.viewDisplay = view;
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


    public void setMapRenderer(GameMapRenderer mapRenderer) {
        this.mapRenderer = mapRenderer;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }
}

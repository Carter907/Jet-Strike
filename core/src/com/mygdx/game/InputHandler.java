package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.actors.Enemy;
import com.mygdx.game.actors.Player;
import com.mygdx.game.actors.Ship;
import com.mygdx.game.item.ForceField;
import com.mygdx.game.item.interactables.Coin;
import com.mygdx.game.item.interactables.Interactable;
import com.mygdx.game.item.projectiles.Projectile;
import screens.GameScreen;

public class InputHandler extends InputAdapter {
    private boolean keyPressed;
    private Array<Ship> ships;
    private Array<Projectile> projectiles;

    private Array<Interactable> interactables;
    private IntSet keycodes = new IntSet(4);
    private boolean mousePressing;
    private float mouseXUnproj;
    private float mouseYUnproj;
    private float mouseXProj;
    private float mouseYProj;
    private float mouseDirection;
    private GameScreen game;

    public InputHandler(GameScreen game) {
        this.game = game;
    }

    @Override
    public boolean keyDown(int keycode) {
        //System.out.println(keycode + " pressed");
        this.keycodes.add(keycode);
        return keyPressed = true;
    }

    @Override
    public boolean keyUp(int keycode) {
        //System.out.println(keycode + " released");
        this.keycodes.remove(keycode);
        return keyPressed = false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //System.out.println("down");
        mousePressing = true;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        //System.out.println("up");
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

        screens.GameScreen.game.getCamera().zoom = amountX / 10f;
        return true;
    }

    public void handleInput() {

        handleMousePosition();
        projectiles = findProjectiles();
        ships = findShips();
        interactables = findInteractables();
        mouseDirection = game.getPlayer().getMouseDirection(mouseXUnproj, mouseYUnproj);
        updateProjectiles();
        checkProjectiles();
        checkInteractables();
        Enemy.AgroAllEnemies(game.getPlayer().getX(), game.getPlayer().getY(), 3f);

        if (!game.getDisplay().getActors().contains(game.getPlayer(), false))
            game.getPlayer().setDead(true);
        game.getDeathLabel().setVisible(game.getPlayer().isDead());
        if (game.getPlayer().isDead()) {

            if (keycodes.contains(Input.Keys.G))
                restartGame();
            return;
        }

        if (mousePressing) {
            if (Gdx.input.isButtonPressed(Input.Buttons.LEFT))
                game.getPlayer().shootProjectile(Projectile.ProjectileType.ROCKET, 0);
        }

        if (keyPressed) {
            if (keycodes.contains(Input.Keys.W) && keycodes.contains(Input.Keys.SHIFT_LEFT))
                game.getPlayer().moveShip(mouseDirection, 6f);
            if (keycodes.contains(Input.Keys.W))
                game.getPlayer().moveShip(mouseDirection, 4.5f);
            if (keycodes.contains(Input.Keys.R) && game.getPlayer().isGodMode())
                Enemy.spawnEnemy(mouseXUnproj, mouseYUnproj);

        }
        if (Enemy.enemyHandler.isRandomEnemySpawn()) {

            Enemy.enemyHandler.getEnemySpawner().scheduleTask(new Timer.Task() {
                @Override
                public void run() {
                    Coin.addCoin(
                            (float) (Math.random() * game.getMapRenderer().getMapWidth() * 16),
                            (float) (Math.random() * game.getMapRenderer().getMapHeight() * 16),
                            Coin.CoinType.NORMAL_COIN);
                    Enemy.spawnEnemy(
                            (float) (Math.random() * game.getMapRenderer().getMapWidth() * 16),
                            (float) (Math.random() * game.getMapRenderer().getMapHeight()) * 16);
                    Enemy.enemyHandler.setRandomEnemySpawn(true);
                }
            }, 1);
            Enemy.enemyHandler.setRandomEnemySpawn(false);
        }

    }

    private void restartGame() {
        game.getDisplay().getActors().removeAll(ships, false);
        Enemy.killCount = 0;
        game.getKillCountLabel().setText("Kill Count: " + Enemy.killCount);
        game.setPlayer(restartPlayer(new Player(game.getMapRenderer().getSpawnPoint().x, game.getMapRenderer().getSpawnPoint().y)));
    }

    private Player restartPlayer(Player player) {

        player.setOrigin(Align.center);
        player.setForceField(new ForceField(player));
        game.getDisplay().addActor(player);

        return player;
    }

    private void handleMousePosition() {

        Vector3 mousePositionUnprojected = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        mousePositionUnprojected = game.getCamera().unproject(mousePositionUnprojected);
        Vector3 mousePositionProjected = new Vector3(Gdx.input.getX(), -1 * (Gdx.input.getY() - Gdx.graphics.getHeight()), 0);
        mouseXUnproj = mousePositionUnprojected.x;
        mouseYUnproj = mousePositionUnprojected.y;
        mouseXProj = mousePositionProjected.x;
        mouseYProj = mousePositionProjected.y;
    }

    private Array<Ship> findShips() {
        Array<Ship> ships = new Array<>();
        for (Actor actor : game.getDisplay().getActors()) {
            if (actor instanceof Ship) {
                ships.add((Ship) actor);
            }
        }
        return ships;
    }

    private Array<Projectile> findProjectiles() {
        Array<Projectile> projectiles = new Array<>();
        for (Actor actor : game.getDisplay().getActors()) {
            if (actor instanceof Projectile) {
                projectiles.add((Projectile) actor);
            }
        }
        return projectiles;
    }

    private Array<Interactable> findInteractables() {

        Array<Interactable> interactables = new Array<>();
        for (Actor actor : game.getDisplay().getActors()) {
            if (actor instanceof Interactable) {
                interactables.add((Interactable) actor);
            }
        }
        return interactables;

    }

    private void checkInteractables() {
        for (Ship ship : ships) {
            for (Interactable intera : interactables) {
                if (ship.contains(intera.getX(), intera.getY())) {
                    game.getDisplay().getActors().removeValue(intera, false);
                    if (ship instanceof Player && intera instanceof Coin) {
                        Player player = (Player) ship;
                        player.setCoins(player.getCoins() + ((Coin) intera).getValue());

                    }
                }

            }
        }


    }

    private void updateKillLabel(int kills) {
        game.getCoinLabel().setText("kill count: " + kills);
    }


    private void checkProjectiles() {
        for (Ship ship : ships)
            for (Projectile projectile : projectiles) {
                if (projectile.isExploded()) {
                    game.getDisplay().getActors().removeValue(projectile, false);
                    continue;
                }
                if (ship.contains(projectile.getX(), projectile.getY())) {

                    projectile.onShipCollision(ship);
                    break;
                }
            }

    }

    private void updateProjectiles() {

        for (Actor actor : game.getDisplay().getActors()) {
            if (actor instanceof Projectile) {
                Projectile projectile = (Projectile) actor;
                if (projectile.getAnimation() == null)
                    if (!projectile.sendProjectile())
                        game.getDisplay().getActors().removeValue(actor, false);

            }

        }
    }

    public static float findDirection(float x1, float y1, float x2, float y2) {

        return (float) Math.toDegrees(Math.atan2((y2 - y1), (x2 - x1)));
    }

    public Array<Ship> getShips() {
        return ships;
    }

    public Array<Projectile> getProjectiles() {
        return projectiles;
    }

    public boolean isKeyPressed() {
        return keyPressed;
    }

    public void setKeyPressed(boolean keyPressed) {
        this.keyPressed = keyPressed;
    }

    public void setShips(Array<Ship> ships) {
        this.ships = ships;
    }

    public void setProjectiles(Array<Projectile> projectiles) {
        this.projectiles = projectiles;
    }

    public IntSet getKeycodes() {
        return keycodes;
    }

    public void setKeycodes(IntSet keycodes) {
        this.keycodes = keycodes;
    }

    public boolean isMousePressing() {
        return mousePressing;
    }

    public void setMousePressing(boolean mousePressing) {
        this.mousePressing = mousePressing;
    }

    public float getMouseXUnproj() {
        return mouseXUnproj;
    }

    public void setMouseXUnproj(float mouseXUnproj) {
        this.mouseXUnproj = mouseXUnproj;
    }

    public float getMouseYUnproj() {
        return mouseYUnproj;
    }

    public void setMouseYUnproj(float mouseYUnproj) {
        this.mouseYUnproj = mouseYUnproj;
    }

    public float getMouseXProj() {
        return mouseXProj;
    }

    public void setMouseXProj(float mouseXProj) {
        this.mouseXProj = mouseXProj;
    }

    public float getMouseYProj() {
        return mouseYProj;
    }

    public void setMouseYProj(float mouseYProj) {
        this.mouseYProj = mouseYProj;
    }

    public float getMouseDirection() {
        return mouseDirection;
    }

    public void setMouseDirection(float mouseDirection) {
        this.mouseDirection = mouseDirection;
    }

    public GameScreen getGame() {
        return game;
    }

    public void setGame(GameScreen GameScreen) {
        this.game = GameScreen;
    }
}
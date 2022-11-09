package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntSet;
import com.mygdx.game.actors.Enemy;
import com.mygdx.game.actors.Player;
import com.mygdx.game.actors.Ship;
import com.mygdx.game.item.ForceField;
import com.mygdx.game.item.projectiles.Projectile;

public class InputHandler extends InputAdapter {
    private boolean keyPressed;
    private Array<Ship> ships;
    private Array<Projectile> projectiles;
    private IntSet keycodes = new IntSet(4);
    private boolean mousePressing;
    private float mouseXUnproj;
    private float mouseYUnproj;
    private float mouseXProj;
    private float mouseYProj;
    private float mouseDirection;
    private Game game;
    public InputHandler(Game game) {
        this.game = game;
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println(keycode + " pressed");
        this.keycodes.add(keycode);
        return keyPressed = true;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println(keycode + " released");
        this.keycodes.remove(keycode);
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

        handleMousePosition();
        projectiles = sortProjectiles();
        ships = sortShips();
        mouseDirection = game.getPlayer().getMouseDirection(mouseXUnproj, mouseYUnproj);
        updateProjectiles();
        checkProjectiles();
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
                game.getPlayer().shootProjectile(Projectile.ProjectileType.ROCKET,0);
        }

        if (keyPressed) {
                if (keycodes.contains(Input.Keys.W) && keycodes.contains(Input.Keys.SHIFT_LEFT))
                    game.getPlayer().moveShip(mouseDirection,6f);
                if (keycodes.contains(Input.Keys.W))
                    game.getPlayer().moveShip(mouseDirection,4.5f);
                if (keycodes.contains(Input.Keys.R) && game.getPlayer().isGodMode())
                    Enemy.spawnEnemy(mouseXUnproj, mouseYUnproj);

        }
        if (game.getStateTime() % 10 > -.2 && game.getStateTime() % 10 < .2) {
            Enemy.spawnEnemy((float) (-1000 + Math.random() * 1500), (float) (-1000 + Math.random() * 1500));
        }
    }

    private void restartGame() {
        game.getDisplay().getActors().removeAll(ships,false);
        Enemy.killCount = 0;
        game.getKillCountLabel().setText("Kill Count: " + Enemy.killCount);
        game.setPlayer(restartPlayer(new Player(game.getMapRenderer().getMapHeight() * 16 / 2f, game.getMapRenderer().getMapHeight() * 16 / 2f)));
    }

    private Player restartPlayer(Player player) {
        player = new Player();
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

    private Array<Ship> sortShips() {
        Array<Ship> ships = new Array<>();
        for (Actor actor : game.getDisplay().getActors()) {
            if (actor instanceof Ship) {
                ships.add((Ship)actor);
            }
        }
        return ships;
    }

    private Array<Projectile> sortProjectiles() {
        Array<Projectile> projectiles = new Array<>();
        for (Actor actor : game.getDisplay().getActors()) {
            if (actor instanceof Projectile) {
                projectiles.add((Projectile)actor);
            }
        }
        return projectiles;
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

        return (float)Math.toDegrees(Math.atan2((y2-y1),(x2-x1)));
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
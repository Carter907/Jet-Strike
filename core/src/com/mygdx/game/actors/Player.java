package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import screens.GameScreen;
import com.mygdx.game.GameAnimation;

public class Player extends Ship {




    public enum PlayerAnimations {
        JET_EXHAUST;

        public GameAnimation getAnimation() {
            return Player.newAnimation(this);
        }

    }
    private boolean godMode;

    private int coins;
    private boolean dead;
    private final Color forceFieldColor = Color.GREEN;

    public Player() {
        super(0, 0, GameScreen.game.getGameStart().getTextureAtlas().findRegion("sprites/ship"));
        this.dead = false;
        this.godMode = false;
        this.setForceFieldColor(forceFieldColor);
        this.setAnimation(PlayerAnimations.JET_EXHAUST.getAnimation());

    }

    public Player(float x, float y) {
        super(x, y, GameScreen.game.getGameStart().getTextureAtlas().findRegion("sprites/ship"));
        this.dead = false;
        this.godMode = false;
        this.setForceFieldColor(forceFieldColor);
        this.setAnimation(PlayerAnimations.JET_EXHAUST.getAnimation());
    }

    private static GameAnimation newAnimation(PlayerAnimations animation) {

        switch (animation) {

            case JET_EXHAUST:
                return new GameAnimation(
                        1 / 15f,
                        GameScreen.game.getGameStart().getTextureAtlas().findRegions("shipExhaustImages/sprite"),
                        Animation.PlayMode.LOOP);

        }
        return null;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getCoins() {
        return coins;
    }
    public boolean isGodMode() {
        return godMode;
    }

    public void setGodMode(boolean godMode) {
        this.godMode = godMode;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
}

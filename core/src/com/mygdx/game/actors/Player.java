package com.mygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Game;
import com.mygdx.game.GameAnimation;
import com.mygdx.game.item.projectiles.Rocket;

public class Player extends Ship {
    public enum PlayerAnimations {
        JET_EXHAUST;

        public GameAnimation getAnimation() {
            return Player.newAnimation(this);
        }

    }
    private boolean dead;
    private final Color forceFieldColor = Color.GREEN;

    public Player() {
        super(0, 0, Game.game.getSprites().findRegion("ship"));
        this.dead = false;
        this.setForceFieldColor(forceFieldColor);
        this.setAnimation(PlayerAnimations.JET_EXHAUST.getAnimation());

    }

    public Player(float x, float y) {
        super(x, y, Game.game.getSprites().findRegion("ship"));
        this.dead = false;
        this.setForceFieldColor(forceFieldColor);
        this.setAnimation(PlayerAnimations.JET_EXHAUST.getAnimation());
    }

    private static GameAnimation newAnimation(PlayerAnimations animation) {

        switch (animation) {

            case JET_EXHAUST:
                return new GameAnimation(
                        1 / 15f,
                        new TextureAtlas(Gdx.files.internal("PlayerAnimations/PlayerExhaustAnimation.atlas")).getRegions(),
                        Animation.PlayMode.LOOP);

        }
        return null;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
}

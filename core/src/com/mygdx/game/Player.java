package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Player extends Ship {

    private enum PlayerAnimations {

        JET_EXHAUST("PlayerAnimations/PlayerExhaustAnimation.atlas");

        private String path;
        PlayerAnimations(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }
    private final Color forceFieldColor = Color.GREEN;
    private final TextureAtlas animationAtlas = new TextureAtlas(Gdx.files.internal(PlayerAnimations.JET_EXHAUST.getPath()));
    private final Animation<TextureAtlas.AtlasRegion> exhaustAnimation = new Animation<>(1/15f, animationAtlas.getRegions(), Animation.PlayMode.LOOP );
    private final TextureAtlas.AtlasRegion sprite = Game.game.getSprites().findRegion("ship");
    public Player() {
        super(0,0, Game.game.getSprites().findRegion("ship"));
        this.setForceFieldColor(forceFieldColor);
        this.setAnimation(exhaustAnimation);

    }

    public Player(float x, float y) {
        super(x,y, Game.game.getSprites().findRegion("ship"));
        this.setForceFieldColor(forceFieldColor);
        this.setAnimation(exhaustAnimation);
    }
}

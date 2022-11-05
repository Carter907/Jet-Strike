package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Enemy extends Ship {

    private enum EnemyAnimations {

        CHOPPER_ANIMATION()

    }
    private final Color forceFieldColor = Color.RED;
    private final TextureAtlas.AtlasRegion sprite = Game.game.getAtlas().findRegion("enemyChoppa");
    public Enemy() {
        super(0,0, Game.game.getAtlas().findRegion("enemyChoppa"));
        setForceFieldColor(forceFieldColor);
    }

    public Enemy(float x, float y) {
        super(x,y, Game.game.getAtlas().findRegion("enemyChoppa"));
        setForceFieldColor(forceFieldColor);
    }
}



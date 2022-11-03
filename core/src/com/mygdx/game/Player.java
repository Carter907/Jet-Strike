package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public class Player extends Ship {
    private final Color forceFieldColor = Color.GREEN;
    private final Texture sprite = new Texture(Gdx.files.internal("ship.png"));
    public Player() {
        super(0,0, new Texture(Gdx.files.internal("ship.png")));
        this.setForceFieldColor(forceFieldColor);
    }

    public Player(float x, float y) {
        super(x,y, new Texture(Gdx.files.internal("ship.png")));
        this.setForceFieldColor(forceFieldColor);
    }
}

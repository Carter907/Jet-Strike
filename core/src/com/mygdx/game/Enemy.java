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

public class Enemy extends Image {

    public static final float maxDistance = 400;
    private float path;
    private final Texture sprite = new Texture(Gdx.files.internal("enemyChoppa.png"));

    public Enemy() {
        this.setX(0);
        this.setY(0);
        this.path = 0;
        this.setRotation(path);
        this.setDrawable(new SpriteDrawable(new Sprite(sprite)));

    }

    public Enemy(float x, float y, float path) {
        this.setX(x);
        this.setY(y);
        this.path = path;
        this.setRotation(path);
        this.setDrawable(new SpriteDrawable(new Sprite(sprite)));
    }
}



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

public class Player extends Image {

    public static final float maxDistance = 400;
    private float path;
    private final Texture sprite = new Texture(Gdx.files.internal("ship.png"));

    public Player() {
        this.setX(0);
        this.setY(0);
        this.path = 0;
        this.setRotation(path);
        this.setDrawable(new SpriteDrawable(new Sprite(sprite)));

    }

    public Player(float x, float y, float path) {
        this.setX(x);
        this.setY(y);
        this.path = path;
        this.setRotation(path);
        this.setDrawable(new SpriteDrawable(new Sprite(sprite)));
    }

    public class ForceField extends Actor {
        private ShapeRenderer shapeRenderer = new ShapeRenderer();
        private Player player;

        public ForceField() {

            this.player = Player.this;
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            batch.end();
            shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
            shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
            shapeRenderer.translate(getX(), getY(), 0);

            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.circle(player.getX() + player.getOriginX(), player.getY() + player.getOriginY(), 50);
            shapeRenderer.setColor(0, 1f, 0, 0);
            shapeRenderer.end();
            batch.begin();
        }
    }
}

package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Projectile extends Actor {

    public static final float maxDistance = 400;
    public static final float pixelsPerSecond = 500;
    private ShapeRenderer renderer;
    private float path;
    private long distanceTraveled;

    public Projectile(float x, float y, float path) {
        this.setX(x);
        this.setY(y);
        this.setWidth(5);
        this.setHeight(2);
        this.path = path;
        this.setRotation(path);
        this.distanceTraveled = 0;
        renderer = new ShapeRenderer();
    }



    public void draw (Batch batch, float parentAlpha) {
        batch.end();

        renderer.setProjectionMatrix(batch.getProjectionMatrix());
        renderer.setTransformMatrix(batch.getTransformMatrix());
        renderer.translate(getX(), getY(), 0);
        renderer.rotate(0,0,1,path);

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.BLUE);
        renderer.rect(0, 0, getWidth(), getHeight());
        renderer.end();

        batch.begin();
    }

    public boolean sendProjectile() {

        if (distanceTraveled > maxDistance)
            return false;
        this.setX((float) (this.getX() + pixelsPerSecond/30*Math.cos(Math.toRadians(path))));
        this.setY((float) (this.getY() + pixelsPerSecond/30*Math.sin(Math.toRadians(path))));
        this.distanceTraveled += 1*pixelsPerSecond/30;
        return true;
    }


}

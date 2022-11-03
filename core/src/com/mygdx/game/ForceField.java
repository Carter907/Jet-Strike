package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ForceField extends Actor {
    private ShapeRenderer shapeRenderer;
    private Ship ship;
    private Color color;

    public ForceField(Ship ship) {

        this.shapeRenderer = new ShapeRenderer();

        this.ship = ship;
        this.color = ship.getForceFieldColor();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        batch.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
        shapeRenderer.translate(getX(), getY(), 0);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.circle(ship.getX() + ship.getOriginX(), ship.getY() + ship.getOriginY(), 50);
        shapeRenderer.setColor(color);
        shapeRenderer.end();
        batch.begin();
    }
}
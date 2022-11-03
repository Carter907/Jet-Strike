package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

public abstract class Ship extends Actor {
    private Color forceFieldColor;
    private ForceField forceField;
    private TextureRegion sprite;

    public Ship() {

        this.setX(0);
        this.setY(0);

        this.sprite = null;
    }

    public Ship(float x, float y) {

        this.setX(x);
        this.setY(y);

        this.sprite = null;
    }

    public Ship(float x, float y, Texture sprite) {

        this.setX(x);
        this.setY(y);

        this.sprite = new TextureRegion(sprite);
        setBounds(this.sprite.getRegionX(), this.sprite.getRegionY(),
                this.sprite.getRegionWidth(), this.sprite.getRegionHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        this.forceField.draw(batch, parentAlpha);

        batch.draw(this.sprite, getX(), getY(), getOriginX(),
                getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

    }

    public Color getForceFieldColor() {
        return forceFieldColor;
    }

    public void setForceFieldColor(Color forceFieldColor) {
        this.forceFieldColor = forceFieldColor;
        if (this.forceField != null) {
            this.forceField.setColor(forceFieldColor);
        }
    }

    public ForceField getForceField() {
        return forceField;
    }

    public void setForceField(ForceField field) {
        this.forceField = field;
    }

    public TextureRegion getSprite() {
        return sprite;
    }

    public void setSprite(TextureRegion sprite) {
        this.sprite = sprite;
    }
}

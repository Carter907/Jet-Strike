package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;


public abstract class Ship extends Actor {
    private Color forceFieldColor;
    private ForceField forceField;
    private TextureAtlas.AtlasRegion sprite;

    private Animation<TextureAtlas.AtlasRegion> animation;

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
    public boolean contains(float x, float y) {

        return (x >= this.getX() && x <= this.getX() + this.getWidth()) && (y >= this.getY() && y <= this.getY() + this.getHeight());
    }

    public Ship(float x, float y, TextureAtlas.AtlasRegion sprite) {

        this.setX(x);
        this.setY(y);
        this.sprite = sprite;
        setBounds(this.sprite.getRegionX(), this.sprite.getRegionY(),
                this.sprite.getRegionWidth(), this.sprite.getRegionHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        this.forceField.draw(batch, parentAlpha);
        if (animation == null) {
            batch.draw(this.sprite, getX(), getY(), getOriginX(),
                    getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        } else {
            batch.draw(this.animation.getKeyFrame(Game.game.getStateTime()), getX(), getY(), getOriginX(),
                    getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
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

    public TextureAtlas.AtlasRegion getSprite() {
        return sprite;
    }

    public void setSprite(TextureAtlas.AtlasRegion sprite) {
        this.sprite = sprite;
    }

    public Animation<TextureAtlas.AtlasRegion> getAnimation() {
        return animation;
    }

    public void setAnimation(Animation<TextureAtlas.AtlasRegion> animation) {
        this.animation = animation;
    }
}

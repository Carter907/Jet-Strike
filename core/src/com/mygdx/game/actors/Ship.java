package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import screens.GameScreen;
import com.mygdx.game.GameAnimation;
import com.mygdx.game.item.ForceField;
import com.mygdx.game.item.projectiles.Bullet;
import com.mygdx.game.item.projectiles.Projectile;
import com.mygdx.game.item.projectiles.Rocket;


public abstract class Ship extends Actor {
    private Color forceFieldColor;
    private ForceField forceField;

    private float timeSinceLastShot;
    private int health;
    private TextureRegion sprite;

    private GameAnimation animation;

    public Ship() {

        this.health = 100;
        this.setX(0);
        this.setY(0);
        this.timeSinceLastShot = 0;
        this.sprite = null;
    }

    public Ship(float x, float y) {

        this.health = 100;
        this.setX(x);
        this.setY(y);
        this.timeSinceLastShot = 0;
        this.sprite = null;
    }

    public boolean contains(float x, float y) {

        return (x >= this.getX() && x <= this.getX() + this.getWidth()) && (y >= this.getY() && y <= this.getY() + this.getHeight());
    }

    public Ship(float x, float y, TextureRegion sprite) {
        this.timeSinceLastShot = 0;
        this.health = 100;
        this.sprite = sprite;
        this.setBounds(x, y, sprite.getRegionWidth(), sprite.getRegionHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        this.forceField.draw(batch, parentAlpha);
        if (animation == null) {
            batch.draw(this.sprite, getX(), getY(), getOriginX(),
                    getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        } else {
            batch.draw(this.animation.getKeyFrame(GameScreen.game.getStateTime()), getX(), getY(), getOriginX(),
                    getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }

    @Override
    public void setWidth(float width) {
        throw new IllegalStateException("you shouldn't change the width of a Ship");
    }

    @Override
    public void setHeight(float height) {
        throw new IllegalStateException("you shouldn't change the height of a Ship");
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

    public void moveShip(float direction, float speed) {

        this.setX((float) (this.getX() + speed * Math.cos(Math.toRadians(direction))));
        this.setY((float) (this.getY() + speed * Math.sin(Math.toRadians(direction))));
        this.setRotation(direction);
    }

    public float getMouseDirection(float x, float y) {

         float mouseDirection = (float) Math.toDegrees(Math.atan2(
                y - (this.getY() + this.getOriginY()),
                x - (this.getX() + this.getOriginX())
        ));
        this.setRotation(mouseDirection);
        return mouseDirection;
    }

    public void shootProjectile(Projectile.ProjectileType type, float accuracy) {
        switch (type) {
            case ROCKET:
                Rocket.shootProjectile(this, accuracy);
                break;
            case BULLET:
                Bullet.shootProjectile(this, accuracy);
                break;
        }
    }

    public float getTimeSinceLastShot() {
        return timeSinceLastShot;
    }

    public void setTimeSinceLastShot(float timeSinceLastShot) {
        this.timeSinceLastShot = timeSinceLastShot;
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

    public GameAnimation getAnimation() {
        return animation;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setAnimation(GameAnimation animation) {
        this.animation = animation;
    }


}

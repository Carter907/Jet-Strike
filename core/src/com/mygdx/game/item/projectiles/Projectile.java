package com.mygdx.game.item.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.Game;
import com.mygdx.game.GameAnimation;
import com.mygdx.game.actors.Enemy;
import com.mygdx.game.actors.Ship;

public abstract class Projectile extends Actor {

    public enum ProjectileType {
        ROCKET,
        BULLET;
    }

    public final float maxDistance;
    public final float fireRate;
    public final float pixelsPerSecond;
    private boolean exploded;
    private GameAnimation animation;
    private float path;
    private long distanceTraveled;
    private TextureRegion sprite;

    public Projectile(float x, float y, float path, TextureRegion sprite, float maxDistance, float fireRate, float pixelsPerSecond) {

        this.setX(x);
        this.setY(y);
        this.setRotation(path);
        this.setBounds(x,y,sprite.getRegionWidth(), sprite.getRegionHeight());

        this.path = path;
        this.distanceTraveled = 0;
        this.sprite = sprite;
        this.maxDistance = maxDistance;
        this.fireRate = fireRate;
        this.pixelsPerSecond = pixelsPerSecond;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if (animation == null)
            batch.draw(sprite, getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        else {
            animation.setAnimationTime(animation.getAnimationTime() + Gdx.graphics.getDeltaTime());
            TextureRegion keyFrame = animation.getKeyFrame(animation.getAnimationTime());
            batch.draw(keyFrame, getX() - keyFrame.getRegionWidth() / 2f, getY() - keyFrame.getRegionHeight() / 2f);
            if (animation.getKeyFrameIndex(animation.getAnimationTime()) == animation.getFrameCount() - 1)
                exploded = true;
        }
    }

    public boolean sendProjectile() {

        if (distanceTraveled > maxDistance)
            return false;
        this.setX((float) (this.getX() + pixelsPerSecond / 30 * Math.cos(Math.toRadians(path))));
        this.setY((float) (this.getY() + pixelsPerSecond / 30 * Math.sin(Math.toRadians(path))));
        this.distanceTraveled += 1 * pixelsPerSecond / 30;
        return true;
    }

    public void onShipCollision(Ship ship) {
        throw new IllegalStateException("Projectile does not have an onShipCollision");
    }

    public GameAnimation getAnimation() {
        return animation;
    }

    public void setAnimation(GameAnimation animation) {
        this.animation = animation;
    }

    public boolean isExploded() {
        return exploded;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    public float getPath() {
        return path;
    }

    public void setPath(float path) {
        this.path = path;
    }

    public long getDistanceTraveled() {
        return distanceTraveled;
    }

    public void setDistanceTraveled(long distanceTraveled) {
        this.distanceTraveled = distanceTraveled;
    }

    public TextureRegion getSprite() {
        return sprite;
    }

    public void setSprite(TextureRegion sprite) {
        this.sprite = sprite;
    }


}

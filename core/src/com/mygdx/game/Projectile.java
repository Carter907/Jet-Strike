package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

public class Projectile extends Actor {



    public enum ProjectileAnimations {


        ROCKET_EXPLOSION(new Animation<TextureAtlas.AtlasRegion>(
                1/15f,
                new TextureAtlas(Gdx.files.internal("RocketAnimations/RocketExplosion.atlas")).getRegions(),
                Animation.PlayMode.LOOP), new TextureAtlas(Gdx.files.internal("RocketAnimations/RocketExplosion.atlas")).getRegions().size);

        private Animation<TextureAtlas.AtlasRegion> animation;
        private int frameCount;

        ProjectileAnimations(Animation<TextureAtlas.AtlasRegion> animation, int frameCount) {
            this.animation = animation;
            System.out.println(frameCount);
            this.frameCount = frameCount;
        }

        public Animation<TextureAtlas.AtlasRegion> getAnimation() {
            return this.animation;
        }

        public int getFrameCount() {
            return this.frameCount;
        }

    }

    public enum ProjectileTypes {

        ROCKET("rocket");

        private String file;

        ProjectileTypes(String file) {
            this.file = file;
        }

        public String getRegion() {
            return this.file;
        }
    }

    public static final float maxDistance = 400;

    public static final float fireRate = 100;

    public static float timeElapsed = 100;
    public static final float pixelsPerSecond = 500;
    private float path;
    private boolean exploded;
    private Animation<TextureAtlas.AtlasRegion> animation;
    private ProjectileAnimations animationType;
    private long distanceTraveled;
    private ProjectileTypes projectileType;
    private TextureRegion sprite;

    public Projectile(float x, float y, float path, ProjectileTypes projectileType) {
        this.setX(x);
        this.setY(y);
        this.setWidth(20);
        this.setHeight(10);
        this.path = path;
        this.setRotation(path);
        this.distanceTraveled = 0;
        this.sprite = Game.game.getAtlas().findRegion(projectileType.getRegion());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (animation == null)
            batch.draw(sprite, getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        else if (animationType != null){
            TextureAtlas.AtlasRegion keyFrame = animation.getKeyFrame(Game.game.getStateTime());
            batch.draw(keyFrame,getX()-keyFrame.getRegionWidth()/2,getY()-keyFrame.getRegionHeight()/2);
            if (animation.getKeyFrameIndex(Game.game.getStateTime()) == animationType.getFrameCount()-1)
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

    public static void shootProjectile(Ship jet) {
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && Game.game.getStateTime()*1000 >= Projectile.timeElapsed+Projectile.fireRate) {
            System.out.println(jet.getRotation());

            Projectile bullet = new Projectile(

                    (float) ((jet.getX() + jet.getOriginX()) + ((jet.getWidth() / 2 + 10) * Math.cos(Math.toRadians(jet.getRotation())))),
                    (float) ((jet.getY() + jet.getOriginY()) + ((jet.getWidth() / 2 + 10) * Math.sin(Math.toRadians(jet.getRotation())))),
                    jet.getRotation(),
                    Projectile.ProjectileTypes.ROCKET
            );

            Game.game.getDisplay().addActor(bullet);
            Projectile.timeElapsed = Game.game.getStateTime()*1000;
        }
    }
    public void onShipCollision(Ship ship) {
        this.setAnimation(Projectile.ProjectileAnimations.ROCKET_EXPLOSION);
        Game.game.getDisplay().getActors().removeValue(ship, false);
        Enemy.killCount++;
        Game.game.getKillCount().setText("Kill Count: " + Enemy.killCount);
    }
    public Animation<TextureAtlas.AtlasRegion> getAnimation() {
        return animation;
    }

    public void setAnimation(Animation<TextureAtlas.AtlasRegion> animation) {
        this.animation = animation;
    }

    public void setAnimation(ProjectileAnimations projectileAnimation) {
        this.animationType = projectileAnimation;
        this.animation = projectileAnimation.getAnimation();
    }

    public ProjectileTypes getProjectileType() {
        return projectileType;
    }

    public void setProjectileType(ProjectileTypes projectileType) {
        this.projectileType = projectileType;
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

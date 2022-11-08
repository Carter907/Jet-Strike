package com.mygdx.game.item.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Game;
import com.mygdx.game.GameAnimation;
import com.mygdx.game.actors.Enemy;
import com.mygdx.game.actors.Player;
import com.mygdx.game.actors.Ship;

public class Bullet extends Projectile {
    public enum BulletAnimation {
        BULLET_IMPACT;

        public GameAnimation getAnimation() {
            return Bullet.newAnimation(this);
        }

    }

    public static final TextureRegion sprite = Game.game.getSprites().findRegion("bullet");
    public static final float maxDistance = 400;
    public static final int damage = 5;
    public static final float fireRate = 200;
    public static float timeSinceLastShot = 100;
    public static final float pixelsPerSecond = 500;

    private boolean exploded;

    public Bullet(float x, float y, float path) {
        super(x, y, path, sprite, maxDistance, fireRate, pixelsPerSecond);
    }

    @Override
    public void onShipCollision(Ship ship) {
        this.setAnimation(Rocket.RocketAnimations.ROCKET_EXPLOSION.getAnimation());
        ship.setHealth(ship.getHealth()-damage);
        if (ship.getHealth() <= 0) {
            if (ship instanceof Player)
                ((Player)ship).setDead(true);
            Game.game.getDisplay().getActors().removeValue(ship, false);
        }
        Enemy.killCount++;
        Game.game.getKillCount().setText("Kill Count: " + Enemy.killCount);
    }


    public static void shootProjectile(Ship jet) {

        if (Game.game.getStateTime() * 1000 >= jet.getTimeSinceLastShot() + fireRate) {
            System.out.println(jet.getRotation());
            Projectile rocket = new Bullet(

                    (float) ((jet.getX() + jet.getOriginX()) + ((jet.getWidth() / 2 + 10) * Math.cos(Math.toRadians(jet.getRotation())))),
                    (float) ((jet.getY() + jet.getOriginY()) + ((jet.getWidth() / 2 + 10) * Math.sin(Math.toRadians(jet.getRotation())))),
                    jet.getRotation()
            );
            Game.game.getDisplay().addActor(rocket);
            jet.setTimeSinceLastShot(Game.game.getStateTime() * 1000);
        }
    }
    public static void shootProjectile(Ship jet, float accuracy) {

        if (Game.game.getStateTime() * 1000 >= jet.getTimeSinceLastShot() + fireRate) {
            System.out.println(jet.getRotation());
            Projectile rocket = new Bullet(

                    (float) ((jet.getX() + jet.getOriginX()) + ((jet.getWidth() / 2 + 10) * Math.cos(Math.toRadians(+jet.getRotation())))),
                    (float) ((jet.getY() + jet.getOriginY()) + ((jet.getWidth() / 2 + 10) * Math.sin(Math.toRadians(jet.getRotation())))),
                    (float)(Math.random())*(100-accuracy)-15+jet.getRotation()
            );
            Game.game.getDisplay().addActor(rocket);
            jet.setTimeSinceLastShot(Game.game.getStateTime() * 1000);
        }
    }

    private static GameAnimation newAnimation(BulletAnimation animation) {

        switch (animation) {

            case BULLET_IMPACT:
                return new GameAnimation(
                        1 / 15f,
                        new TextureAtlas(Gdx.files.internal("RocketAnimations/RocketExplosion.atlas")).getRegions(),
                        Animation.PlayMode.LOOP);

        }
        return null;
    }

}
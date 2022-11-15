package com.mygdx.game.item.projectiles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import screens.GameScreen;
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

    public static final TextureRegion sprite = GameScreen.game.getGameStart().getTextureAtlas().findRegion("sprites/bullet");
    public static final float maxDistance = 400;
    public static final int damage = 5;
    public static final float fireRate = 200;
    public static float timeSinceLastShot = 100;
    public static final float pixelsPerSecond = 500;

    private boolean exploded;

    public Bullet(float x, float y, float path, Ship originShip) {
        super(x, y, path, sprite, originShip, maxDistance, fireRate, pixelsPerSecond);
    }

    @Override
    public void onShipCollision(Ship ship) {
        this.setAnimation(Rocket.RocketAnimations.ROCKET_EXPLOSION.getAnimation());
        ship.setHealth(ship.getHealth()-damage);
        if (ship.getHealth() <= 0) {
            if (ship instanceof Player)
                ((Player)ship).setDead(true);
            GameScreen.game.getDisplay().getActors().removeValue(ship, false);
            if (this.getOriginShip() instanceof Player) {
                Enemy.killCount++;
                GameScreen.game.getKillCount().setText("Kill Count: " + Enemy.killCount);
            }
        }



    }


    public static void shootProjectile(Ship jet) {

        if (GameScreen.game.getStateTime() * 1000 >= jet.getTimeSinceLastShot() + fireRate) {
            System.out.println(jet.getRotation());
            Projectile rocket = new Bullet(

                    (float) ((jet.getX() + jet.getOriginX()) + ((jet.getWidth() / 2 + 10) * Math.cos(Math.toRadians(jet.getRotation())))),
                    (float) ((jet.getY() + jet.getOriginY()) + ((jet.getWidth() / 2 + 10) * Math.sin(Math.toRadians(jet.getRotation())))),
                    jet.getRotation(), jet
            );
            GameScreen.game.getDisplay().addActor(rocket);
            jet.setTimeSinceLastShot(GameScreen.game.getStateTime() * 1000);
        }
    }
    public static void shootProjectile(Ship jet, float accuracy) {

        if (GameScreen.game.getStateTime() * 1000 >= jet.getTimeSinceLastShot() + fireRate) {
            System.out.println(jet.getRotation());
            Projectile rocket = new Bullet(

                    (float) ((jet.getX() + jet.getOriginX()) + ((jet.getWidth() / 2 + 10) * Math.cos(Math.toRadians(+jet.getRotation())))),
                    (float) ((jet.getY() + jet.getOriginY()) + ((jet.getWidth() / 2 + 10) * Math.sin(Math.toRadians(jet.getRotation())))),
                    (float)(Math.random())*(100-accuracy)-15+jet.getRotation(), jet
            );
            GameScreen.game.getDisplay().addActor(rocket);
            jet.setTimeSinceLastShot(GameScreen.game.getStateTime() * 1000);
        }
    }

    private static GameAnimation newAnimation(BulletAnimation animation) {

        switch (animation) {

            case BULLET_IMPACT:
                return new GameAnimation(
                        1 / 15f,
                        GameScreen.game.getGameStart().getTextureAtlas().findRegions("rocketExplosionImages/sprite"),
                        Animation.PlayMode.LOOP);

        }
        return null;
    }

}
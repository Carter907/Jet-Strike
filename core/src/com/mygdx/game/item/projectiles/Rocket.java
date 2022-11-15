package com.mygdx.game.item.projectiles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import screens.GameScreen;
import com.mygdx.game.GameAnimation;
import com.mygdx.game.actors.Enemy;
import com.mygdx.game.actors.Ship;

public class Rocket extends Projectile {

    public enum RocketAnimations {
        ROCKET_EXPLOSION;

        public GameAnimation getAnimation() {
            return Rocket.newAnimation(this);
        }

    }

    public static final TextureRegion sprite = GameScreen.game.getGameStart().getTextureAtlas().findRegion("sprites/rocket");
    public static final float maxDistance = 1000;
    public static final float fireRate = 100;

    public static final float pixelsPerSecond = 500;

    private boolean exploded;

    public Rocket(float x, float y, float path, Ship originShip) {
        super(x, y, path, sprite, originShip, maxDistance, fireRate, pixelsPerSecond);
    }

    @Override
    public void onShipCollision(Ship ship) {
        this.setAnimation(RocketAnimations.ROCKET_EXPLOSION.getAnimation());
        GameScreen.game.getDisplay().getActors().removeValue(ship, false);
        Enemy.killCount++;
        GameScreen.game.getKillCount().setText("Kill Count: " + Enemy.killCount);
    }


    public static void shootProjectile(Ship jet, float accuracy) {

        if (GameScreen.game.getStateTime() * 1000 >= jet.getTimeSinceLastShot() + fireRate) {
            System.out.println(jet.getRotation());
            Projectile rocket = new Rocket(

                    (float) ((jet.getX() + jet.getOriginX()) + ((jet.getWidth() / 2 + 10) * Math.cos(Math.toRadians(jet.getRotation())))),
                    (float) ((jet.getY() + jet.getOriginY()) + ((jet.getWidth() / 2 + 10) * Math.sin(Math.toRadians(jet.getRotation())))),
                    jet.getRotation(), jet
            );
            GameScreen.game.getDisplay().addActor(rocket);
            jet.setTimeSinceLastShot(GameScreen.game.getStateTime() * 1000);
        }
    }

    private static GameAnimation newAnimation(RocketAnimations animation) {

        switch (animation) {

            case ROCKET_EXPLOSION:
                return new GameAnimation(
                        1 / 15f,
                        GameScreen.game.getGameStart().getTextureAtlas().findRegions("rocketExplosionImages/sprite"),
                        Animation.PlayMode.LOOP);

        }
        return null;
    }

}

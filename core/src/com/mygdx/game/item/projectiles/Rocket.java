package com.mygdx.game.item.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Game;
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

    public static final TextureRegion sprite = Game.game.getSprites().findRegion("rocket");
    public static final float maxDistance = 400;
    public static final float fireRate = 100;

    public static final float pixelsPerSecond = 500;

    private boolean exploded;

    public Rocket(float x, float y, float path) {
        super(x, y, path, sprite, maxDistance, fireRate, pixelsPerSecond);
    }

    @Override
    public void onShipCollision(Ship ship) {
        this.setAnimation(RocketAnimations.ROCKET_EXPLOSION.getAnimation());
        Game.game.getDisplay().getActors().removeValue(ship, false);
        Enemy.killCount++;
        Game.game.getKillCount().setText("Kill Count: " + Enemy.killCount);
    }


    public static void shootProjectile(Ship jet, float accuracy) {

        if (Game.game.getStateTime() * 1000 >= jet.getTimeSinceLastShot() + fireRate) {
            System.out.println(jet.getRotation());
            Projectile rocket = new Rocket(

                    (float) ((jet.getX() + jet.getOriginX()) + ((jet.getWidth() / 2 + 10) * Math.cos(Math.toRadians(jet.getRotation())))),
                    (float) ((jet.getY() + jet.getOriginY()) + ((jet.getWidth() / 2 + 10) * Math.sin(Math.toRadians(jet.getRotation())))),
                    jet.getRotation()
            );
            Game.game.getDisplay().addActor(rocket);
            jet.setTimeSinceLastShot(Game.game.getStateTime() * 1000);
        }
    }

    private static GameAnimation newAnimation(RocketAnimations animation) {

        switch (animation) {

            case ROCKET_EXPLOSION:
                return new GameAnimation(
                        1 / 15f,
                        new TextureAtlas(Gdx.files.internal("RocketAnimations/RocketExplosion.atlas")).getRegions(),
                        Animation.PlayMode.LOOP);

        }
        return null;
    }

}

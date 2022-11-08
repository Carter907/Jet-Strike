package com.mygdx.game.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.Game;
import com.mygdx.game.InputHandler;
import com.mygdx.game.item.ForceField;
import com.mygdx.game.item.projectiles.Projectile;

public class Enemy extends Ship {

    public static final float difficultyLevel = 80f;
    public static int killCount = 0;
    private boolean aggressive;

    private enum EnemyAnimations {

        CHOPPER_ANIMATION()

    }
    private final Color forceFieldColor = Color.RED;
    private final TextureRegion sprite = Game.game.getSprites().findRegion("enemyChoppa");
    public Enemy() {
        super(0,0, Game.game.getSprites().findRegion("enemyChoppa"));
        setForceFieldColor(forceFieldColor);
        aggressive = false;
    }

    public Enemy(float x, float y) {
        super(x,y, Game.game.getSprites().findRegion("enemyChoppa"));
        setForceFieldColor(forceFieldColor);
        aggressive = false;
    }

    private void fight(float direction, float speed) {
        if (aggressive) {
            this.moveShip(direction,speed);
            this.shootProjectile(Projectile.ProjectileType.BULLET, difficultyLevel);
        }
    }

    public static void AgroAllEnemies(float x, float y, float speed) {

        for (Ship ship : Game.game.getInputHandler().getShips()) {

            if (ship instanceof Enemy) {
                Enemy enemy = (Enemy)ship;

                float direction = InputHandler.findDirection(x,y,enemy.getX(),enemy.getY());


                enemy.setAggressive(true);
                enemy.fight(direction+180,speed);
            }
        }
    }

    private void setAggressive(boolean aggressive) {
        this.aggressive = aggressive;
    }

    public static void spawnEnemy(float x, float y) {
        Enemy enemyJet = new Enemy();
        enemyJet.setOrigin(enemyJet.getWidth()/2, enemyJet.getHeight()/2);
        enemyJet.setPosition(x-enemyJet.getOriginX(), y-enemyJet.getOriginY());
        enemyJet.setForceField(new ForceField(enemyJet));

        Game.game.getDisplay().addActor(enemyJet);
    }
}



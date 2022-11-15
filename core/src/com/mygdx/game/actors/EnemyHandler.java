package com.mygdx.game.actors;

import com.badlogic.gdx.utils.Timer;

public class EnemyHandler {

    private boolean randomEnemySpawn;
    private float difficultyLevel;
    private Timer EnemySpawner;
    private Timer.Task spawnTask;

    public EnemyHandler(boolean randomEnemySpawn, float difficultyLevel) {

        this.difficultyLevel = difficultyLevel;
        this.randomEnemySpawn = randomEnemySpawn;
        this.EnemySpawner = new Timer();
    }
    public EnemyHandler(boolean randomEnemySpawn, float difficultyLevel, Timer enemySpawner) {

        this.difficultyLevel = difficultyLevel;
        this.randomEnemySpawn = randomEnemySpawn;
        this.EnemySpawner = enemySpawner;
    }


    public EnemyHandler(boolean randomEnemySpawn, float difficultyLevel, Timer.Task enemySpawnerTask) {

        this.difficultyLevel = difficultyLevel;
        this.randomEnemySpawn = randomEnemySpawn;
        this.EnemySpawner = new Timer();
    }

    public boolean isRandomEnemySpawn() {
        return randomEnemySpawn;
    }

    public void setRandomEnemySpawn(boolean randomEnemySpawn) {
        this.randomEnemySpawn = randomEnemySpawn;
    }

    public float getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(float difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Timer getEnemySpawner() {
        return EnemySpawner;
    }

    public void setEnemySpawner(Timer enemySpawner) {
        EnemySpawner = enemySpawner;
    }

    public Timer.Task getSpawnTask() {
        return spawnTask;
    }

    public void setSpawnTask(Timer.Task spawnTask) {
        this.spawnTask = spawnTask;
    }
}

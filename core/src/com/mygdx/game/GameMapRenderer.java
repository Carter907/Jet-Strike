package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;

public class GameMapRenderer extends OrthogonalTiledMapRenderer {

    public enum Maps {

        BLACKHOLE(new TmxMapLoader().load("Maps/blackhole.tmx")),
        AT_SEA(new TmxMapLoader().load("Maps/atSea.tmx"));

        private final TiledMap map;

        Maps(TiledMap map) {
            this.map = map;
        }

        public TiledMap getMap() {
            return this.map;
        }
    }

    private int mapHeight;
    private int mapWidth;
    private Vector3 spawnPoint;

    public GameMapRenderer(TiledMap map) {
        super(map);
        spawnPoint = new Vector3(250, 250, 0);
        mapHeight = (int)map.getProperties().get("height");
        mapWidth = (int)map.getProperties().get("width");
    }


    public GameMapRenderer(TiledMap map, float unitScale) {
        super(map, unitScale);
        spawnPoint = new Vector3(250, 250, 0);
        mapHeight = (int)map.getProperties().get("height");
        mapWidth = (int)map.getProperties().get("width");
    }

    public GameMapRenderer(TiledMap map, float unitScale, Vector3 spawnPoint) {
        super(map, unitScale);
        this.spawnPoint = spawnPoint;
        mapHeight = (int)map.getProperties().get("height");
        mapWidth = (int)map.getProperties().get("width");
    }

    public GameMapRenderer(TiledMap map, float unitScale, Batch batch) {
        super(map, unitScale, batch);
        spawnPoint = new Vector3(250, 250, 0);
        mapHeight = (int)map.getProperties().get("height");
        mapWidth = (int)map.getProperties().get("width");
    }

    public GameMapRenderer(TiledMap map, float unitScale, Batch batch, Vector3 spawnPoint) {
        super(map, unitScale, batch);
        this.spawnPoint = spawnPoint;
        mapHeight = (int)map.getProperties().get("height");
        mapWidth = (int)map.getProperties().get("width");
    }

    public Vector3 getSpawnPoint() {
        return spawnPoint;
    }

    public void setSpawnPoint(Vector3 spawnPoint) {
        this.spawnPoint = spawnPoint;
    }
    private void updateHeightAndWidth() {
        mapHeight = (int)map.getProperties().get("height");
        mapWidth = (int)map.getProperties().get("width");
    }
    public int getMapHeight() {
        return mapHeight;
    }
    public int getMapWidth() {
        return mapWidth;
    }
}

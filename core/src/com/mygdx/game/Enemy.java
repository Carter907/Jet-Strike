package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public class Enemy extends Ship {
    private final Color forceFieldColor = Color.RED;
    private final Texture sprite = new Texture(Gdx.files.internal("ship.png"));
    public Enemy() {
        super(0,0, new Texture(Gdx.files.internal("ship.png")));
        setForceFieldColor(forceFieldColor);
    }

    public Enemy(float x, float y) {
        super(x,y, new Texture(Gdx.files.internal("ship.png")));
        setForceFieldColor(forceFieldColor);
    }
}



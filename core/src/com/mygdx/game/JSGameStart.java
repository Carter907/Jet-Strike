package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import screens.*;

public class JSGameStart extends Game {

    private AssetManager assetManager;
    private TextureAtlas textureAtlas;
    @Override
    public void create() {

        textureAtlas = new TextureAtlas(Gdx.files.internal("pack/textures.atlas"));
        assetManager = new AssetManager();
        this.setScreen(new TitleScreen(this));
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    public void setTextureAtlas(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
    }
}

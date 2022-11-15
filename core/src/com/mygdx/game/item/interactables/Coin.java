package com.mygdx.game.item.interactables;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import screens.GameScreen;

public class Coin extends Interactable {


    public enum CoinType {

        NORMAL_COIN(GameScreen.game.getGameStart().getTextureAtlas().findRegion("sprites/coin"), 1);

        private final TextureRegion sprite;
        private final int value;
        CoinType(TextureRegion sprite,int value) {
            this.sprite = sprite;
            this.value = value;
        }

        public TextureRegion getSprite() {
            return this.sprite;
        }

        public int getValue() {
            return value;
        }
    }

    private int value;
    private CoinType type;

    public Coin(CoinType type) {
        super(true, type.getSprite());
        this.type = type;
        this.value = type.getValue();
    }
    public Coin(CoinType type, boolean interactive) {
        super(interactive, type.getSprite());
        this.type = type;
        this.value = type.getValue();
    }
    public Coin(float x, float y, CoinType type) {
        super(true, type.getSprite());
        this.setBounds(x, y, super.sprite.getRegionWidth(), super.sprite.getRegionHeight());
        this.type = type;
        this.value = type.getValue();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {


        batch.draw(super.sprite, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }
    public static void addCoin(float x, float y, CoinType type) {
        GameScreen.game.getDisplay().getActors().add(new Coin(x,y,type));
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public CoinType getType() {
        return type;
    }

    public void setType(CoinType type) {
        this.type = type;
    }
}

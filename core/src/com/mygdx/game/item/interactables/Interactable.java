package com.mygdx.game.item.interactables;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public abstract class Interactable extends Actor {

    protected TextureRegion sprite;

    protected Interactable(boolean interactive, TextureRegion sprite) {
        super.setTouchable(interactive ? Touchable.enabled : Touchable.disabled);
        this.sprite = sprite;
    }

}

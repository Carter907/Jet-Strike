package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class GameAnimation extends Animation<TextureRegion> {

    private final float animationStart;
    private float animationTime;
    private float frameCount;


    public GameAnimation(float frameDuration, Array<? extends TextureRegion> keyFrames) {
        super(frameDuration, keyFrames);
        animationStart = 0;
        animationTime = animationStart;
        frameCount = keyFrames.size;
    }

    public GameAnimation(float frameDuration, Array<? extends TextureRegion> keyFrames, PlayMode playMode) {
        super(frameDuration, keyFrames, playMode);
        animationStart = 0;
        animationTime = animationStart;
        frameCount = keyFrames.size;

    }

    public GameAnimation(float frameDuration, TextureRegion... keyFrames) {
        super(frameDuration, keyFrames);
        animationStart = 0;
        animationTime = animationStart;
        frameCount = keyFrames.length;
    }

    public float getAnimationTime() {
        return animationTime;
    }

    public void setAnimationTime(float animationTime) {
        this.animationTime = animationTime;
    }

    public float getFrameCount() {
        return frameCount;
    }

    public void setFrameCount(float frameCount) {
        this.frameCount = frameCount;
    }

    public float getAnimationStart() {
        return animationStart;
    }
}

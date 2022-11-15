package com.mygdx.game;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class PackTextures {

    public static void main(String[] args) {

        TexturePacker.process("assets/images", "assets/pack", "textures");
    }
}

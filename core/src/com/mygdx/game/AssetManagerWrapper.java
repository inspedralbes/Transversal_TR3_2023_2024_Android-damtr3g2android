package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetManagerWrapper {

    public static Texture knightSheet;
    public static TextureRegion knight;

    public static void load() {
        knightSheet = new Texture(Gdx.files.internal("Fire_Warrior-Sheet.png"));
        knight = new TextureRegion(knightSheet, 0, 0, 30, 30);
    }
}

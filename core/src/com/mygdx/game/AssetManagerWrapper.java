package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetManagerWrapper {

    //PersonajePrincipal
    public static Texture knightSheet;
    public static TextureRegion knight;

    //Enemigo Rana
    public static Texture ranaWalkSheet;
    public static TextureRegion ranaWalk;
    public static Texture ranaAttackSheet;
    public static Texture ranaDamageSheet;
    public static Texture ranaDeathSheet;




    public static void load() {
        knightSheet = new Texture(Gdx.files.internal("Fire_Warrior-Sheet.png"));
        knight = new TextureRegion(knightSheet, 0, 0, 30, 30);


        ranaWalkSheet = new Texture(Gdx.files.internal("Toad_Walk.png"));
        ranaWalk = new TextureRegion(ranaWalkSheet, 0, 0, 30, 30);

        ranaAttackSheet = new Texture(Gdx.files.internal("Toad_Attack.png"));
        ranaDamageSheet = new Texture(Gdx.files.internal("Toad_Damage.png"));
        ranaDeathSheet = new Texture(Gdx.files.internal("Toad_Death.png"));



    }
}

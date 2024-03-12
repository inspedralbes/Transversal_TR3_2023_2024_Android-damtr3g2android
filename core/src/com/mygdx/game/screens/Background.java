package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {
    private Texture texture;

    public Background(String imagePath) {
        texture = new Texture(Gdx.files.internal(imagePath));
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void dispose() {
        texture.dispose();
    }
}

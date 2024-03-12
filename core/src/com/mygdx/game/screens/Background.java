package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {
    private Texture staticBackground;
    private Texture movingBackground;
    private float scrollSpeed = 10f; // Velocidad de desplazamiento

    private float scrollX = 0;

    public Background() {
        staticBackground = new Texture(Gdx.files.internal("BackgroundTree/background1.png"));
        movingBackground = new Texture(Gdx.files.internal("BackgroundTree/background2.png"));
    }

    public void update(float delta) {
        // Actualiza la posición del fondo en movimiento
        scrollX -= scrollSpeed * delta;
        // Si el fondo se ha movido completamente fuera de la pantalla, resetea su posición
        if (scrollX <= -Gdx.graphics.getWidth()) {
            scrollX = 0;
        }
    }

    public void draw(SpriteBatch batch) {
        // Dibuja el fondo estático
        batch.draw(staticBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Dibuja el fondo en movimiento
        batch.draw(movingBackground, scrollX, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Dibuja otra copia del fondo en movimiento para simular el desplazamiento continuo
        batch.draw(movingBackground, scrollX + Gdx.graphics.getWidth(), 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void dispose() {
        staticBackground.dispose();
        movingBackground.dispose();
    }
}

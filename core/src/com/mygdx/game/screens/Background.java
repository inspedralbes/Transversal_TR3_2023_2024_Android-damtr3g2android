package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Background {
    private Texture staticBackground;
    private Texture movingBackground2;
    private Texture movingBackground3;
    private float scrollSpeed2 = 75f; // Velocidad de desplazamiento de background2
    private float scrollSpeed3 = 150f; // Velocidad de desplazamiento de background3

    private float scrollX2 = 0;
    private float scrollX3 = 0;

    public Background() {
        staticBackground = new Texture(Gdx.files.internal("BackgroundTree/background1.png"));
        movingBackground2 = new Texture(Gdx.files.internal("BackgroundTree/background2.png"));
        movingBackground3 = new Texture(Gdx.files.internal("BackgroundTree/background3.png"));
    }

    public void update(float delta) {
        // Actualiza la posición del fondo en movimiento 2
        scrollX2 -= scrollSpeed2 * delta;
        // Si el fondo se ha movido completamente fuera de la pantalla, resetea su posición
        if (scrollX2 <= -Gdx.graphics.getWidth()) {
            scrollX2 = 0;
        }

        // Actualiza la posición del fondo en movimiento 3
        scrollX3 -= scrollSpeed3 * delta;
        // Si el fondo se ha movido completamente fuera de la pantalla, resetea su posición
        if (scrollX3 <= -Gdx.graphics.getWidth()) {
            scrollX3 = 0;
        }
    }

    public void draw(SpriteBatch batch) {
        // Dibuja el fondo estático
        batch.draw(staticBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Dibuja el fondo en movimiento 2
        batch.draw(movingBackground2, scrollX2, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Dibuja otra copia del fondo en movimiento 2 para simular el desplazamiento continuo
        batch.draw(movingBackground2, scrollX2 + Gdx.graphics.getWidth(), 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Dibuja el fondo en movimiento 3
        batch.draw(movingBackground3, scrollX3, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // Dibuja otra copia del fondo en movimiento 3 para simular el desplazamiento continuo
        batch.draw(movingBackground3, scrollX3 + Gdx.graphics.getWidth(), 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void dispose() {
        staticBackground.dispose();
        movingBackground2.dispose();
        movingBackground3.dispose();
    }
}

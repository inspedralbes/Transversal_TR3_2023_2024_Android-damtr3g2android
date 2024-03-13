package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Rana {
    private TextureRegion[] frames;
    private int currentFrameIndex;
    private Vector2 position;
    private Rectangle bounds;

    private static final int FRAME_WIDTH = 80;
    private static final int FRAME_HEIGHT = 64;
    private static final int FRAMES_IN_COLUMN = 4;
    private static final float FRAME_DURATION = 0.3f;
    private static final float SPEED = 100f; // Velocidad de movimiento en píxeles por segundo

    private float stateTime;

    public Rana(Vector2 position) {
        Texture spriteSheet = new Texture(Gdx.files.internal("Toad_Walk.png"));
        frames = new TextureRegion[FRAMES_IN_COLUMN];

        for (int i = 0; i < FRAMES_IN_COLUMN; i++) {
            frames[i] = new TextureRegion(spriteSheet, i * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
        }

        // Ajustar la posición inicial para que aparezca en la derecha de la pantalla
        this.position = new Vector2(Gdx.graphics.getWidth(), position.y);
        this.bounds = new Rectangle(this.position.x, this.position.y, FRAME_WIDTH, FRAME_HEIGHT);
    }

    public void update(float delta) {
        stateTime += delta;

        // Mover la rana hacia la izquierda
        position.x -= SPEED * delta;

        // Comprobar si la rana ha salido de la pantalla
        if (position.x + FRAME_WIDTH < 0) {
            // Reiniciar la posición de la rana a la derecha de la pantalla
            position.x = Gdx.graphics.getWidth();
        }

        // Actualizar los fotogramas de animación
        if (stateTime >= FRAME_DURATION) {
            currentFrameIndex = (currentFrameIndex - 1 + frames.length) % frames.length; // Cambio aquí
            stateTime = 0;
        }
    }


    public void render(SpriteBatch batch) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Calcular la escala en función del ancho de la pantalla
        float scale = screenWidth / 800f; // Suponiendo que el tamaño base de la bruja sea para una pantalla de ancho 800
        scale *= 2.7f; // Aumentar la escala en un 50%

        // Dibujar el fotograma actual con la escala calculada
        batch.draw(frames[currentFrameIndex], position.x, position.y, FRAME_WIDTH * scale, FRAME_HEIGHT * scale);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void dispose() {
        for (TextureRegion frame : frames) {
            frame.getTexture().dispose();
        }
    }
}

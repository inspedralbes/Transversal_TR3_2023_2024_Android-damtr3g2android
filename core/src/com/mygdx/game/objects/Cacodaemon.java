package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Cacodaemon {
    private TextureRegion[] frames;
    private int currentFrameIndex;
    private int totalFrames;
    private Vector2 position;
    private Rectangle bounds;

    private static final int SPRITESHEET_COLS = 8;
    private static final int SPRITESHEET_ROWS = 4;
    private static final float FRAME_DURATION = 0.1f;

    private static final int FRAME_WIDTH = 64;
    private static final int FRAME_HEIGHT = 64;

    private float stateTime;

    public Cacodaemon(Vector2 position, int fila, int columna) {
        Texture spriteSheet = new Texture(Gdx.files.internal("Cacodaemon/Cacodaemon.png"));

        frames = new TextureRegion[columna];
        int frameWidth = spriteSheet.getWidth() / SPRITESHEET_COLS;
        int frameHeight = spriteSheet.getHeight() / SPRITESHEET_ROWS;

        int rowIndex = fila; // Índice de la tercera fila (empezando desde 0)

        // Modificar el bucle para que comience desde la última columna hacia la primera
        for (int j = columna - 1; j >= 0; j--) {
            frames[columna - 1 - j] = new TextureRegion(spriteSheet, j * frameWidth, rowIndex * frameHeight, frameWidth, frameHeight);
        }

        this.position = position;
        this.bounds = new Rectangle(position.x, position.y, frameWidth, frameHeight);
        this.totalFrames = columna;
    }


    public void update(float delta) {
        stateTime += delta;

        if (stateTime >= FRAME_DURATION) {
            currentFrameIndex = (currentFrameIndex + 1) % totalFrames;
            stateTime = 0;
        }
    }

    public void render(SpriteBatch batch) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Calcular la escala en función del ancho de la pantalla
        float scale = screenWidth / 2000f; // Suponiendo que el tamaño base de la bruja sea para una pantalla de ancho 800
        scale *= 5f; // Aumentar la escala en un 50%

        // Calcular la posición X para que el sprite comience en el borde derecho de la pantalla
        float spriteX = screenWidth - (position.x + FRAME_WIDTH * scale);

        // Dibujar el fotograma actual con la escala calculada y la posición ajustada
        batch.draw(frames[currentFrameIndex], spriteX, position.y, FRAME_WIDTH * scale, FRAME_HEIGHT * scale);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isAnimationFinished() {
        return currentFrameIndex == totalFrames - 1;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
        bounds.setPosition(position);
    }

    public void resetAnimation() {
        currentFrameIndex = 0; // Reiniciar la animación al primer cuadro
        stateTime = 0; // Reiniciar el tiempo de animación
    }

    public void dispose() {
        frames[0].getTexture().dispose();
    }
}

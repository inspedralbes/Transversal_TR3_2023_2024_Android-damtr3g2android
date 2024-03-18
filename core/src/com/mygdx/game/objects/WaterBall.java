package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class WaterBall {

    private TextureRegion[] frames;
    private static final int FRAME_WIDTH = 64;
    private static final int FRAME_HEIGHT = 64;
    private static final int FRAMES_IN_COLUMN = 5;
    private static final int TOTAL_FRAMES = 20; // 5 filas x 4 columnas
    private static final float FRAME_DURATION = 0.1f;

    private float stateTime = 0;
    private int currentFrameIndex = 0;
    private Vector2 position;
    public WaterBall(Vector2 position) {
        this.position = position;
        Texture spriteSheet = new Texture(Gdx.files.internal("WaterSpell/waterballspell.png"));
        TextureRegion[][] tmpFrames = TextureRegion.split(spriteSheet, FRAME_WIDTH, FRAME_HEIGHT);
        frames = new TextureRegion[TOTAL_FRAMES];
        int index = 0;
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 4; col++) {
                if (row != 4) { // Excluir la última fila
                    frames[index] = tmpFrames[row][col];
                    index++;
                }
            }
        }
    }

    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = getCurrentFrame();
        if (currentFrame != null) {
            batch.draw(currentFrame, position.x, position.y);
        }
    }

    private TextureRegion getCurrentFrame() {
        // Verificar si currentFrameIndex está dentro de los límites del arreglo frames
        if (currentFrameIndex >= 0 && currentFrameIndex < frames.length) {
            // Calcular la fila y columna actual
            int row = currentFrameIndex / FRAMES_IN_COLUMN;
            int col = currentFrameIndex % FRAMES_IN_COLUMN;
            // Calcular el índice actual del frame en el arreglo
            int frameIndex = row * FRAMES_IN_COLUMN + col;
            return frames[frameIndex];
        } else {
            // Si currentFrameIndex está fuera de los límites, devolver null
            return null;
        }
    }

    public void update(float deltaTime) {
        // Si ya ha pasado el tiempo necesario para reproducir la primera fila una vez
        if (stateTime > (FRAME_DURATION * FRAMES_IN_COLUMN)) {
            // Calcula el tiempo restante para actualizar el índice del fotograma actual
            float remainingTime = stateTime - (FRAME_DURATION * FRAMES_IN_COLUMN);
            // Calcula el índice del fotograma actual basado en el tiempo restante
            currentFrameIndex = (int) (remainingTime / FRAME_DURATION) % (15 - FRAMES_IN_COLUMN) + FRAMES_IN_COLUMN;
        } else {
            // Si todavía está reproduciendo la primera fila, actualiza el índice normalmente
            currentFrameIndex = (int) (stateTime / FRAME_DURATION);
        }

        // Incrementa stateTime con deltaTime
        stateTime += deltaTime;
    }



}

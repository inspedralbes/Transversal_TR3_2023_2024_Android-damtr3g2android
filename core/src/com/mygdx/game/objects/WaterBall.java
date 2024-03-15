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

    public TextureRegion getCurrentFrame() {
        // Iniciar desde la segunda fila
        int row = (currentFrameIndex / FRAMES_IN_COLUMN) + 1;
        // Repetir el movimiento de la fila 2-3-4
        if (row > 4) {
            row = 2;
            currentFrameIndex = row * FRAMES_IN_COLUMN;
        }
        // Calcular el índice actual del frame
        currentFrameIndex = row * FRAMES_IN_COLUMN + (currentFrameIndex % FRAMES_IN_COLUMN);
        return frames[currentFrameIndex];
    }
    public void render(SpriteBatch batch) {
        batch.draw(getCurrentFrame(), position.x, position.y);
    }
    public void update(float deltaTime) {
        stateTime += deltaTime;
        // Aquí puedes implementar la lógica de actualización de la animación, si es necesario.
        // Por ejemplo, cambiar el frame actual basado en el tiempo transcurrido.
    }

}

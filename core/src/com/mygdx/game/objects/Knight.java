package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Knight {
    private TextureRegion[] frames;
    private int currentFrameIndex;
    private int totalFrames;
    private Vector2 position;
    private Rectangle bounds;

    private static final int SPRITESHEET_COLS = 16;
    private static final int SPRITESHEET_ROWS = 25;
    private static final float FRAME_DURATION = 0.1f;

    private static final int FRAME_WIDTH = 47;
    private static final int FRAME_HEIGHT = 27;

    private float stateTime;

    public Knight(Vector2 position, int fila, int columna) {
        Texture spriteSheet = new Texture(Gdx.files.internal("Fire_Warrior-Sheet.png"));

        frames = new TextureRegion[columna];
        int frameWidth = spriteSheet.getWidth() / SPRITESHEET_COLS;
        int frameHeight = spriteSheet.getHeight() / SPRITESHEET_ROWS;

        int rowIndex = fila; // Índice de la tercera fila (empezando desde 0)

        for (int j = 0; j < columna; j++) {
            frames[j] = new TextureRegion(spriteSheet, j * frameWidth, rowIndex * frameHeight, frameWidth, frameHeight);
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

    public void updateSalto(float delta) {
        stateTime += delta;

        if (stateTime >= 0.15f) {
            currentFrameIndex = (currentFrameIndex + 1) % totalFrames;
            stateTime = 0;
        }
    }

    public void updateAgachar(float delta) {
        stateTime += delta;

        if (stateTime >= 0.40f) {
            currentFrameIndex = (currentFrameIndex + 1) % totalFrames;
            stateTime = 0;
        }
    }

    public void render(SpriteBatch batch) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Calcular la escala en función del ancho de la pantalla
        float scale = screenWidth / 1080f; // Suponiendo que el tamaño base de la bruja sea para una pantalla de ancho 800
        scale *= 7f; // Aumentar la escala en un 50%

        // Dibujar el fotograma actual con la escala calculada
        batch.draw(frames[currentFrameIndex], position.x, position.y, FRAME_WIDTH * scale, FRAME_HEIGHT * scale);
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

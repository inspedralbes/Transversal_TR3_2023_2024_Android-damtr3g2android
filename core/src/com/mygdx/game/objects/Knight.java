package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Knight {
    private TextureRegion[] framesCaminar;
    private int currentFrameIndex;
    private int totalFrames;
    private Vector2 position;
    private Rectangle bounds;

    private static final int SPRITESHEET_COLS = 16;
    private static final int SPRITESHEET_ROWS = 25;
    private static final float FRAME_DURATION = 0.1f;

    private float stateTime;

    public Knight(Vector2 position, int fila, int columna) {
        Texture spriteSheet = new Texture(Gdx.files.internal("Fire_Warrior-Sheet.png"));

        framesCaminar = new TextureRegion[columna];
        int frameWidth = spriteSheet.getWidth() / SPRITESHEET_COLS;
        int frameHeight = spriteSheet.getHeight() / SPRITESHEET_ROWS;

        int rowIndex = fila; // Índice de la tercera fila (empezando desde 0)

        for (int j = 0; j < columna; j++) {
            framesCaminar[j] = new TextureRegion(spriteSheet, j * frameWidth, rowIndex * frameHeight, frameWidth, frameHeight);
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
        batch.draw(framesCaminar[currentFrameIndex], position.x, position.y);
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
        framesCaminar[0].getTexture().dispose();
    }
}

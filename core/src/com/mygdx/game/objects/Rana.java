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

    private static final int FRAME_WIDTH = 64;
    private static final int FRAME_HEIGHT = 80;
    private static final int FRAMES_IN_COLUMN = 4; // Cambiado a 4
    private static final float FRAME_DURATION = 0.1f;

    private float stateTime;

    public Rana(Vector2 position) {
        Texture spriteSheet = new Texture(Gdx.files.internal("Toad_Walk.png"));
        frames = new TextureRegion[FRAMES_IN_COLUMN];

        for (int i = 0; i < FRAMES_IN_COLUMN; i++) {
            frames[i] = new TextureRegion(spriteSheet, 0, i * FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT);
        }

        // Ajustar la posición inicial para que aparezca en la derecha de la pantalla
        this.position = new Vector2(Gdx.graphics.getWidth() - FRAME_WIDTH, position.y);
        this.bounds = new Rectangle(this.position.x, this.position.y, FRAME_WIDTH, FRAME_HEIGHT);
    }

    public void update(float delta) {
        stateTime += delta;
        if (stateTime >= FRAME_DURATION) {
            currentFrameIndex = (currentFrameIndex + 1) % frames.length;
            stateTime = 0;
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(frames[currentFrameIndex], position.x, position.y);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
        bounds.setPosition(position);
    }

    public void dispose() {
        for (TextureRegion frame : frames) {
            frame.getTexture().dispose();
        }
    }
}

package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Witch {
    private TextureRegion[] frames;
    private int currentFrameIndex;
    private Vector2 position;
    private Rectangle bounds;

    private static final int FRAME_WIDTH = 36;
    private static final int FRAME_HEIGHT = 48;
    private static final int FRAMES_IN_COLUMN = 6;
    private static final float FRAME_DURATION = 0.1f; // Tiempo entre cambios de fotograma

    private float stateTime;

    public Witch(Vector2 position) {
        // Cargar el spritesheet desde el archivo interno witch.png
        Texture spriteSheet = new Texture(Gdx.files.internal("Witch/B_witch_idle.png"));

        // Crear un array de regiones de textura para almacenar los cuadros individuales
        frames = new TextureRegion[FRAMES_IN_COLUMN];

        // Dividir el spritesheet en regiones de textura individuales
        for (int i = 0; i < FRAMES_IN_COLUMN; i++) {
            frames[i] = new TextureRegion(spriteSheet, 0, i * FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT);
        }

        this.position = position;
        this.bounds = new Rectangle(position.x, position.y, FRAME_WIDTH, FRAME_HEIGHT);
    }

    public void update(float delta) {
        // Actualizar el tiempo de estado para controlar la animación
        stateTime += delta;

        // Cambiar de fotograma cuando haya pasado el tiempo de duración de un fotograma
        if (stateTime >= FRAME_DURATION) {
            currentFrameIndex = (currentFrameIndex + 1) % frames.length;
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

    // Este método te permite cambiar la posición del Witch.
    public void setPosition(Vector2 position) {
        this.position = position;
        bounds.setPosition(position);
    }

    // Método para liberar recursos cuando ya no se necesiten.
    public void dispose() {
        // Liberar la textura del spritesheet
        frames[0].getTexture().dispose();
    }
}

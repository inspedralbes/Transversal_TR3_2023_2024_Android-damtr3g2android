package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class DemonFly {
    private TextureRegion[] framesIdle;
    private TextureRegion[] framesAttack;
    private Vector2 position;
    private Rectangle bounds;

    private static final int FRAME_WIDTH_IDLE = 960 / 6; // Ancho de cada imagen individual en el spritesheet de idle
    private static final int FRAME_WIDTH_ATTACK = 2640 / 11; // Ancho de cada imagen individual en el spritesheet de ataque
    private static final int FRAME_HEIGHT = 192; // Altura del spritesheet
    private static final int FRAMES_IN_ROW_IDLE = 6; // Número de imágenes en una fila en el spritesheet de idle
    private static final int FRAMES_IN_ROW_ATTACK = 11; // Número de imágenes en una fila en el spritesheet de ataque
    private static final float SWITCH_ANIMATION_DURATION = 10f; // Cambiar la animación cada 10 segundos

    private float stateTime;
    private boolean isAttacking;

    public DemonFly(Vector2 position) {
        // Cargar los spritesheets desde los archivos internos
        Texture spriteSheetIdle = new Texture(Gdx.files.internal("DemonFly/demon-idle.png"));
        Texture spriteSheetAttack = new Texture(Gdx.files.internal("DemonFly/demon-attack.png"));

        // Crear arrays de regiones de textura para almacenar los cuadros individuales de cada spritesheet
        framesIdle = new TextureRegion[FRAMES_IN_ROW_IDLE];
        framesAttack = new TextureRegion[FRAMES_IN_ROW_ATTACK];

        // Dividir los spritesheets en regiones de textura individuales
        for (int i = 0; i < FRAMES_IN_ROW_IDLE; i++) {
            framesIdle[i] = new TextureRegion(spriteSheetIdle, i * FRAME_WIDTH_IDLE, 0, FRAME_WIDTH_IDLE, FRAME_HEIGHT);
        }
        for (int i = 0; i < FRAMES_IN_ROW_ATTACK; i++) {
            framesAttack[i] = new TextureRegion(spriteSheetAttack, i * FRAME_WIDTH_ATTACK, 0, FRAME_WIDTH_ATTACK, FRAME_HEIGHT);
        }

        this.position = position;
        this.bounds = new Rectangle(position.x, position.y, FRAME_WIDTH_IDLE, FRAME_HEIGHT);
        this.isAttacking = false;
    }

    public void update(float delta) {
        // Actualizar el tiempo de estado para controlar la animación
        stateTime += delta;

        // Cambiar entre los spritesheets cada 10 segundos
        if (stateTime >= SWITCH_ANIMATION_DURATION) {
            isAttacking = !isAttacking;
            stateTime = 0;
        }
    }

    public void render(SpriteBatch batch) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Calcular la escala en función del ancho de la pantalla
        float scale = screenWidth / 800f; // Suponiendo que el tamaño base del DemonFly sea para una pantalla de ancho 800
        scale *= 4f; // Aumentar la escala en un 50%

        // Dibujar el fotograma actual con la escala calculada
        if (isAttacking) {
            batch.draw(framesAttack[(int) (stateTime / SWITCH_ANIMATION_DURATION * FRAMES_IN_ROW_ATTACK)], position.x, position.y, FRAME_WIDTH_ATTACK * scale, FRAME_HEIGHT * scale);
        } else {
            batch.draw(framesIdle[(int) (stateTime / SWITCH_ANIMATION_DURATION * FRAMES_IN_ROW_IDLE)], position.x, position.y, FRAME_WIDTH_IDLE * scale, FRAME_HEIGHT * scale);
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    // Este método te permite cambiar la posición del DemonFly.
    public void setPosition(Vector2 position) {
        this.position = position;
        bounds.setPosition(position);
    }

    // Método para liberar recursos cuando ya no se necesiten.
    public void dispose() {
        // Liberar las texturas de los spritesheets
        for (TextureRegion frame : framesIdle) {
            frame.getTexture().dispose();
        }
        for (TextureRegion frame : framesAttack) {
            frame.getTexture().dispose();
        }
    }
}

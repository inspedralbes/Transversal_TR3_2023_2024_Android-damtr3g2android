package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class DemonFly {
    private TextureRegion[] idleFrames; // Spritesheet para la animación de reposo
    private TextureRegion[] attackFrames; // Spritesheet para la animación de ataque
    private int currentFrameIndex;
    private Vector2 position;
    private Rectangle bounds;

    private static final int FRAME_WIDTH_IDLE = 960 / 6; // Ancho de cada imagen individual de la animación de reposo
    private static final int FRAME_WIDTH_ATTACK = 2604 / 11; // Ancho de cada imagen individual de la animación de ataque
    private static final int FRAME_HEIGHT = 192; // Altura del spritesheet (tanto para la animación de reposo como la de ataque)
    private static final int FRAMES_IN_ROW_IDLE = 6; // Número de imágenes en una fila para la animación de reposo
    private static final int FRAMES_IN_ROW_ATTACK = 11; // Número de imágenes en una fila para la animación de ataque
    private static final float FRAME_DURATION = 0.1f; // Tiempo entre cambios de fotograma

    private float stateTime;
    private boolean isAttacking;
    private float attackTimer;

    public DemonFly(Vector2 position) {
        // Cargar los spritesheets desde los archivos internos demon-idle.png y demon-attack.png
        Texture idleSpriteSheet = new Texture(Gdx.files.internal("DemonFly/demon-idle.png"));
        Texture attackSpriteSheet = new Texture(Gdx.files.internal("DemonFly/demon-attack.png"));

        // Crear arrays de regiones de textura para almacenar los cuadros individuales de cada animación
        idleFrames = new TextureRegion[FRAMES_IN_ROW_IDLE];
        attackFrames = new TextureRegion[FRAMES_IN_ROW_ATTACK];

        // Dividir los spritesheets en regiones de textura individuales
        for (int i = 0; i < FRAMES_IN_ROW_IDLE; i++) {
            idleFrames[i] = new TextureRegion(idleSpriteSheet, i * FRAME_WIDTH_IDLE, 0, FRAME_WIDTH_IDLE, FRAME_HEIGHT);
        }

        for (int i = 0; i < FRAMES_IN_ROW_ATTACK; i++) {
            attackFrames[i] = new TextureRegion(attackSpriteSheet, i * FRAME_WIDTH_ATTACK, 0, FRAME_WIDTH_ATTACK, FRAME_HEIGHT);
        }

        // Ajustar la posición del demonio más abajo
        this.position = new Vector2(position.x, 0); // Ajustar la posición Y aquí
        this.bounds = new Rectangle(position.x, position.y, FRAME_WIDTH_IDLE, FRAME_HEIGHT);
        this.isAttacking = false;
        this.attackTimer = 0f;
    }


    public void update(float delta) {
        // Actualizar el tiempo de estado para controlar la animación
        stateTime += delta;

        // Incrementar el temporizador de ataque
        attackTimer += delta;

        // Si han pasado 10 segundos, iniciar el ataque y reiniciar el temporizador
        if (attackTimer >= 5f) {
            startAttack();
            attackTimer = 0f;
        }

        // Cambiar de fotograma cuando haya pasado el tiempo de duración de un fotograma
        if (isAttacking) {
            // Cambiar de fotograma de la animación de ataque
            currentFrameIndex = (int) (stateTime / FRAME_DURATION) % attackFrames.length;
            if (stateTime >= FRAME_DURATION * attackFrames.length) {
                stateTime = 0; // Reiniciar el tiempo de estado después de completar la animación de ataque
                isAttacking = false; // Cambiar al estado de reposo después de completar la animación de ataque
            }
        } else if (stateTime >= FRAME_DURATION) {
            // Cambiar de fotograma de la animación de reposo
            currentFrameIndex = (currentFrameIndex + 1) % idleFrames.length;
            stateTime = 0;
        }
    }



    public void render(SpriteBatch batch) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Calcular la escala en función del ancho de la pantalla
        float scale = screenWidth / 800f; // Suponiendo que el tamaño base del DemonFly sea para una pantalla de ancho 800

        // Dibujar el fotograma actual con la escala calculada
        if (isAttacking) {
            // Mover ligeramente a la izquierda durante el ataque
            float attackPositionX = position.x - 700; // Ajusta este valor según sea necesario
            float attackPositionY = position.y + 100;
            float attackScale = scale * 2.5f; // Ajusta el factor de escala según sea necesario
            batch.draw(attackFrames[currentFrameIndex], attackPositionX, attackPositionY, FRAME_WIDTH_ATTACK * attackScale, FRAME_HEIGHT * attackScale);
        } else {
            float idleScale = scale * 2.5f; // Ajusta el factor de escala según sea necesario
            batch.draw(idleFrames[currentFrameIndex], position.x, position.y, FRAME_WIDTH_IDLE * idleScale, FRAME_HEIGHT * idleScale);
        }
    }



    public void startAttack() {
        isAttacking = true;
        currentFrameIndex = 0; // Reiniciar la animación de ataque al principio
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
        for (TextureRegion frame : idleFrames) {
            frame.getTexture().dispose();
        }
        for (TextureRegion frame : attackFrames) {
            frame.getTexture().dispose();
        }
    }
}

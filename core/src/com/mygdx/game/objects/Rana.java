package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Rana {
    private TextureRegion[] walkFrames;
    private TextureRegion[] attackFrames;

    private int currentFrameIndex;
    private Vector2 position;
    private Rectangle bounds;

    private static final int FRAME_WIDTH = 80;
    private static final int FRAME_HEIGHT = 64;
    private static final int FRAMES_IN_ROW_WALK = 4;
    private static final int FRAMES_IN_ROW_ATTACK = 8;
    private static final float FRAME_DURATION = 0.3f;
    private static final float SPEED = 180f; // Velocidad de movimiento en píxeles por segundo

    private float stateTime;
    private boolean isAttacking;
    private float attackTimer;

    public Rana(Vector2 position) {
        // Cargar los spritesheets
        Texture WalkSpriteSheet = new Texture(Gdx.files.internal("Rana/Toad_Walk.png"));
        Texture AttackSpriteSheet = new Texture(Gdx.files.internal("Rana/Toad_Attack.png"));

        walkFrames = new TextureRegion[FRAMES_IN_ROW_WALK];
        attackFrames = new TextureRegion[FRAMES_IN_ROW_ATTACK];

        // Cargar frames de caminar
        for (int i = 0; i < FRAMES_IN_ROW_WALK; i++) {
            int index = (FRAMES_IN_ROW_WALK - 1) - i; // Empezar desde el final del sprite sheet
            walkFrames[i] = new TextureRegion(WalkSpriteSheet, index * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
        }

        // Cargar frames de ataque
        for (int i = 0; i < FRAMES_IN_ROW_ATTACK; i++) {
            int index = (FRAMES_IN_ROW_ATTACK - 1) - i; // Empezar desde el final del sprite sheet
            attackFrames[i] = new TextureRegion(AttackSpriteSheet, index * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
        }

        // Ajustar la posición inicial para que aparezca en la derecha de la pantalla
        this.position = new Vector2(Gdx.graphics.getWidth(), position.y);
        this.bounds = new Rectangle(this.position.x, this.position.y, FRAME_WIDTH, FRAME_HEIGHT);
        this.isAttacking = false;
        this.attackTimer = 0f;
    }

    public void update(float delta) {
        stateTime += delta;

        // Mover la rana hacia la izquierda
        position.x -= SPEED * delta;

        // Incrementar el temporizador de ataque
        attackTimer += delta;

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
            currentFrameIndex = (currentFrameIndex + 1) % walkFrames.length;
            stateTime = 0;
        }

        // Comprobar si la rana ha salido de la pantalla
        if (position.x + FRAME_WIDTH < 0) {
            // Reiniciar la posición de la rana a la derecha de la pantalla
            position.x = Gdx.graphics.getWidth();
        }
    }

    public void render(SpriteBatch batch) {
        float screenWidth = Gdx.graphics.getWidth();

        // Calcular la escala en función del ancho de la pantalla
        float scale = screenWidth / 800f; // Suponiendo que el tamaño base de la bruja sea para una pantalla de ancho 800
        scale *= 2.7f; // Aumentar la escala en un 50%

        // Dibujar el fotograma actual con la escala calculada
        if (isAttacking) {
            // Mover ligeramente a la izquierda durante el ataque
            float attackPositionX = position.x - 150; // Ajusta este valor según sea necesario
            batch.draw(attackFrames[currentFrameIndex], position.x, position.y, FRAME_WIDTH * scale, FRAME_HEIGHT * scale);
        } else {
            batch.draw(walkFrames[currentFrameIndex], position.x, position.y, FRAME_WIDTH * scale, FRAME_HEIGHT * scale);
        }
    }

    public void startAttack() {
        isAttacking = true;
        currentFrameIndex = 0; // Reiniciar la animación de ataque al principio
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
        bounds.setPosition(position);
    }

    public void dispose() {
        for (TextureRegion frame : walkFrames) {
            frame.getTexture().dispose();
        }
        for (TextureRegion frame : attackFrames) {
            frame.getTexture().dispose();
        }
    }
}

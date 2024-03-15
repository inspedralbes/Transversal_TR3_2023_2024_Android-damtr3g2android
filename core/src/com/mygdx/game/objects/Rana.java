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
    private TextureRegion[] deathFrames;

    private int currentFrameIndex;
    private Vector2 position;
    private Rectangle bounds;
    private int vida;

    private static final int FRAME_WIDTH = 80;
    private static final int FRAME_HEIGHT = 64;
    private static final int FRAMES_IN_ROW_WALK = 4;
    private static final int FRAMES_IN_ROW_ATTACK = 5;
    private static final int FRAMES_IN_ROW_DEATH = 5;
    private static final float FRAME_DURATION = 0.3f;
    private static final float DESAPARECER_IZQUIERDA = 200f; // Ajustar la cantidad de píxeles antes de desaparecer

    private static final float SPEED = 2000f; // Aumentar la velocidad de movimiento en píxeles por segundo

    private float stateTime;
    private boolean isAttacking;
    private boolean isDead;
    private float attackTimer;

    public Rana(Vector2 position, int vida) {
        // Cargar los spritesheets
        Texture WalkSpriteSheet = new Texture(Gdx.files.internal("Rana/Toad_Walk.png"));
        Texture AttackSpriteSheet = new Texture(Gdx.files.internal("Rana/Toad_Attack.png"));
        Texture DeathSpriteSheet = new Texture(Gdx.files.internal("Rana/Toad_Death.png"));

        walkFrames = new TextureRegion[FRAMES_IN_ROW_WALK];
        attackFrames = new TextureRegion[FRAMES_IN_ROW_ATTACK];
        deathFrames = new TextureRegion[FRAMES_IN_ROW_DEATH];

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

        // Cargar frames de muerte
        for (int i = 0; i < FRAMES_IN_ROW_DEATH; i++) {
            int index = (FRAMES_IN_ROW_DEATH - 1) - i; // Empezar desde el final del sprite sheet
            deathFrames[i] = new TextureRegion(DeathSpriteSheet, index * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
        }

        // Ajustar la posición inicial para que la rana aparezca desde la derecha
        this.position = new Vector2(Gdx.graphics.getWidth(), position.y);
        this.bounds = new Rectangle(this.position.x, this.position.y, FRAME_WIDTH, FRAME_HEIGHT);
        this.vida = vida;
        this.isAttacking = false;
        this.isDead = false;
        this.attackTimer = 0f;
    }

    public void update(float delta) {
        stateTime += delta;

        if (!isDead) {
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
                    position.x -= SPEED * delta; // Ajustar la posición para que la rana vuelva a su posición original
                }
            } else if (stateTime >= FRAME_DURATION) {
                // Cambiar de fotograma de la animación de reposo
                currentFrameIndex = (currentFrameIndex + 1) % walkFrames.length;
                stateTime = 0;
                // Mover la rana hacia la izquierda
                position.x -= SPEED * delta;
            }

            // Comprobar si la rana ha salido completamente de la pantalla
            if (position.x + FRAME_WIDTH < -60) {
                // Reiniciar la posición de la rana a la derecha de la pantalla
                position.x = Gdx.graphics.getWidth();
            }
        } else {
            // Animación de muerte
            currentFrameIndex = (int) (stateTime / FRAME_DURATION) % deathFrames.length;
            if (currentFrameIndex == deathFrames.length - 1) {
                // Si se ha completado la animación de muerte, eliminar la rana
                dispose();
            }
        }
    }

    public void render(SpriteBatch batch) {
        float screenWidth = Gdx.graphics.getWidth();
        float scale = screenWidth / 1080f; // Suponiendo que el tamaño base de la bruja sea para una pantalla de ancho 800
        scale *= 2.7f; // Aumentar la escala en un 50%

        // Dibujar el fotograma actual con la escala calculada
        if (isDead) {
            batch.draw(deathFrames[currentFrameIndex], position.x, position.y, FRAME_WIDTH * scale, FRAME_HEIGHT * scale);
        } else if (isAttacking) {
            // Mover ligeramente a la izquierda durante el ataque
            float attackPositionX = position.x - 150; // Ajusta este valor según sea necesario
            batch.draw(attackFrames[currentFrameIndex], position.x, position.y, FRAME_WIDTH * scale, FRAME_HEIGHT * scale);
        } else {
            batch.draw(walkFrames[currentFrameIndex], position.x, position.y, FRAME_WIDTH * scale, FRAME_HEIGHT * scale);
        }
    }

    public void startAttack() {
        isAttacking = true;
        // Ajustar la posición para que la rana permanezca en su lugar
        position.x -= SPEED * Gdx.graphics.getDeltaTime(); // Ajusta la posición para que la rana se quede en la última posición de caminar
        currentFrameIndex = 0; // Reiniciar la animación de ataque al principio
    }

    public void receiveDamage(int damage) {
        vida -= damage;
        if (vida <= 0) {
            isDead = true;
            stateTime = 0; // Reiniciar el tiempo para la animación de muerte
            currentFrameIndex = 0; // Reiniciar el índice del fotograma para la animación de muerte
        }
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
        for (TextureRegion frame : deathFrames) {
            frame.getTexture().dispose();
        }
    }

    // Métodos getter y setter para la vida
    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }
    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, FRAME_WIDTH, FRAME_WIDTH);
    }
}

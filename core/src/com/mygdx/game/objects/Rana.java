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

    private boolean isDisposed;

    private static final int FRAME_WIDTH = 80;
    private static final int FRAME_HEIGHT = 64;
    private static final int FRAMES_IN_ROW_WALK = 4;
    private static final int FRAMES_IN_ROW_ATTACK = 5;
    private static final int FRAMES_IN_ROW_DEATH = 5;
    private static final float FRAME_DURATION = 0.3f;
    private static final float DESAPARECER_IZQUIERDA = 200f;

    private static final float SPEED = 2000f;

    private float stateTime;
    private boolean isAttacking;
    private boolean isDead;
    private float attackTimer;
    private int damageDealt;

    public Rana(Vector2 position, int vida) {
        Texture WalkSpriteSheet = new Texture(Gdx.files.internal("Rana/Toad_Walk.png"));
        Texture AttackSpriteSheet = new Texture(Gdx.files.internal("Rana/Toad_Attack.png"));
        Texture DeathSpriteSheet = new Texture(Gdx.files.internal("Rana/Toad_Death.png"));

        walkFrames = new TextureRegion[FRAMES_IN_ROW_WALK];
        attackFrames = new TextureRegion[FRAMES_IN_ROW_ATTACK];
        deathFrames = new TextureRegion[FRAMES_IN_ROW_DEATH];

        for (int i = 0; i < FRAMES_IN_ROW_WALK; i++) {
            int index = (FRAMES_IN_ROW_WALK - 1) - i;
            walkFrames[i] = new TextureRegion(WalkSpriteSheet, index * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
        }

        for (int i = 0; i < FRAMES_IN_ROW_ATTACK; i++) {
            int index = (FRAMES_IN_ROW_ATTACK - 1) - i;
            attackFrames[i] = new TextureRegion(AttackSpriteSheet, index * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
        }

        for (int i = 0; i < FRAMES_IN_ROW_DEATH; i++) {
            int index = (FRAMES_IN_ROW_DEATH - 1) - i;
            deathFrames[i] = new TextureRegion(DeathSpriteSheet, index * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
        }

        this.position = new Vector2(Gdx.graphics.getWidth(), position.y);
        this.bounds = new Rectangle(this.position.x, this.position.y, FRAME_WIDTH, FRAME_HEIGHT);
        this.vida = vida;
        this.isAttacking = false;
        this.isDead = false;
        this.attackTimer = 0f;
        damageDealt =0;
    }

    public void update(float delta) {
        stateTime += delta;

        if (!isDead) {
            attackTimer += delta;

            if (attackTimer >= 5f) {
                startAttack();
                attackTimer = 0f;
            }

            if (isAttacking) {
                currentFrameIndex = (int) (stateTime / FRAME_DURATION) % attackFrames.length;
                if (stateTime >= FRAME_DURATION * attackFrames.length) {
                    stateTime = 0;
                    isAttacking = false;
                    position.x -= SPEED * delta;
                }
            } else if (stateTime >= FRAME_DURATION) {
                currentFrameIndex = (currentFrameIndex + 1) % walkFrames.length;
                stateTime = 0;
                position.x -= SPEED * delta;
            }

            if (position.x + FRAME_WIDTH < -60) {
                position.x = Gdx.graphics.getWidth();
            }
        } else {
            currentFrameIndex = (int) (stateTime / FRAME_DURATION) % deathFrames.length;
            if (currentFrameIndex == deathFrames.length - 1) {
                dispose();
            }
        }

        if (vida <= 0 && !isDead) {
            vida = 0;
            isDead = true;
            stateTime = 0;
            currentFrameIndex = 0;
        }
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    

    public void render(SpriteBatch batch) {
        if (!isDisposed) {
            float screenWidth = Gdx.graphics.getWidth();
            float scale = screenWidth / 1080f;
            scale *= 2.7f;

            if (isDead) {
                batch.draw(deathFrames[currentFrameIndex], position.x, position.y, FRAME_WIDTH * scale, FRAME_HEIGHT * scale);
            } else if (isAttacking) {
                float attackPositionX = position.x - 150;
                batch.draw(attackFrames[currentFrameIndex], position.x, position.y, FRAME_WIDTH * scale, FRAME_HEIGHT * scale);
            } else {
                batch.draw(walkFrames[currentFrameIndex], position.x, position.y, FRAME_WIDTH * scale, FRAME_HEIGHT * scale);
            }
        }
    }

    public void startAttack() {
        isAttacking = true;
        position.x -= SPEED * Gdx.graphics.getDeltaTime();
        currentFrameIndex = 0;
    }

    public void receiveDamage(int damage) {
        vida -= damage;
        if (vida <= 0) {
            isDead = true;
            stateTime = 0;
            currentFrameIndex = 0;
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
        isDisposed = true;
    }

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

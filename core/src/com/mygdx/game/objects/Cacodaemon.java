package com.mygdx.game.objects;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Cacodaemon {
    private TextureRegion[] walkFrames;
    private TextureRegion[] deathFrames;

    private int currentFrameIndex;
    private Vector2 position;
    private Rectangle bounds;

    private static final int FRAME_WIDTH = 64;
    private static final int FRAME_HEIGHT = 64;
    private static final int FRAMES_IN_ROW_WALK = 6;
    private static final int FRAMES_IN_ROW_DEATH = 4;

    private static final float FRAME_DURATION = 0.1f;
    private static final float SPEED = 1000f;

    private float stateTime;
    private boolean isAttacking;
    private boolean isDying;
    private boolean isDead;
    private int health; // Variable para la vida

    private float attackTimer;

    public Cacodaemon(Vector2 position, int health) {
        Texture walkSpriteSheet = new Texture(Gdx.files.internal("Cacodaemon/Cacodaemon.png"));
        Texture deathSpriteSheet = new Texture(Gdx.files.internal("Cacodaemon/CacodaemonDeath.png"));

        walkFrames = new TextureRegion[FRAMES_IN_ROW_WALK];
        deathFrames = new TextureRegion[FRAMES_IN_ROW_DEATH];

        for (int i = 0; i < FRAMES_IN_ROW_WALK; i++) {
            int index = (FRAMES_IN_ROW_WALK - 1) - i;
            walkFrames[i] = new TextureRegion(walkSpriteSheet, index * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
        }

        for (int i = 0; i < FRAMES_IN_ROW_DEATH; i++) {
            int index = (FRAMES_IN_ROW_DEATH - 1) - i;
            deathFrames[i] = new TextureRegion(deathSpriteSheet, i * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT);
        }

        this.position = new Vector2(Gdx.graphics.getWidth(), position.y);
        this.bounds = new Rectangle(this.position.x, this.position.y, FRAME_WIDTH, FRAME_HEIGHT);
        this.isAttacking = false;
        this.attackTimer = 0f;
        this.health = health;
        this.isDead = false;
    }

    public void update(float delta) {
        stateTime += delta;

        if (!isDead) {
            if (isDying) {
                currentFrameIndex = (int) (stateTime / FRAME_DURATION) % deathFrames.length;
                if (stateTime >= FRAME_DURATION * deathFrames.length) {
                    isDead = true;
                    dispose();
                }
            } else {
                if (attackTimer >= 5f) {
                    startAttack();
                    attackTimer = 0f;
                }

                if (isAttacking) {
                    currentFrameIndex = (int) (stateTime / FRAME_DURATION) % walkFrames.length;
                    if (stateTime >= FRAME_DURATION * walkFrames.length) {
                        stateTime = 0;
                        isAttacking = false;
                        position.x -= SPEED * delta;
                    }
                } else if (stateTime >= FRAME_DURATION) {
                    currentFrameIndex = (currentFrameIndex + 1) % walkFrames.length;
                    stateTime = 0;
                    position.x -= SPEED * delta;
                }

                if (position.x + FRAME_WIDTH < 0) {
                    position.x = Gdx.graphics.getWidth();
                }
            }
        }
    }

    public void render(SpriteBatch batch) {
        if (!isDead) {
            float screenWidth = Gdx.graphics.getWidth();
            float scale = screenWidth / 2000f;
            scale *= 2.7f;

            if (isDying) {
                batch.draw(deathFrames[currentFrameIndex], position.x, position.y, FRAME_WIDTH * scale, FRAME_HEIGHT * scale);
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

    public void startDeathAnimation() {
        isDying = true;
        stateTime = 0;
        currentFrameIndex = 0;
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
        for (TextureRegion frame : deathFrames) {
            frame.getTexture().dispose();
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            startDeathAnimation();
        }
    }
}

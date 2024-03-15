package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.objects.Cacodaemon;
import com.mygdx.game.objects.DemonFly;
import com.mygdx.game.objects.Knight;
import com.mygdx.game.objects.Rana;
import com.mygdx.game.objects.TempanoHielo;
import com.mygdx.game.objects.Witch;

public class GameScreen implements Screen {
    private final SpriteBatch batch;
    private Background background;
    //
    private Witch witch;
    private Knight knightWalk, knightAttack, knightCrouch, knightJump;
    private boolean isAttacking = false, isCrouched = false, isJumping = false;

    //Boses
    private DemonFly demonFly;
    private Rana rana;
    private TempanoHielo tempanodehielo;
    private Cacodaemon cacodaemon;

    public GameScreen(SpriteBatch batch) {
        this.batch = batch;
        // Crea el fondo del juego
        background = new Background();
        // Crea la bruja del juego en la posición inicial
        witch = new Witch(new Vector2(100, 100));
        demonFly = new DemonFly(new Vector2(300, 100));

        knightWalk = new Knight(new Vector2(800, 100), 2, 8); // Por ejemplo, posición (100, 100)
        knightAttack = new Knight(new Vector2(800, 100), 9, 5); // Por ejemplo, posición (100, 100)
        knightCrouch = new Knight(new Vector2(800, 100), 15, 3); // Por ejemplo, posición (100, 100)
        knightJump = new Knight(new Vector2(800, 200), 22, 5); // Por ejemplo, posición (100, 100)

        rana = new Rana(new Vector2(500, 100),100);
        tempanodehielo = new TempanoHielo(new Vector2(500, 50));
        cacodaemon = new Cacodaemon(new Vector2(100, 900));
    }

    @Override
    public void show() {
        // Este método se llama cuando la pantalla se vuelve visible
    }

    @Override
    public void render(float delta) {
        // Actualiza la lógica del juego
        update(delta);

        // Limpia la pantalla
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        // Dibuja el fondo del juego
        background.draw(batch);
        // Dibuja la bruja del juego
        witch.render(batch);

        demonFly.render(batch);
        if (isAttacking) {
            knightAttack.render(batch);
        }
        else if (isCrouched) {
            knightCrouch.render(batch);
        }
        else if(isJumping) {
            knightJump.render(batch);
        }
        else {
            knightWalk.render(batch);
        }
        rana.render(batch);
        tempanodehielo.render(batch);
        cacodaemon.render(batch);
        batch.end();
    }

    private void update(float delta) {
        // Actualiza la lógica del fondo del juego
        background.update(delta);
        // Actualiza la lógica de la bruja del juego
        witch.update(delta);
        demonFly.update(delta);

        // Check if the S key is pressed
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            isCrouched = true;
        } else {
            isCrouched = false;
        }

        // Check for other key events and handle them
        if (Gdx.input.isKeyPressed(Input.Keys.A) && !isAttacking) {
            isAttacking = true;
            // Reinicia la animación de ataque
            knightAttack.resetAnimation();
        }

        if (isAttacking) {
            knightAttack.update(delta);
            if (knightAttack.isAnimationFinished()) {
                isAttacking = false;
            }
        } else {
            knightWalk.update(delta);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W) && !isJumping) {
            isJumping = true;
            knightJump.resetAnimation();
        }
        if (isJumping) {
            knightJump.updateSalto(delta);
            if (knightJump.isAnimationFinished()) {
                isJumping = false;
            }
        } else {
            knightWalk.update(delta);
        }

        // Update crouch animation if crouched
        if (isCrouched) {
            knightCrouch.update(delta);
        }

        rana.update(delta);
        cacodaemon.update(delta);
        tempanodehielo.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        // Actualiza el viewport del Batch
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
    }

    @Override
    public void pause() {
        // Este método se llama cuando el juego se pausa
    }

    @Override
    public void resume() {
        // Este método se llama cuando el juego se reanuda después de haber estado pausado
    }

    @Override
    public void hide() {
        // Este método se llama cuando la pantalla ya no es la actual
    }

    @Override
    public void dispose() {
        // Libera los recursos del fondo del juego
        background.dispose();
        // Libera los recursos de la bruja del juego
        witch.dispose();
        demonFly.dispose();
        knightWalk.dispose();
        knightAttack.dispose();
        knightCrouch.dispose();
        knightJump.dispose();
        rana.dispose();
        cacodaemon.dispose();
        tempanodehielo.dispose();
    }
}

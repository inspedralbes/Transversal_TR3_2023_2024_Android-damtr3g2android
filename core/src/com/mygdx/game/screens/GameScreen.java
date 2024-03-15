package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
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
    private Knight knightWalk, knightAttack;
    private boolean isAttacking = false;

    private float elapsedTime = 0;
    private static final float TIENDA_INTERVAL = 30;

    //Boses
    private DemonFly demonFly;
    private Rana rana;
    private TempanoHielo tempanodehielo;
    private Cacodaemon cacodaemon;
<<<<<<< Updated upstream
=======
    private boolean jumpCooldownActive = false;
    private float jumpCooldownTimer = 0f;
    private static final float JUMP_COOLDOWN_DURATION = 1f;
>>>>>>> Stashed changes

    public GameScreen(SpriteBatch batch) {
        this.batch = batch;
        // Crea el fondo del juego
        background = new Background();
        // Crea la bruja del juego en la posición inicial
        witch = new Witch(new Vector2(100, 100));
        demonFly = new DemonFly(new Vector2(300, 100));

        knightWalk = new Knight(new Vector2(300, 100), 2, 8);
        knightAttack = new Knight(new Vector2(300, 100), 9, 5);

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
        elapsedTime += delta; // Incrementa el tiempo transcurrido
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
        } else {
            knightWalk.render(batch);
        }
        rana.render(batch);
        tempanodehielo.render(batch);
        cacodaemon.render(batch);
        batch.end();

        // Verifica si ha pasado el tiempo para mostrar la tienda
        if (elapsedTime >= TIENDA_INTERVAL) {
            // Cambia a la pantalla de la tienda
            ((MyGdxGame) Gdx.app.getApplicationListener()).changeScreen(MyGdxGame.TIENDA_SCREEN);
            // Reinicia el temporizador
            elapsedTime = 0;
        }
    }

    private void update(float delta) {
        // Actualiza la lógica del fondo del juego
        background.update(delta);
        // Actualiza la lógica de la bruja del juego
        witch.update(delta);
        demonFly.update(delta);
        if (Gdx.input.isKeyJustPressed(Input.Keys.A) && !isAttacking) {
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
        rana.dispose();
        cacodaemon.dispose();
        tempanodehielo.dispose();
    }
}

package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.objects.DemonFly;
import com.mygdx.game.objects.Witch;

public class GameScreen implements Screen {
    private final SpriteBatch batch;
    private Background background;
    private Witch witch;

    private DemonFly demonFly;

    public GameScreen(SpriteBatch batch) {
        this.batch = batch;
        // Crea el fondo del juego
        background = new Background();
        // Crea la bruja del juego en la posición inicial
        witch = new Witch(new Vector2(100, 100)); // Por ejemplo, posición (100, 100)
        demonFly = new DemonFly(new Vector2(300, 0)); // Por ejemplo, posición (100, 100)

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
        batch.end();
    }

    private void update(float delta) {
        // Actualiza la lógica del fondo del juego
        background.update(delta);
        // Actualiza la lógica de la bruja del juego
        witch.update(delta);
        demonFly.update(delta);
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
    }
}

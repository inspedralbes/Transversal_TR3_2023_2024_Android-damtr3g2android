package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {
    private final SpriteBatch batch;
    private Background gameBackground;

    public GameScreen(SpriteBatch batch) {
        this.batch = batch;
        // Crea el fondo del juego
        gameBackground = new Background("BackgroundTree/background1.png");
    }

    @Override
    public void show() {
        // Este método se llama cuando la pantalla se vuelve visible
    }

    @Override
    public void render(float delta) {
        // Limpia la pantalla
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        // Dibuja el fondo del juego
        gameBackground.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // Este método se llama cuando el tamaño de la pantalla cambia
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
        gameBackground.dispose();
    }
}

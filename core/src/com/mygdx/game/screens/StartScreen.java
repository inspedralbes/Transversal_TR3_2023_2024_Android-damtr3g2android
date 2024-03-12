package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;

public class StartScreen implements Screen {
    private final SpriteBatch batch;
    private Texture startScreenTexture;
    private BitmapFont font;
    private Rectangle startButton;

    public StartScreen(SpriteBatch batch) {
        this.batch = batch;
        // Crea la textura de la pantalla de inicio programáticamente
        createStartScreenTexture();
        // Crea la fuente para el texto
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2);

        // Define el área del botón de inicio (por ahora, un rectángulo simple)
        startButton = new Rectangle(300, 200, 200, 50);
    }

    private void createStartScreenTexture() {
        Pixmap pixmap = new Pixmap(800, 600, Pixmap.Format.RGBA8888);
        // Llena el pixmap con un color rojo oscuro
        pixmap.setColor(Color.RED);
        pixmap.fill();

        // Crea una textura a partir del pixmap
        startScreenTexture = new Texture(pixmap);

        // Libera el pixmap, ya que no lo necesitamos más
        pixmap.dispose();
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
        // Dibuja la textura de la pantalla de inicio en la esquina superior izquierda
        batch.draw(startScreenTexture, 0, 0);

        // Dibuja el texto del botón de inicio
        font.draw(batch, "Start Game", startButton.x, startButton.y + startButton.height);

        batch.end();

        // Verifica si se hizo clic en el botón de inicio
        if (Gdx.input.justTouched()) {
            // Obtiene las coordenadas del clic
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();

            // Verifica si el clic ocurrió dentro del área del botón de inicio
            if (startButton.contains(x, y)) {
                // Si se hizo clic en el botón de inicio, cambia a la pantalla de juego
                ((MyGdxGame) Gdx.app.getApplicationListener()).changeScreen(MyGdxGame.GAME_SCREEN);
            }
        }
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
        // Libera los recursos de la textura de la pantalla de inicio y la fuente
        startScreenTexture.dispose();
        font.dispose();
    }
}

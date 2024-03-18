package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.audio.Music; // Importar la clase Music de LibGDX
import com.mygdx.game.MyGdxGame;

public class StartScreen implements Screen {
    private final SpriteBatch batch;
    private Texture startScreenTexture;
    private BitmapFont font;
    private Rectangle startButton;
    private Music backgroundMusic; // Variable para la música de fondo

    public StartScreen(SpriteBatch batch) {
        this.batch = batch;
        // Crea la textura de la pantalla de inicio programáticamente
        createStartScreenTexture();
        // Crea la fuente para el texto
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2);

        // Define el área del botón de inicio centrado en la pantalla
        float buttonWidth = Gdx.graphics.getWidth() / 4;
        float buttonHeight = Gdx.graphics.getHeight() / 15;
        float buttonX = (Gdx.graphics.getWidth() - buttonWidth) / 2;
        float buttonY = (Gdx.graphics.getHeight() - buttonHeight) / 2;
        startButton = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);

        // Cargar la música de fondo
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music/dragon-ball.mp3"));
        backgroundMusic.setLooping(true); // Para reproducir la música en bucle
        backgroundMusic.play(); // Comenzar a reproducir la música
    }

    private void createStartScreenTexture() {
        // Crea una textura a partir de la pantalla completa
        startScreenTexture = new Texture(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        // Llena la textura con un color rojo oscuro
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();
        startScreenTexture.draw(pixmap, 0, 0);
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
        // Reajusta el tamaño y la posición del botón de inicio al cambiar el tamaño de la ventana
        float buttonWidth = width / 4;
        float buttonHeight = height / 15;
        float buttonX = (width - buttonWidth) / 2;
        float buttonY = (height - buttonHeight) / 2;
        startButton.set(buttonX, buttonY, buttonWidth, buttonHeight);

        // Actualiza el tamaño de la textura de fondo al cambiar el tamaño de la ventana
        startScreenTexture.dispose();
        createStartScreenTexture();

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
        backgroundMusic.stop();
        backgroundMusic.dispose();
    }

    @Override
    public void dispose() {
        // Libera los recursos de la textura de la pantalla de inicio y la fuente
        startScreenTexture.dispose();
        font.dispose();
        // Detener y liberar la música
        backgroundMusic.stop();
        backgroundMusic.dispose();
    }
}

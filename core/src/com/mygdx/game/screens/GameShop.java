package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.MyGdxGame;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;


public class GameShop implements Screen {
    private static final String SERVER_URL = "http://localhost:3001"; // Cambia la dirección IP al servidor Node.js
    private static Socket socket;

    private final SpriteBatch batch;
    private Texture backgroundTexture; // Texture para el fondo de pantalla
    private BitmapFont font;
    private Rectangle[] buttons;
    private String userInput = ""; // Para almacenar la entrada de texto

    public static void connect() {
        try {
            socket = IO.socket(SERVER_URL);
            socket.connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    

    public static void disconnect() {
        if (socket != null && socket.connected()) {
            socket.disconnect();
        }
    }

    public GameShop(SpriteBatch batch) {
        this.batch = batch;

        // Cargar la imagen de fondo desde la carpeta de assets
        backgroundTexture = new Texture("Background/Tienda.png");

        // Crea la fuente para el texto
        font = new BitmapFont();
        font.getData().setScale(2);

        // Define el área de los botones centrados en la pantalla
        buttons = new Rectangle[4];
        float buttonWidth = Gdx.graphics.getWidth() / 4;
        float buttonHeight = Gdx.graphics.getHeight() / 15;
        float buttonX = (Gdx.graphics.getWidth() - buttonWidth) / 2;
        float buttonY = (Gdx.graphics.getHeight() - buttonHeight) / 2;

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new Rectangle(buttonX, buttonY - i * (buttonHeight + 10), buttonWidth, buttonHeight);
        }
    }

    @Override
    public void render(float delta) {
        // Limpia la pantalla
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Dibujar el fondo de pantalla
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Dibujar los botones
        for (int i = 0; i < buttons.length; i++) {
            font.draw(batch, "Botón " + (i+1), buttons[i].x, buttons[i].y + buttons[i].height);
        }

        // Dibujar el texto ingresado por el usuario
        font.draw(batch, "Input: " + userInput, 50, Gdx.graphics.getHeight() - 50);

        batch.end();

        // Verifica si se hizo clic en algún botón
        if (Gdx.input.justTouched()) {
            // Obtiene las coordenadas del clic
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();

            // Verifica si el clic ocurrió dentro de algún botón
            for (int i = 0; i < buttons.length; i++) {
                if (buttons[i].contains(x, y)) {
                    // Si se hizo clic en un botón, realiza la acción de cambio de pantalla
                    ((MyGdxGame) Gdx.app.getApplicationListener()).changeScreen(MyGdxGame.GAME_SCREEN);
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        // Reajusta el tamaño y la posición de los botones al cambiar el tamaño de la ventana
        float buttonWidth = width / 4;
        float buttonHeight = height / 15;
        float buttonX = (width - buttonWidth) / 2;
        float buttonY = (height - buttonHeight) / 2;

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].set(buttonX, buttonY - i * (buttonHeight + 10), buttonWidth, buttonHeight);
        }

        // Actualiza el viewport del Batch
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
    }

    @Override
    public void show() {
        // Este método se llama cuando la pantalla se vuelve visible
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
        backgroundTexture.dispose();
        font.dispose();
    }
}

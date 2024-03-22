package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.MyGdxGame;

public class GameShop implements Screen {
    private final SpriteBatch batch;
    private Texture backgroundTexture;
    private BitmapFont font;
    private Texture[] buttonTextures;
    private Rectangle[] buttonBounds;

    public GameShop(SpriteBatch batch) {
        this.batch = batch;

        backgroundTexture = new Texture("Background/Tienda.png");
        font = new BitmapFont();
        font.getData().setScale(2);

        // Cargar las imágenes de los botones desde la carpeta de assets
        buttonTextures = new Texture[5];
        buttonTextures[0] = new Texture("batman.jpg");
        buttonTextures[1] = new Texture("batman.jpg");
        buttonTextures[2] = new Texture("batman.jpg");
        buttonTextures[3] = new Texture("batman.jpg");
        buttonTextures[4] = new Texture("batman.jpg");

        // Define las áreas de los botones
        buttonBounds = new Rectangle[5];
        float buttonWidth = Gdx.graphics.getWidth() / 5;
        float buttonHeight = Gdx.graphics.getHeight() / 5;
        float buttonY = (Gdx.graphics.getHeight() - buttonHeight) / 2;

        for (int i = 0; i < buttonBounds.length; i++) {
            float buttonX = i * (buttonWidth + 10) + 10;
            buttonBounds[i] = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Dibujar los botones
        for (int i = 0; i < buttonTextures.length; i++) {
            batch.draw(buttonTextures[i], buttonBounds[i].x, buttonBounds[i].y, buttonBounds[i].width, buttonBounds[i].height);
        }

        batch.end();

        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();

            // Verifica si el toque ocurrió dentro de algún botón
            for (int i = 0; i < buttonBounds.length; i++) {
                if (buttonBounds[i].contains(x, y)) {
                    // Aquí puedes manejar la acción de clic para cada botón
                    // Por ejemplo, cambiar de pantalla, abrir una ventana emergente, etc.
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        // Reajusta el tamaño y la posición de los botones al cambiar el tamaño de la ventana
        float buttonWidth = width / 5;
        float buttonHeight = height / 5;
        float buttonY = (height - buttonHeight) / 2;

        for (int i = 0; i < buttonBounds.length; i++) {
            float buttonX = i * (buttonWidth + 10) + 10;
            buttonBounds[i].set(buttonX, buttonY, buttonWidth, buttonHeight);
        }
    }

    @Override
    public void show() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        font.dispose();

        // Libera las texturas de los botones
        for (Texture texture : buttonTextures) {
            texture.dispose();
        }
    }
}

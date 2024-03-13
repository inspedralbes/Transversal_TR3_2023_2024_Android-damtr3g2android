package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
<<<<<<< Updated upstream
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
=======
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
>>>>>>> Stashed changes
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.objects.Knight;

public class GameScreen implements Screen {
<<<<<<< Updated upstream
    private final SpriteBatch batch;
    private Texture gameBackground;
=======
    private final Batch batch;
    private Background background;
>>>>>>> Stashed changes

    private Stage stage;
    private Knight knight;
    private ShapeRenderer shapeRenderer;

    public GameScreen(Batch batch) {
        shapeRenderer = new ShapeRenderer();
        OrthographicCamera camera = new OrthographicCamera(500, 500);
        this.batch = batch;
        // Crea la textura del fondo del juego programáticamente
        createGameBackground();
    }

    private void createGameBackground() {
        Pixmap pixmap = new Pixmap(800, 600, Pixmap.Format.RGB888);
        // Llena el pixmap con un color azul oscuro
        pixmap.setColor(0, 0, 128, 255); // Azul oscuro (R, G, B, A)
        pixmap.fill();

        // Crea una textura a partir del pixmap
        gameBackground = new Texture(pixmap);

        // Libera el pixmap, ya que no lo necesitamos más
        pixmap.dispose();
    }

    @Override
    public void show() {
        // Este método se llama cuando la pantalla se vuelve visible
    }

    @Override
    public void render(float delta) {
        // Limpia la pantalla con un color azul oscuro
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        // Dibuja la textura del fondo del juego en la esquina superior izquierda
        batch.draw(gameBackground, 0, 0);
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
        // Libera los recursos de la textura del fondo del juego
        gameBackground.dispose();
    }
}

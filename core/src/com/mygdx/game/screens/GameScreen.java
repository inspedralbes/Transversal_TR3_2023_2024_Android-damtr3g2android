package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.objects.Knight;

public class GameScreen implements Screen {

    private Stage stage;
    private Knight knight;
    private ShapeRenderer shapeRenderer;

    private Batch batch;
    private SpriteBatch batch2;
    private Background background;

    public GameScreen(SpriteBatch batch2) {
        this.batch2 = batch2;
        shapeRenderer = new ShapeRenderer();
        OrthographicCamera camera = new OrthographicCamera(500, 500);
        camera.setToOrtho(true);
        StretchViewport viewport = new StretchViewport(500, 500, camera);
        stage = new Stage(viewport);
        batch = stage.getBatch();
        knight = new Knight(0, 0, 100, 100);
        stage.addActor(knight);
        knight.setName("knight");
        // Crea el fondo del juego
        background = new Background();
    }

    private void drawElements(){
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0, 1, 0, 1));
        shapeRenderer.rect(knight.getX(), knight.getY(), knight.getWidth(), knight.getHeight());
        shapeRenderer.end();
    }

    @Override
    public void show() {
        // Este método se llama cuando la pantalla se vuelve visible
    }

    public Stage getStage() {
        return stage;
    }

    public Knight getKnight() {
        return knight;
    }

    @Override
    public void render(float delta) {
        // Actualiza la lógica del juego
        update(delta);

        // Limpia la pantalla
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch2.begin();
        // Dibuja el fondo del juego
        background.draw(batch2);
        batch2.end();
        stage.draw();
        stage.act(delta);
    }

    private void update(float delta) {
        // Actualiza la lógica del fondo del juego
        background.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        // Actualiza el viewport del Batch
        batch2.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
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
    }
}

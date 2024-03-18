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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGdxGame;

public class StartScreen implements Screen {
    private final SpriteBatch batch;
    private Texture startScreenTexture;
    private BitmapFont font;
    private Rectangle startButton;
    private Rectangle createRoomButton;
    private Rectangle joinRoomButton;

    private Stage stage;
    private TextField textField;

    public StartScreen(SpriteBatch batch) {
        this.batch = batch;
        createTextField();
        createStartScreenTexture();
        createFont();

        float buttonWidth = Gdx.graphics.getWidth() / 4;
        float buttonHeight = Gdx.graphics.getHeight() / 15;
        float buttonX = (Gdx.graphics.getWidth() - buttonWidth) / 2;
        float buttonY = (Gdx.graphics.getHeight() - buttonHeight) / 2;
        startButton = new Rectangle(buttonX, buttonY + 100, buttonWidth, buttonHeight);
        createRoomButton = new Rectangle(buttonX, buttonY - 50, buttonWidth, buttonHeight);
        joinRoomButton = new Rectangle(buttonX, buttonY - 200, buttonWidth, buttonHeight);
    }

    private void createTextField() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        TextFieldStyle textFieldStyle = new TextFieldStyle();
        textFieldStyle.font = new BitmapFont();
        textFieldStyle.fontColor = Color.BLACK;

        // Creamos un fondo blanco para el TextField
        Pixmap pixmap = new Pixmap(200, 40, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        textFieldStyle.background = new Image(new Texture(pixmap)).getDrawable();

        // Creamos un cursor negro para el TextField
        Pixmap cursorPixmap = new Pixmap(1, (int) textFieldStyle.font.getLineHeight(), Pixmap.Format.RGBA8888);
        cursorPixmap.setColor(Color.BLACK);
        cursorPixmap.fill();
        textFieldStyle.cursor = new Image(new Texture(cursorPixmap)).getDrawable();

        textField = new TextField("", textFieldStyle);
        textField.setPosition(Gdx.graphics.getWidth() / 2 - 100, Gdx.graphics.getHeight() / 2);
        textField.setSize(200, 40);

        stage.addActor(textField);

        // Liberamos los pixmap para evitar pérdidas de memoria
        pixmap.dispose();
        cursorPixmap.dispose();
    }

    private void createStartScreenTexture() {
        startScreenTexture = new Texture(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();
        startScreenTexture.draw(pixmap, 0, 0);
        pixmap.dispose();
    }

    private void createFont() {
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(2);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(startScreenTexture, 0, 0);
        font.draw(batch, "Start Game", startButton.x, startButton.y + startButton.height);
        font.draw(batch, "Create Room", createRoomButton.x, createRoomButton.y + createRoomButton.height);
        font.draw(batch, "Join Room", joinRoomButton.x, joinRoomButton.y + joinRoomButton.height);
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        if (Gdx.input.justTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (startButton.contains(x, y)) {
                ((MyGdxGame) Gdx.app.getApplicationListener()).changeScreen(MyGdxGame.GAME_SCREEN);
            } else if (createRoomButton.contains(x, y)) {
            } else if (joinRoomButton.contains(x, y)) {
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        float buttonWidth = width / 4;
        float buttonHeight = height / 15;
        float buttonX = (width - buttonWidth) / 2;
        float buttonY = (height - buttonHeight) / 2;
        startButton.set(buttonX, buttonY + 100, buttonWidth, buttonHeight);
        createRoomButton.set(buttonX, buttonY - 50, buttonWidth, buttonHeight);
        joinRoomButton.set(buttonX, buttonY - 200, buttonWidth, buttonHeight);

        startScreenTexture.dispose();
        createStartScreenTexture();

        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
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
        startScreenTexture.dispose();
        font.dispose();
    }
}

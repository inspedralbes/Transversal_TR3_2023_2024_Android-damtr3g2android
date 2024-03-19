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

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class StartScreen implements Screen {

    private Socket socket;
    private final SpriteBatch batch;
    private Texture startScreenTexture;
    private BitmapFont font;
    private Rectangle startButton;
    private Rectangle createRoomButton;
    private Rectangle joinRoomButton;
    private String inputText = "";
    private Stage stage;
    private TextField textField;

    public StartScreen(SpriteBatch batch) {
        this.batch = batch;
        createTextField();
        createStartScreenTexture();
        createFont();
        connectSocket();

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

        // Agregamos un Listener al TextField para manejar los cambios de texto
        textField.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                // Guardamos el texto ingresado en una variable
                // En este ejemplo, la variable se llama "inputText"
                inputText = textField.getText();
            }
        });

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
        socket.on("startGame", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                // Lógica para cambiar a la pantalla de juego
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        ((MyGdxGame) Gdx.app.getApplicationListener()).changeScreen(MyGdxGame.GAME_SCREEN);
                    }
                });
            }
        });
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
                socket.emit("createRoom", inputText);
            } else if (joinRoomButton.contains(x, y)) {
                socket.emit("joinRoom", inputText);
                socket.emit("message", "HOLAAAAAAAAA");
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

    public void connectSocket() {
        try {
            socket = IO.socket("http://localhost:3001"); // Change the IP address to your Node.js server's IP
            socket.connect();

            // Add listeners for any events you want to handle
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    // Handle connection
                    Gdx.app.log("SocketIO", "Connected");
                }
            }).on("message", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    // Handle incoming message
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String message = data.getString("message");
                        Gdx.app.log("SocketIO", "Received message: " + message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    // Handle disconnection
                    Gdx.app.log("SocketIO", "Disconnected");
                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}

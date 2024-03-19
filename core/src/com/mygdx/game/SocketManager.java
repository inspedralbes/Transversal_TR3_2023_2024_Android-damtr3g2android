package com.mygdx.game;

import com.badlogic.gdx.Gdx;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URISyntaxException;

public class SocketManager {
    private static final String SERVER_URL = "http://localhost:3001"; // Cambia la dirección IP al servidor Node.js
    private static Socket socket;

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

    public static void addStartGameListener() {
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

    public static void createRoom(String roomName) {
        socket.emit("createRoom", roomName);
    }

    public static void joinRoom(String roomName) {
        socket.emit("joinRoom", roomName);
        socket.emit("message", "HOLAAAAAAAAA");
    }

    public static Socket getSocket() {
        return socket;
    }
}

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.screens.GameScreen;

import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URISyntaxException;

public class SocketManager {
    private static final String SERVER_URL = "http://localhost:3001"; // Cambia la dirección IP al servidor Node.js
    private static Socket socket;
    private static String currentRoom;
    public static String rol;
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
                JSONObject roles = (JSONObject) args[0];
                // Lógica para cambiar a la pantalla de juego y manejar los roles
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {

                        String knightId = null;
                        try {
                            knightId = roles.getString("knight");
                            String witchId = roles.getString("witch");
                            if (socket.id().equals(knightId)) {
                                System.out.println("Knight");
                                setRol("knight");
                                ((MyGdxGame) Gdx.app.getApplicationListener()).changeScreen(MyGdxGame.GAME_SCREEN);

                            } else if (socket.id().equals(witchId)) {
                                System.out.println("Witch");
                                setRol("witch");
                                ((MyGdxGame) Gdx.app.getApplicationListener()).changeScreen(MyGdxGame.GAME_SCREEN);

                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });
            }
        });
    }


    public static void createRoom(String roomName) {
        socket.emit("createRoom", roomName);
        setCurrentRoom(roomName);
    }

    public static void joinRoom(String roomName) {
        socket.emit("joinRoom", roomName);
        socket.emit("message", "HOLAAAAAAAAA");
        setCurrentRoom(roomName);

    }



    public static void emitKnightAttack(){
        socket.emit("knightAttack", getCurrentRoom());
    }

    public static void emitKnightJump(){
        socket.emit("knightJump", getCurrentRoom());
    }

    public static void emitKnightCrouch(){
        socket.emit("knightCrouch", getCurrentRoom());
    }

    public static void emitWitchBall() {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                socket.emit("witchWaterBall", getCurrentRoom());
            }
        });
    }

    public static void addWitchWaterBallListener(GameScreen gameScreen) {
        socket.on("witchWaterBalling", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        gameScreen.witchWaterBall();
                        // Aquí maneja la respuesta del servidor
                        // Por ejemplo, puedes imprimir la respuesta en la consola
                        System.out.println("Respuesta del servidor: ");
                        // También puedes hacer otras operaciones según la respuesta del servidor
                    }
                });
            }
        });
    }
    public static void addKnightAttackListener(GameScreen gameScreen){
        socket.on("knightAttacking", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                gameScreen.knightAttack();
                // Aquí maneja la respuesta del servidor
                // Por ejemplo, puedes imprimir la respuesta en la consola
                System.out.println("Respuesta del servidor: ");
                // También puedes hacer otras operaciones según la respuesta del servidor
            }
        });
    }

    public static void addKnightCrouch(GameScreen gameScreen){
        socket.on("knightCrouching", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                gameScreen.knightCrouch();
                // Aquí maneja la respuesta del servidor
                // Por ejemplo, puedes imprimir la respuesta en la consola
                System.out.println("Respuesta del servidor: ");
                // También puedes hacer otras operaciones según la respuesta del servidor
            }
        });
    }
    public static void addKnightJumpListener(GameScreen gameScreen){
        socket.on("knightJumping", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                gameScreen.knightJump();
                // Aquí maneja la respuesta del servidor
                // Por ejemplo, puedes imprimir la respuesta en la consola
                System.out.println("Respuesta del servidor: ");
                // También puedes hacer otras operaciones según la respuesta del servidor
            }
        });
    }
    public static Socket getSocket() {
        return socket;
    }


    public static String getRol() {
        return rol;
    }

    public static void setRol(String rol) {
        SocketManager.rol = rol;
    }

    public static void setCurrentRoom(String roomName) {
        currentRoom = roomName;
    }

    public static String getCurrentRoom() {
        return currentRoom;
    }
}

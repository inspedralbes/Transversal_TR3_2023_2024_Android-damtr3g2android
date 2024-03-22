package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.objects.Cacodaemon;
import com.mygdx.game.SocketManager;
import com.mygdx.game.objects.WaterBall;
import com.mygdx.game.screens.Background;
import com.mygdx.game.objects.Knight;
import com.mygdx.game.objects.Rana;
import com.mygdx.game.objects.Witch;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


public class GameScreen implements Screen {
    private final SpriteBatch batch;
    private Background background;
    private WaterBall waterball;

    private Cacodaemon cacodaemon;
    private Witch witch;
    private Knight knightWalk, knightAttack, knightCrouch, knightJump, knightCrouchAttack;
    private boolean isAttacking = false, isCrouched = false, isJumping = false;
    private List<Rana> listaRanas;
    private List<Cacodaemon> listaCacodaemon;
    private List<WaterBall> listaWaterBalls;
    private boolean jumpCooldownActive = false;
    private float jumpCooldownTimer = 0f;
    private static final float JUMP_COOLDOWN_DURATION = 1f;
    private float ranaSpawnTimer = 0f;
    private float cacodaemonSpawnTimer = 0f;
    private static final float CACODAEMON_SPAWN_TIMER = 10f;

    private static final float RANA_SPAWN_INTERVAL = 10f;

    private float elapsedTime = 0f;
    private static final float TIENDA_INTERVAL = 10f;
    private BitmapFont font;
    private boolean isRenderingAttack = false;
    private boolean isRenderingCrouch = false;
    private boolean isRenderingJump = false;
    private boolean knightDead = false;
    private static final String DEATH_MESSAGE = "Has muerto. Presiona cualquier tecla para reiniciar.";

    public GameScreen(SpriteBatch batch) {
        this.batch = batch;
        background = new Background();
        witch = new Witch(new Vector2(0, 700), 100);

        knightWalk = new Knight(new Vector2(-150, 0), 2, 8,false, 100);
        knightAttack = new Knight(new Vector2(0, 0), 9, 5,false, 100);
        knightCrouch = new Knight(new Vector2(-150, 0), 15, 4,true, 100);
        knightJump = new Knight(new Vector2(-150, 200), 22, 5,false, 100);
        knightCrouchAttack = new Knight(new Vector2(0, 0), 16, 5,false, 100);
        listaRanas = new ArrayList<>();
        listaCacodaemon = new ArrayList<>();
        listaWaterBalls = new ArrayList<>();
        font = new BitmapFont();

    }

    public void show() {
        SocketManager.addKnightAttackListener(this);
        SocketManager.addKnightJumpListener(this);
        SocketManager.addKnightCrouch(this);
        SocketManager.addWitchWaterBallListener(this);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        witch.render(batch);

        if (knightDead) {
            renderDeathMessage();
            return;
        }
        if (isCrouched && isAttacking) {
            knightCrouchAttack.render(batch);

        }
        else if (isAttacking) {
            knightAttack.render(batch);
        } else if (isCrouched) {
            knightCrouch.render(batch);
            isCrouched = false;
        } else if (isJumping) {
            knightJump.render(batch);
        } else {
            knightWalk.render(batch);
        }
        //barres de vida
        font.draw(batch, "Knight Life: " + knightWalk.getVida(), 20, Gdx.graphics.getHeight() - 20);
        font.draw(batch, "Witch Life: " + witch.getVida(), 20, Gdx.graphics.getHeight() - 40);
        for (Rana rana : listaRanas) {
            rana.render(batch);
        }

        for(Cacodaemon cacodaemon :listaCacodaemon){
            cacodaemon.render(batch);
        }
        for (WaterBall waterBall : listaWaterBalls) {
            waterBall.render(batch);
        }
        batch.end();
    }

    private void update(float delta) {
        elapsedTime += delta;
        background.update(delta);
        witch.update(delta);

        knightWalk.update(delta);
        // Actualiza el tiempo de cooldown de salto si está activo
        if(SocketManager.getRol()=="knight"){
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                knightCrouch();
                SocketManager.emitKnightCrouch();
                if (Gdx.input.isKeyPressed(Input.Keys.A) && !isAttacking) {
                    isAttacking = true;
                    for (Iterator<Rana> iterator = listaRanas.iterator(); iterator.hasNext();) {
                        Rana rana = iterator.next();
                        if (knightCrouchAttack.getBounds().overlaps(rana.getBounds())) {
                            rana.setVida(0);
                        }
                    }
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A) && !isAttacking) {
                knightAttack();
                SocketManager.emitKnightAttack();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W) && !isJumping && !jumpCooldownActive) {
                knightJump();
                SocketManager.emitKnightJump();
            }
        }else if(SocketManager.getRol()=="witch"){
            if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
                witchWaterBall();
                SocketManager.emitWitchBall();
            }
        }

        if (jumpCooldownActive) {
            jumpCooldownTimer -= delta;
            if (jumpCooldownTimer <= 0) {
                jumpCooldownActive = false;
            }
        }

        if (isAttacking) {
            knightAttack.update(delta);
            if (knightAttack.isAnimationFinished()) {
                isAttacking = false;
            }
        }

        if (isJumping) {
            knightJump.updateSalto(delta);
            if (knightJump.isAnimationFinished()) {
                isJumping = false;
            }
        }
        if (isCrouched && isAttacking) {
            knightCrouchAttack.update(delta);
        } else if (isCrouched) {
            knightCrouch.update(delta);
        }
        for (Rana rana : listaRanas) {
            rana.update(delta);
            if (isCrouched && rana.isAttacking() && rana.getDamageDealt() < 10 && knightCrouch.getBounds().overlaps(rana.getBounds())) {
                knightCrouch.setVida(knightCrouch.getVida() - 10);
                rana.addDamageDealt(10); // Actualizar el daño total infligido
            } else if (!isCrouched && rana.isAttacking() && rana.getDamageDealt() < 10 && knightWalk.getBounds().overlaps(rana.getBounds())) {
                knightWalk.setVida(knightWalk.getVida() - 10);
                rana.addDamageDealt(10); // Actualizar el daño total infligido
            }
            if (rana.isAnimationFinished()) {
                rana.resetDamageDealt();
            }
        }

        for (Cacodaemon cacodaemon : listaCacodaemon) {
            cacodaemon.update(delta);
        }
        for (WaterBall waterBall : listaWaterBalls) {
            waterBall.update(delta);
        }
        ranaSpawnTimer += delta;
        if (ranaSpawnTimer >= RANA_SPAWN_INTERVAL) {
            listaRanas.add(new Rana(new Vector2(0, -20), 100));
            ranaSpawnTimer = 0f;
        }

        cacodaemonSpawnTimer += delta;
        if (cacodaemonSpawnTimer >= CACODAEMON_SPAWN_TIMER) {
            listaCacodaemon.add(new Cacodaemon(new Vector2(300,700)));
            cacodaemonSpawnTimer = 0f;
        }
        isRenderingAttack = isAttacking;
        isRenderingCrouch = isCrouched;
        isRenderingJump = isJumping;
        // Generar WaterBalls cuando se presiona la tecla 'T'

        for (Iterator<Cacodaemon> cacodaemonIterator = listaCacodaemon.iterator(); cacodaemonIterator.hasNext();) {
            Cacodaemon cacodaemon = cacodaemonIterator.next();
            for (Iterator<WaterBall> waterBallIterator = listaWaterBalls.iterator(); waterBallIterator.hasNext();) {
                WaterBall waterBall = waterBallIterator.next();
                if (cacodaemon.getBounds().overlaps(waterBall.getBounds())) {
                    // Colisión detectada, realiza las acciones necesarias
                    // Por ejemplo, eliminar la WaterBall y reducir la vida del Cacodaemon
                    waterBallIterator.remove(); // Elimina la WaterBall
                    cacodaemon.dispose(); // Reducción de la vida del Cacodaemon

                }
            }
        }
        // Verificar si la vida del Knight llega a 0
        if (knightWalk.getVida() <= 0) {
            knightDead = true;
        }
    }
    private void renderDeathMessage() {
        batch.begin();
        font.draw(batch, DEATH_MESSAGE, Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 2);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
    }

    public void knightAttack(){
        isAttacking = true;
        knightAttack.resetAnimation();
        for (Iterator<Rana> iterator = listaRanas.iterator(); iterator.hasNext();) {
            Rana rana = iterator.next();
            if (knightAttack.getBounds().overlaps(rana.getBounds())) {
                rana.setVida(0);
            }
        }

    }
    public void witchWaterBall() {
        WaterBall newWaterball = createWaterBall(); // Crear una nueva WaterBall
        listaWaterBalls.add(newWaterball); // Agregar la nueva WaterBall a la lista
    }

    public WaterBall createWaterBall() {
        Vector2 waterballPosition = new Vector2(120, 750); // Ajusta la posición según necesites
        return new WaterBall(waterballPosition);
    }

    public void knightCrouch(){
        isCrouched = true;
    }
    public void knightJump(){
        isJumping = true;
        knightJump.resetAnimation();
        jumpCooldownActive = true;
        jumpCooldownTimer = JUMP_COOLDOWN_DURATION;
    }
    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        background.dispose();
        witch.dispose();
        knightWalk.dispose();
        knightAttack.dispose();
        knightCrouch.dispose();
        knightJump.dispose();

        for (Rana rana : listaRanas) {
            rana.dispose();
        }
        for (WaterBall waterBall : listaWaterBalls) {
            waterBall.dispose();
        }
        listaWaterBalls.clear(); // Limpiar la lista de WaterBalls
        for (Cacodaemon cacodaemon : listaCacodaemon) {
            cacodaemon.dispose();
        }
        listaCacodaemon.clear(); // Limpiar la lista de Cacodaemons
        font.dispose();

    }

}
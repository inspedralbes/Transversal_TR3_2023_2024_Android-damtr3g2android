package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.StartScreen;

public class MyGdxGame extends Game {
	private SpriteBatch batch;
	private StartScreen startScreen;
	private GameScreen gameScreen;

	public static final int START_SCREEN = 0;
	public static final int GAME_SCREEN = 1;

	@Override
	public void create () {
		batch = new SpriteBatch();
		startScreen = new StartScreen(batch);
		gameScreen = new GameScreen(batch);
		AssetManagerWrapper.load();
		// Configura la pantalla de inicio como la pantalla actual del juego
		setScreen(startScreen);
	}

	@Override
	public void dispose () {
		batch.dispose();
		startScreen.dispose();
		gameScreen.dispose();
	}

	public void changeScreen(int screen) {
		// Cambia a la pantalla deseada
		Screen newScreen = null;
		switch (screen) {
			case START_SCREEN:
				newScreen = startScreen;
				break;
			case GAME_SCREEN:
				newScreen = gameScreen;
				break;
		}
		setScreen(newScreen);
	}
}

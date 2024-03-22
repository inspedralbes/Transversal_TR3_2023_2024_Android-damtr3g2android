package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.GameShop;
import com.mygdx.game.screens.StartScreen;
import com.mygdx.game.screens.TiendainGameScreen;

public class MyGdxGame extends Game {
	private SpriteBatch batch;
	private StartScreen startScreen;
	private GameScreen gameScreen;
	private TiendainGameScreen tiendainGameScreen;
	private GameShop gameShop;



	public static final int START_SCREEN = 0;
	public static final int GAME_SCREEN = 1;
	public static final int TIENDA_SCREEN = 3;
	public static final int GAME_SHOP =4;

	@Override
	public void create () {
		batch = new SpriteBatch();
		startScreen = new StartScreen(batch);
		gameScreen = new GameScreen(batch);
		tiendainGameScreen = new TiendainGameScreen(batch);
		gameShop= new GameShop(batch);
		AssetManagerWrapper.load();
		SocketManager.connect();
		// Configura la pantalla de inicio como la pantalla actual del juego
		setScreen(startScreen);
	}

	@Override
	public void dispose () {
		batch.dispose();
		startScreen.dispose();
		gameScreen.dispose();
		tiendainGameScreen.dispose();
		gameShop.dispose();
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
			case TIENDA_SCREEN:
				newScreen = tiendainGameScreen;
				break;
			case GAME_SHOP:
				newScreen = gameShop;
				break;
		}
		setScreen(newScreen);
	}
}

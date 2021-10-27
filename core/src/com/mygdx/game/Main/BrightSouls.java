package com.mygdx.game.Main;
import com.mygdx.game.Screens.LoginScreen;
import com.mygdx.game.Screens.MenuScreen;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Tools.AssetsLoader;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BrightSouls extends Game {
	public SpriteBatch batch;
	public AssetsLoader assetsLoader;
	public int screenW;
	public int screenH;

	//Virtual Screen size and Box2D Scale(Pixels Per Meter)
	public static final int V_WIDTH = 1920;
	public static final int V_HEIGHT = 1056;
	public static final float PPM = 100;

	public static final short GROUND_BIT = 1;
	public static final short HERO_BIT = 2;
	public static final short ENEMY_BIT = 4;
	public static final short SWORD_BIT = 8;



	@Override
	public void create () {
		this.assetsLoader = new AssetsLoader();
		this.batch = new SpriteBatch();
		this.screenH = Gdx.graphics.getHeight();
		this.screenW = Gdx.graphics.getWidth();
		//setScreen(new LoginScreen( this));
		//setScreen(new RegisterScreen( this));
		//setScreen(new PlayScreen( this));
		setScreen(new MenuScreen( this));
		//setScreen(new LevelOne( this));
	}

	@Override
	public  void render(){
		super.render();
	}

	@Override
	public void setScreen(Screen screen){
		super.setScreen(screen);
	}

	@Override
	public void dispose () {
		assetsLoader.dispose();
	}

	public static void log(String message) { Gdx.app.log("Bright Souls", message); }


}

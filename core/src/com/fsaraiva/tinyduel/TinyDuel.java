package com.fsaraiva.tinyduel;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.fsaraiva.tinyduel.engine.utils.Advertisement;
import com.fsaraiva.tinyduel.engine.utils.InputHandler;
import com.fsaraiva.tinyduel.engine.utils.Settings;
import com.fsaraiva.tinyduel.game.menus.mainmenu.MainMenuScene;


public class TinyDuel extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public String OStype;

	public Settings settings;

	public ShapeRenderer shape;
	public InputHandler input;
	public boolean DebugMode, DebugMode2 = false;
	public boolean musicOn, soundOn = true;
	public int soundLvl = 0;
	public int musicLvl = 0;
	public Advertisement advertisement;

	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont(); // Use LibGDX's default Arial font
//		font24 = new BitmapFont(Gdx.files.internal("bitmapfont/RioGrande.fnt"));
		shape = new ShapeRenderer();
		input = new InputHandler();
		Gdx.input.setInputProcessor(input);
		Controllers.addListener(input);

		OStype = Gdx.app.getType().toString();
		settings = new Settings("TinyDuelSettings");
		settings.getValues(this);
		// settings.useOnScreenCtrl1 = false;
		// settings.useOnScreenCtrl2 = false;

		if (Gdx.app.getType() == Application.ApplicationType.Desktop || Gdx.app.getType() == Application.ApplicationType.HeadlessDesktop) {
			try {
				this.advertisement = (Advertisement) ClassReflection.newInstance(ClassReflection.forName("com.fsaraiva.tinyduel.DesktopAdvertisement"));
			} catch (ReflectionException e) {
				e.printStackTrace();
			}
		}
		if (Gdx.app.getType() == Application.ApplicationType.Android) {
			try {
				this.advertisement = (Advertisement) ClassReflection.newInstance(ClassReflection.forName("com.fsaraiva.tinyduel.AndroidAdvertisement"));
			} catch (ReflectionException e) {
				e.printStackTrace();
			}
		}

		this.setScreen(new MainMenuScene(this));
	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
		shape.dispose();
	}
}

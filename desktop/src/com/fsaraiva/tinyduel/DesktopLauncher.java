package com.fsaraiva.tinyduel;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("TinyDuel");
		config.setResizable(false);
		config.setWindowIcon(Files.FileType.Internal, "splashscreen1.png");
		//config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		config.setWindowedMode(1920, 1080);  //1280,7201920, 1080);
		new Lwjgl3Application(new TinyDuel(), config);

		// optional, maximized but window mode
		//int width = Lwjgl3ApplicationConfiguration.getDisplayMode().width;
		//int height = Lwjgl3ApplicationConfiguration.getDisplayMode().height;
		//config.setWindowedMode(width,height);  //1920, 1080);
	}
}

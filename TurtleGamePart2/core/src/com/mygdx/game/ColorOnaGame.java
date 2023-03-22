
//spawnchunks: no changes made to this file

package com.mygdx.game;

import com.mygdx.game.menu.MenuScreen;
import com.mygdx.game.menu.Settings;
import com.mygdx.game.menu.instructions.InstructionsP1;
import com.mygdx.game.menu.instructions.InstructionsP2;
import com.mygdx.game.menu.instructions.InstructionsP3;

public class ColorOnaGame extends BaseGame
{
	private MenuScreen menuScreen;
	private Settings settingsScreen;
	private LeaderboardScreen leaderboardScreen;
	private InstructionsP1 instructionsP1Screen;
	private InstructionsP2 instructionsP2Screen;
	private InstructionsP3 instructionsP3Screen;

	public void create()
	{
		super.create();
		showMenuScreen();
	}

	public void showMenuScreen() {
		if (menuScreen == null) {
			menuScreen = new MenuScreen(this);
		}
		setActiveScreen(menuScreen);
	}

	public void showSettingsScreen() {
		if (settingsScreen == null) {
			settingsScreen = new Settings(this);
		}
		setActiveScreen(settingsScreen);
	}

	public void showLeaderboardScreen() {
		if (leaderboardScreen == null) {
			leaderboardScreen = new LeaderboardScreen(this);
		}
		setActiveScreen(leaderboardScreen);
	}

	public void showInstructions1Screen() {
		if (instructionsP1Screen == null) {
			instructionsP1Screen = new InstructionsP1(this);
		}
		setActiveScreen(instructionsP1Screen);
	}

	public void showInstructions2Screen() {
		if (instructionsP2Screen == null) {
			instructionsP2Screen = new InstructionsP2(this);
		}
		setActiveScreen(instructionsP2Screen);
	}

	public void showInstructions3Screen() {
		if (instructionsP3Screen == null) {
			instructionsP3Screen = new InstructionsP3(this);
		}
		setActiveScreen(instructionsP3Screen);
	}
}

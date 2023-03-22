package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public abstract class ColorOnaScreen extends BaseScreen {

    final ColorOnaGame colorOnaGame;
    Skin skin;

    public ColorOnaScreen(ColorOnaGame colorOnaGame) {
        this.colorOnaGame = colorOnaGame;
        skin = new Skin(Gdx.files.internal("uiskin.json"));
    }

    public ColorOnaGame getColorOnaGame() {
        return colorOnaGame;
    }
}

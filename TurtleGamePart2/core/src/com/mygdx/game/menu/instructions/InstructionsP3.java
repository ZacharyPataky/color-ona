package com.mygdx.game.menu.instructions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.BaseActor;
import com.mygdx.game.BaseScreen;
import com.mygdx.game.ColorOnaGame;
import com.mygdx.game.ColorOnaScreen;
import com.mygdx.game.menu.MenuScreen;

public class InstructionsP3 extends ColorOnaScreen
{
    /////////////////////////////////////////////////////////////////////////////////
    // VARIABLE

    public static Sound buttonSound = Gdx.audio.newSound(Gdx.files.internal("buttonSound.wav"));

    /////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR

    public InstructionsP3(ColorOnaGame colorOnaGame) {
        super(colorOnaGame);
    }

    /////////////////////////////////////////////////////////////////////////////////
    // BASESCREEN_SHENANIGANS

    @Override
    public void initialize()
    {
        /////////////////////////////////////////////////////////////////////////////////
        // MAIN_PAGE

        BaseActor background = new BaseActor(0, 0, mainStage);
        background.loadTexture("credits.png");
        background.setSize(900, 900);

        /////////////////////////////////////////////////////////////////////////////////
        // PAGE_TWO

        Button.ButtonStyle buttonStyleP2 = new Button.ButtonStyle();

        Texture buttonTexP2 = new Texture(Gdx.files.internal("prev.png"));

        TextureRegion buttonRegionP2 = new TextureRegion(buttonTexP2);
        buttonStyleP2.up = new TextureRegionDrawable(buttonRegionP2);

        Button P2Button = new Button(buttonStyleP2);
        P2Button.setPosition(20, 813);
        P2Button.setSize(93, 70);
        uiStage.addActor(P2Button);

        P2Button.addListener(
                (Event e) ->
                {
                    if (!(e instanceof InputEvent))
                        return false;

                    if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                        return false;

                    buttonSound.play();
                    this.getColorOnaGame().showInstructions2Screen();
                    return true;
                }
        );

        /////////////////////////////////////////////////////////////////////////////////
        // MAIN_MENU

        Button.ButtonStyle buttonStyleMenu = new Button.ButtonStyle();

        Texture buttonTexMenu = new Texture(Gdx.files.internal("menu.png"));

        TextureRegion buttonRegionMenu = new TextureRegion(buttonTexMenu);
        buttonStyleMenu.up = new TextureRegionDrawable(buttonRegionMenu);

        Button menuButton = new Button(buttonStyleMenu);
        menuButton.setPosition(787, 813);
        menuButton.setSize(93, 70);
        uiStage.addActor(menuButton);

        menuButton.addListener(
                (Event e) ->
                {
                    if (!(e instanceof InputEvent))
                        return false;

                    if (!((InputEvent) e).getType().equals(InputEvent.Type.touchDown))
                        return false;

                    buttonSound.play();
                    this.getColorOnaGame().showMenuScreen();
                    return true;
                }
        );
    }

    @Override
    public void update(float dt) { }

    public boolean keyDown(int keyCode)
    {
        if (Gdx.input.isKeyPressed(Input.Keys.P)) { this.getColorOnaGame().showInstructions2Screen(); }
        if (Gdx.input.isKeyPressed(Input.Keys.M)) { this.getColorOnaGame().showMenuScreen(); }
        return false;
    }
}

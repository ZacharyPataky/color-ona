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
import com.mygdx.game.*;
import com.mygdx.game.menu.MenuScreen;

public class InstructionsP1 extends ColorOnaScreen
{
    /////////////////////////////////////////////////////////////////////////////////
    // VARIABLE

    public static Sound buttonSound = Gdx.audio.newSound(Gdx.files.internal("buttonSound.wav"));

    /////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR

    public InstructionsP1(ColorOnaGame colorOnaGame) {
        super(colorOnaGame);
    }

    /////////////////////////////////////////////////////////////////////////////////
    // BASESCREEN_SHENANIGANS

    @Override
    public void initialize()
    {
        /////////////////////////////////////////////////////////////////////////////////
        // MAIN_PAGE

        BaseActor background = new BaseActor(0,0, mainStage);
        background.loadTexture( "instructions1.png" );
        background.setSize(900,900);

        /////////////////////////////////////////////////////////////////////////////////
        // MAIN_MENU

        Button.ButtonStyle buttonStyleMenu = new Button.ButtonStyle();

        Texture buttonTexMenu = new Texture(Gdx.files.internal("menu.png"));

        TextureRegion buttonRegionMenu =  new TextureRegion(buttonTexMenu);
        buttonStyleMenu.up = new TextureRegionDrawable( buttonRegionMenu );

        Button menuButton = new Button( buttonStyleMenu );
        menuButton.setPosition(20, 813);
        menuButton.setSize(93, 70);
        uiStage.addActor(menuButton);

        menuButton.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;

                    buttonSound.play();
                    //ColorOnaGame.setActiveScreen(new MenuScreen());
                    this.getColorOnaGame().showMenuScreen();
                    return true;
                }
        );

        /////////////////////////////////////////////////////////////////////////////////
        // PAGE_TWO

        Button.ButtonStyle buttonStyleP2 = new Button.ButtonStyle();

        Texture buttonTexP2 = new Texture(Gdx.files.internal("next.png"));

        TextureRegion buttonRegionP2 =  new TextureRegion(buttonTexP2);
        buttonStyleP2.up = new TextureRegionDrawable( buttonRegionP2);

        Button P2Button = new Button( buttonStyleP2 );
        P2Button.setPosition(787, 813);
        P2Button.setSize(93, 70);
        uiStage.addActor(P2Button);

        P2Button.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;

                    buttonSound.play();
                    this.getColorOnaGame().showInstructions2Screen();
                    return true;
                }
        );
    }

    @Override
    public void update(float dt) { }

    public boolean keyDown(int keyCode)
    {
        if (Gdx.input.isKeyPressed(Input.Keys.M)) { this.getColorOnaGame().showMenuScreen(); }
        if (Gdx.input.isKeyPressed(Input.Keys.N)) { this.getColorOnaGame().showInstructions2Screen(); }
        return false;
    }
}

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

public class InstructionsP2 extends ColorOnaScreen
{
    /////////////////////////////////////////////////////////////////////////////////
    // VARIABLE

    public static Sound buttonSound = Gdx.audio.newSound(Gdx.files.internal("buttonSound.wav"));

    /////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR

    public InstructionsP2(ColorOnaGame colorOnaGame) {
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
        background.loadTexture( "instructions2.png" );
        background.setSize(900,900);

        /////////////////////////////////////////////////////////////////////////////////
        // PAGE_ONE

        Button.ButtonStyle buttonStyleP1 = new Button.ButtonStyle();

        Texture buttonTexP1 = new Texture(Gdx.files.internal("prev.png"));

        TextureRegion buttonRegionP1 =  new TextureRegion(buttonTexP1);
        buttonStyleP1.up = new TextureRegionDrawable( buttonRegionP1 );

        Button P1Button = new Button( buttonStyleP1 );
        P1Button.setPosition(20, 813);
        P1Button.setSize(93, 70);
        uiStage.addActor(P1Button);

        P1Button.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;

                    buttonSound.play();
                    this.getColorOnaGame().showInstructions1Screen();
                    return true;
                }
        );

        /////////////////////////////////////////////////////////////////////////////////
        // PAGE_THREE

        Button.ButtonStyle buttonStyleP3 = new Button.ButtonStyle();

        Texture buttonTexP3 = new Texture(Gdx.files.internal("credits2.png"));

        TextureRegion buttonRegionP3 =  new TextureRegion(buttonTexP3);
        buttonStyleP3.up = new TextureRegionDrawable( buttonRegionP3);

        Button P3Button = new Button( buttonStyleP3 );
        P3Button.setPosition(787, 813);
        P3Button.setSize(93, 70);
        uiStage.addActor(P3Button);

        P3Button.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;

                    buttonSound.play();
                    this.getColorOnaGame().showInstructions3Screen();
                    return true;
                }
        );
    }

    @Override
    public void update(float dt) { }

    public boolean keyDown(int keyCode)
    {
        if (Gdx.input.isKeyPressed(Input.Keys.P)) { this.getColorOnaGame().showInstructions1Screen(); }
        if (Gdx.input.isKeyPressed(Input.Keys.C)) { this.getColorOnaGame().showInstructions3Screen(); }
        return false;
    }
}

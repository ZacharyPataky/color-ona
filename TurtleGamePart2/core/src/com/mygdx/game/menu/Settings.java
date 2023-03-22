package com.mygdx.game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.*;

/**
 * @author Lee Stemkoski
 */
public class Settings extends ColorOnaScreen
{
    public Settings(ColorOnaGame colorOnaGame) { super(colorOnaGame); }

    public void initialize()
    {
        BaseActor background = new BaseActor(0,0, mainStage);
        background.loadTexture( "fire.jpg" );
        background.setSize(900,900);

        BaseActor title = new BaseActor(0,0, mainStage);
        title.loadTexture( "transparentlogo.png" );
        Double width = 0.4 * title.getWidth();
        Double height = 0.4 * title.getHeight();
        title.moveBy(0,100);
        title.setSize(width.floatValue(),height.floatValue());
        title.centerAtPosition(450,600);
        
        TextButton quitButton = new TextButton( "Quit", BaseGame.textButtonStyle );
        quitButton.setPosition(740,0);
        uiStage.addActor(quitButton);
        
        quitButton.addListener(
            (Event e) -> 
            { 
                if ( !(e instanceof InputEvent) )
                    return false;

                if ( !((InputEvent)e).getType().equals(Type.touchDown) )
                    return false;
                    
                Gdx.app.exit();
                return true;
            }
        );

        TextButton menuButton = new TextButton( "Menu", BaseGame.textButtonStyle );
        menuButton.setPosition(0,0);
        uiStage.addActor(menuButton);

        menuButton.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(Type.touchDown) )
                        return false;

                    this.getColorOnaGame().showMenuScreen();
                    return true;
                }
        );

        TextButton musicButton = new TextButton( "Play music", BaseGame.textButtonStyle );
        musicButton.setPosition(400,370);
        uiStage.addActor(musicButton);

        musicButton.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(Type.touchDown) )
                        return false;

                    if (MenuScreen.music.isPlaying()) { MenuScreen.music.pause(); }

                    return true;
                }
        );

        Label musicLabel = new Label("Music", BaseGame.labelStyle);
        musicLabel.setColor(Color.WHITE);
        musicLabel.setPosition(50,400);
        uiStage.addActor(musicLabel);

        Label diffLabel = new Label("Difficulty", BaseGame.labelStyle);
        diffLabel.setColor(Color.WHITE);
        diffLabel.setPosition(50,273);
        uiStage.addActor(diffLabel);

        TextButton easyButton = new TextButton( "Easy", BaseGame.textButtonStyle );
        easyButton.setPosition(300,250);
        uiStage.addActor(easyButton);

        easyButton.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(Type.touchDown) )
                        return false;


                    this.getColorOnaGame().showMenuScreen();
                    return true;
                }
        );

        TextButton normButton = new TextButton( "Normal", BaseGame.textButtonStyle );
        normButton.setPosition(450,250);
        uiStage.addActor(normButton);

        normButton.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(Type.touchDown) )
                        return false;


                    this.getColorOnaGame().showMenuScreen();
                    return true;
                }
        );

        TextButton hardButton = new TextButton( "Hard", BaseGame.textButtonStyle );
        hardButton.setPosition(675,250);
        uiStage.addActor(hardButton);

        hardButton.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(Type.touchDown) )
                        return false;


                    this.getColorOnaGame().showMenuScreen();
                    return true;
                }
        );

        Label godLabel = new Label("God Mode", BaseGame.labelStyle);
        godLabel.setColor(Color.WHITE);
        godLabel.setPosition(50,170);
        uiStage.addActor(godLabel);

        TextButton godButton = new TextButton( "God Mode", BaseGame.textButtonStyle );
        godButton.setPosition(423,135);
        uiStage.addActor(godButton);

        godButton.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(Type.touchDown) )
                        return false;

                    this.getColorOnaGame().showMenuScreen();
                    return true;
                }
        );
    }

    public void update(float dt)
    {

    }
}
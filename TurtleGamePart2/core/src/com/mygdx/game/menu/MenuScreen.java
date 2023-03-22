package com.mygdx.game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.*;
import com.mygdx.game.menu.instructions.InstructionsP1;

public class MenuScreen extends ColorOnaScreen
{

    public static boolean ignoreButtons; //ignore m/p/r/q buttons for name entry

    private Label versionLabel;

    public static Music music = Gdx.audio.newMusic(Gdx.files.internal("No one's around to help. Extended (Perfect loop).mp3"));
    public static boolean musicLock = false;

    // Button Sound Effect
    public static Sound buttonSound = Gdx.audio.newSound(Gdx.files.internal("buttonSound.wav"));

    public MenuScreen(ColorOnaGame colorOnaGame) {
        super(colorOnaGame);
    }

    public void initialize()
    {
        ignoreButtons=false;

        music.setVolume(0.5f);
        music.setLooping(true);
        if (!music.isPlaying() && !musicLock) { music.play(); }

        BaseActor background = new BaseActor(0,0, mainStage);
        background.loadTexture( "vapor1200.png" );
        background.setSize(900,900);

        BaseActor title = new BaseActor(0,0, mainStage);
        title.loadTexture( "transparentlogo.png" );
        Double width = 0.4 * title.getWidth();
        Double height = 0.4 * title.getHeight();
        title.moveBy(0,100);
        title.setSize(width.floatValue(),height.floatValue());
        title.centerAtPosition(450,540);

        /////////////////////////////////////////////////////////////////////////////////
        // START_BUTTON

        /*
        TextButton startButton = new TextButton( "Start Game", BaseGame.textButtonStyle );
        startButton.setPosition(100,300);
        uiStage.addActor(startButton);
        */

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        Texture buttonTex = new Texture(Gdx.files.internal("start.png"));

        TextureRegion buttonRegion =  new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable( buttonRegion );

        Button startButton = new Button( buttonStyle );
        startButton.setPosition(100,300);
        startButton.setSize(300, 131);
        uiStage.addActor(startButton);

        startButton.addListener(
                (Event e) ->
                        /*
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(Type.touchDown) )
                        return false;

                    buttonSound.play();
                    ColorOnaGame.setActiveScreen( new DebugLevel() );
                    return true;
                }
                        */
                {
                    InputEvent ie = (InputEvent)e;
                    if ( ie.getType().equals(Type.touchDown) )
                    {
                        buttonSound.play();
                        ColorOnaGame.setActiveScreen( new DebugLevel(this.getColorOnaGame()) );
                    }

                    return false;
                }
        );

        /////////////////////////////////////////////////////////////////////////////////
        // QUIT_BUTTON

        /*
        TextButton quitButton = new TextButton( "Quit Game", BaseGame.textButtonStyle );
        quitButton.setPosition(500,300);
        uiStage.addActor(quitButton);
        */

        Button.ButtonStyle buttonStyleQuit = new Button.ButtonStyle();

        Texture buttonTexQuit = new Texture( Gdx.files.internal("quit.png") );
        TextureRegion buttonRegionQuit =  new TextureRegion(buttonTexQuit);
        buttonStyleQuit.up = new TextureRegionDrawable( buttonRegionQuit );

        Button quitButton = new Button( buttonStyleQuit );
        quitButton.setPosition(500,150);
        quitButton.setSize(300, 131);
        uiStage.addActor(quitButton);

        quitButton.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(Type.touchDown) )
                        return false;

                    buttonSound.play();
                    Gdx.app.exit();
                    return true;
                }
        );

        /////////////////////////////////////////////////////////////////////////////////
        // INFORMATION_BUTTON

        /*
        TextButton InfoButton = new TextButton( "Information", BaseGame.textButtonStyle );
        InfoButton.setPosition(100,150);
        uiStage.addActor(InfoButton);
        */

        Button.ButtonStyle buttonStyleInfo = new Button.ButtonStyle();

        Texture buttonTexInfo = new Texture( Gdx.files.internal("info.png") );
        TextureRegion buttonRegionInfo =  new TextureRegion(buttonTexInfo);
        buttonStyleInfo.up = new TextureRegionDrawable( buttonRegionInfo );

        Button infoButton = new Button( buttonStyleInfo );
        infoButton.setPosition(500,300);
        infoButton.setSize(300, 131);
        uiStage.addActor(infoButton);

        infoButton.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(Type.touchDown) )
                        return false;

                    buttonSound.play();
                    this.getColorOnaGame().showInstructions1Screen();
                    return true;
                }
        );

        /////////////////////////////////////////////////////////////////////////////////
        // MUSIC_BUTTON

        /*
        TextButton MusicButton = new TextButton("Play Music", BaseGame.textButtonStyle);
        MusicButton.setPosition(100, 650);
        uiStage.addActor(MusicButton);
        */

        Button.ButtonStyle buttonStyleMusic = new Button.ButtonStyle();

        Texture buttonTexMusic = new Texture( Gdx.files.internal("sound.png") );
        TextureRegion buttonRegionMusic =  new TextureRegion(buttonTexMusic);
        buttonStyleMusic.up = new TextureRegionDrawable( buttonRegionMusic );

        Button musicButton = new Button( buttonStyleMusic );
        musicButton.setPosition(737,782);
        musicButton.setSize(138, 93);
        uiStage.addActor(musicButton);

        musicButton.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(Type.touchDown) )
                        return false;

                    if (music.isPlaying())
                    {
                        buttonSound.play();
                        music.pause();
                        musicLock = true;
                    }
                    else
                    {
                        buttonSound.play();
                        music.play();
                        musicLock = false;
                    }

                    return true;
                }
        );
/*
        /////////////////////////////////////////////////////////////////////////////////
        // SETTINGS_BUTTON

        TextButton SettButton = new TextButton( "Settings", BaseGame.textButtonStyle );
        SettButton.setPosition(500,150);
        uiStage.addActor(SettButton);

        SettButton.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(Type.touchDown) )
                        return false;

                    ColorOnaGame.setActiveScreen( new Settings() );
                    return true;
                }
        );
*/

        /////////////////////////////////////////////////////////////////////////////////
        // LEADERBOARD_BUTTON

        /*
        TextButton LeadButton = new TextButton( "Leaderboard", BaseGame.textButtonStyle );
        LeadButton.setPosition(100,150);
        uiStage.addActor(LeadButton);
        */

        Button.ButtonStyle buttonStyleLeaderboard = new Button.ButtonStyle();

        Texture buttonTexLeaderboard = new Texture( Gdx.files.internal("leaderboard.png") );
        TextureRegion buttonRegionLeaderboard =  new TextureRegion(buttonTexLeaderboard);
        buttonStyleLeaderboard.up = new TextureRegionDrawable( buttonRegionLeaderboard );

        Button leaderboardButton = new Button( buttonStyleLeaderboard );
        leaderboardButton.setPosition(100,150);
        leaderboardButton.setSize(300, 131);
        uiStage.addActor(leaderboardButton);

        leaderboardButton.addListener((Event e) ->
                {
                    if ( !(e instanceof InputEvent) ) { return false; }
                    if ( !((InputEvent)e).getType().equals(Type.touchDown) ) { return false; }

                    buttonSound.play();
                    this.getColorOnaGame().showLeaderboardScreen();
                    return true;
                }
        );

        /////////////////////////////////////////////////////////////////////////////////
        // VERSION_LABEL

        versionLabel = new Label("Final Build",BaseGame.labelStyle);
        versionLabel.setColor(Color.WHITE);
        versionLabel.setPosition(645,25);
        versionLabel.setSize(5,5);
        uiStage.addActor(versionLabel);
    }

    /////////////////////////////////////////////////////////////////////////////////
    // UPDATE

    public void update(float dt) { }

    /////////////////////////////////////////////////////////////////////////////////
    // INPUT/OUTPUT

    public boolean keyDown(int keyCode)
    {
        if (Gdx.input.isKeyPressed(Keys.ENTER)) { ColorOnaGame.setActiveScreen( new DebugLevel(this.getColorOnaGame()) ); }
        if (Gdx.input.isKeyPressed(Keys.ESCAPE)) { Gdx.app.exit(); }
        if (Gdx.input.isKeyPressed(Keys.I)) { this.getColorOnaGame().showInstructions1Screen(); }
        if (Gdx.input.isKeyPressed(Keys.L)) { this.getColorOnaGame().showLeaderboardScreen(); }
        return false;
    }
}
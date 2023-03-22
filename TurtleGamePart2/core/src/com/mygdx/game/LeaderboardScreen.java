package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mygdx.game.BaseActor;
import com.mygdx.game.BaseGame;
import com.mygdx.game.ColorOnaGame;
import com.mygdx.game.ColorOnaScreen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static com.badlogic.gdx.Gdx.app;
import static com.mygdx.game.menu.MenuScreen.buttonSound;
import static com.mygdx.game.menu.MenuScreen.ignoreButtons;

public class LeaderboardScreen extends ColorOnaScreen
{
    private Label versionLabel;
    private Table table;
    Scanner scan;
    int count ;
    Button resetButton;

    public static Sound buttonSound = Gdx.audio.newSound(Gdx.files.internal("buttonSound.wav"));

    public LeaderboardScreen(ColorOnaGame colorOnaGame) {
        super(colorOnaGame);
    }

    public void initialize() {
        BaseActor background = new BaseActor(0, 0, mainStage);
        background.loadTexture("vapor1200.png");
        background.setSize(900, 900);

        BaseActor title = new BaseActor(0, 0, mainStage);
        title.loadTexture("transparentlogo.png");
        Double width = 0.4 * title.getWidth();
        Double height = 0.4 * title.getHeight();
        title.moveBy(0, 100);
        title.setSize(width.floatValue(), height.floatValue());
        title.centerAtPosition(450, 820);

        table = new Table();
        table.setFillParent(true);
        table.center();
        uiStage.addActor(table);

        Button.ButtonStyle buttonStyleReset = new Button.ButtonStyle();

        Texture buttonTexReset = new Texture( Gdx.files.internal("resetscores.png") );
        TextureRegion buttonRegionReset =  new TextureRegion(buttonTexReset);
        buttonStyleReset.up = new TextureRegionDrawable( buttonRegionReset );

        resetButton = new Button( buttonStyleReset );
        resetButton.setPosition(263, 25);
        resetButton.setSize(373, 148);
        uiStage.addActor(resetButton);

        BaseActor leader = new BaseActor(0,0, mainStage);
        leader.loadTexture( "leaderboardtext.png" );
        Double width4 = 0.3 * leader.getWidth();
        Double height4 = 0.3 * leader.getHeight();
        leader.moveBy(0,100);
        leader.setSize(width4.floatValue(),height4.floatValue());
        leader.centerAtPosition(450,640);

        BaseActor square = new BaseActor(0,0, mainStage);
        square.loadTexture("grey.png");
        Double width2 = 0.3 * square.getWidth();
        Double height2 = 0.24 * square.getHeight();
        //title.moveBy(0,100);
        square.setSize(width2.floatValue(),height2.floatValue());
        square.centerAtPosition(450,450);


        Button.ButtonStyle buttonStyleMenu = new Button.ButtonStyle();

        Texture buttonTexMenu = new Texture(Gdx.files.internal("menu.png"));

        TextureRegion buttonRegionMenu =  new TextureRegion(buttonTexMenu);
        buttonStyleMenu.up = new TextureRegionDrawable( buttonRegionMenu );
        Button menuButton = new Button( buttonStyleMenu );
        menuButton.setPosition(25, 767);
        menuButton.setSize(142, 108);
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
    }

    @Override
    public void show() {
        ignoreButtons = false;
        super.show();

        table.clear();
        Label label = new Label("..............................", BaseGame.labelStyle);

        table.add(label).colspan(2);
        table.row();
        Path path = Paths.get("leaderboard.txt");
        File tempfile2 = new File(String.valueOf(path));
        if(!tempfile2.exists()){
            new File(String.valueOf(path));
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(path)));
            Scanner scan = new Scanner(tempfile2);
            count = 1;

            while(scan.hasNextLine()){
                String value = scan.next();
                String key = scan.next();

                table.add(new Label(count + ") "+ value , BaseGame.smallLabelStyle)).left();
                table.add(new Label(key, BaseGame.smallLabelStyle));
                table.row();
                count ++;

                if(scan.nextLine()==null){
                    break;
                }
            }
            scan.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //table.add(new Label(". . . . . . . . . . . . . . . . .", BaseGame.labelStyle));

        resetButton.addListener(
                (Event ev) ->
                {
                    if ( !(ev instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)ev).getType().equals(InputEvent.Type.touchDown) )
                        return false;
                    buttonSound.play();
                    String fileName = "leaderboard.txt";
                    try {
                        Files.delete(Paths.get(fileName));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String fileName2 = "leaderboard.txt";
                    File newLeaderBoard = new File(fileName2);
                    try {
                        newLeaderBoard.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    this.getColorOnaGame().showMenuScreen();
                    return true;
                }
        );
    }

    /////////////////////////////////////////////////////////////////////////////////
    // UPDATE

    public void update(float dt) { }

    /////////////////////////////////////////////////////////////////////////////////
    // INPUT/OUTPUT

    public boolean keyDown(int keyCode) {
        if (Gdx.input.isKeyPressed(Keys.M)) { this.getColorOnaGame().showMenuScreen(); }
        return false;
    }
}
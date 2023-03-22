package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.menu.MenuScreen;

import static com.mygdx.game.menu.MenuScreen.ignoreButtons;

import java.io.*;
import java.nio.file.*;
import java.util.*;

// Credit GabLeg on stack overflow


public class Leaderboard {


    SortedMap<Integer,String> highScores = new TreeMap<Integer, String>(Collections.reverseOrder());
    private Table table;
    private Skin skin;
    private TextField nameTextField;
    Dialog dialog;
    ColorOnaScreen colorOnaScreen;

    private int finalScore;

    public Leaderboard(ColorOnaScreen colorOnaScreen) {
        this.colorOnaScreen = colorOnaScreen;

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        nameTextField = new TextField("", skin);
        nameTextField.addListener(new InputListener(){
            public boolean keyDown (InputEvent event, int keycode) {
                if (keycode == Input.Keys.ENTER) {
                    updateLeaderboard();
                }
                return false;
            }
        });

        dialog = new Dialog("New High Score!", skin);
        dialog.center();
        dialog.setMovable(false);
        dialog.setPosition(250,250);
        dialog.add(new Label("Enter Name:", skin));
        dialog.add(nameTextField);
    }

    public void endGame(int finalScore) throws IOException {
        SortedMap<Integer, String> highScores = retrieveScores();
        if (highScores.isEmpty() || finalScore > highScores.lastKey()) {
            ignoreButtons = true;
            this.finalScore = finalScore;
            dialog.show(colorOnaScreen.uiStage);
            colorOnaScreen.uiStage.setKeyboardFocus(nameTextField);
        }
    }

    private void updateLeaderboard() {
        dialog.hide();
        addNewScore();
        try {
            writeScores();
        } catch (IOException e) {
            e.printStackTrace();
        }
        colorOnaScreen.getColorOnaGame().showLeaderboardScreen();
    }

    private SortedMap<Integer,String> retrieveScores() throws IOException {
        Path path = Paths.get("leaderboard.txt");
        File tempfile2 = new File(String.valueOf(path));
        if(!tempfile2.exists()){
            tempfile2.createNewFile();
        }
        Scanner scan = new Scanner(tempfile2);

        while(scan.hasNext()){
           String value = scan.next();
           String key = scan.next();
           highScores.put(Integer.parseInt(key), value);
        }

        scan.close();
        return highScores;
    }
    private void addNewScore() {
        String str = nameTextField.getText();
        str = str.replaceAll("\\s", "");
        if (str.equals("")) { str = "NULL"; }

        highScores.put(finalScore, str);
        if (highScores.size() > 10) {
            highScores.remove(highScores.lastKey());
        }
    }
    private void writeScores() throws IOException {
        Path path = Paths.get("leaderboard.txt");
        FileWriter writer = new FileWriter(String.valueOf(path));
        File tempfile2 = new File(String.valueOf(path));
        if(!tempfile2.exists()){

            new File(String.valueOf(path));
        }

        for(Map.Entry mapElement : highScores.entrySet() ){
            String value = (String) mapElement.getValue();
            writer.write(value + " ");
            writer.write(mapElement.getKey().toString()+"\n");
        }

        writer.close();
    }
    private void showLeaderBoard() {
        System.out.println("*** TOP 5 LEADERBOARD ***");
        int count = 1;

        for (Map.Entry mapElement : highScores.entrySet()) {

            int key = (int) mapElement.getKey();

            // Finding the value
            String value = (String) mapElement.getValue();

            System.out.println(Integer.toString(count) + ") " + value + " : " + key);
            count ++;

        }
        System.out.println("*** TOP 5 LEADERBOARD ***");
    }
}

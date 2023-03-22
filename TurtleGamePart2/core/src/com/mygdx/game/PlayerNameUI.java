package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.*;


public class PlayerNameUI extends BaseScreen {
    private Label versionLabel;
    private Table table;
    private Skin skin;
    public TextField nameTextField;
    private String playerName;
    Dialog dialog;

    public void initialize() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        nameTextField = new TextField("", skin);

        dialog = new Dialog("Enter Name, Winner!", skin);
        dialog.setPosition(250,250);

        dialog.add(new Label("Enter Name:", skin));
        dialog.add(nameTextField);
        this.show();

        uiStage.addActor(dialog);
    }


    public void update(float dt) { }

    public boolean keyDown(int keyCode)
    {
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            System.out.println(nameTextField.getText());
            dialog.hide();
        }

       return false;
    }
}

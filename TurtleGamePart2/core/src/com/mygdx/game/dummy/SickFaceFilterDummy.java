package com.mygdx.game.dummy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.DebugLevel;

public class SickFaceFilterDummy extends BaseDummy
{
    /////////////////////////////////////////////////////////////////////////////////
    // VARIABLES

    public int dummyColor;

    /////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR

    public SickFaceFilterDummy(float x, float y, Stage s)
    {
        super(x,y,s);
        //this.centerAtActor(DebugLevel.player);

        this.loadTexture("covereyes dummy.png");
        //this.loadTexture("player_white_60_idle_noeyes.png");
        this.setColor(new Color(DebugLevel.player.hexColor));
        //this.setColor(new Color(0xffffffff));
        this.visible = false;
        this.setVisible(this.visible);
        this.setBoundaryPolygon(8);
    }

    /////////////////////////////////////////////////////////////////////////////////
    // METHODS

    public void act(float dt)
    {
        this.centerAtActor(DebugLevel.player);
    }
}

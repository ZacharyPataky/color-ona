package com.mygdx.game.dummy;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.BaseActor;
import com.mygdx.game.DebugLevel;

public class SickDummy extends BaseDummy
{
    /////////////////////////////////////////////////////////////////////////////////
    // VARIABLES

    // FIXME

    /////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR

    public SickDummy(float x, float y, Stage s)
    {
        super(x,y,s);
        //this.centerAtActor(DebugLevel.player);

        // this.loadTexture("dummy.png");
        this.loadTexture("sickface_60.png");
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

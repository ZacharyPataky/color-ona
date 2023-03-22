package com.mygdx.game.dummy;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.DebugLevel;

public class OneUpDummy extends BaseDummy
{
    /////////////////////////////////////////////////////////////////////////////////
    // VARIABLE

    // FIXME

    /////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR

    public OneUpDummy(float x, float y, Stage s)
    {
        super(x,y,s);
        this.centerAtActor(DebugLevel.player);

        // this.loadTexture("dummy.png");
        this.loadTexture("pill.png");
        this.setSize(45, 25);
        this.visible = false;
        this.setVisible(this.visible);
        this.setBoundaryPolygon(8);
    }

    /////////////////////////////////////////////////////////////////////////////////
    // METHOD

    public void act(float dt)
    {
        this.centerAtActor(DebugLevel.player);
    }
}

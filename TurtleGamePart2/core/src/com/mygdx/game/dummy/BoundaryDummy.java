package com.mygdx.game.dummy;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.BaseActor;
import com.mygdx.game.DebugLevel;
import com.mygdx.game.Player;
import com.mygdx.game.obstacle.McDouble;

import java.io.IOException;

public class BoundaryDummy extends BaseDummy
{
    /////////////////////////////////////////////////////////////////////////////////
    // VARIABLES

    boolean boundary;
    boolean lockPlace;
    boolean initialize;

    /////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR

    public BoundaryDummy(float x, float y, Stage s, boolean boundary)
    {
        super(x,y,s);
        this.boundary = boundary;
        this.lockPlace = false;
        this.initialize = true;
        this.centerAtActor(DebugLevel.player);

        //this.loadTexture("dummy.png");
        //this.loadTexture("player_white_60_idle.png");
        this.loadTexture("1x1.png");
        this.setVisible(false);
        this.setBoundaryPolygon(8);
    }

    /////////////////////////////////////////////////////////////////////////////////
    // METHODS

    public void act(float dt)
    {
        this.wrapAroundWorld();

        if (this.initialize)
        {
            this.alignCamera();
            this.initialize = false;
        }

        if ((DebugLevel.player.state == Player.State.JUMPING) && (!this.lockPlace))
        {
            //DebugLevel.player.get
            this.centerAtActor(DebugLevel.player);
            this.alignCamera();
        }
        else
        {
            this.lockPlace = true;
            //if (DebugLevel.player.overlaps(this)) { this.lockPlace = false; }
            if (DebugLevel.player.isWithinDistance(0, this)) {this.lockPlace = false; }
        }
    }
}

//spawnchunks: Changes have been made to this file!!!


package com.mygdx.game.modifier;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.DebugLevel;
import com.mygdx.game.Player;

public class PlagueParticle_Green extends PlagueParticle implements Pool.Poolable
{
    /////////////////////////////////////////////////////////////////////////////////
    // VARIABLE

    public static Sound greenCoronaSound = Gdx.audio.newSound(Gdx.files.internal("greenCoronaSound.wav"));

    /////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR

    //spawnchunks addition: next 2 lines
    public int arrayIndexX;
    public int arrayIndexY;

    //public PlagueParticle_Green(float x, float y, Stage s) spawnchunks replace with next line
    public PlagueParticle_Green(float x, float y, Stage s, int arrIndexX, int arrIndexY)
    {
        super( x, y, s );
        loadTexture("green_corona.png");
        // changed from 50 to 100 for resize
        setSize(100, 100);
        setBoundaryPolygon(8);
        setOrigin(50, 50);

        //spawnchunks addition: next 2 lines
        arrayIndexX = arrIndexX;
        arrayIndexY = arrIndexY;

        // Action spin = Actions.rotateBy(-60, 1);
        // this.addAction( Actions.forever(spin) );
    }

    /////////////////////////////////////////////////////////////////////////////////
    // METHOD

    // Rotate Clockwise
    @Override
    public void rotate(Player player)
    {
        if((player.overlaps(this)) && !this.isCollected)
        {
            player.rotateBy(270);
            DebugLevel.dummySickFace.rotateBy(270);
            DebugLevel.dummySickFace.setVisible(true);
            DebugLevel.dummySickFaceFilter.rotateBy(270);
            DebugLevel.dummySickFaceFilter.setVisible(true);

            this.clearActions();
            this.addAction(Actions.fadeOut(1));

            this.isCollected = true;

            greenCoronaSound.play();

            switch (player.orient)
            {
                case FALL_DOWN:
                    player.orient = Player.Orientation.FALL_LEFT;
                    DebugLevel.snapPlayerY(player);
                    break;
                case FALL_LEFT:
                    player.orient = Player.Orientation.FALL_UP;
                    DebugLevel.snapPlayerX(player);
                    break;
                case FALL_UP:
                    player.orient = Player.Orientation.FALL_RIGHT;
                    DebugLevel.snapPlayerY(player);
                    break;
                case FALL_RIGHT:
                    player.orient = Player.Orientation.FALL_DOWN;
                    DebugLevel.snapPlayerX(player);
                    break;
            }
        }
    }

    public void init(float x, float y, int arrIndexX, int arrIndexY) {
        setOpacity(1);  // Ensure it's alpha/opacity is restored if it was collected
        setPosition(x,y);
        arrayIndexX = arrIndexX;
        arrayIndexY = arrIndexY;
        isCollected = false;
        Action spin = Actions.rotateBy(-60, 1);
        this.addAction( Actions.forever(spin) );
    }

    @Override
    public void reset() {
        this.clearActions();
        isCollected=false;
        arrayIndexY=0;
        arrayIndexX=0;
        setOpacity(0);
    }
}


//spawnchunks: Changes have been made to this file!!!

package com.mygdx.game.modifier;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.BaseActor;
import com.mygdx.game.DebugLevel;
import com.mygdx.game.Player;


public class OneUp extends BaseModifier implements Pool.Poolable
{
    /////////////////////////////////////////////////////////////////////////////////
    // VARIABLE

    public static Sound oneUpSound = Gdx.audio.newSound(Gdx.files.internal("oneUpSound.mp3"));

    /////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR

    //spawnchunks addition: next 2 lines
    public int arrayIndexX;
    public int arrayIndexY;

    //public OneUp(float x, float y, Stage s)
    public OneUp(float x, float y, Stage s, int arrIndexX, int arrIndexY)
    {
        // Call constructor from BaseActor class
        super( x, y, s );
        loadTexture("pill bottle.png");
        // changed from 50 to 100 for resize
        setSize(78, 100);
        setBoundaryPolygon(8);
        setOrigin(50, 50);

        //spawnchunks addition next 2 lines
        arrayIndexX = arrIndexX;
        arrayIndexY = arrIndexY;

        //Action spin = Actions.rotateBy(-60, 1);
        //this.addAction( Actions.forever(spin) );
    }

    /////////////////////////////////////////////////////////////////////////////////
    // METHOD

    public void gainOneUp(Player player)
    {
        if (player.overlaps(this) && !this.isCollected && !player.hasOneUp)
        {
            System.out.println("OneUp: Touched a OneUp"); //spawnchunks addition

            DebugLevel.dummySickFace.setVisible(false);
            DebugLevel.dummySickFaceFilter.setVisible(false);

            this.clearActions();
            this.addAction(Actions.fadeOut(1));

            this.isCollected = true;
            player.hasOneUp = true;
            DebugLevel.dummyOneUp.setVisible(true);

            oneUpSound.play();
        }
    }

    public void init(float x, float y, int arrIndexX, int arrIndexY) {
        setOpacity(1);  // Ensure it's alpha/opacity is restored if it was collected
        setPosition(x, y);
        arrayIndexX = arrIndexX;
        arrayIndexY = arrIndexY;
        isCollected = false;
        Action spin = Actions.rotateBy(-60, 1);
        this.addAction(Actions.forever(spin));
    }

    @Override
    public void reset() {
        this.clearActions();
        isCollected=false;
        arrayIndexX=0;
        arrayIndexY=0;
        setOpacity(0);
    }
}
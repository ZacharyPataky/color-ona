
//spawnchunks: Changes have been made to this file!!!

package com.mygdx.game.obstacle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.BaseActor;
import com.mygdx.game.DebugLevel;
import com.mygdx.game.Player;

public class McDouble extends BaseActor implements Pool.Poolable
{
    /////////////////////////////////////////////////////////////////////////////////
    // VARIABLE

    //spawnchunks: next 2 lines
    public int arrayIndexX;
    public int arrayIndexY;

    public boolean isCollected;
    public static int score = 0;

    public static Sound mcDoubleSound = Gdx.audio.newSound(Gdx.files.internal("mcDoubleSound.wav"));

    /////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR

    //public McDouble(float x, float y, Stage s) spawnchunks replaced with next line
    public McDouble(float x, float y, Stage s, int arrIndexX, int arrIndexY)
    {
        super(x, y, s);

        //spawnchunks addition next 2 lines
        arrayIndexX = arrIndexX;
        arrayIndexY = arrIndexY;

        loadTexture("mcdouble.png");
        setSize(100, 100);
        setBoundaryPolygon(8);
        setOrigin(50, 50);

        isCollected = false;
    }

    public void init(float x, float y, int arrIndexX, int arrIndexY) {
        setOpacity(1);  // Ensure it's alpha/opacity is restored if it was collected
        setPosition(x,y);
        arrayIndexX = arrIndexX;
        arrayIndexY = arrIndexY;
        isCollected = false;

        Action spin = Actions.rotateBy(60, 1);
        this.addAction( Actions.forever(spin) );
    }

    /////////////////////////////////////////////////////////////////////////////////
    // METHOD

    public void gainPoint(Player player)
    {
        if ((player.overlaps(this)) && !this.isCollected)
        {
            System.out.println("MD: Touched a MD"); //spawnchunks addition

            mcDoubleSound.play();
            DebugLevel.dummySickFace.setVisible(false);
            DebugLevel.dummySickFaceFilter.setVisible(false);
            this.isCollected = true;
            score++;

            this.clearActions();

            // Just fade out the actor, which sets its alpha to 0 (or Opacity to 0)
            this.addAction(Actions.fadeOut(1));
        }
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

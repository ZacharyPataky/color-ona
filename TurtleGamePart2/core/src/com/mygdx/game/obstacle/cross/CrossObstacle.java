package com.mygdx.game.obstacle.cross;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.DebugLevel;
import com.mygdx.game.Player;
import com.mygdx.game.obstacle.BaseObstacle;

public class CrossObstacle extends BaseObstacle implements Pool.Poolable
{
    /////////////////////////////////////////////////////////////////////////////////
    // VARIABLE

    public int crossColor = 0xffffffff;

    //spawnchunk: next 3 lines
    public int arrayIndexX;
    public int arrayIndexY;

    boolean deadObstacle;

    /////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR

    public CrossObstacle(float x, float y, Stage s, int arrIndexX, int arrIndexY)
    {
        super(x - 104, y - 104, s);

        //spawnchunks addition: next 2 lines
        arrayIndexX = arrIndexX;
        arrayIndexY = arrIndexY;

        deadObstacle = false;

        this.loadTexture("cross.png");
        float[] vertices = {
                137, 0,
                163, 0,
                163, 137,
                300, 137,
                300, 163,
                163, 163,
                163, 300,
                137, 300,
                137, 163,
                0, 163,
                0, 137,
                137, 137
        };
        this.setBoundaryRectangle();
        this.getBoundaryPolygon().setVertices(vertices);
        this.setColor(new Color(crossColor));

        // Action spin = Actions.rotateBy(60, 1);
        // this.addAction( Actions.forever(spin) );
    }

    /////////////////////////////////////////////////////////////////////////////////
    // METHOD

    public void init(float x, float y, int arrIndexX, int arrIndexY)
    {
        setOpacity(1);  // Ensure it's alpha/opacity is restored if it was collected
        setPosition(x - 104, y - 104);

        arrayIndexX = arrIndexX;
        arrayIndexY = arrIndexY;

        deadObstacle = false;

        this.clearActions();

        crossColor = obstacleColors[colorTick];
        setColor(new Color(crossColor));

        Action spin = Actions.rotateBy(60, 1);
        this.addAction( Actions.forever(spin) );
    }

    @Override
    public void reset() {
        this.clearActions();
        arrayIndexX=0;
        arrayIndexY=0;
        setOpacity(0);
    }

    @Override
    public void drawDebugBounds(ShapeRenderer shapes)
    {
        shapes.polygon(getBoundaryPolygon().getTransformedVertices());
    }

    @Override
    public boolean playerDies(Player player)
    {
        if ((player.overlaps(this)) && (player.hexColor != this.crossColor) && (!player.hasOneUp) && (DebugLevel.player.state != Player.State.DEAD) && (!deadObstacle))
        {
            playerDeathSound.play();
            this.setVisible(false);
            DebugLevel.dummySickFace.setVisible(false);
            DebugLevel.dummySickFaceFilter.setVisible(false);
            DebugLevel.dummySickFace.setVisible(false);
            player.state = player.state.DEAD;
            return true;
            // System.exit(0);
        }
        else if ((player.overlaps(this)) && (player.hexColor != this.crossColor) && (player.hasOneUp))
        {
            this.setVisible(false);
            obstacleShieldSound.play();
            player.hasOneUp = false;
            DebugLevel.dummyOneUp.setVisible(false);
            deadObstacle = true;
            this.clearActions();
            this.addAction(Actions.fadeOut(1));
            //this.addAction(Actions.after(Actions.removeActor()));
            //this.remove();
        }

        return false;
    }

    public void crossColorChange()
    {
        crossColor = obstacleColors[colorTick];
        this.setColor(new Color(crossColor));
    }
}

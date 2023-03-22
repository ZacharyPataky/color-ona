
//spawnchunks: no changes made to this file

package com.mygdx.game.obstacle.square;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.DebugLevel;
import com.mygdx.game.Player;
import com.mygdx.game.obstacle.BaseObstacle;

public class SquareComponent extends BaseObstacle
{
    /////////////////////////////////////////////////////////////////////////////////
    // VARIABLE

    public int componentColor = 0xffffffff;

    public boolean deadObstacle;

    /////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR

    public SquareComponent(String fileName, float x, float y, Stage s)
    {
        super(x, y, s);
        if (fileName != null) { loadTexture(fileName); }
        float[] vertices = { 0, 300, 300, 300, 300, 275, 0, 275 };
        this.setBoundaryRectangle();
        this.getBoundaryPolygon().setVertices(vertices);
        this.deadObstacle = false;
        setColor(new Color(componentColor));
    }

    /////////////////////////////////////////////////////////////////////////////////
    // METHOD

    @Override
    public void drawDebugBounds(ShapeRenderer shapes)
    {
        shapes.polygon(getBoundaryPolygon().getTransformedVertices());
    }

    @Override
    public boolean playerDies(Player player)
    {
        if ((player.overlaps(this)) && (player.hexColor != this.componentColor) && (!player.hasOneUp) && (DebugLevel.player.state != Player.State.DEAD) && (!deadObstacle))
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
        else if ((player.overlaps(this)) && (player.hexColor != this.componentColor) && (player.hasOneUp))
        {
            this.setVisible(false);
            obstacleShieldSound.play();
            player.hasOneUp = false;
            DebugLevel.dummyOneUp.setVisible(false);
            this.deadObstacle = true;
            this.clearActions();
            this.addAction(Actions.fadeOut(1));
            this.addAction(Actions.after(Actions.removeActor()));
            this.remove();
        }

        return false;
    }

    public void init(float x, float y){
        setOpacity(1);  // Ensure it's alpha/opacity is restored if it was collected
        setPosition(x,y);
        setVisible(true);
        this.deadObstacle = false;
        this.clearActions(); //resets it to prevent "action stacking" which results in CRAZY FAST spinning
        //Action spin = Actions.rotateBy(30, 1);
        //addAction( Actions.forever(spin) );
    }

    public void reset(){
        setOpacity(0);
        clearActions();
    }
}


//spawnchunks: no changes have been made to this file

package com.mygdx.game.obstacle.circle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.BaseActor;
import com.mygdx.game.DebugLevel;
import com.mygdx.game.Player;
import com.mygdx.game.obstacle.BaseObstacle;

public class CircleComponent extends BaseObstacle
{
    /////////////////////////////////////////////////////////////////////////////////
    // VARIABLE

    public int componentColor = 0xffffffff;

    public boolean deadObstacle;

    /////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR

    public CircleComponent(String fileName, float x, float y, Stage s)
    {
        super(x, y, s);
        if (fileName != null) { loadTexture(fileName); }
        float[] vertices = {
                0, 0, // Beginning of Outer Side
                2, 30,
                10, 54,
                22, 78,
                38, 98,
                54, 114,
                70, 126,
                88, 136,
                108, 144,
                128, 148,
                150 /*148*/, 150, // End of Outer Side
                150 /*148*/, 127 /*125*/, // Side
                136, 125 /*122*/, //Beginning of Inner Side
                114, 122 /*118*/,
                88, 112 /*108*/,
                66, 96 /*92*/,
                46, 74 /*70*/,
                34, 52 /*48*/,
                30, 40, // All new
                27 /*26*/, 29/*22*/, // End of Inner Side
                23 /*26*/, 0 // Side
        };
        this.setBoundaryRectangle();
        this.getBoundaryPolygon().setVertices(vertices);
        setColor(new Color(componentColor));

        deadObstacle = false;
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
            //this.addAction(Actions.after(Actions.removeActor()));
            //this.remove();
        }

        return false;
    }

    public void init(float x, float y){
        setOpacity(1);  // Ensure it's alpha/opacity is restored if it was collected
        setPosition(x,y);
        setVisible(true);
        this.clearActions(); //resets it to prevent "action stacking" which results in CRAZY FAST spinning
        this.deadObstacle = false;
        Action spin = Actions.rotateBy(30, 1);
        addAction( Actions.forever(spin) );
    }

    public void reset(){
        setOpacity(0);
        clearActions();
    }
}
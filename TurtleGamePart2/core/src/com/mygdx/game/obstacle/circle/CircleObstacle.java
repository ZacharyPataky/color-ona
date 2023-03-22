

//spawnchunks: Changes have been made to this file!!!

package com.mygdx.game.obstacle.circle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Player;
import com.mygdx.game.obstacle.BaseObstacle;

public class CircleObstacle extends BaseObstacle implements Pool.Poolable
{
    /////////////////////////////////////////////////////////////////////////////////
    // VARIABLE

    public CircleComponent topLeft;
    public CircleComponent topRight;
    public CircleComponent bottomLeft;
    public CircleComponent bottomRight;

    //spawnchunk: next 3 lines
    public int arrayIndexX;
    public int arrayIndexY;

    /////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR

    //public CircleObstacle(float x, float y, Stage s) spawnchunks: replaced w/ next line
    public CircleObstacle(float x, float y, Stage s, int arrIndexX, int arrIndexY)
    {
        super(x, y, s);

        //spawnchunks addition: next 2 lines
        arrayIndexX = arrIndexX;
        arrayIndexY = arrIndexY;

        topLeft = new CircleComponent("topleft2.png", x - 104, y + 44, s);
        topLeft.componentColor = obstacleColors[0];
        topLeft.setColor(new Color(topLeft.componentColor));
        topLeft.setOrigin(150,0);
        Action spinTL = Actions.rotateBy(60, 1);
        topLeft.addAction( Actions.forever(spinTL) );

        topRight = new CircleComponent("topleft2.png", x - 104, y + 44, s);
        topRight.componentColor = obstacleColors[1];
        topRight.setColor((new Color(topRight.componentColor)));
        topRight.rotateBy(270);
        topRight.setOrigin(150,0);
        Action spinTR = Actions.rotateBy(60, 1);
        topRight.addAction( Actions.forever(spinTR) );

        bottomLeft = new CircleComponent("topleft2.png", x - 104, y + 44, s);
        bottomLeft.componentColor = obstacleColors[2];
        bottomLeft.setColor((new Color(bottomLeft.componentColor)));
        bottomLeft.rotateBy(90);
        bottomLeft.setOrigin(150,0);
        Action spinBL = Actions.rotateBy(60, 1);
        bottomLeft.addAction( Actions.forever(spinBL) );

        bottomRight = new CircleComponent("topleft2.png", x -104, y + 44, s);
        bottomRight.componentColor = obstacleColors[3];
        bottomRight.setColor((new Color(bottomRight.componentColor)));
        bottomRight.rotateBy(180);
        bottomRight.setOrigin(150, 0);
        Action spinBR = Actions.rotateBy(60, 1);
        bottomRight.addAction( Actions.forever(spinBR) );

        //spawnchunks: next line
    }

    public void init(float x, float y, int arrIndexX, int arrIndexY) {
        topLeft.init(x-104, y+44);
        topRight.init(x-104, y+44);
        bottomLeft.init(x-104, y+44);
        bottomRight.init(x-104, y+44);

        //Set this obstacle to the current color pallete
        topLeft.setColor(new Color(BaseObstacle.obstacleColors[0]));
        topLeft.componentColor = BaseObstacle.obstacleColors[0];

        topRight.setColor(new Color(BaseObstacle.obstacleColors[1]));
        topRight.componentColor = BaseObstacle.obstacleColors[1];

        bottomLeft.setColor(new Color(BaseObstacle.obstacleColors[2]));
        bottomLeft.componentColor = BaseObstacle.obstacleColors[2];

        bottomRight.setColor(new Color(BaseObstacle.obstacleColors[3]));
        bottomRight.componentColor = BaseObstacle.obstacleColors[3];

        arrayIndexX = arrIndexX;
        arrayIndexY = arrIndexY;

        setPosition(x,y);
    }

    /////////////////////////////////////////////////////////////////////////////////
    // METHOD

    @Override
    public boolean playerDies(Player player)
    {
        if (topLeft.playerDies(player)) return true;
        if (topRight.playerDies(player)) return true;
        if (bottomLeft.playerDies(player)) return true;
        if (bottomRight.playerDies(player)) return true;
        return false;
    }

    @Override
    public void reset() {
        this.clearActions();

        topLeft.reset();
        topRight.reset();
        bottomLeft.reset();
        bottomRight.reset();

        arrayIndexX=0;
        arrayIndexY=0;
    }
}
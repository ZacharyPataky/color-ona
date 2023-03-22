

//spawnchunks: Changes have been made to this file!!!

package com.mygdx.game.obstacle.square;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Player;
import com.mygdx.game.obstacle.BaseObstacle;
import com.mygdx.game.obstacle.circle.CircleComponent;

public class SquareObstacle extends BaseObstacle implements Pool.Poolable
{
    /////////////////////////////////////////////////////////////////////////////////
    // VARIABLE

    //spawnchunks addition: next 2 lines
    public int arrayIndexX;
    public int arrayIndexY;

    public SquareComponent top;
    public SquareComponent bottom;
    public SquareComponent left;
    public SquareComponent right;

    //spawnchunks addition: next line
    public static SquareComponent[] componentArr;

    /////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR

    //public SquareObstacle(float x, float y, Stage s) spawnchunks: replaced with next line
    public SquareObstacle(float x, float y, Stage s, int arrIndexX, int arrIndexY)
    {
        super(x, y, s);

        //spawnchunks addition: next 2 lines
        arrayIndexX = arrIndexX;
        arrayIndexY = arrIndexY;

        top = new SquareComponent("square25_top.png", x - 104, y - 104, s);
        top.componentColor = BaseObstacle.obstacleColors[0];
        top.setColor(new Color(top.componentColor));

        bottom = new SquareComponent("square25_top.png", x - 104, y - 104, s);
        bottom.rotateBy(180);
        bottom.componentColor = BaseObstacle.obstacleColors[2];
        bottom.setColor(new Color(bottom.componentColor));

        left = new SquareComponent("square25_top.png", x - 105, y - 104, s);
        left.rotateBy(90);
        left.componentColor = BaseObstacle.obstacleColors[3];
        left.setColor(new Color(left.componentColor));

        right = new SquareComponent("square25_top.png", x - 103, y - 104, s);
        right.rotateBy(270);
        right.componentColor = BaseObstacle.obstacleColors[1];
        right.setColor(new Color(right.componentColor));

        //chunkspawn addition: next line
        componentArr = new SquareComponent[]{ top, right, bottom, left };
    }

    /////////////////////////////////////////////////////////////////////////////////
    // METHOD

    public void squareColorChange()
    {
        int topColor = top.componentColor;

        top.componentColor = right.componentColor;
        top.setColor(new Color(top.componentColor));
        right.componentColor = bottom.componentColor;
        right.setColor(new Color(right.componentColor));
        bottom.componentColor = left.componentColor;
        bottom.setColor(new Color(bottom.componentColor));
        left.componentColor = topColor;
        left.setColor(new Color(left.componentColor));
    }

    @Override
    public boolean playerDies(Player player)
    {
        if (top.playerDies(player)) return true;
        if (right.playerDies(player)) return true;
        if (left.playerDies(player)) return true;
        if (bottom.playerDies(player)) return true;
        return false;
    }

    public void init(float x, float y, int arrIndexX, int arrIndexY) {
        top.init(x-104, y-104);
        bottom.init(x-104, y-104);
        left.init(x-105, y-104);
        right.init(x-103, y-104);

        top.setColor(new Color(BaseObstacle.obstacleColors[0]));
        top.componentColor = BaseObstacle.obstacleColors[0];

        right.setColor(new Color(BaseObstacle.obstacleColors[1]));
        right.componentColor = BaseObstacle.obstacleColors[1];

        bottom.setColor(new Color(BaseObstacle.obstacleColors[2]));
        bottom.componentColor = BaseObstacle.obstacleColors[2];

        left.setColor(new Color(BaseObstacle.obstacleColors[3]));
        left.componentColor = BaseObstacle.obstacleColors[3];

        arrayIndexX = arrIndexX;
        arrayIndexY = arrIndexY;

        setPosition(x,y);
    }

    @Override
    public void reset() {
        this.clearActions();

        top.reset();
        bottom.reset();
        left.reset();
        right.reset();

        arrayIndexX=0;
        arrayIndexY=0;
    }
}

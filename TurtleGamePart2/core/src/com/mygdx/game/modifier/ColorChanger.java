
//spawnchunks: Changes have been made to this file!!!

package com.mygdx.game.modifier;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.*;
import com.mygdx.game.obstacle.BaseObstacle;
import com.mygdx.game.obstacle.circle.CircleObstacle;
import com.mygdx.game.obstacle.cross.CrossObstacle;
import com.mygdx.game.obstacle.line.LineObstacle;
import com.mygdx.game.obstacle.square.SquareObstacle;

import java.util.ArrayList; //spawnchunks addition

public class ColorChanger extends BaseModifier implements ColorPalette, Pool.Poolable
{
    /////////////////////////////////////////////////////////////////////////////////
    // VARIABLE

    public static Sound colorChangerSound = Gdx.audio.newSound(Gdx.files.internal("colorChangerSound.wav"));

    //spawnchunks addition: next 2 lines
    public int arrayIndexX;
    public int arrayIndexY;

    /////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR

    //public ColorChanger(float x, float y, Stage s) chunkspawn replacement: next line
    public ColorChanger(float x, float y, Stage s, int arrIndexX, int arrIndexY)
    {
        super(x, y, s);
        loadTexture("colorchanger_bars.png");
        // changed from 50 to 100 for resize
        setSize(114, 100);
        setBoundaryPolygon(8);

        setOrigin(50, 50);

        //spawnchunks addition: next 2 lines
        arrayIndexX = arrIndexX;
        arrayIndexY = arrIndexY;

        // Action spin = Actions.rotateBy(60, 1);
        // this.addAction( Actions.forever(spin) );
    }

    /////////////////////////////////////////////////////////////////////////////////
    // METHOD

    public void changeColor(Player player, BaseActor background)
    {
        if((player.overlaps(this)) && !this.isCollected)
        {
            int randPalette = (int)(Math.random() * 10);
            int randColor =  (int)((Math.random() * 4) + 1);

            this.clearActions();
            this.addAction(Actions.fadeOut(1));

            this.isCollected = true;

            player.hexColor = ColorPalette.colorsHexa[randPalette][randColor];
            player.setColor(new Color(player.hexColor));
            DebugLevel.dummySickFaceFilter.setColor(new Color(player.hexColor));

            background.setColor(new Color(ColorPalette.colorsHexa[randPalette][0]));

            colorChangerSound.play();

            /////////////////////////////////////////////////////////////////////////////////

            BaseObstacle.obstacleColors[0] = ColorPalette.colorsHexa[randPalette][1];
            BaseObstacle.obstacleColors[1] = ColorPalette.colorsHexa[randPalette][2];
            BaseObstacle.obstacleColors[2] = ColorPalette.colorsHexa[randPalette][3];
            BaseObstacle.obstacleColors[3] = ColorPalette.colorsHexa[randPalette][4];

            //for (CircleObstacle circleObstacle : DebugLevel.circleObstacles) chunkspawn replacement: this for loop with the following nested for's
            for (ArrayList<CircleObstacle> circleArr : DebugLevel.circleObstacles) {
                for (CircleObstacle circleObstacle : circleArr) {
                    if (circleObstacle != null) { //spawnchunks addition

                        circleObstacle.topLeft.setColor(new Color(BaseObstacle.obstacleColors[0]));
                        circleObstacle.topLeft.componentColor = BaseObstacle.obstacleColors[0];

                        circleObstacle.topRight.setColor(new Color(BaseObstacle.obstacleColors[1]));
                        circleObstacle.topRight.componentColor = BaseObstacle.obstacleColors[1];

                        circleObstacle.bottomLeft.setColor(new Color(BaseObstacle.obstacleColors[2]));
                        circleObstacle.bottomLeft.componentColor = BaseObstacle.obstacleColors[2];

                        circleObstacle.bottomRight.setColor(new Color(BaseObstacle.obstacleColors[3]));
                        circleObstacle.bottomRight.componentColor = BaseObstacle.obstacleColors[3];
                    }
                }
            }

            //for (SquareObstacle squareObstacle : DebugLevel.squareObstacles)
            //spawnchunks: replace with next nested for statements
            for (ArrayList<SquareObstacle> squareArr : DebugLevel.squareObstacles) {
                for (SquareObstacle squareObstacle : squareArr) {
                    if (squareObstacle != null) {
                        squareObstacle.top.setColor(new Color(BaseObstacle.obstacleColors[0]));
                        squareObstacle.top.componentColor = BaseObstacle.obstacleColors[0];

                        squareObstacle.right.setColor(new Color(BaseObstacle.obstacleColors[1]));
                        squareObstacle.right.componentColor = BaseObstacle.obstacleColors[1];

                        squareObstacle.bottom.setColor(new Color(BaseObstacle.obstacleColors[2]));
                        squareObstacle.bottom.componentColor = BaseObstacle.obstacleColors[2];

                        squareObstacle.left.setColor(new Color(BaseObstacle.obstacleColors[3]));
                        squareObstacle.left.componentColor = BaseObstacle.obstacleColors[3];
                    }
                }
            }

            for (ArrayList<CrossObstacle> crossArr : DebugLevel.crossObstacles) {
                for (CrossObstacle crossObstacle : crossArr) {
                    if (crossObstacle != null) {
                        crossObstacle.crossColor = BaseObstacle.obstacleColors[BaseObstacle.colorTick];
                        crossObstacle.setColor(new Color(crossObstacle.crossColor));
                    }
                }
            }

            for (ArrayList<LineObstacle> lineArr : DebugLevel.lineObstacles) {
                for (LineObstacle lineObstacle : lineArr) {
                    if (lineObstacle != null) {
                        if (DebugLevel.player.hexColor != BaseObstacle.obstacleColors[BaseObstacle.colorTick])
                        {
                            lineObstacle.lineColor = BaseObstacle.obstacleColors[BaseObstacle.colorTick];
                            lineObstacle.setColor(new Color(lineObstacle.lineColor));
                        }
                        else
                        {
                            if ((BaseObstacle.colorTick + 1) < BaseObstacle.obstacleColors.length)
                            {
                                lineObstacle.lineColor = BaseObstacle.obstacleColors[BaseObstacle.colorTick + 1];
                                lineObstacle.setColor(new Color(lineObstacle.lineColor));
                            }
                            else
                            {
                                lineObstacle.lineColor = BaseObstacle.obstacleColors[BaseObstacle.colorTick - 1];
                                lineObstacle.setColor(new Color(lineObstacle.lineColor));
                            }
                        }
                    }
                }
            }
        }
    }

    public void init(float x, float y, int arrIndexX, int arrIndexY) {
        setOpacity(1);  // Ensure it's alpha/opacity is restored if it was collected
        setPosition(x, y);
        arrayIndexX = arrIndexX;
        arrayIndexY = arrIndexY;
        isCollected = false;
        Action spin = Actions.rotateBy(60, 1);
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

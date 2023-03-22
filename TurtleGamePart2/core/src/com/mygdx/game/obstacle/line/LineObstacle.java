package com.mygdx.game.obstacle.line;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.DebugLevel;
import com.mygdx.game.Player;
import com.mygdx.game.obstacle.BaseObstacle;

public class LineObstacle extends BaseObstacle implements Pool.Poolable {
    /////////////////////////////////////////////////////////////////////////////////
    // VARIABLE

    //spawnchunk: next 3 lines
    public int arrayIndexX;
    public int arrayIndexY;

    public int lineColor = 0xffffffff;

    public boolean deadObstacle;
/*
    Action moveRight1 = Actions.moveBy(100, 0, 0.80f);
    Action rotate1 = Actions.rotateBy(90, 0.80f);
    Action moveUp1 = Actions.moveBy(0, 100, 0.80f);
    Action moveDown1 = Actions.moveBy(0, -100, 0.80f);
    Action rotate2 = Actions.rotateBy(90, 0.80f);
    Action moveRight2 = Actions.moveBy(100, 0, 0.80f);
    Action moveLeft1 = Actions.moveBy(-100, 0, 0.80f);
    Action rotate3 = Actions.rotateBy(90, 0.80f);
    Action moveDown2 = Actions.moveBy(0, -100, 0.80f);
    Action moveUp2 = Actions.moveBy(0, 100, 0.80f);
    Action rotate4 = Actions.rotateBy(90, 0.80f);
    Action moveLeft2 = Actions.moveBy(-100, 0, 0.80f);
*/
    /////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR

    public LineObstacle(float x, float y, Stage s, int arrIndexX, int arrIndexY) {
        super(x - 103, y + 33, s);

        //spawnchunks addition: next 2 lines
        arrayIndexX = arrIndexX;
        arrayIndexY = arrIndexY;

        deadObstacle = false;

        this.setOrigin(50, 13);
        this.loadTexture("line.png");
        float[] vertices = {
                0, 0,
                0, 26,
                100, 26,
                100, 0
        };
        this.setBoundaryRectangle();
        this.getBoundaryPolygon().setVertices(vertices);
/*
        if (DebugLevel.player.hexColor == obstacleColors[0]) {
            lineColor = obstacleColors[1];
            this.setColor(new Color(lineColor));
        } else if (DebugLevel.player.hexColor == obstacleColors[1]) {
            lineColor = obstacleColors[2];
            this.setColor(new Color(lineColor));
        } else if (DebugLevel.player.hexColor == obstacleColors[2]) {
            lineColor = obstacleColors[3];
            this.setColor(new Color(lineColor));
        } else {
            lineColor = obstacleColors[0];
            this.setColor(new Color(lineColor));
        }
*/
        //this.addAction(Actions.forever(Actions.sequence(moveRight1, rotate1, moveUp1, moveDown1,
        //        rotate2, moveRight2, moveLeft1, rotate3, moveDown2, moveUp2, rotate4, moveLeft2)));
    }

    /////////////////////////////////////////////////////////////////////////////////
    // METHOD

    public void init(float x, float y, int arrIndexX, int arrIndexY) {
        setOpacity(1);  // Ensure it's alpha/opacity is restored if it was collected
        setPosition(x - 103, y + 33);

        arrayIndexX = arrIndexX;
        arrayIndexY = arrIndexY;

        deadObstacle = false;

        if (DebugLevel.player.hexColor == obstacleColors[0]) {
            lineColor = obstacleColors[1];
            this.setColor(new Color(lineColor));
        } else if (DebugLevel.player.hexColor == obstacleColors[1]) {
            lineColor = obstacleColors[2];
            this.setColor(new Color(lineColor));
        } else if (DebugLevel.player.hexColor == obstacleColors[2]) {
            lineColor = obstacleColors[3];
            this.setColor(new Color(lineColor));
        } else {
            lineColor = obstacleColors[0];
            this.setColor(new Color(lineColor));
        }

        this.clearActions();
        //this.addAction(Actions.forever(Actions.sequence(moveRight1, rotate1, moveUp1, moveDown1,
        //        rotate2, moveRight2, moveLeft1, rotate3, moveDown2, moveUp2, rotate4, moveLeft2)));

        //this.addAction(Actions.forever(Actions.sequence(rotate1)));

        Action moveRight1 = Actions.moveBy(100, 0, 0.80f);
        Action rotate1 = Actions.rotateBy(90, 0.80f);
        Action moveUp1 = Actions.moveBy(0, 100, 0.80f);
        Action moveDown1 = Actions.moveBy(0, -100, 0.80f);
        Action rotate2 = Actions.rotateBy(90, 0.80f);
        Action moveRight2 = Actions.moveBy(100, 0, 0.80f);
        Action moveLeft1 = Actions.moveBy(-100, 0, 0.80f);
        Action rotate3 = Actions.rotateBy(90, 0.80f);
        Action moveDown2 = Actions.moveBy(0, -100, 0.80f);
        Action moveUp2 = Actions.moveBy(0, 100, 0.80f);
        Action rotate4 = Actions.rotateBy(90, 0.80f);
        Action moveLeft2 = Actions.moveBy(-100, 0, 0.80f);

        this.addAction(Actions.forever(Actions.sequence(moveRight1, rotate1, moveUp1, moveDown1,
                rotate2, moveRight2, moveLeft1, rotate3, moveDown2, moveUp2, rotate4, moveLeft2)));
    }

    @Override
    public void reset() {
        this.clearActions();
        this.setRotation(0);
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
        if ((player.overlaps(this)) && (player.hexColor != this.lineColor) && (!player.hasOneUp) && (DebugLevel.player.state != Player.State.DEAD) && (!deadObstacle))
        {
            playerDeathSound.play();
            this.setVisible(false);
            DebugLevel.dummySickFace.setVisible(false);
            DebugLevel.dummySickFaceFilter.setVisible(false);
            DebugLevel.dummySickFace.setVisible(false);
            player.state = Player.State.DEAD;
            return true;
            // System.exit(0);
        }
        else if ((player.overlaps(this)) && (player.hexColor != this.lineColor) && (player.hasOneUp) && (this.isVisible()))
        {
            this.setVisible(false);
            obstacleShieldSound.play();
            player.hasOneUp = false;
            DebugLevel.dummyOneUp.setVisible(false);
            this.deadObstacle = true;
            this.clearActions();
            this.addAction(Actions.fadeOut(1));
            // this.addAction(Actions.after(Actions.removeActor()));
            // this.remove();
        }

        return false;
    }
}

//spawnchunks: no changes made to this file

package com.mygdx.game.obstacle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.BaseActor;
import com.mygdx.game.DebugLevel;
import com.mygdx.game.Player;

public abstract class BaseObstacle extends BaseActor
{
    /////////////////////////////////////////////////////////////////////////////////
    // VARIABLE

    public static float colorTime = 3;
    public static int colorTick = 0;

    public static int[] obstacleColors = new int[4];

    public static Sound playerDeathSound = Gdx.audio.newSound(Gdx.files.internal("playerDeathSound.mp3"));
    public static Sound obstacleShieldSound = Gdx.audio.newSound(Gdx.files.internal("obstacleShieldSound.ogg"));

    /////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR

    public BaseObstacle(float x, float y, Stage s)
    {
        super(x, y, s);
    }

    /////////////////////////////////////////////////////////////////////////////////
    // METHOD

    public abstract boolean playerDies(Player player);
}

//spawnchunks: no changes have been made to this file

package com.mygdx.game.modifier;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.BaseActor;

public abstract class BaseModifier extends BaseActor
{
    /////////////////////////////////////////////////////////////////////////////////
    // VARIABLE

    public boolean isCollected;

    /////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR

    public BaseModifier(float x, float y, Stage s)
    {
        super(x, y, s);
        isCollected = false;
    }

    /////////////////////////////////////////////////////////////////////////////////
    // METHOD

    // FIXME
}

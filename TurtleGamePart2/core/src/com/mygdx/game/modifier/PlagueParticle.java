
//spawnchunks: no changes made to this file

package com.mygdx.game.modifier;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.BaseActor;
import com.mygdx.game.Player;

abstract class PlagueParticle extends BaseModifier
{
    /////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR

    public PlagueParticle(float x, float y, Stage s) { super(x, y, s); }

    /////////////////////////////////////////////////////////////////////////////////
    // METHOD

    public abstract void rotate(Player player);
}

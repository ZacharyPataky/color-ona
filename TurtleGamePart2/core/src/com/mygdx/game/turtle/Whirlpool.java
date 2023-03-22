package com.mygdx.game.turtle;

import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.mygdx.game.BaseActor;

public class Whirlpool extends BaseActor
{
    public Whirlpool(float x, float y, Stage s)
    {
       super(x,y,s);
        
       loadAnimationFromSheet("whirlpool.png", 2, 5, 0.1f, false);
    }
    
    public void act(float dt)
    {
        super.act(dt);
        
        if ( isAnimationFinished() )
            remove();
    }
}
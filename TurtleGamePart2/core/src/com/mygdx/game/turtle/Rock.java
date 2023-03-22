package com.mygdx.game.turtle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.BaseActor;

public class Rock extends BaseActor
{
    public Rock(float x, float y, Stage s)
    { 
       super(x,y,s);
       loadTexture("rock.png");
       setBoundaryPolygon(8);
    }    
}
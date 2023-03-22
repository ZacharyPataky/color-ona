package com.mygdx.game.turtle;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.BaseActor;

public class Shark extends BaseActor
{
    public Shark(float x, float y, Stage s)
    { 
       super(x,y,s);
       loadTexture("sharky.png");
       setBoundaryPolygon(8);
    }    
}
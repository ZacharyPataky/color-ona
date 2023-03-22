
//spawnchunks: no changes have been made to this file

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class Player extends BaseActor
{
    /////////////////////////////////////////////////////////////////////////////////
    // VARIABLES

    private Animation<TextureRegion> animation;

    public float moveIncrement;

    public boolean ignoreJump;
    public boolean hasOneUp;

    public int hexColor = ColorPalette.colorsHexa[DebugLevel.randPalette][DebugLevel.randColor];

    public enum State { IDLE, JUMPING, FALLING, DEAD }
    public State state;

    public enum Orientation { FALL_UP, FALL_DOWN, FALL_LEFT, FALL_RIGHT }
    public Orientation orient;

    public static Sound playerJumpSound = Gdx.audio.newSound(Gdx.files.internal("playerJumpSound.wav"));

    /////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR

    public Player(float x, float y, Stage s)
    {
        super(x,y,s);

        state = State.IDLE;
        orient = Orientation.FALL_DOWN;

        moveIncrement = 0;
        ignoreJump = false;
        hasOneUp = false;

        this.loadTexture("player_white_60_idle.png");

        float[] vertices = {
                29, 4,
                21, 5,
                14, 9,
                9, 14,
                5, 21,
                4, 30,
                5, 38,
                9, 45,
                14, 50,
                21, 54,
                30, 55,
                38, 54,
                45, 50,
                54, 38,
                55, 29,
                54, 21,
                50, 14,
                45, 9,
                38, 5
        };
        this.setBoundaryRectangle();
        this.getBoundaryPolygon().setVertices(vertices);

        this.setColor( new Color(hexColor));

        //******************** changed from 30 for resize !!!!!!!!!!!!!!!
        //setSize(60,60);

        //useless because we hard-coded our physics
/*        setAcceleration(400);
        setMaxSpeed(200);
        setDeceleration(400);*/

        setBoundaryPolygon(8);
    }

    /////////////////////////////////////////////////////////////////////////////////
    // METHOD

    @Override
    public void drawDebugBounds(ShapeRenderer shapes)
    {
//        super.drawDebugBounds(shapes);
        shapes.polygon(getBoundaryPolygon().getTransformedVertices());
    }

    public void act(float dt)
    {
        super.act( dt );
        applyPhysics(dt);
        //boundToWorld();
        //wrapAroundWorld();
        //alignCameraForPlayer();
    }

    /**
     * If the jump button (spacebar) is pressed, [player] jumps five pixels.
     * The player cannot jump again until he releases the jump button (spacebar).
     */
    public void playerJump()
    {
        // If the jump button (spacebar) is pressed, [player] jumps five pixels.
        if((Gdx.input.isKeyPressed(Input.Keys.SPACE)) && (!ignoreJump)
                && (state != Player.State.DEAD))
        {
            playerJumpSound.play();
            this.state = State.JUMPING;
            this.loadTexture("player_white_60_jump.png");
            ignoreJump = true;
           // physics adjusted for resize; from 5 to 9
            moveIncrement = 9;
        }

        // [player] cannot jump again until the jump button (spacebar) is released.
        if(!Gdx.input.isKeyPressed(Input.Keys.SPACE)) { ignoreJump = false; }

        /////////////////////////////////////////////////////////////////////////////////

        // How jumping and falling pertains to orientation.
        if(this.state == State.JUMPING)
        {
            if(moveIncrement > 0)
            {
                switch(orient)
                {
                    case FALL_DOWN:
                        moveBy(0, moveIncrement);
                        DebugLevel.dummySickFace.moveBy(0, moveIncrement);
                        DebugLevel.dummySickFaceFilter.moveBy(0, moveIncrement);
                        DebugLevel.dummyOneUp.moveBy(0, moveIncrement);
                        break;
                    case FALL_RIGHT:
                        moveBy((-1f * moveIncrement), 0);
                        DebugLevel.dummySickFace.moveBy((-1f * moveIncrement), 0);
                        DebugLevel.dummySickFaceFilter.moveBy((-1f * moveIncrement), 0);
                        DebugLevel.dummyOneUp.moveBy((-1f * moveIncrement), 0);
                        break;
                    case FALL_UP:
                        moveBy(0, -1f * moveIncrement);
                        DebugLevel.dummySickFace.moveBy(0, -1f * moveIncrement);
                        DebugLevel.dummySickFaceFilter.moveBy(0, (-1f * moveIncrement));
                        DebugLevel.dummyOneUp.moveBy(0, (-1f * moveIncrement));
                        break;
                    case FALL_LEFT:
                        moveBy(moveIncrement, 0);
                        DebugLevel.dummySickFace.moveBy(moveIncrement, 0);
                        DebugLevel.dummySickFaceFilter.moveBy(moveIncrement, 0);
                        DebugLevel.dummyOneUp.moveBy(moveIncrement, 0);
                        break;
                }

                moveIncrement -= 0.4f;
            }

            // [player] switches to state [FALLING] after it reaches the apex of the jump.
            else {
                state = Player.State.FALLING;
                this.loadTexture("player_white_60_idle.png");
            }
        }
        else if(this.state == State.FALLING)
        {
            // The maximum fall speed (terminal velocity) is 13 pixels / units.
            // Resize: now 15
            if(moveIncrement < -15f) { moveIncrement = -15f; }
            else { moveIncrement -= 0.2f; }

            // Pulls [player] in the state FALLING downstream of gravity.
            switch(orient)
            {
                case FALL_DOWN:
                    moveBy(0, moveIncrement);
                    DebugLevel.dummySickFace.moveBy(0, moveIncrement);
                    DebugLevel.dummySickFaceFilter.moveBy(0, moveIncrement);
                    DebugLevel.dummyOneUp.moveBy(0, moveIncrement);
                    break;
                case FALL_RIGHT:
                    moveBy(-1f*moveIncrement, 0);
                    DebugLevel.dummySickFace.moveBy(-1f*moveIncrement, 0);
                    DebugLevel.dummySickFaceFilter.moveBy(-1f*moveIncrement, 0);
                    DebugLevel.dummyOneUp.moveBy(-1f*moveIncrement, 0);
                    break;
                case FALL_UP:
                    moveBy(0, -1f*moveIncrement);
                    DebugLevel.dummySickFace.moveBy(0, -1f*moveIncrement);
                    DebugLevel.dummySickFaceFilter.moveBy(0, -1f*moveIncrement);
                    DebugLevel.dummyOneUp.moveBy(0, -1f*moveIncrement);
                    break;
                case FALL_LEFT:
                    moveBy(moveIncrement, 0);
                    DebugLevel.dummySickFace.moveBy(moveIncrement, 0);
                    DebugLevel.dummySickFaceFilter.moveBy(moveIncrement, 0);
                    DebugLevel.dummyOneUp.moveBy(moveIncrement, 0);
                    break;
            }
        }
    }

    public Animation<TextureRegion> loadAnimationFromFiles(String[] fileNames, float frameDuration, boolean loop)
    {
        int fileCount = fileNames.length;
        Array<TextureRegion> textureArray = new Array<TextureRegion>();

        for (int n = 0; n < fileCount; n++)
        {
            String fileName = fileNames[n];
            Texture texture = new Texture(Gdx.files.internal(fileName));
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            textureArray.add(new TextureRegion(texture));
        }

        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);

        if (loop) { anim.setPlayMode(Animation.PlayMode.NORMAL); }
        else { anim.setPlayMode(Animation.PlayMode.NORMAL); }

        if (animation == null) { setAnimation(anim); }

        return anim;
    }
}
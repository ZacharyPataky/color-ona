
//spawnchunks: Changes have been made to this file!!!

package com.mygdx.game;
import java.util.Random;

/**
 * This object is a set of collected values that represent one Chunk on the field.
 * It does not control the grapics of the chunk, but contains the coordinates and
 * other data about each chunk on the grid.
 * In the DebugLevel class, they are contained in a 2-Dimensional Array to represent
 * every chunk on the field.
 */
public class Chunk {

    /////////////////////////////////////////////////////////////////////////////////
    // VARIABLE

    public int leftX;
    public int bottomY;
    public int rightX;
    public int topY;
    public int centerX;
    public int centerY;

    //todo: probably remove below 2 fields b/c indexes are arbitrary -ss
    public int arrayIndexX; //chunkspawn addition
    public int arrayIndexY; //chunkspawn addition

    public static int chunkSize;

    public enum Spawn {
        NOTHING,
        COLOR_CHANGER,
        ONE_UP,
        GREEN_CORONA, //spawnchunks addition
        PURPLE_CORONA, //spawnchunks addition
        CIRCLE_OBSTACLE, //spawnchunks addition
        SQUARE_OBSTACLE, //spawnchunk addition
        CROSS_OBSTACLE, // ZP
        LINE_OBSTACLE; // ZP
        }

    public Spawn spawn;

    /** Chunk constructor method
     *
     * @param bottomLeftX X coordinate of where the bottom-left corner of this chunk should be placed
     * @param bottomLeftY Y coordinate of where the bottom-left corner of this chunk should be placed
     * @param indexX X index into the chunk array in DebugLevel for this chunk (obsolete??)
     * @param indexY Y index into the chunk array in DebugLevel for this chunk (obsolete??)
     * @param forceNothing Whether to force this chunk to spawn nothing (no objects, no obstacles);
     *                     used on the chunk the player is spawned in at start of game
     * @param forbidObstacle Whether to forbid this chunk from spawning an obstacle (other objects are a-ok);
     *                       used for preventing two obstacles from spawning next to each other which could cause
     *                       a state where the player cannot continue without dying
     */
    public Chunk (int bottomLeftX, int bottomLeftY, int indexX, int indexY, boolean forceNothing, boolean forbidObstacle) //spawnchunks addition
    {
        Random ran = new Random();

        leftX = bottomLeftX;
        bottomY = bottomLeftY;

        //spawnchunk addition: next 2 lines
        arrayIndexX = indexX;
        arrayIndexY = indexY;

        // changed from 150 to 300 for resize
        chunkSize = 300;

        rightX = leftX + chunkSize;
        topY = bottomY + chunkSize;
        centerX = leftX + (chunkSize/2);
        centerY = bottomY + (chunkSize/2);

        //Will this chunk be a blank chunk, corona chunk, one-up chunk, or color-changer chunk?
        float ranNum = ran.nextFloat(); //Generate random float between 0.0 and 1.0


        if ( forceNothing ) {
            spawn = Spawn.NOTHING;
        }
        else if ( forbidObstacle ){
            if (ranNum < 0.29) { spawn = Spawn.COLOR_CHANGER; } // 29% Chance
            else if (ranNum < 0.36) { spawn = Spawn.ONE_UP; } // 7% Chance
            else if (ranNum < 0.50) { spawn = Spawn.GREEN_CORONA; } // 14% Chance
            else if (ranNum < 0.64) { spawn = Spawn.PURPLE_CORONA; } //14% chance
            else { spawn = Spawn.NOTHING; } //36% chance
            }
        else {
            if (ranNum < 0.075) { spawn = Spawn.CIRCLE_OBSTACLE; } // 7.5% chance
            else if (ranNum < 0.15) { spawn = Spawn.SQUARE_OBSTACLE; } // 7.5% chance
            else if (ranNum < 0.225) { spawn = Spawn.CROSS_OBSTACLE; } // 7.5% chance
            else if (ranNum < 0.30) { spawn = Spawn.LINE_OBSTACLE; } // 7.5% chance
            else if (ranNum < 0.50) { spawn = Spawn.COLOR_CHANGER; } // 20% Chance
            else if (ranNum < 0.55) { spawn = Spawn.ONE_UP; } // 5% Chance
            else if (ranNum < 0.65) { spawn = Spawn.GREEN_CORONA; } // 10% Chance
            else if (ranNum < 0.75) { spawn = Spawn.PURPLE_CORONA; } //10% chance
            else { spawn = Spawn.NOTHING; } //25% chance
        }
    }

    /////////////////////////////////////////////////////////////////////////////////
    // METHOD

    /* Obsolete and broken by the arbitrary index architecture

    public Chunk getCurrentChunk(int x, int y)
    {
        int someX = x-(x%150); //get to the nearest multiple of 150
        int someY = y-(y%150);

        int indexX = (someX / 150);
        int indexY = (someY / 150);

        return chunks.get(indexX).get(indexY);
    }
    */
}

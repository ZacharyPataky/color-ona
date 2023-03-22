

//spawnchunks: MANY CRITICAL CHANGES have been made to this file!!!!!!


package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.dummy.BoundaryDummy;
import com.mygdx.game.dummy.OneUpDummy;
import com.mygdx.game.dummy.SickDummy;
import com.mygdx.game.dummy.SickFaceFilterDummy;
import com.mygdx.game.modifier.ColorChanger;
import com.mygdx.game.modifier.OneUp;
import com.mygdx.game.modifier.PlagueParticle_Green;
import com.mygdx.game.modifier.PlagueParticle_Purple;
import com.mygdx.game.obstacle.*;
import com.mygdx.game.obstacle.circle.CircleObstacle;
import com.mygdx.game.obstacle.cross.CrossObstacle;
import com.mygdx.game.obstacle.line.LineObstacle;
import com.mygdx.game.obstacle.square.SquareObstacle;

import java.io.IOException;
import java.util.ArrayList;

import static com.badlogic.gdx.Gdx.app;
import static com.mygdx.game.menu.MenuScreen.ignoreButtons;

public class DebugLevel extends ColorOnaScreen {
    /////////////////////////////////////////////////////////////////////////////////
    // VARIABLES

    // The Below Value is the allotted number of rows/columns of chunks that are
    // existing on the field at any time. So, the total number of allotted chunks
    // is this value squared.
    private static final int NUM_ROWS_COLUMNS = 5; //spawnchunks addition

    public static Player player;                                       // The Player.
    public static BoundaryDummy dummyBoundary;
    public static SickDummy dummySickFace;
    public static OneUpDummy dummyOneUp;
    public static SickFaceFilterDummy dummySickFaceFilter;
    public BaseActor grid;                                         // The Background.

    // Starting color-palette code
    public static int randPalette = (int) (Math.random() * 10);
    public static int randColor = (int) ((Math.random() * 4) + 1);

    // Leaderboard
    Leaderboard leaderboard;
    boolean boundaryCheck = false;

    //2D Arrays for each object type; they hold the currently ACTIVE objects.
    private ArrayList<ArrayList<PlagueParticle_Green>> plagueParticleGreens; // ArrayList for [PlagueParticle_Green]s.
    private ArrayList<ArrayList<PlagueParticle_Purple>> plagueParticlePurples;
    private ArrayList<ArrayList<OneUp>> oneUps;                            // ArrayList for [OneUp]s.
    private ArrayList<ArrayList<ColorChanger>> colorChangers;
    public static ArrayList<ArrayList<CircleObstacle>> circleObstacles;
    public static ArrayList<ArrayList<SquareObstacle>> squareObstacles;
    public static ArrayList<ArrayList<CrossObstacle>> crossObstacles;
    public static ArrayList<ArrayList<LineObstacle>> lineObstacles;
    public ArrayList<ArrayList<McDouble>> mcDoubles;

    //Pools for each object. This enables objects that despawn to be "recycled"
    //into new ones instead of constantly being re/dealloc'd
    public Pool<McDouble> mcDoublePool;
    public Pool<PlagueParticle_Green> plagueParticleGreenPool;
    public Pool<PlagueParticle_Purple> plagueParticlePurplePool;
    public Pool<OneUp> oneUpPool;
    public Pool<ColorChanger> colorChangerPool;
    public Pool<CircleObstacle> circleObstaclePool;
    public Pool<SquareObstacle> squareObstaclePool;
    public Pool<CrossObstacle> crossObstaclePool;
    public Pool<LineObstacle> lineObstaclePool;

    public static ArrayList<ArrayList<Chunk>> chunks; //2D array representing the chunks on the field
    //first dimension is X, second is Y

    // Button Sound Effect
    public static Sound buttonSound = Gdx.audio.newSound(Gdx.files.internal("buttonSound.wav"));

    // Formal Score Label
    private Label scoreTag;

    // Debug Labels
    private Label stateLabel;                      // Label stating [player]'s state.
    private Label orientLabel;               // Label stating [player]'s orientation.
    private Label speedLabel;                      // Label stating [player]'s speed.
    private Label xyLabel;                   // Label stating [player]'s coordinates.
    private Label oneUpLabel;               // Label stating if [player] has [oneUp].
    private Label hexLabel;
    private Label scoreLabel;
    private Label obstacleTimeLabel;
    private Label boundaryLabel;

    /////////////////////////////////////////////////////////////////////////////////
    // CONSTRUCTOR

    public DebugLevel(ColorOnaGame colorOnaGame) {
        super(colorOnaGame);
    }

    /////////////////////////////////////////////////////////////////////////////////
    // METHODS

    //spawnchunks addition: entire addObjects method
    public void addObjects(Chunk thisChunk, int x, int y) {
        //Takes a chunk, checks what object it's supposed to hold, and performs all necessary
        //actions to add that object in the game. (Takes it from pool, adds it to the 2D arrays)

        // The "x" parameter refers to the current x index of the chunk.
        // This will be used to index into the first dimension of each object array,
        // "y" refers to the current y index of the chunk
        // will index into the second dimension of each object array. (Will either be 0 or chunk.size()-1.)

        int greenX = x;    int greenY = y;
        int purpleX = x;   int purpleY = y;
        int oneupX = x;    int oneupY = y;
        int colorX = x;    int colorY = y;
        int circleX = x;   int circleY = y;
        int squareX = x;   int squareY = y;
        int crossX = x;    int crossY = y;
        int lineX = x;     int lineY = y;
        int mcdoubleX = x; int mcdoubleY = y;

        int someOffset = 46;
        int chunkY = thisChunk.arrayIndexY;
        int chunkX = thisChunk.arrayIndexX;
        float coordY = thisChunk.centerY - someOffset;
        float coordX = thisChunk.centerX - someOffset;

        //The sizes of each array are typically 5, but can be 6 when a new column is spawned and before
        //the opposing one has been despawned. Also, the sizes will range from 0-5 when the game is
        //first starting up. These if blocks just set each index value to a usable value to account
        //for these conditions and prevent index out of bounds.

        if (x >= plagueParticleGreens.size()) {
            greenX = plagueParticleGreens.size() - 1;
            purpleX = plagueParticlePurples.size() - 1;
            oneupX = oneUps.size() - 1;
            colorX = colorChangers.size() - 1;
            circleX = circleObstacles.size() - 1;
            squareX = squareObstacles.size() - 1;
            crossX = crossObstacles.size() - 1;
            lineX = lineObstacles.size() - 1;
            mcdoubleX = mcDoubles.size() - 1;
        } //Is size-1 because these are actual indexes into the first dimension.

        if (y != 0) {
            greenY = plagueParticleGreens.get(x).size();
            purpleY = plagueParticlePurples.get(x).size();
            oneupY = oneUps.get(x).size();
            colorY = colorChangers.get(x).size();
            circleY = circleObstacles.get(x).size();
            squareY = squareObstacles.get(x).size();
            crossY = crossObstacles.get(x).size();
            lineY = lineObstacles.get(x).size();
            mcdoubleY = mcDoubles.get(x).size();
        } //is just size (not size-1) because these are the indexes to be added *at*.
        //These indexes never refer to anything but either the first or the last index,
        //which is why I have it as if it's not the first (0), make it the last.

        if (thisChunk.spawn == Chunk.Spawn.GREEN_CORONA) {
            PlagueParticle_Green ppg = plagueParticleGreenPool.obtain();

            System.out.println("Add Objects: Obtained PPG from Pool;" +
                    " free PPGs remaining in pool: " + plagueParticleGreenPool.getFree());

            ppg.init(coordX, coordY, chunkX, chunkY);

            plagueParticleGreens.get(greenX).add(greenY, ppg);

            System.out.println("Add Objects: added PPG at coords ( X:" + coordX + ", Y:" + coordY + " )");


            //Adding these null objects to every object array makes it so their indexes are consistent
            //with the indexes of the chunk array, which is necessary for spawning/despawning whole
            //rows in spawnTopRow() and spawnBottomRow().

            //plagueParticleGreens.get(greenX).add(null);
            plagueParticlePurples.get(purpleX).add(purpleY, null);
            oneUps.get(oneupX).add(oneupY, null);
            colorChangers.get(colorX).add(colorY, null);
            circleObstacles.get(circleX).add(circleY, null);
            squareObstacles.get(squareX).add(squareY, null);
            crossObstacles.get(crossX).add(crossY, null);
            lineObstacles.get(lineX).add(lineY, null);
            mcDoubles.get(mcdoubleX).add(mcdoubleY, null);
        } else if (thisChunk.spawn == Chunk.Spawn.PURPLE_CORONA) {
            PlagueParticle_Purple ppp = plagueParticlePurplePool.obtain();

            System.out.println("Add Objects: Obtained PPP from Pool;" +
                    " free PPPs remaining in pool: " + plagueParticleGreenPool.getFree());

            ppp.init(coordX, coordY, chunkX, chunkY);

            //System.out.println("PPPs size " + plagueParticlePurples.get(x).size());
            plagueParticlePurples.get(purpleX).add(purpleY, ppp);
            //ppp.setVisible(true);

            System.out.println("Add Objects: added PPP at coords ( X:" + coordX + ", Y:" + coordY + " ) and at" +
                    " chunk X:" + chunkX + ", Y:" + chunkY);

            plagueParticleGreens.get(greenX).add(greenY, null);
            //plagueParticlePurples.get(purpleX).add(purpleY, null);
            oneUps.get(oneupX).add(oneupY, null);
            colorChangers.get(colorX).add(colorY, null);
            circleObstacles.get(circleX).add(circleY, null);
            squareObstacles.get(squareX).add(squareY, null);
            crossObstacles.get(crossX).add(crossY, null);
            lineObstacles.get(lineX).add(lineY, null);
            mcDoubles.get(mcdoubleX).add(mcdoubleY, null);
        } else if (thisChunk.spawn == Chunk.Spawn.ONE_UP) {
            OneUp oneup = oneUpPool.obtain();

            System.out.println("Add Objects: Obtained One Up from Pool;" +
                    " free One Ups remaining in pool: " + oneUpPool.getFree());

            oneup.init(coordX, coordY, chunkX, chunkY);

            oneUps.get(x).add(oneupY, oneup);
            System.out.println("Add Objects: added 1U at coords ( X:" + coordX + ", Y:" + coordY + " ) and at" +
                    " chunk X:" + chunkX + ", Y:" + chunkY);

            plagueParticleGreens.get(greenX).add(greenY, null);
            plagueParticlePurples.get(purpleX).add(purpleY, null);
            //oneUps.get(oneupX).add(oneupY, null);
            colorChangers.get(colorX).add(colorY, null);
            circleObstacles.get(circleX).add(circleY, null);
            squareObstacles.get(squareX).add(squareY, null);
            crossObstacles.get(crossX).add(crossY, null);
            lineObstacles.get(lineX).add(lineY, null);
            mcDoubles.get(mcdoubleX).add(mcdoubleY, null);
        } else if (thisChunk.spawn == Chunk.Spawn.COLOR_CHANGER) {
            ColorChanger cc = colorChangerPool.obtain();
            System.out.println("Add Objects: Obtained CC from Pool;" +
                    " free CC remaining in pool: " + colorChangerPool.getFree());

            cc.init(coordX, coordY, chunkX, chunkY);

            colorChangers.get(x).add(colorY, cc);

            System.out.println("Add Objects: added CC at coords ( X:" + coordX + ", Y:" + coordY + " ) and at" +
                    " chunk X:" + chunkX + ", Y:" + chunkY);

            plagueParticleGreens.get(greenX).add(greenY, null);
            plagueParticlePurples.get(purpleX).add(purpleY, null);
            oneUps.get(oneupX).add(oneupY, null);
            //colorChangers.get(colorX).add(colorY, null);
            circleObstacles.get(circleX).add(circleY, null);
            squareObstacles.get(squareX).add(squareY, null);
            crossObstacles.get(crossX).add(crossY, null);
            lineObstacles.get(lineX).add(lineY, null);
            mcDoubles.get(mcdoubleX).add(mcdoubleY, null);
        } else if (thisChunk.spawn == Chunk.Spawn.CIRCLE_OBSTACLE) {
            //circleObstacles.get(x).add(circleY, new CircleObstacle(coordX,coordY,mainStage,chunkX, chunkY));
            CircleObstacle circle = circleObstaclePool.obtain();
            System.out.println("Add Objects: Obtained Circle from Pool;" +
                    " free Circles remaining in pool: " + circleObstaclePool.getFree());
            circle.init(coordX, coordY, chunkX, chunkY);
            circleObstacles.get(x).add(circleY, circle);

            McDouble md = mcDoublePool.obtain();
            System.out.println("Add Objects: Obtained McDouble from Pool for Circle;" +
                    " free McDoubles remaining in pool: " + mcDoublePool.getFree());
            md.init(coordX, coordY, chunkX, chunkY);
            mcDoubles.get(x).add(mcdoubleY, md);

            System.out.println("Add Objects: added Circle and its McDouble at coords ( X:" + coordX + ", Y:" + coordY + " ) and at" +
                    " chunk X:" + chunkX + ", Y:" + chunkY);

            plagueParticleGreens.get(greenX).add(greenY, null);
            plagueParticlePurples.get(purpleX).add(purpleY, null);
            oneUps.get(oneupX).add(oneupY, null);
            colorChangers.get(colorX).add(colorY, null);
            //circleObstacles.get(circleX).add(circleY, null);
            squareObstacles.get(squareX).add(squareY, null);
            crossObstacles.get(crossX).add(crossY, null);
            lineObstacles.get(lineX).add(lineY, null);
            //mcDoubles.get(mcdoubleX).add(mcdoubleY, null);

        } else if (thisChunk.spawn == Chunk.Spawn.SQUARE_OBSTACLE) {
            //squareObstacles.get(x).add(squareY, new SquareObstacle(coordX,coordY,mainStage,chunkX, chunkY));
            SquareObstacle square = squareObstaclePool.obtain();

            System.out.println("Add Objects: Obtained Square from Pool;" +
                    " free Squares remaining in pool: " + squareObstaclePool.getFree());

            square.init(coordX, coordY, chunkX, chunkY);
            squareObstacles.get(x).add(square);

            McDouble md = mcDoublePool.obtain();
            System.out.println("Add Objects: Obtained McDouble from Pool for Square;" +
                    " free McDoubles remaining in pool: " + mcDoublePool.getFree());
            md.init(coordX, coordY, chunkX, chunkY);
            mcDoubles.get(x).add(mcdoubleY, md);

            System.out.println("Add Objects: added Square and its McDouble ( X:" + coordX + ", Y:" + coordY + " ) and at" +
                    " chunk X:" + chunkX + ", Y:" + chunkY);

            plagueParticleGreens.get(greenX).add(greenY, null);
            plagueParticlePurples.get(purpleX).add(purpleY, null);
            oneUps.get(oneupX).add(oneupY, null);
            colorChangers.get(colorX).add(colorY, null);
            circleObstacles.get(circleX).add(circleY, null);
            //squareObstacles.get(squareX).add(squareY, null);
            crossObstacles.get(crossX).add(crossY, null);
            lineObstacles.get(lineX).add(lineY, null);
            //mcDoubles.get(mcdoubleX).add(mcdoubleY, null);

        } else if (thisChunk.spawn == Chunk.Spawn.CROSS_OBSTACLE) {
            //crossObstacles.get(x).add(crossY, new CrossObstacle(coordX,coordY,mainStage,chunkX, chunkY));
            CrossObstacle cross = crossObstaclePool.obtain();

            System.out.println("Add Objects: Obtained Cross from Pool;" +
                    " free Crosses remaining in pool: " + crossObstaclePool.getFree());

            cross.init(coordX, coordY, chunkX, chunkY);
            crossObstacles.get(x).add(cross);

            McDouble md = mcDoublePool.obtain();
            System.out.println("Add Objects: Obtained McDouble from Pool for Cross;" +
                    " free McDoubles remaining in pool: " + mcDoublePool.getFree());
            md.init(coordX, coordY, chunkX, chunkY);
            mcDoubles.get(x).add(mcdoubleY, md);

            System.out.println("Add Objects: added Cross and its McDouble ( X:" + coordX + ", Y:" + coordY + " ) and at" +
                    " chunk X:" + chunkX + ", Y:" + chunkY);

            plagueParticleGreens.get(greenX).add(greenY, null);
            plagueParticlePurples.get(purpleX).add(purpleY, null);
            oneUps.get(oneupX).add(oneupY, null);
            colorChangers.get(colorX).add(colorY, null);
            circleObstacles.get(circleX).add(circleY, null);
            squareObstacles.get(squareX).add(squareY, null);
            //crossObstacles.get(crossX).add(crossY, null);
            lineObstacles.get(lineX).add(lineY, null);
            //mcDoubles.get(mcdoubleX).add(mcdoubleY, null);
        } else if (thisChunk.spawn == Chunk.Spawn.LINE_OBSTACLE) {
            //lineObstacles.get(x).add(lineY, new LineObstacle(coordX,coordY,mainStage,chunkX, chunkY));
            LineObstacle line = lineObstaclePool.obtain();

            System.out.println("Add Objects: Obtained Line from Pool;" +
                    " free Lines remaining in pool: " + lineObstaclePool.getFree());

            line.init(coordX, coordY, chunkX, chunkY);
            lineObstacles.get(x).add(line);

            McDouble md = mcDoublePool.obtain();
            System.out.println("Add Objects: Obtained McDouble from Pool for Line;" +
                    " free McDoubles remaining in pool: " + mcDoublePool.getFree());
            md.init(coordX, coordY, chunkX, chunkY);
            mcDoubles.get(x).add(mcdoubleY, md);

            System.out.println("Add Objects: added Line and its McDouble ( X:" + coordX + ", Y:" + coordY + " ) and at" +
                    " chunk X:" + chunkX + ", Y:" + chunkY);

            plagueParticleGreens.get(greenX).add(greenY, null);
            plagueParticlePurples.get(purpleX).add(purpleY, null);
            oneUps.get(oneupX).add(oneupY, null);
            colorChangers.get(colorX).add(colorY, null);
            circleObstacles.get(circleX).add(circleY, null);
            squareObstacles.get(squareX).add(squareY, null);
            crossObstacles.get(crossX).add(crossY, null);
            //lineObstacles.get(lineX).add(lineY, null);
            //mcDoubles.get(mcdoubleX).add(mcdoubleY, null);

        } else { //chunk is nothing; stuff with null objects
            plagueParticleGreens.get(greenX).add(greenY, null);
            plagueParticlePurples.get(purpleX).add(purpleY, null);
            oneUps.get(oneupX).add(oneupY, null);
            colorChangers.get(colorX).add(colorY, null);
            circleObstacles.get(circleX).add(circleY, null);
            squareObstacles.get(squareX).add(squareY, null);
            crossObstacles.get(crossX).add(crossY, null);
            lineObstacles.get(lineX).add(lineY, null);
            mcDoubles.get(mcdoubleX).add(mcdoubleY, null);
        }
    }

    //spawnchunks addition: all 4 spawn methods

    public void spawnRightColumn() {
        //Add a new arraylist to end of Chunks and populate it.
        //Also removes the first array list from Chunks.

        //Spawning a column on the right means adding a new arraylist to the end of
        //the 2D chunk arraylist (and by extension, all the object arraylists too)
        //Deleting a column on the left means deleting the first arraylist in each one instead.

        System.out.println("R Spawn: Will spawn a right column and delete the left now.");

        //the arraylist (i.e, column) we will eventually add to the chunk arraylist
        ArrayList<Chunk> newList = new ArrayList<Chunk>();

        //variables to keep track of the coordinates of what we're adding
        int addCoordY = 0;
        int addIndexY = 0;

        //System.out.println("R Spawn: Chunks size at beginning of right spawner: " + chunks.size());
        //System.out.println("R Spawn: CCs size at beginning of right spawner: " + colorChangers.size());

        int startXcoord = chunks.get(chunks.size() - 1).get(0).leftX + Chunk.chunkSize;
        int startXindex = chunks.get(chunks.size() - 1).get(0).arrayIndexX + 1;

        addCoordY = chunks.get(0).get(0).bottomY;

        //add new arraylists (i.e, columns) to each object's arraylist
        colorChangers.add(new ArrayList<ColorChanger>());
        oneUps.add(new ArrayList<OneUp>());
        plagueParticleGreens.add(new ArrayList<PlagueParticle_Green>());
        plagueParticlePurples.add(new ArrayList<PlagueParticle_Purple>());
        circleObstacles.add(new ArrayList<CircleObstacle>());
        squareObstacles.add(new ArrayList<SquareObstacle>());
        crossObstacles.add(new ArrayList<CrossObstacle>());
        lineObstacles.add(new ArrayList<LineObstacle>());
        mcDoubles.add(new ArrayList<McDouble>());

        int leftAdjacentChunkIndex;
        int bottomAdjacentChunkIndex;
        boolean forbidObstacle;

        //create chunks with the appropriate x/y data and store them in the new chunk column
        for (int f = 0; f < NUM_ROWS_COLUMNS; f++) {

            Chunk newChunk;

            //Look at the 2 chunks immediately below and to the left of this chunk, check to see
            //if they contain an obstacle, if they do, forbid obstacles for this chunk.

            forbidObstacle = false;

            leftAdjacentChunkIndex = chunks.size() - 1;
            bottomAdjacentChunkIndex = f - 1;


            //Check the chunk to the left
            if (chunks.size() != 0 && chunks.get(leftAdjacentChunkIndex).size() > f) { //prevent index out of bounds
                if (chunks.get(leftAdjacentChunkIndex).get(f).spawn == Chunk.Spawn.CIRCLE_OBSTACLE ||
                        chunks.get(leftAdjacentChunkIndex).get(f).spawn == Chunk.Spawn.SQUARE_OBSTACLE ||
                        chunks.get(leftAdjacentChunkIndex).get(f).spawn == Chunk.Spawn.CROSS_OBSTACLE ||
                        chunks.get(leftAdjacentChunkIndex).get(f).spawn == Chunk.Spawn.LINE_OBSTACLE) {
                    forbidObstacle = true;
                }
            }

            //Check the chunk below
            if (newList.size() != 0 && newList.size() > bottomAdjacentChunkIndex) { //prevent index out of bounds
                if (newList.get(bottomAdjacentChunkIndex).spawn == Chunk.Spawn.CIRCLE_OBSTACLE ||
                        newList.get(bottomAdjacentChunkIndex).spawn == Chunk.Spawn.SQUARE_OBSTACLE ||
                        newList.get(bottomAdjacentChunkIndex).spawn == Chunk.Spawn.CROSS_OBSTACLE ||
                        newList.get(bottomAdjacentChunkIndex).spawn == Chunk.Spawn.LINE_OBSTACLE) {
                    forbidObstacle = true;
                }
            }

            newChunk = new Chunk(startXcoord, addCoordY, startXindex, addIndexY, false, forbidObstacle);


            addObjects(newChunk, NUM_ROWS_COLUMNS, NUM_ROWS_COLUMNS);
            newList.add(newChunk);
            //System.out.println("R Spawn: Created chunk of coordinate (" + startXcoord + ", " + addCoordY + "), index "
            //        + startXindex + " / " + addIndexY);
            addCoordY += Chunk.chunkSize;
            addIndexY += 1;
        }

        //System.out.println("R Spawn: Adding these chunks");

        //add the column
        chunks.add(newList);
        System.out.println("R Spawn: Chunks size after adding: " + chunks.size() + "\nR Spawn: Now freeing the left.");


        //free all contents of the chunk column to be deleted
        // (the first arraylist in each arraylist, AKA the left column)
        for (Chunk chunk : chunks.get(0)) {
            chunk = null;
        }
        for (ColorChanger cc : colorChangers.get(0)) {
            if (cc != null) {

                colorChangerPool.free(cc);
                System.out.println("R Spawn: Free CCs remaining in pool " +
                        "after freeing this one: " + colorChangerPool.getFree());
            }
        }
        for (OneUp ou : oneUps.get(0)) {
            if (ou != null) {

                //System.out.println("R Spawn: Free One Ups remaining in pool " +
                //        "before freeing this one: " + oneUpPool.getFree());
                oneUpPool.free(ou);
                System.out.println("R Spawn: Free One Ups remaining in pool " +
                        "after freeing this one: " + oneUpPool.getFree());
            }
        }
        for (PlagueParticle_Green pg : plagueParticleGreens.get(0)) {
            if (pg != null) {
                //System.out.println("R Spawn: Free PPGs remaining in pool " +
                //        "before freeing this one: " + plagueParticleGreenPool.getFree());
                plagueParticleGreenPool.free(pg);
                System.out.println("R Spawn: Free PPGs remaining in pool " +
                        "after freeing this one: " + plagueParticleGreenPool.getFree());
            }
        }
        for (PlagueParticle_Purple pp : plagueParticlePurples.get(0)) {
            if (pp != null) {
                //System.out.println("R Spawn: Free PPPs remaining in pool " +
                //        "before freeing this one: " + plagueParticlePurplePool.getFree());
                plagueParticlePurplePool.free(pp);
                System.out.println("R Spawn: Free PPPs remaining in pool " +
                        "after freeing this one: " + plagueParticlePurplePool.getFree());
            }
        }
        for (CircleObstacle co : circleObstacles.get(0)) {
            if (co != null) {
                circleObstaclePool.free(co);
                System.out.println("R Spawn: Free COs remaining in pool " +
                        "after freeing this one: " + circleObstaclePool.getFree());
            }
        }
        for (SquareObstacle so : squareObstacles.get(0)) {
            if (so != null) {
                squareObstaclePool.free(so);
                System.out.println("R Spawn: Free SOs remaining in pool " +
                        "after freeing this one: " + squareObstaclePool.getFree());
            }
        }
        for (CrossObstacle cross : crossObstacles.get(0)) {
            if (cross != null) {
                crossObstaclePool.free(cross);
                System.out.println("R Spawn: Free COs remaining in pool " +
                        "after freeing this one: " + crossObstaclePool.getFree());
            }
        }
        for (LineObstacle lo : lineObstacles.get(0)) {
            if (lo != null) {
                lineObstaclePool.free(lo);
                System.out.println("R Spawn: Free LOs remaining in pool " +
                        "after freeing this one: " + lineObstaclePool.getFree());
            }
        }
        for (McDouble md : mcDoubles.get(0)) {
            if (md != null) {
                //System.out.println("R Spawn: Free McDoubles remaining in pool " +
                //        "before freeing this one: " + mcDoublePool.getFree());
                mcDoublePool.free(md);
                System.out.println("R Spawn: Free McDoubles remaining in pool " +
                        "after freeing this one: " + mcDoublePool.getFree());
            }
        }

        //remove the first arraylist in chunks (i.e. the left column)
        chunks.remove(0);

        //System.out.println("R Spawn: Chunks array size after removal: " + chunks.size());
        //System.out.println("R Spawn: ColorChangers array size before removal: " + colorChangers.size());

        //do the same for all the object arraylists (remember, we already went
        //through them and freed all the objects back to the pool)
        colorChangers.remove(0);
        oneUps.remove(0);
        plagueParticleGreens.remove(0);
        plagueParticlePurples.remove(0);
        circleObstacles.remove(0);
        squareObstacles.remove(0);
        crossObstacles.remove(0);
        lineObstacles.remove(0);
        mcDoubles.remove(0);

/*        System.out.println("R Spawn: post-remove sizes: CCs: " + colorChangers.size() + "; 1Us: " + oneUps.size()
                + "; PPGs: " + plagueParticleGreens.size() + "; PPPs: " + plagueParticlePurples.size() +
                "; Circles: " + circleObstacles.size() + "; Squares: " + squareObstacles.size() + "; MDs: " +
                mcDoubles.size() + " (these should all be the same as chunks.size())");*/
        //System.out.println("R Spawn: post-remove size of CCs: " + colorChangers.size());

        //System.gc(); //labor-intensive
    }

    public void spawnLeftColumn() {
        //Add a new array list to beginning of Chunks and populate it.
        //Also removes the last array list from Chunks.

        //Spawning a column on the right means adding a new arraylist to the end of
        //  the 2D chunk arraylist (and by extension, all the object arraylists too)
        //Deleting a column on the left means deleting the first arraylist in each one instead

        System.out.println("L Spawn: Will spawn a left column and delete the right now.");

        ArrayList<Chunk> newList = new ArrayList<Chunk>();

        int addCoordY = 0;
        int addIndexY = 0;
        //System.out.println("L Spawn: Chunks size at beginning of left spawner: " + chunks.size());
        //System.out.println("L Spawn: CCs size at beginning of left spawner: " + colorChangers.size());

        int startXcoord = chunks.get(0).get(0).leftX - Chunk.chunkSize;
        int startXindex = chunks.get(0).get(0).arrayIndexX - 1;

        addCoordY = chunks.get(chunks.size() - 1).get(0).bottomY;
        //get the bottom y of the bottom-right chunk

        colorChangers.add(0, new ArrayList<ColorChanger>());
        oneUps.add(0, new ArrayList<OneUp>());
        plagueParticleGreens.add(0, new ArrayList<PlagueParticle_Green>());
        plagueParticlePurples.add(0, new ArrayList<PlagueParticle_Purple>());
        circleObstacles.add(0, new ArrayList<CircleObstacle>());
        squareObstacles.add(0, new ArrayList<SquareObstacle>());
        crossObstacles.add(0, new ArrayList<CrossObstacle>());
        lineObstacles.add(0, new ArrayList<LineObstacle>());
        mcDoubles.add(0, new ArrayList<McDouble>());


        int rightAdjacentChunkIndex;
        int bottomAdjacentChunkIndex;
        boolean forbidObstacle;


        for (int f = 0; f < NUM_ROWS_COLUMNS; f++) {


            //Look at the 2 chunks immediately below and to the right of this chunk, check to see
            //if they contain an obstacle, if they do, forbid obstacles for this chunk.

            Chunk newChunk;
            forbidObstacle = false;

            rightAdjacentChunkIndex = 0;
            bottomAdjacentChunkIndex = f - 1;

            //check the chunk to the right
            if (chunks.size() != 0 && chunks.get(rightAdjacentChunkIndex).size() > f) { //prevent index out of bounds
                if (chunks.get(rightAdjacentChunkIndex).get(f).spawn == Chunk.Spawn.CIRCLE_OBSTACLE ||
                        chunks.get(rightAdjacentChunkIndex).get(f).spawn == Chunk.Spawn.SQUARE_OBSTACLE ||
                        chunks.get(rightAdjacentChunkIndex).get(f).spawn == Chunk.Spawn.CROSS_OBSTACLE ||
                        chunks.get(rightAdjacentChunkIndex).get(f).spawn == Chunk.Spawn.LINE_OBSTACLE) {
                    forbidObstacle = true;
                }
            }

            //check the chunk below
            if (newList.size() != 0 && newList.size() > bottomAdjacentChunkIndex) { //prevent index out of bounds
                if (newList.get(bottomAdjacentChunkIndex).spawn == Chunk.Spawn.CIRCLE_OBSTACLE ||
                        newList.get(bottomAdjacentChunkIndex).spawn == Chunk.Spawn.SQUARE_OBSTACLE ||
                        newList.get(bottomAdjacentChunkIndex).spawn == Chunk.Spawn.CROSS_OBSTACLE ||
                        newList.get(bottomAdjacentChunkIndex).spawn == Chunk.Spawn.LINE_OBSTACLE) {
                    forbidObstacle = true;
                }
            }


            newChunk = new Chunk(startXcoord, addCoordY, startXindex, addIndexY, false, forbidObstacle);

            addObjects(newChunk, 0, NUM_ROWS_COLUMNS - 1);
            newList.add(newChunk);
            //System.out.println("L Spawn: Dynamically created chunk of coordinate (" + startXcoord + ", " + addCoordY + "), index "
            //        + startXindex + " / " + addIndexY);

            addCoordY += Chunk.chunkSize;
            addIndexY += 1;
        }

        //System.out.println(chunks.size());
        //System.out.println("L Spawn: Adding these chunks");
        chunks.add(0, newList);
        //System.out.println("L Spawn: Chunks size after adding: " + chunks.size() + "\nL Spawn: Now freeing the right");

        System.out.println("L Spawn: freeing the right");

        //free the contents of the chunk column to be deleted

        //for (int i = 0; i < NUM_ROWS_COLUMNS; i++) {
        int lastIndex = chunks.size() - 1;

        for (Chunk chunk : chunks.get(lastIndex)) {
            chunk = null;
        }
        for (ColorChanger cc : colorChangers.get(lastIndex)) {
            if (cc != null) {
/*                cc.clearActions();
                cc.remove();
                cc = null;*/
                colorChangerPool.free(cc);
            }
        }
        for (OneUp ou : oneUps.get(lastIndex)) {
            if (ou != null) {
                oneUpPool.free(ou);
            }
        }
        for (PlagueParticle_Green pg : plagueParticleGreens.get(lastIndex)) {
            if (pg != null) {
                plagueParticleGreenPool.free(pg);
            }
        }
        for (PlagueParticle_Purple pp : plagueParticlePurples.get(lastIndex)) {
            if (pp != null) {
                plagueParticlePurplePool.free(pp);
            }
        }
        for (CircleObstacle co : circleObstacles.get(lastIndex)) {
            if (co != null) {
                circleObstaclePool.free(co);
            }
        }
        for (SquareObstacle so : squareObstacles.get(lastIndex)) {
            if (so != null) {
                squareObstaclePool.free(so);
            }
        }
        for (CrossObstacle cross : crossObstacles.get(lastIndex)) {
            if (cross != null) {
                crossObstaclePool.free(cross);
            }
        }
        for (LineObstacle lo : lineObstacles.get(lastIndex)) {
            if (lo != null) {
                lineObstaclePool.free(lo);
            }
        }
        for (McDouble md : mcDoubles.get(lastIndex)) {
            if (md != null) {
                //md.clearActions();
                //md.remove();
                mcDoublePool.free(md);
            }
        }

        chunks.remove(lastIndex);

        //System.out.println("L Spawn: Chunks array size after removal: " + chunks.size());
        //System.out.println("L Spawn: ColorChangers array size before removal: " + colorChangers.size());

        colorChangers.remove(lastIndex);
        oneUps.remove(lastIndex);
        plagueParticleGreens.remove(lastIndex);
        plagueParticlePurples.remove(lastIndex);
        circleObstacles.remove(lastIndex);
        squareObstacles.remove(lastIndex);
        crossObstacles.remove(lastIndex);
        lineObstacles.remove(lastIndex);
        mcDoubles.remove(lastIndex);

/*        System.out.println("L Spawn: post-remove sizes: CCs: " + colorChangers.size() + "; 1Us: " + oneUps.size()
                + "; PPGs: " + plagueParticleGreens.size() + "; PPPs: " + plagueParticlePurples.size() +
                "; Circles: " + circleObstacles.size() + "; Squares: " + squareObstacles.size() + "; MDs: " +
                mcDoubles.size() + " (these should all be the same as chunks.size())");*/

        //System.gc();
    }


    public void spawnTopRow() {
        //Add a new chunk to the end of every arraylist in the Chunks arraylist.
        //Also removes the first chunk from every arraylist.

        //Spawning a row on the top means adding a new chunk to the end of every array in the
        //the 2D chunk arraylist (and by extension, all the object arraylists too).
        //Deleting a row on the bottom means deleting the first object in each arraylist in each one.

        System.out.println("T Spawn: Will spawn a top row and delete the bottom now.");

        ArrayList<Chunk> newList = new ArrayList<Chunk>();

        int addCoordX = 0;
        int addIndexX = 0;

        //System.out.println("T Spawn: Chunks size at beginning of top spawner: " + chunks.size());
        //System.out.println("T Spawn: Chunks.get(0) size at beginning of top spawner: " + chunks.get(0).size());
        //System.out.println("T Spawn: CCs size at beginning of top spawner: " + colorChangers.size());
        //System.out.println("T Spawn: CCs.get(0) size at beginning of top spawner: " + colorChangers.get(0).size());

        int startYcoord = chunks.get(0).get(chunks.get(0).size() - 1).bottomY + Chunk.chunkSize;
        //The Y coord of all chunks that will be added; the chunk above the top-left chunk's Y coord

        int startYindex = chunks.get(0).get(chunks.get(0).size() - 1).arrayIndexY + 1;
        // the Y index of all chunks that will be added; the index above the top-left chunk's Y index

        addCoordX = chunks.get(0).get(chunks.get(0).size() - 1).leftX;
        // get the left of the top-left chunk
        // this will have to be incremented because we are generating chunks from left to right.


        int leftAdjacentChunkIndex;
        int bottomAdjacentChunkIndex;
        boolean forbidObstacle;
        int lastIndexHolder;

        // fixme - recent addition
        Chunk.Spawn spawnHolder;

        for (int index = 0; index < NUM_ROWS_COLUMNS; index++) {

            Chunk newChunk;

            //Look at the 2 chunks immediately below and to the left of this chunk, check to see
            //if they contain an obstacle, if they do, forbid obstacles for this chunk.

            forbidObstacle = false;

            leftAdjacentChunkIndex = index - 1;
            bottomAdjacentChunkIndex = NUM_ROWS_COLUMNS - 2;

            //check chunk to the left..
            if (chunks.size() != 0 && chunks.size() > leftAdjacentChunkIndex && leftAdjacentChunkIndex >= 0) { //for index oob
                if (chunks.get(leftAdjacentChunkIndex).size() > 0) { //for index oob
                    lastIndexHolder = chunks.get(leftAdjacentChunkIndex).size() - 1;
                    spawnHolder = chunks.get(leftAdjacentChunkIndex).get(lastIndexHolder).spawn;
                    if (spawnHolder == Chunk.Spawn.CIRCLE_OBSTACLE ||
                            spawnHolder == Chunk.Spawn.SQUARE_OBSTACLE ||
                            spawnHolder == Chunk.Spawn.CROSS_OBSTACLE ||
                            spawnHolder == Chunk.Spawn.LINE_OBSTACLE) {
                        forbidObstacle = true;
                    }
                }
            }

            if (chunks.get(index).size() > bottomAdjacentChunkIndex) {
                if (chunks.get(index).size() != 0) {
                    lastIndexHolder = chunks.get(index).size() - 1;
                    spawnHolder = chunks.get(index).get(lastIndexHolder).spawn;
                    if (spawnHolder == Chunk.Spawn.CIRCLE_OBSTACLE ||
                            spawnHolder == Chunk.Spawn.SQUARE_OBSTACLE ||
                            spawnHolder == Chunk.Spawn.CROSS_OBSTACLE ||
                            spawnHolder == Chunk.Spawn.LINE_OBSTACLE) {
                        forbidObstacle = true;
                    }
                }
            }


            newChunk = new Chunk(addCoordX, startYcoord, addIndexX, startYindex, false, forbidObstacle);
            addObjects(newChunk, index, NUM_ROWS_COLUMNS - 1);
            chunks.get(index).add(newChunk);
            //newList.add(newChunk);
            //System.out.println("T Spawn: Dynamically created and added chunk of coordinate (" + addCoordX + ", " + startYcoord + "), index "
            //        + addIndexX + " / " + startYindex);

            addCoordX += Chunk.chunkSize;
            addIndexX += 1;
        }

        //System.out.println("T Spawn: Chunks.get(0) size after adding: " + chunks.get(0).size() + "\nT Spawn: Now freeing the bottom");

        //free the contents of the chunk row to be deleted (that is, the first thing in every inner arraylist)

        for (ArrayList<Chunk> chunkArr : chunks) {
            chunkArr.set(0, null);
            chunkArr.remove(0);
        }

        for (ArrayList<ColorChanger> cc : colorChangers) {
            if (cc.get(0) != null) {
/*                cc.get(0).clearActions();
                cc.get(0).remove();*/
                colorChangerPool.free(cc.get(0));
                //cc.set(0, null);
            }
            cc.remove(0);
            //System.out.println("REMOVE CC, size " + cc.size());
        }
        for (ArrayList<OneUp> ou : oneUps) {
            if (ou.get(0) != null) {
                oneUpPool.free(ou.get(0));
                //ou.set(0, null);
            }
            ou.remove(0);
        }
        for (ArrayList<PlagueParticle_Green> pg : plagueParticleGreens) {
            if (pg.get(0) != null) {
//                pg.get(0).clearActions();
//                pg.get(0).remove();
                plagueParticleGreenPool.free(pg.get(0));
                //pg.set(0, null);
            }
            pg.remove(0);
        }
        for (ArrayList<PlagueParticle_Purple> pp : plagueParticlePurples) {
            if (pp.get(0) != null) {
/*                pp.get(0).clearActions();
                pp.get(0).remove();
                pp.set(0, null);*/
                plagueParticlePurplePool.free(pp.get(0));
            }
            pp.remove(0);
        }
        for (ArrayList<CircleObstacle> co : circleObstacles) {
            if (co.get(0) != null) {
                circleObstaclePool.free(co.get(0));
            }
            co.remove(0);
        }
        for (ArrayList<SquareObstacle> so : squareObstacles) {
            if (so.get(0) != null) {
                squareObstaclePool.free(so.get(0));
            }
            so.remove(0);
        }
        for (ArrayList<CrossObstacle> cross : crossObstacles) {
            if (cross.get(0) != null) {
                crossObstaclePool.free(cross.get(0));
            }
            cross.remove(0);
        }
        for (ArrayList<LineObstacle> lo : lineObstacles) {
            if (lo.get(0) != null) {
                lineObstaclePool.free(lo.get(0));
            }
            lo.remove(0);
        }
        for (ArrayList<McDouble> md : mcDoubles) {
            if (md.get(0) != null) {
                //md.get(0).clearActions();
                //md.get(0).remove();
                mcDoublePool.free(md.get(0));
            }
            //md.set(0, null);}
            md.remove(0);
        }

        //System.out.println("T Spawn: Chunks size after removal: " + chunks.size());
        //System.out.println("T Spawn: Chunks.get(0) size after removal: " + chunks.get(0).size());
        //System.out.println("T Spawn: CCs size after removal: " + colorChangers.size());
        //System.out.println("T Spawn: CCs.get(0) size after removal: " + colorChangers.get(0).size());


/*        System.out.println("Spawn: post-remove sizes: CCs: " + colorChangers.size() + "; 1Us: " + oneUps.size()
                + "; PPGs: " + plagueParticleGreens.size() + "; PPPs: " + plagueParticlePurples.size() +
                "; Circles: " + circleObstacles.size() + "; Squares: " + squareObstacles.size() + "; MDs: " +
                mcDoubles.size() + " (these should all be the same as chunks.size())");*/

        //System.gc();
    }

    public void spawnBottomRow() {
        //Add a new chunk to the end of every arraylist in the Chunks arraylist and populate it.
        //Also removes the first chunk from every arraylist.

        //Spawning a row on the bottom means adding a new chunk to the BEGINNING of every array in the
        //the 2D chunk arraylist (and by extension, all the object arraylists too)
        //Deleting a row on the top means deleting the last object in each arraylist in each one instead

        System.out.println("B Spawn: Will spawn a bottom row and delete the top now.");

        int addCoordX = 0;
        int addIndexX = 0;

        //System.out.println("B Spawn: Chunks size at beginning of top spawner: " + chunks.size());
        //System.out.println("B Spawn: Chunks.get(0) size at beginning of top spawner: " + chunks.get(0).size());
        //System.out.println("B Spawn: CCs size at beginning of top spawner: " + colorChangers.size());
        //System.out.println("B Spawn: CCs.get(0) size at beginning of top spawner: " + colorChangers.get(0).size());

        int startYcoord = chunks.get(0).get(0).bottomY - Chunk.chunkSize;
        //System.out.println("B Spawn: startYcoord: " + startYcoord);
        //The Y coord of all chunks that will be added; the chunk below bottom-left chunk's Y coord

        int startYindex = chunks.get(0).get(0).arrayIndexY;
        // the Y index of all chunks that will be added; the index below the bottom-left chunk's Y index

        addCoordX = chunks.get(0).get(0).leftX;
        // get the left of the bottom-left chunk
        // this will have to be incremented because we are generating chunks from left to right.

        System.out.println("Y: " + startYcoord + "\nX: " + addCoordX);


        boolean forbidObstacles;
        int topAdjacentChunkIndex;
        int leftAdjacentChunkIndex;

        Chunk.Spawn spawnHolder;

        for (int index = 0; index < NUM_ROWS_COLUMNS; index++) {


            //Look at the 2 chunks immediately ABOVE and to the left of this chunk, check to see
            //if they contain an obstacle, if they do, forbid obstacles for this chunk.

            forbidObstacles = false;

            //will be first chunk in this column

            //check top chunk (first chink in this column)
            if (chunks.size() > index && chunks.get(index).size() != 0) {
                spawnHolder = chunks.get(index).get(0).spawn;
                if (spawnHolder == Chunk.Spawn.CIRCLE_OBSTACLE ||
                        spawnHolder == Chunk.Spawn.SQUARE_OBSTACLE ||
                        spawnHolder == Chunk.Spawn.CROSS_OBSTACLE ||
                        spawnHolder == Chunk.Spawn.LINE_OBSTACLE) {
                    forbidObstacles = true;
                }
            }

            //check left chunk (first chunk in 1 column to the left)
            if (index > 0 && chunks.get(index - 1).size() != 0) {
                spawnHolder = chunks.get(index-1).get(0).spawn;
                if (spawnHolder == Chunk.Spawn.CIRCLE_OBSTACLE ||
                        spawnHolder == Chunk.Spawn.SQUARE_OBSTACLE ||
                        spawnHolder == Chunk.Spawn.CROSS_OBSTACLE ||
                        spawnHolder == Chunk.Spawn.LINE_OBSTACLE) {
                    forbidObstacles = true;
                }
            }

            Chunk newChunk = new Chunk(addCoordX, startYcoord, addIndexX, startYindex, false, forbidObstacles);

            addObjects(newChunk, index, 0);
            chunks.get(index).add(0, newChunk);
            //System.out.println("B Spawn: Dynamically created and added chunk of coordinate (" + addCoordX + ", " + startYcoord + "), index "
            //        + addIndexX + " / " + startYindex);

            addCoordX += Chunk.chunkSize;
            addIndexX += 1;
        }

        //System.out.println("B Spawn: Chunks.get(0) size after adding: " + chunks.get(0).size() + "\nB Spawn: Now freeing the top");

        //free the contents of the chunk row to be deleted (that is, the first thing in every inner arraylist)

        int lastIndex = chunks.size() - 1;

        for (ArrayList<Chunk> chunkArr : chunks) {
            chunkArr.set(chunkArr.size() - 1, null);
            chunkArr.remove(chunkArr.size() - 1);
            //System.out.println("Bottom Y: " + chunkArr.get(chunks.size()-1).bottomY);
        }

        for (ArrayList<ColorChanger> cc : colorChangers) {
            if (cc.get(lastIndex) != null) {
/*                cc.get(lastIndex).clearActions();
                cc.get(lastIndex).remove();*/
                //cc.set(lastIndex, null);
                colorChangerPool.free(cc.get(lastIndex));
            }
            cc.remove(lastIndex);
            //System.out.println("REMOVE CC, size " + cc.size());
        }
        for (ArrayList<OneUp> ou : oneUps) {
            if (ou.get(lastIndex) != null) {
                oneUpPool.free(ou.get(lastIndex));
                //ou.set(lastIndex, null);
            }
            ou.remove(lastIndex);
        }
        for (ArrayList<PlagueParticle_Green> pg : plagueParticleGreens) {
            if (pg.get(lastIndex) != null) {
//                pg.get(lastIndex).clearActions();
//                pg.get(lastIndex).remove();
                plagueParticleGreenPool.free(pg.get(lastIndex));
                //pg.set(lastIndex, null);
            }
            pg.remove(lastIndex);
        }
        for (ArrayList<PlagueParticle_Purple> pp : plagueParticlePurples) {
            if (pp.get(lastIndex) != null) {
//                pp.get(lastIndex).clearActions();
//                pp.get(lastIndex).remove();
                plagueParticlePurplePool.free(pp.get(lastIndex));
                //pp.set(lastIndex, null);
            }
            pp.remove(lastIndex);
        }
        System.out.println("B Spawn: CO size is " + circleObstacles.size());
        for (ArrayList<CircleObstacle> co : circleObstacles) {
            System.out.println("B Spawn: this CO inner array is size " + co.size());
            if (co.get(lastIndex) != null) {
                circleObstaclePool.free(co.get(lastIndex));
            }
            co.remove(lastIndex);
        }
        for (ArrayList<SquareObstacle> so : squareObstacles) {
            if (so.get(lastIndex) != null) {
                squareObstaclePool.free(so.get(lastIndex));
            }
            so.remove(lastIndex);
        }
        for (ArrayList<CrossObstacle> cross : crossObstacles) {
            if (cross.get(lastIndex) != null) {
                crossObstaclePool.free(cross.get(lastIndex));
            }
            cross.remove(lastIndex);
        }
        for (ArrayList<LineObstacle> line : lineObstacles) {
            if (line.get(lastIndex) != null) {
                lineObstaclePool.free(line.get(lastIndex));
            }
            line.remove(lastIndex);
        }
        for (ArrayList<McDouble> md : mcDoubles) {
            if (md.get(lastIndex) != null) {
                mcDoublePool.free(md.get(lastIndex));
            }
            md.remove(lastIndex);
        }
    }


    //spawnchunks addition: redid snapPlayerX.


    public static void snapPlayerX(Player player) {
        float someOffset = 120;
        float x = player.getX() - someOffset;
        float something = (float) (300 * (Math.round((double) x / 300)));
        player.setX(something + someOffset);
    }

    /*
     * Snaps [player]'s current position to the center of the nearest chunk
     *     on the Y-axis.
     * [player]'s orientation: FALL_LEFT, FALL_RIGHT
     * spawnchunks addition: redid snapPlayerY.
     */

    public static void snapPlayerY(Player player) {
        float someOffset = 120;
        float y = player.getY() - someOffset;
        float something = (float) (300 * (Math.round((double) y / 300)));
        player.setY(something + someOffset);
    }
/*
    // FIXME - Rudimentary pause button created by ZP
    //spawnchunk addition: uncommented this out
    public boolean keyDown(int keyCode)
    {
        if (Gdx.input.isKeyPressed(Input.Keys.P))
            player.state = player.state.IDLE;
        return false;
    }
*/

    /////////////////////////////////////////////////////////////////////////////////
    // INITIALIZE_THE_STAGE

    public void initialize() {
        // Reset the Score
        McDouble.score = 0;
        this.boundaryCheck = false;

        // Leaderboard
        leaderboard = new Leaderboard(this);

        //Create the Pools for each of our objects.

        mcDoublePool = new Pool<McDouble>(25) {
            @Override
            protected McDouble newObject() {
                System.out.println("Init: creating new MD in pool");
                return new McDouble(0, 0, mainStage, 0, 0);
            }
        };

        plagueParticleGreenPool = new Pool<PlagueParticle_Green>(25) {
            @Override
            protected PlagueParticle_Green newObject() {
                System.out.println("Init: creating new PPG in pool");
                return new PlagueParticle_Green(0, 0, mainStage, 0, 0);
            }
        };

        plagueParticlePurplePool = new Pool<PlagueParticle_Purple>(25) {
            @Override
            protected PlagueParticle_Purple newObject() {
                System.out.println("Init: creating new PPP in pool");
                return new PlagueParticle_Purple(0, 0, mainStage, 0, 0);
            }
        };

        oneUpPool = new Pool<OneUp>(25) {
            @Override
            protected OneUp newObject() {
                System.out.println("Init: creating new One Up in pool");
                return new OneUp(0, 0, mainStage, 0, 0);
            }
        };

        colorChangerPool = new Pool<ColorChanger>(25) {
            @Override
            protected ColorChanger newObject() {
                System.out.println("Init: creating new CC in pool");
                return new ColorChanger(0, 0, mainStage, 0, 0);
            }
        };

        circleObstaclePool = new Pool<CircleObstacle>() {
            @Override
            protected CircleObstacle newObject() {
                System.out.println("Init: creating new Circle in pool");
                return new CircleObstacle(0, 0, mainStage, 0, 0);
            }
        };

        squareObstaclePool = new Pool<SquareObstacle>() {
            @Override
            protected SquareObstacle newObject() {
                System.out.println("Init: creating new Square in pool");
                return new SquareObstacle(0, 0, mainStage, 0, 0);
            }
        };

        crossObstaclePool = new Pool<CrossObstacle>() {
            @Override
            protected CrossObstacle newObject() {
                System.out.println("Init: creating new Cross in pool");
                return new CrossObstacle(0, 0, mainStage, 0, 0);
            }
        };

        lineObstaclePool = new Pool<LineObstacle>() {
            @Override
            protected LineObstacle newObject() {
                System.out.println("Init: creating new Line in pool");
                return new LineObstacle(0, 0, mainStage, 0, 0);
            }
        };

        /////////////////////////////////////////////////////////////////////////////////
        // SCORE_TAG

        scoreTag = new Label("0", BaseGame.labelStyle);
        scoreTag.setColor(new Color(0x06ff00ff));
        scoreTag.setPosition(20, 800);
        scoreTag.setFontScale(2f);
        uiStage.addActor(scoreTag);

        /////////////////////////////////////////////////////////////////////////////////
        // STATE_LABEL

        stateLabel = new Label("State: ", BaseGame.labelStyle);
        stateLabel.setColor(Color.CYAN);
        stateLabel.setPosition(20, 500);
        stateLabel.setFontScale(0.5f);
        uiStage.addActor(stateLabel);

        ///////////////////////////////////////////////////////
        ///// Populate the chunk list
        chunks = new ArrayList<ArrayList<Chunk>>();

        // initialize the second dimension arraylists.
        for (int e = 0; e < NUM_ROWS_COLUMNS; e++) {
            chunks.add(new ArrayList<Chunk>());
        } //end for

        // actually create chunk objects and add them to the arraylists

        //int xCoord = 0;
        //int xCoord = 900; //spawnchunk addition
        int xCoord = 4500;
        //int yCoord = 0;
        //int yCoord = 900; //spawnchunk addition
        int yCoord = 4500;
        int storeYCoord = yCoord; //used to restore the val of ycoord with each column

        boolean forbidObstacle;
        boolean forceNothing;

        for (int i = 0; i < NUM_ROWS_COLUMNS; i++) { //x
            for (int j = 0; j < NUM_ROWS_COLUMNS; j++) { //y

                forbidObstacle = false;
                forceNothing = false;

                if (i > 0) { //check left chunk for obstacle
                    if (chunks.get(i - 1).get(j).spawn == Chunk.Spawn.CIRCLE_OBSTACLE ||
                            chunks.get(i - 1).get(j).spawn == Chunk.Spawn.SQUARE_OBSTACLE ||
                            chunks.get(i - 1).get(j).spawn == Chunk.Spawn.CROSS_OBSTACLE ||
                            chunks.get(i - 1).get(j).spawn == Chunk.Spawn.LINE_OBSTACLE) {
                        forbidObstacle = true;
                    }
                }
                if (j > 0) { //check bottom chunk for obstacle
                    if (chunks.get(i).get(j - 1).spawn == Chunk.Spawn.CIRCLE_OBSTACLE ||
                            chunks.get(i).get(j - 1).spawn == Chunk.Spawn.SQUARE_OBSTACLE ||
                            chunks.get(i).get(j - 1).spawn == Chunk.Spawn.CROSS_OBSTACLE ||
                            chunks.get(i).get(j - 1).spawn == Chunk.Spawn.LINE_OBSTACLE) {
                        forbidObstacle = true;
                    }
                }

                if (i == 1 && j == 1) { //Middle chunk and the player's spawn chunk.
                    forceNothing = true;
                }

                chunks.get(i).add(new Chunk(xCoord, yCoord, i, j, forceNothing, forbidObstacle));

                System.out.println("Init: added chunk of coordinate (" + xCoord + ", " + yCoord + "), index " + i + " / " + j);

                yCoord += Chunk.chunkSize;
            }
            xCoord += Chunk.chunkSize;
            //yCoord = 0;
            yCoord = storeYCoord;
            //System.out.println("outer loop");
        } //end for

        System.out.println("Init: final size of chunk arr: " + chunks.size());

        /////////////////////////////////////////////////////////////////////////////////
        // ORIENTATION_LABEL

        orientLabel = new Label("Orientation: ", BaseGame.labelStyle);
        orientLabel.setColor(Color.CYAN);
        orientLabel.setPosition(20, 450);
        orientLabel.setFontScale(0.5f);
        uiStage.addActor(orientLabel);

        /////////////////////////////////////////////////////////////////////////////////
        // SPEED_LABEL

        speedLabel = new Label("Speed (rounded): ", BaseGame.labelStyle);
        speedLabel.setColor(Color.CYAN);
        speedLabel.setPosition(20, 400);
        speedLabel.setFontScale(0.5f);
        uiStage.addActor(speedLabel);

        /////////////////////////////////////////////////////////////////////////////////
        // XY_LABEL

        xyLabel = new Label("Location: ", BaseGame.labelStyle);
        xyLabel.setColor(Color.CYAN);
        xyLabel.setPosition(20, 350);
        xyLabel.setFontScale(0.5f);
        uiStage.addActor(xyLabel);

        /////////////////////////////////////////////////////////////////////////////////
        // ONE_UP_LABEL

        oneUpLabel = new Label("Has One-Up: ", BaseGame.labelStyle);
        oneUpLabel.setColor(Color.CYAN);
        oneUpLabel.setPosition(20, 300);
        oneUpLabel.setFontScale(0.5f);
        uiStage.addActor(oneUpLabel);

        /////////////////////////////////////////////////////////////////////////////////
        // HEX_LABEL

        hexLabel = new Label("RGB Value: ", BaseGame.labelStyle);
        hexLabel.setColor(Color.CYAN);
        hexLabel.setPosition(20, 250);
        hexLabel.setFontScale(0.5f);
        uiStage.addActor(hexLabel);

        /////////////////////////////////////////////////////////////////////////////////
        // SCORE_LABEL

        scoreLabel = new Label("Score Value: ", BaseGame.labelStyle);
        scoreLabel.setColor(Color.CYAN);
        scoreLabel.setPosition(20, 200);
        scoreLabel.setFontScale(0.5f);
        uiStage.addActor(scoreLabel);

        /////////////////////////////////////////////////////////////////////////////////
        // OBSTACLE_TIME_LABEL

        obstacleTimeLabel = new Label("Time Left: ", BaseGame.labelStyle);
        obstacleTimeLabel.setColor(Color.CYAN);
        obstacleTimeLabel.setPosition(20, 150);
        obstacleTimeLabel.setFontScale(0.5f);
        uiStage.addActor(obstacleTimeLabel);

        /////////////////////////////////////////////////////////////////////////////////
        // BOUNDARY_LABEL

        boundaryLabel = new Label("Boundary: ", BaseGame.labelStyle);
        boundaryLabel.setColor(Color.CYAN);
        boundaryLabel.setPosition(20, 100);
        boundaryLabel.setFontScale(0.5f);
        uiStage.addActor(boundaryLabel);

        /////////////////////////////////////////////////////////////////////////////////
        // GRID_BACKGROUND

        // FIX BACKGROUND

        grid = new BaseActor(0, 0, mainStage);
        grid.loadTexture("triangle.png");
        //grid.loadTexture("9x9.png");
        //****** changed from 1350 for resize!
        grid.setSize(2700, 2700);
        //BaseActor.setWorldBounds(grid);
        BaseActor.setWorldBounds(10000, 10000);//spawnchunks addition

        /////////////////////////////////////////////////////////////////////////////////
        // CREATE_[PLAYER]

        //player = new Player(680, 380, mainStage);
        //player = new Player(1350,1350, mainStage); //spawnchunk addition
        player = new Player(4800, 4800, mainStage);

        snapPlayerX(player);
        snapPlayerY(player);

        dummyBoundary = new BoundaryDummy(4800, 4800, mainStage, true);
        dummyBoundary.centerAtActor(player);
        dummySickFaceFilter = new SickFaceFilterDummy(4800, 4800, mainStage);
        dummySickFaceFilter.centerAtActor(player);
        dummySickFace = new SickDummy(4800, 4800, mainStage);
        dummySickFace.centerAtActor(player);
        dummyOneUp = new OneUpDummy(4800, 4800, mainStage);
        dummyOneUp.centerAtActor(player);

        grid.centerAtActor(dummyBoundary);

        /////////////////////////////////////////////////////////////////////////////////
        // INITIALIZE_OBSTACLE_COLORS

        BaseObstacle.obstacleColors[0] = ColorPalette.colorsHexa[randPalette][1];
        BaseObstacle.obstacleColors[1] = ColorPalette.colorsHexa[randPalette][2];
        BaseObstacle.obstacleColors[2] = ColorPalette.colorsHexa[randPalette][3];
        BaseObstacle.obstacleColors[3] = ColorPalette.colorsHexa[randPalette][4];

        /////////////////////////////////////////////////////////////////////////////////
        // CHUNK SPAWNING

        // Go through all chunks and add plague/one up if that chunk is set to contain one.
        // (whether it contains one is set to it in Chunk class upon each chunk initialization.)

        plagueParticleGreens = new ArrayList<>();
        plagueParticlePurples = new ArrayList<>();
        oneUps = new ArrayList<>();
        colorChangers = new ArrayList<>();
        circleObstacles = new ArrayList<>();
        squareObstacles = new ArrayList<>();
        crossObstacles = new ArrayList<>();
        lineObstacles = new ArrayList<>();
        mcDoubles = new ArrayList<>();
        //Chunk thisChunk; -- spawnchunks removal

        //int someOffset = 46; //again, found through trial and error
        //spawnchunks removal


        //spawnchunk addition: this whole section below to the end of initialize
        for (int i = 0; i <= NUM_ROWS_COLUMNS - 1; i++) {
            plagueParticleGreens.add(new ArrayList<PlagueParticle_Green>());
            plagueParticlePurples.add(new ArrayList<PlagueParticle_Purple>());
            oneUps.add(new ArrayList<OneUp>());
            colorChangers.add(new ArrayList<ColorChanger>());
            circleObstacles.add(new ArrayList<CircleObstacle>());
            squareObstacles.add(new ArrayList<SquareObstacle>());
            crossObstacles.add(new ArrayList<CrossObstacle>());
            lineObstacles.add(new ArrayList<LineObstacle>());
            mcDoubles.add(new ArrayList<McDouble>());

            //System.out.println("Init: PPG size in loop: " + plagueParticleGreens.size());
        }

        Chunk thisChunk;

        //int someOffset = 46; //again, found through trial and error

        for (int f = 0; f < NUM_ROWS_COLUMNS; f++) {
            for (int h = 0; h < NUM_ROWS_COLUMNS; h++) {
                thisChunk = chunks.get(f).get(h);
                addObjects(thisChunk, f, h);

            }
        }

    } //end initialize

    public void handlePlayerDies() {
        player.addAction(Actions.fadeOut(1));
        BaseActor gameOverMessage = new BaseActor(0,0,uiStage);
        gameOverMessage.loadTexture("gameover.png");
        gameOverMessage.setSize(632, 146);
        gameOverMessage.centerAtPosition(450,600);
        gameOverMessage.setOpacity(0);
        gameOverMessage.addAction( Actions.delay(1) );
        gameOverMessage.addAction( Actions.after( Actions.fadeIn(1) ) );

        Button.ButtonStyle buttonStyleMenu = new Button.ButtonStyle();

        Texture buttonTexMenu = new Texture(Gdx.files.internal("menu.png"));

        TextureRegion buttonRegionMenu =  new TextureRegion(buttonTexMenu);
        buttonStyleMenu.up = new TextureRegionDrawable( buttonRegionMenu );
        Button menuButton = new Button( buttonStyleMenu );
        menuButton.setPosition(25, 25);
        menuButton.setSize(142, 108);
        uiStage.addActor(menuButton);

        menuButton.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;

                    buttonSound.play();
                    //ColorOnaGame.setActiveScreen(new MenuScreen());
                    this.getColorOnaGame().showMenuScreen();
                    return true;
                }
        );

        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                try {
                    leaderboard.endGame(McDouble.score);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 1.5f);
    }

    /////////////////////////////////////////////////////////////////////////////////
    // UPDATE

    public void update(float dt)
    {
        grid.centerAtActor(dummyBoundary);

        /////////////////////////////////////////////////////////////////////////////////
        // INPUT/OUTPUT_UI
        // Quit
        if ((!ignoreButtons && Gdx.input.isKeyPressed(Input.Keys.Q)) || Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            buttonSound.play();
            app.exit();
        }
        // Menu
        if (!ignoreButtons && Gdx.input.isKeyPressed(Input.Keys.M)) {
            this.getColorOnaGame().showMenuScreen();
            buttonSound.play();
        }
        //Pause
        if (!ignoreButtons && Gdx.input.isKeyPressed(Input.Keys.P)) {
            player.state = Player.State.IDLE;
            snapPlayerX(player);
            snapPlayerY(player);
            buttonSound.play();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.R)) {
            player.state = Player.State.FALLING;
            buttonSound.play();
        }

        /////////////////////////////////////////////////////////////////////////////////
        // INPUT/OUTPUT_DEBUGGING_FOR_GRAVITY
/*
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.orient = Player.Orientation.FALL_DOWN;
            snapPlayerX(player);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.orient = Player.Orientation.FALL_RIGHT;
            snapPlayerY(player);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.orient = Player.Orientation.FALL_UP;
            snapPlayerX(player);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.orient = Player.Orientation.FALL_LEFT;
            snapPlayerY(player);
        }
*/
        /////////////////////////////////////////////////////////////////////////////////
        // JUMP_AND_FALL

        player.playerJump();

        //spawnchunk addition: below block up to the line of asterisks

/*        System.out.println("Update: printing lengths of inner arraylist of Chunks");
        System.out.println("Update: chunks size: " + chunks.size());
        for (ArrayList<Chunk> arr : chunks){
            System.out.println(arr.size());
        }*/

        /////////////////////////////////////////////////////////////////////////////////

        //Detect whether to spawn new right column and despawn left.
        int leftColumnX = chunks.get(0).get(0).leftX; //bottom-left chunk
        if (player.getX() - leftColumnX >= 1000) {
            System.out.println("Update: Calling right col spawner/despawner, chunks[0]'s size is " + chunks.get(0).size());
            spawnRightColumn();
        }

        //get the left x coord of the last col on the right to detect whether to spawn new left column
        int rightColumnX = chunks.get(chunks.size() - 1).get(0).leftX; //bottom-right chunk
        if (player.getX() - rightColumnX <= -1000) {
            System.out.println("Update: Calling left col spawner/despawner, chunks[0]'s size is " + chunks.get(0).size());
            spawnLeftColumn();
        }


        int bottomRowY = chunks.get(0).get(0).bottomY; //bottom-left chunk
        if (player.getY() - bottomRowY >= 1000) {
            System.out.println(bottomRowY);
            System.out.println((player.getY() - bottomRowY));
            System.out.println("Update: Calling top row spawner/despawner, chunks[0]'s size is " + chunks.get(0).size());
            spawnTopRow();

        }

        int topRowY = chunks.get(0).get(chunks.get(0).size() - 1).bottomY; //top-left chunk
        //System.out.println(topRowY);
        //System.out.println("Update: PlayerY (" + player.getY() + ") - TopRowY (" + topRowY + ") = " + (player.getY() - topRowY));
        if (player.getY() - topRowY <= -1000) {
            //System.out.println(topRowY);
            //System.out.println((player.getY()-topRowY));
            System.out.println("Update: Calling bottom row spawner/despawner, chunks[0]'s size is " + chunks.get(0).size());
             spawnBottomRow();
        }

        //************


        /////////////////////////////////////////////////////////////////////////////////
        // SCORE_TAG_TEXT

        scoreTag.setText("" + McDouble.score);

        /////////////////////////////////////////////////////////////////////////////////
        // DEBUG_STATE_TEXT
/*
        if (player.state == Player.State.JUMPING) {
            stateLabel.setText("State: JUMPING");
        } else if (player.state == Player.State.IDLE) {
            stateLabel.setText("State: IDLE");
        } else if (player.state == Player.State.FALLING) {
            stateLabel.setText("State: FALLING");
        } else if (player.state == Player.State.DEAD) {
            stateLabel.setText("State: DEAD");
        }
*/
        stateLabel.setText("");

        /////////////////////////////////////////////////////////////////////////////////
        // DEBUG_SPEED_TEXT

        // speedLabel.setText("Speed (rounded): " + (int) player.moveIncrement);
        speedLabel.setText("");

        /////////////////////////////////////////////////////////////////////////////////
        // DEBUG_XY_TEXT

        //xyLabel.setText("Location (rounded): " + (int) player.getX() + ", " + (int) player.getY());
        xyLabel.setText("");

        /////////////////////////////////////////////////////////////////////////////////
        // DEBUG_ORIENTATION_TEXT

        // orientLabel.setText("Orientation: " + player.orient);
        orientLabel.setText("");

        /////////////////////////////////////////////////////////////////////////////////
        // DEBUG_ONE_UP_TEXT
/*
        if (player.hasOneUp) {
            oneUpLabel.setText("One-Up: True");
        } else {
            oneUpLabel.setText("One-Up: False");
        }
*/
        oneUpLabel.setText("");

        /////////////////////////////////////////////////////////////////////////////////
        // DEBUG_HEX_TEXT

        // hexLabel.setText("Hex Value: " + player.hexColor);
        hexLabel.setText("");

        /////////////////////////////////////////////////////////////////////////////////
        // DEBUG_SCORE_TEXT

        // scoreLabel.setText("Score Value: " + McDouble.score);
        scoreLabel.setText("");

        /////////////////////////////////////////////////////////////////////////////////
        // DEBUG_OBSTACLE_TIME_TEXT

        // FIXME - Also used as a basis for updating BaseObstacle.colorTime

        if (BaseObstacle.colorTime > 0) {
            BaseObstacle.colorTime -= dt;
        } else if (BaseObstacle.colorTime <= 0) {
            BaseObstacle.colorTime = 2;
            BaseObstacle.colorTick++;

            if (BaseObstacle.colorTick == 4) {
                BaseObstacle.colorTick = 0;
            }
        }

        //obstacleTimeLabel.setText("Time Left: " + (int) BaseObstacle.colorTime);
        obstacleTimeLabel.setText("");

        /////////////////////////////////////////////////////////////////////////////////
        // DEBUG_BOUNDARY_TEXT

        //boundaryLabel.setText("Boundary Value: " + (int) dummyBoundary.getX() + ", " + (int) dummyBoundary.getY());
        boundaryLabel.setText("");

        /////////////////////////////////////////////////////////////////////////////////
        // COLLISION_BETWEEN_[PLAYER]_AND_PLAGUE_PARTICLES

//        for (PlagueParticle_Green particle : plagueParticleGreens) {
//            particle.rotate(player);
//        } spawnchunks removal; replaced with below for loop
        for (ArrayList<PlagueParticle_Green> ppgArray : plagueParticleGreens) {
            for (PlagueParticle_Green ppg : ppgArray) {
                if (ppg != null) { //Needs to be here because null objects are in the array to fill the spaces
                    ppg.rotate(player);
                }
            }
        }

/*        for (PlagueParticle_Purple particle : plagueParticlePurples) {
            particle.rotate(player);
        }*/ //spawnchunks removal; replaced with below for loop
        for (ArrayList<PlagueParticle_Purple> pppArray : plagueParticlePurples) {
            for (PlagueParticle_Purple ppp : pppArray) {
                if (ppp != null) {
                    ppp.rotate(player);
                }
            }
        }

        /////////////////////////////////////////////////////////////////////////////////
        // COLLISION_BETWEEN_[PLAYER]_AND_[ONE_UP]

/*        for (OneUp oneUp : oneUps) {
            oneUp.gainOneUp(player);
        }*/ //spawnchunks removal; replaced with below for loop
        for (ArrayList<OneUp> oneUpArray : oneUps) {
            for (OneUp oneup : oneUpArray) {
                if (oneup != null) {
                    oneup.gainOneUp(player);
                }
            }
        }

        /////////////////////////////////////////////////////////////////////////////////
        // COLLISION_BETWEEN_[PLAYER]_AND_[COLOR_CHANGER]

/*        for (ColorChanger colorChanger : colorChangers) {
            colorChanger.changeColor(player, grid);
        }*/ //spawnchunks removal; replaced with below for loop
        for (ArrayList<ColorChanger> ccArray : colorChangers) {
            for (ColorChanger cc : ccArray) {
                if (cc != null) {
                    cc.changeColor(player, grid);
                }
            }
        }


        /////////////////////////////////////////////////////////////////////////////////
        // COLLISION_BETWEEN_[PLAYER]_AND_[MCDOUBLE]

/*        for (McDouble mcDouble : mcDoubles) {
            mcDouble.gainPoint(player);
        }*/ //spawnchunks removal: replaced with below for loop
        for (ArrayList<McDouble> mdArray : mcDoubles) {
            for (McDouble md : mdArray) {
                if (md != null) {
                    md.gainPoint(player);
                }
            }
        }

        /////////////////////////////////////////////////////////////////////////////////
        // COLLISION_BETWEEN_[PLAYER]_AND_[CIRCLE_OBSTACLE]

/*        for (CircleObstacle circleObstacle : circleObstacles) {
            circleObstacle.playerDies(player);
        }*/ //spawnchunks removal: replaced with below for loop
        for (ArrayList<CircleObstacle> circleArr : circleObstacles) {
            for (CircleObstacle circleObstacle : circleArr) {
                if (circleObstacle != null && circleObstacle.playerDies(player)) {
                    handlePlayerDies();
                }
            }
        }

        /////////////////////////////////////////////////////////////////////////////////
        // COLLISION_BETWEEN_[PLAYER]_AND_[SQUARE_OBSTACLE]

/*        for (SquareObstacle squareObstacle : squareObstacles)
        {
            squareObstacle.playerDies(player);
        }*/ //spawnchunks removal: replaced with below for loop
        for (ArrayList<SquareObstacle> squareArr : squareObstacles) {
            for (SquareObstacle squareObstacle : squareArr) {
                if (squareObstacle != null && squareObstacle.playerDies(player)) {
                    handlePlayerDies();
                }
            }
        }

        /////////////////////////////////////////////////////////////////////////////////
        // CROSS OBSTACLE

        for (ArrayList<CrossObstacle> crossArr : crossObstacles) {
            for (CrossObstacle crossObstacle : crossArr) {
                if (crossObstacle != null && crossObstacle.playerDies(player)) {
                    handlePlayerDies();
                }
            }
        }

        /////////////////////////////////////////////////////////////////////////////////
        // LINE OBSTACLE

        for (ArrayList<LineObstacle> lineArr : lineObstacles) {
            for (LineObstacle lineObstacle : lineArr) {
                if (lineObstacle != null && lineObstacle.playerDies(player)) {
                    handlePlayerDies();
                }
            }
        }

        /////////////////////////////////////////////////////////////////////////////////
        // BOUNDARY_DUMMY

        if(!player.isWithinDistance(458, dummyBoundary) && !boundaryCheck)
        {
            this.boundaryCheck = true;
            player.state = Player.State.DEAD;
            handlePlayerDies();
        }

        /////////////////////////////////////////////////////////////////////////////////
        // SQUARE_OBSTACLE_COLOR_CHANGE

        if (BaseObstacle.colorTime <= 0) {
            //for (SquareObstacle squareObstacle : squareObstacles) { squareObstacle.squareColorChange(); } spawnchunk removal: replaced with below for loop
            for (ArrayList<SquareObstacle> squareArr : squareObstacles) {
                for (SquareObstacle squareObstacle : squareArr) {
                    if (squareObstacle != null) {
                        squareObstacle.squareColorChange();
                    }
                }
            }

            //for (SquareObstacle squareObstacle : squareObstacles) { squareObstacle.squareColorChange(); } spawnchunk removal: replaced with below for loop
            for (ArrayList<CrossObstacle> crossArr : crossObstacles) {
                for (CrossObstacle crossObstacle : crossArr) {
                    if (crossObstacle != null) {
                        crossObstacle.crossColorChange();
                    }
                }
            }

            //BaseObstacle.colorTime = 1;
        }
    }
}

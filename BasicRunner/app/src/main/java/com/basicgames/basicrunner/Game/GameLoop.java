package com.basicgames.basicrunner.Game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import com.basicgames.basicrunner.GUI.GameDrawer;

import java.text.DecimalFormat;

public class GameLoop extends Thread {

    private final static int MAX_FPS = 60;
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;
    private final static int MAX_FRAME_SKIPS = 5;
    // we'll be reading the stats every second
    private final static int STAT_INTERVAL = 1000; //ms
    // the average will be calculated by storing
    // the last n FPSs
    private final static int FPS_HISTORY_NR = 10;
    private String TAG = getClass().getName();
    private boolean running = false;
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private Canvas mainCanvas;
    private DecimalFormat df = new DecimalFormat("0.##");  // 2 dp
    // last time the status was stored
    private long lastStatusStore = 0;
    // the status time counter
    private long statusIntervalTimer = 0l;

    // number of rendered frames in an interval
    private int frameCountPerStatCycle = 0;
    // the last FPS values
    private double fpsStore[];
    // the number of times the stat has been read
    private long statsCount = 0;
    // the average FPS since the game started
    private double averageFps = 0.0;

    public GameLoop(SurfaceHolder surfaceHolder, GameView gameView) {
        super();

        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public void setRunning(boolean flag) {
        Log.d(TAG, "running=" + flag);
        this.running = flag;
    }

    @Override
    public void run() {
        loop();
    }

    public void loop() {
        int frameCount = 0;
        int fps = 0;
        //This value would probably be stored elsewhere.
        final double GAME_HERTZ = 30.0;
        //Calculate how many ns each frame should take for our target game hertz.
        final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
        //At the very most we will update the game this many times before a new render.
        //If you're worried about visual hitches more than perfect timing, set this to 1.
        final int MAX_UPDATES_BEFORE_RENDER = 5;
        //We will need the last update time.
        double lastUpdateTime = System.nanoTime();
        //Store the last time we rendered.
        double lastRenderTime = System.nanoTime();

        //If we are able to get as high as this FPS, don't render again.
        final double TARGET_FPS = 60;
        final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;

        //Simple way of finding FPS.
        int lastSecondTime = (int) (lastUpdateTime / 1000000000);

        while (running) {
            mainCanvas = null;
            try {
                mainCanvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    double now = System.nanoTime();
                    int updateCount = 0;

                    //Do as many game updates as we need to, potentially playing catchup.
                    while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER) {
                        gameView.updateLogic();
                        lastUpdateTime += TIME_BETWEEN_UPDATES;
                        updateCount++;
                    }

                    //If for some reason an update takes forever, we don't want to do an insane number of catchups.
                    //If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
                    if (now - lastUpdateTime > TIME_BETWEEN_UPDATES) {
                        lastUpdateTime = now - TIME_BETWEEN_UPDATES;
                    }

                    //Render. To do so, we need to calculate interpolation for a smooth render.
                    float interpolation = Math.min(1.0f, (float) ((now - lastUpdateTime) / TIME_BETWEEN_UPDATES));
                    lastRenderTime = now;

                    //Update the frames we got.
                    int thisSecond = (int) (lastUpdateTime / 1000000000);
                    if (thisSecond > lastSecondTime) {
                        System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
                        fps = frameCount;
                        frameCount = 0;
                        lastSecondTime = thisSecond;
                    }
                    gameView.drawLogic(mainCanvas, fps);


                    //Yield until it has been at least the target time between renders. This saves the CPU from hogging.
                    while (now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES) {
                        Thread.yield();

                        now = System.nanoTime();
                    }
                    frameCount++;

                }
            } catch (Exception e) {
            } finally {
                if (mainCanvas != null)
                    surfaceHolder.unlockCanvasAndPost(mainCanvas);
            }

        }
    }

    public void oldLoop() {
        Log.d(TAG, "Starting game loop");

        initTimingElements();

        long beginTime;
        int framesSkipped;
        long timeDiff;
        int sleepTime;

        while (running) {
            mainCanvas = null;
            try {
                mainCanvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0;

                    this.gameView.updateLogic();

                    this.gameView.drawLogic(mainCanvas, averageFps);

                    timeDiff = System.currentTimeMillis() - beginTime;
                    sleepTime = (int) (FRAME_PERIOD - timeDiff);

                    if (sleepTime > 0) {
                        // if sleepTime > 0 we're OK
                        try {
                            // send the thread to sleep for a short period
                            // very useful for battery saving
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                        }
                    }

                    while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                        Log.d(TAG, "Late");
                        // we need to catch up
                        // update without rendering
                        this.gameView.updateLogic();
                        // add frame period to check if in next frame
                        sleepTime += FRAME_PERIOD;
                        framesSkipped++;
                    }
                    storeStats();
                }
            } catch (Exception e) {
            } finally {
                if (mainCanvas != null)
                    surfaceHolder.unlockCanvasAndPost(mainCanvas);
            }
        }
    }

    private void initTimingElements() {
        // initialise timing elements
        fpsStore = new double[FPS_HISTORY_NR];
        for (int i = 0; i < FPS_HISTORY_NR; i++) {
            fpsStore[i] = 0.0;
        }
        Log.d(TAG + ".initTimingElements()", "Timing elements for stats initialised");
    }

    private void storeStats() {
        frameCountPerStatCycle++;

        // check the actual time
        statusIntervalTimer += (System.currentTimeMillis() - statusIntervalTimer);

        if (statusIntervalTimer >= lastStatusStore + STAT_INTERVAL) {
            // calculate the actual frames pers status check interval
            double actualFps = (double) (frameCountPerStatCycle / (STAT_INTERVAL / 1000));

            //stores the latest fps in the array
            fpsStore[(int) statsCount % FPS_HISTORY_NR] = actualFps;

            // increase the number of times statistics was calculated
            statsCount++;

            double totalFps = 0.0;
            // sum up the stored fps values
            for (int i = 0; i < FPS_HISTORY_NR; i++) {
                totalFps += fpsStore[i];
            }

            // obtain the average
            if (statsCount < FPS_HISTORY_NR) {
                // in case of the first 10 triggers
                averageFps = totalFps / statsCount;
            } else {
                averageFps = totalFps / FPS_HISTORY_NR;
            }
            // resetting the counters after a status record (1 sec)
            statusIntervalTimer = 0;
            frameCountPerStatCycle = 0;

            statusIntervalTimer = System.currentTimeMillis();
            lastStatusStore = statusIntervalTimer;
        }
    }
}

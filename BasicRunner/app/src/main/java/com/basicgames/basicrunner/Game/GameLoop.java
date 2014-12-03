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

    public GameLoop(SurfaceHolder surfaceHolder, GameView gameView)
    {
        super();

        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public void setRunning(boolean flag){
        Log.d(TAG, "running=" + flag);
        this.running = flag;
    }

    @Override
    public void run() {
        Log.d(TAG, "Starting game loop");

        initTimingElements();

        long beginTime;
        int framesSkipped;
        long timeDiff;
        int sleepTime;

        while (running) {
            mainCanvas = null;
            try{
                mainCanvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0;

                    this.gameView.updateLogic();

                    this.gameView.drawLogic(mainCanvas, df.format(averageFps));

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
            } catch (Exception e)
            {
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

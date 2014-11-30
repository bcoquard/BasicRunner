package com.basicrunner.basicgames.basicrunner;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by Benjamin on 29/11/2014.
 */
public class GameLoop extends Thread {

    private String TAG = "GAME LOOP";

    private boolean running = false;
    private SurfaceHolder surfaceHolder;
    private GameView gameView;

    private Canvas mainCanvas;

    private final static int MAX_FPS = 60;
    private final static int MAX_FRAME_SKIPS = 5;
    private final static int  FRAME_PERIOD = 1000 / MAX_FPS;

    public GameLoop(SurfaceHolder surfaceHolder, GameView gameView)
    {
        super();

        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public void setRunning(boolean flag){
        Log.d(TAG, "running=" + flag);
;        this.running = flag;
    }

    @Override
    public void run() {
        Log.d(TAG, "Starting game loop");

        long beginTime = 0;
        int framesSkipped = 0;
        long timeDiff = 0;
        int sleepTime = 0;


        while (running) {
            mainCanvas = null;

            try{
                mainCanvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0;

                    this.gameView.updateLogic();

                    this.gameView.drawLogic(mainCanvas);

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
                }
            } catch (Exception e)
            {

            } finally {
                if (mainCanvas != null)
                    surfaceHolder.unlockCanvasAndPost(mainCanvas);
            }
        }


    }




}


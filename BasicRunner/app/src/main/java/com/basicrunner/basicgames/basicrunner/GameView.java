package com.basicrunner.basicgames.basicrunner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.basicrunner.basicgames.basicrunner.GUI.GameSceneDrawer;
import com.basicrunner.basicgames.basicrunner.Models.GameScene;

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
    private GameLoop _loop;
    private String TAG = "Game View";
    private GameScene _gameScene;

    public GameView(Context context)
    {
        super(context);

        //Link the call back event to our game view
        getHolder().addCallback(this);

        //Make the our game view able to receive events
        setFocusable(true);

        //Create our game loop
        _loop = new GameLoop(getHolder(), this);
        _gameScene = new GameScene();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        if (_loop.getState() == Thread.State.NEW)
        {
            _loop.setRunning(true);
            _loop.start();
        }
        else if (_loop.getState() == Thread.State.TERMINATED)
        {
            _loop = new GameLoop(getHolder(), this);
            _loop.setRunning(true);
            _loop.start(); // Start a new thread
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        while (retry)
        {
            try
            {
                _loop.join();
                retry = false;
            }
            catch (InterruptedException e)
            {

            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
        }
        _gameScene.handleTouchEvent(event.getX(), event.getY());
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
    }

    public void stopGame()
    {
        _loop.setRunning(false);
    }

    public void updateLogic()
    {
        // TODO: find correct time passed (ms).
        final int timePassed = 0;
        _gameScene.update(timePassed);
    }

    public void drawLogic(Canvas canvas)
    {
        //Reset The background first.
        canvas.drawColor(Color.BLACK);
        GameSceneDrawer.draw(canvas, _gameScene);
    }
}

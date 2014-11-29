package com.example.benjamin.basicrunner;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Benjamin on 29/11/2014.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameLoop _loop;
    private String TAG = "Game View";
    private Player _player;

    public GameView(Context context) {
        super(context);

        //Link the call back event to our game view
        getHolder().addCallback(this);

        //Make the our game view able to receive events
        setFocusable(true);

        //Create our game loop
        _loop = new GameLoop(getHolder(), this);
        _player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.elaine), new Point(80, 80), 15, 5);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if(_loop.getState() == Thread.State.NEW){
            _loop.setRunning(true);
            _loop.start();
        }
        else
        if (_loop.getState() == Thread.State.TERMINATED) {
            _loop = new GameLoop(getHolder(), this);
            _loop.setRunning(true);
            _loop.start(); // Start a new thread
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry){
            try {
                _loop.join();
               retry = false;
            } catch (InterruptedException e){

            }
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
        }
        _player.handleTouchEvent(event.getX(), event.getY());

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void stopGame(){
        _loop.setRunning(false);
    }


    public void updateLogic(){
        _player.update(System.currentTimeMillis());
    }

    public void drawLogic(Canvas canvas){
        //Reset The background first
        canvas.drawColor(Color.BLACK);

        _player.draw(canvas);
    }
}

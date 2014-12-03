package com.basicrunner.basicgames.basicrunner.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.basicrunner.basicgames.basicrunner.GUI.GameDrawer;
import com.basicrunner.basicgames.basicrunner.Game.Models.GameScene;
import com.basicrunner.basicgames.basicrunner.Game.Models.Point;
import com.basicrunner.basicgames.basicrunner.MainActivity;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private final String TAG = getClass().getSimpleName();

    private MainActivity _activity;
    private GameLoop _loop;
    private GameScene _gameScene;
    private final GameDrawer _gameDrawer;

    public GameView(Context context) {
        super(context);
        // Link the call back event to our game view.
        getHolder().addCallback(this);
        // Make the our game view able to receive events.
        setFocusable(true);

        // Save our activity.
        this._activity = (MainActivity)context;

        // Create our game loop and game objects.
        _loop = new GameLoop(getHolder(), this);
        _gameScene = new GameScene();
        _gameScene.init();
        _gameDrawer = new GameDrawer(_gameScene);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        _gameDrawer.init(new Point(width, height));
     }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (_loop.getState() == Thread.State.NEW) {
            _loop.setRunning(true);
            _loop.start();
        } else if (_loop.getState() == Thread.State.TERMINATED) {
            _loop = new GameLoop(getHolder(), this);
            _loop.setRunning(true);
            _loop.start(); // Start a new thread
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                _loop.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO: catch HUD touch here.
        final Point indexPos = _gameDrawer.getIndexPos(new Point(event.getX(), event.getY()));
        _gameScene.touchEvent(indexPos, event.getAction());
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void stopGame() {
        _loop.setRunning(false);
    }

    public void updateLogic() {
        _gameScene.update();
        if (!_gameScene.getPlayer().isAlive())
        {
            stopGame();
            _activity.endGame();
        }
    }

    public void drawLogic(Canvas canvas) {
        _gameDrawer.draw(canvas);
    }
}

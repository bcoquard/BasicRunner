package com.basicgames.basicrunner.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.basicgames.basicrunner.GUI.GameDrawer;
import com.basicgames.basicrunner.Game.Interface.IPoint;
import com.basicgames.basicrunner.Game.Models.GameScene;
import com.basicgames.basicrunner.Game.Models.Point;
import com.basicgames.basicrunner.MainActivity;

public class GameView extends View
{
    private final String TAG = getClass().getSimpleName();

    private final GameDrawer _gameDrawer;
    private final MainActivity _activity;
    private final GameScene _gameScene;
    private boolean _initialized;
    private StatisticManager _statisticsManager;

    public GameView(Context context)
    {
        super(context);

        // Save our activity.
        this._activity = (MainActivity) context;

        // Create game objects.
        _gameScene = new GameScene();
        _gameDrawer = new GameDrawer(_gameScene);
        _statisticsManager = new StatisticManager();
        _initialized = false;
    }

    /**
     * Will reset the game without recreating all objects.
     */
    public void init()
    {
        _gameScene.init();
        _statisticsManager.init();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight)
    {
        _gameDrawer.init(new Point(width, height));
        _initialized = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        // TODO: catch HUD touch here.
        if (event.getAction() == MotionEvent.BUTTON_BACK)
            return super.onTouchEvent(event);

        final Point indexPos = _gameDrawer.getIndexPos(new Point(event.getX(), event.getY()));
        _gameScene.touchEvent(indexPos, event.getAction());
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        // Wait for onSizeChanged callback.
        if (!_initialized) return;

        // Update statistics & get time passed since last update.
        int timePassed = _statisticsManager.update();

        // Update game objects.
        _gameScene.update(timePassed);
        if (!_gameScene.getPlayer().isAlive())
            _activity.endGame();

        // Draw game objects.
        _gameDrawer.draw(canvas);
        _gameDrawer.drawFPS(canvas, _statisticsManager.getFps());

        // Make redraw.
        invalidate();
    }
}

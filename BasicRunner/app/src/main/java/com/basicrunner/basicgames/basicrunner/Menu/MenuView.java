package com.basicrunner.basicgames.basicrunner.Menu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.basicrunner.basicgames.basicrunner.GUI.MenuDrawer;
import com.basicrunner.basicgames.basicrunner.Game.Models.Point;
import com.basicrunner.basicgames.basicrunner.MainActivity;

public class MenuView extends View
{
    private final String TAG = getClass().getSimpleName();

    private MainActivity _activity;
    private final MenuDrawer _menuDrawer;

    public MenuView(Context context)
    {
        super(context);
        this._activity = (MainActivity) context;

        // Set background color.
        this.setBackgroundColor(Color.rgb(20, 10, 40));

        // Create our game loop and game objects.
        this._menuDrawer = new MenuDrawer();
    }

    @Override
    public void onSizeChanged(int width, int height, int oldWidth, int oldHeight)
    {
        _menuDrawer.init(new Point(width, height));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == event.ACTION_DOWN)
            _activity.startGame();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        _menuDrawer.draw(canvas);
        invalidate();
    }
}

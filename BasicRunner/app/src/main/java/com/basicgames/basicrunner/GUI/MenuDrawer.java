package com.basicgames.basicrunner.GUI;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.basicgames.basicrunner.Game.Interface.IPoint;

public class MenuDrawer
{
    private final String TAG = getClass().getSimpleName();

    private IPoint _screenSize;
    private static Paint _textPaint;

    public MenuDrawer()
    {
        _textPaint = new Paint();
        _textPaint.setColor(Color.WHITE);
        _textPaint.setTextAlign(Paint.Align.CENTER);
        _textPaint.setStrokeWidth(1);
    }

    public void init(IPoint screenSize)
    {
        _screenSize = screenSize;
        _textPaint.setTextSize(screenSize.X() / 15);
    }

    public boolean initialized()
    {
        return (_screenSize != null);
    }

    public void draw(Canvas canvas)
    {
        // If drawer isn't initialized, the draw effects are unspecified.
        if (!initialized()) throw new InternalError();
        // Start by drawing background.
        canvas.drawColor(Color.DKGRAY);
        // Draw message on screen.
        canvas.drawText("Touch the screen to start!", _screenSize.X() / 2, _screenSize.Y() / 2, _textPaint);
    }

}

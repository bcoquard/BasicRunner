package com.basicrunner.basicgames.basicrunner;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Benjamin on 29/11/2014.
 */
public class Player {
    private String TAG = "PLAYER";

    private Point _position;
    private Rect _box;
    private Point _touchedPoint;
    private Bitmap _sprite;
    private int _width;
    private int _height;

    private int _frameNr;
    private int _currentFrame;
    private long _frameTicker;
    private int _framePeriod;

    private Rect _sourceRect;

    private boolean _moving = false;

    public Player(Bitmap sprite, Point start, int fps, int frameCount)
    {
        _position = start;

        _width = sprite.getWidth() / frameCount;
        _height = sprite.getHeight();

        _sprite = sprite;

        _box = new Rect(start.x, start.y, start.x + _width, start.y + _height);
        _sourceRect = new Rect(0, 0, _width, _height);

        _touchedPoint = null;

        _currentFrame = 2;
        _frameNr = frameCount;
        _framePeriod = 1000 / fps;
        _frameTicker = 0l;

    }

    public void handleTouchEvent(float x, float y){
        _touchedPoint = new Point((int)x, (int)y);
        _moving = true;
    }

    public void update(long gameTime) {
        if (!_moving) {
            _currentFrame = 2;
            this._sourceRect.left = _currentFrame * _width;
            this._sourceRect.right = this._sourceRect.left + _width;
            return;
        }

        if (gameTime > _frameTicker + _framePeriod) {
            _frameTicker = gameTime;
            _currentFrame++;
            if (_currentFrame >= _frameNr) {
                _currentFrame = 0;
            }
        }
        this._sourceRect.left = _currentFrame * _width;
        this._sourceRect.right = this._sourceRect.left + _width;



        if (_touchedPoint == null) {
            return;
        }

        if (_touchedPoint == _position) {
            _touchedPoint = null;
            _moving = false;
            return;
        }

        goToPoint(_touchedPoint);
    }

    public void goToPoint(Point newPos) {
        _touchedPoint.y = _position.y;
        int x = (newPos.x - _position.x) / (newPos.x - _position.x);
        if (newPos.x < _position.x)
            x *= -1;
        int y = _position.y;
        Log.d(TAG, "x = " + x);
        _position.x += x;
        _box.set(_position.x, _position.y, _position.x + _width, _position.y + _height);
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(_sprite, _sourceRect, _box, null);
    }

}

package com.basicrunner.basicgames.basicrunner.Models;

import android.graphics.RectF;

import com.basicrunner.basicgames.basicrunner.Models.Interface.IMovableObject;
import com.basicrunner.basicgames.basicrunner.Models.Interface.IPoint;

public class Player implements IMovableObject
{
    private String TAG = "PLAYER";

    private Point _position;
    private Point _size;
    private RectF _box;
    private Point _touchedPoint;
    private boolean _moving = false;

    public Player(Point position)
    {
        _position = position;
        _size = new Point(1, 1);
        _box = new RectF(_position.x, _position.y, _position.x + _size.x, _position.y + _size.y);
        _touchedPoint = null;
    }

    @Override
    public IPoint getPosition()
    {
        return _position;
    }

    @Override
    public IPoint getSize()
    {
        return _size;
    }

    public void handleTouchEvent(float x, float y)
    {
        _touchedPoint = new Point((int) x, (int) y);
        _moving = true;
    }

    public void update(int timePased)
    {
        /*
        if (!_moving)
        {
            _currentFrame = 2;
            this._sourceRect.left = _currentFrame * _width;
            this._sourceRect.right = this._sourceRect.left + _width;
            return;
        }

        if (gameTime > _frameTicker + _framePeriod)
        {
            _frameTicker = gameTime;
            _currentFrame++;
            if (_currentFrame >= _frameNr)
            {
                _currentFrame = 0;
            }
        }
        this._sourceRect.left = _currentFrame * _width;
        this._sourceRect.right = this._sourceRect.left + _width;


        if (_touchedPoint == null)
        {
            return;
        }

        if (_touchedPoint == _position)
        {
            _touchedPoint = null;
            _moving = false;
            return;
        }
        */
        goToPoint(_touchedPoint);
    }

    public void goToPoint(Point newPos)
    {
        /*
        _touchedPoint.y = _position.y;
        int x = (newPos.x - _position.x) / (newPos.x - _position.x);
        if (newPos.x < _position.x)
            x *= -1;
        int y = _position.y;
        Log.d(TAG, "x = " + x);
        _position.x += x;
        _box.set(_position.x, _position.y, _position.x + _width, _position.y + _height);
        */
    }

}

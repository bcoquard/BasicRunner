package com.basicrunner.basicgames.basicrunner.Models;

import android.graphics.RectF;
import android.view.MotionEvent;

import com.basicrunner.basicgames.basicrunner.Models.Interface.IMovableObject;
import com.basicrunner.basicgames.basicrunner.Models.Interface.IPoint;

public class Player implements IMovableObject
{
    private String TAG = "PLAYER";

    private Point _position;
    private Point _size;
    private RectF _box;
    private float _velocity;
    private boolean _isAlive;

    public Player()
    {
        _velocity = 0.05f;
        _position = new Point();
        _size = new Point(1, 1);
        _box = new RectF(_position.x, _position.y, _position.x + _size.x, _position.y + _size.y);
        _isAlive = false;
    }

    public void init(Point position)
    {
        this._position.setLocation(position);
        _isAlive = true;
    }

    @Override
    public RectF getBox()
    {
        return _box;
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

    @Override
    public boolean isAlive()
    {
        return _isAlive;
    }

    public void kill()
    {
        _isAlive = false;
    }

    public void moveTo(IPoint directionPoint)
    {
        if (directionPoint.X() > _position.x)
            _position.x += _velocity;
        else if (directionPoint.X() < _position.x)
            _position.x -= _velocity;
        _box.left = _position.x;
        _box.top = _position.y;
    }

    public void update(IPoint magnetPosition)
    {
        // TODO: maybe determine player position from middle of the object.
        if (magnetPosition != null)
            _position.setLocation(magnetPosition);
            //moveTo(magnetPosition);
    }
}

package com.basicrunner.basicgames.basicrunner.Models;

import android.graphics.RectF;
import com.basicrunner.basicgames.basicrunner.Models.Interface.IMovableObject;
import com.basicrunner.basicgames.basicrunner.Models.Interface.IPoint;

public class Obstacle implements IMovableObject
{
    private Point _position;
    private RectF _box;
    private Point _size;
    private boolean _isAlive;
    private float _velocity;

    public Obstacle(Point position)
    {
        _isAlive = true;
        _position = position;
        _size = new Point(32, 32);
        _box = new RectF(_position.x, _position.y, _position.x + _size.x, _position.y + _size.y);
        _velocity = 0.0001f;
    }

    @Override
    public IPoint getPosition()
    {
        return _position;
    }

    @Override
    public RectF getBox() {
        return _box;
    }

    @Override
    public IPoint getSize()
    {
        return _size;
    }

    @Override
    public boolean isAlive() {
        return _isAlive;
    }

    public void update()
    {
        _position.y -= _velocity;
    }
}

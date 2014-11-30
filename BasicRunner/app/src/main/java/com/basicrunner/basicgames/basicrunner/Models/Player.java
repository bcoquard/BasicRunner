package com.basicrunner.basicgames.basicrunner.Models;

import android.graphics.RectF;
import android.view.MotionEvent;
import com.basicrunner.basicgames.basicrunner.Models.Interface.IMovableObject;
import com.basicrunner.basicgames.basicrunner.Models.Interface.IPoint;


public class Player implements IMovableObject {
    private String TAG = "PLAYER";

    private Point _position;
    private Point _size;
    private RectF _box;
    private float _speed;
    private boolean _isAlive;

    private Point _touchedPoint;


    public Player(Point position) {
        _speed = 5f;
        _isAlive = true;
        _position = position;
        _size = new Point(32, 32);
        _box = new RectF(_position.x, _position.y, _position.x + _size.x, _position.y + _size.y);

        _touchedPoint = null;
    }

    @Override
    public RectF getBox() {
        return _box;
    }

    @Override
    public IPoint getPosition() {
        return _position;
    }

    @Override
    public IPoint getSize() {
        return _size;
    }

    @Override
    public boolean isAlive() {
        return _isAlive;
    }

    public void kill()
    {
        _isAlive = false;
    }

    public void handleTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
            _touchedPoint = new Point(event.getX(), event.getY());

        if (event.getAction() == MotionEvent.ACTION_UP)
            _touchedPoint = null;
    }

    public void moveTo(Point directionPoint)
    {
        if (directionPoint.x > _position.x)
            _position.x += _speed;
        else if (directionPoint.x < _position.x)
            _position.x -= _speed;

        _box.left = _position.x;
        _box.top = _position.y;
    }

    public void update(int timePased)
    {
        if (_touchedPoint != null)
            moveTo(_touchedPoint);
    }

}
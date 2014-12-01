package com.basicrunner.basicgames.basicrunner.Models;

import com.basicrunner.basicgames.basicrunner.Models.Interface.IMovableObject;
import com.basicrunner.basicgames.basicrunner.Models.Interface.IPoint;

public class Player implements IMovableObject
{
    private final String TAG = getClass().getSimpleName();

    private Point _position;
    private Point _size;
    private float _velocity;
    private boolean _isAlive;

    public Player()
    {
        _velocity = 0.1f;
        _position = new Point();
        _size = new Point(1, 1);
        _isAlive = false;
    }

    public void init(Point position)
    {
        this._position.setLocation(position);
        _isAlive = true;
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

    /**
     * Defines the player behavior towards a collision.
     * Current : the player dies directly.
     *
     * @param object Object the player entered in collision with
     */
    public void onCollision(IMovableObject object)
    {
        // The implementation could change (bounce, life, ...)
        _isAlive = false;
    }

    public void moveTo(IPoint directionPoint)
    {
        //Log.d(TAG, "object: " + _position + " -- doigt: " + directionPoint);
        float tmpVelocity = _velocity;

        if (directionPoint.X() > _position.x + _size.x / 2)
        {
            if (directionPoint.X() - (_position.X() + _size.X() / 2) < _velocity)
                tmpVelocity = directionPoint.X() - (_position.X() + _size.X() / 2);
            if (_position.X() + _size.X() / 2 + tmpVelocity > GameScene._size.X() - 1 + _size.X() / 2)
                return;
            _position.x += tmpVelocity;
        }
        else if (directionPoint.X() < _position.x + _size.x / 2)
        {
            if ((_position.X() + _size.X() / 2) - directionPoint.X() < _velocity)
                tmpVelocity = (_position.X() + _size.X() / 2) - directionPoint.X();
            if (_position.X() - tmpVelocity < 0)
                return;
            _position.x -= tmpVelocity;
        }
        /*
        if (directionPoint.X() > _position.x)
            _position.x += _velocity;
        else if (directionPoint.X() < _position.x)
            _position.x -= _velocity;
        */
    }

    public void update(IPoint magnetPosition)
    {
        // TODO: maybe determine player position from middle of the object.
        if (magnetPosition != null)
            //_position.setLocation(magnetPosition);
            moveTo(magnetPosition);
    }
}

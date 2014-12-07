package com.basicgames.basicrunner.Game.Models;

import com.basicgames.basicrunner.Game.Interface.IMovableObject;
import com.basicgames.basicrunner.Game.Interface.IPoint;

public class Player implements IMovableObject
{
    private final String TAG = getClass().getSimpleName();

    private final Point _position;
    private final Point _size;
    private final float _velocity;
    private boolean _isAlive;

    public Player()
    {
        _velocity = 0.005f;
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

    /**
     * Moves the player towards the given point.
     * @param timePassed ms since the last update
     * @param directionPoint
     */
    public void moveTo(int timePassed, IPoint directionPoint)
    {
        // TODO: Don't move the player if the distance is small.
        float tmpVelocity = _velocity;
        if (directionPoint.X() > _position.x + _size.x / 2)
        {
            if (directionPoint.X() - (_position.X() + _size.X() / 2) < _velocity)
                tmpVelocity = directionPoint.X() - (_position.X() + _size.X() / 2);
            if (_position.X() + _size.X() / 2 + tmpVelocity > GameScene._size.X() - 1 + _size.X() / 2)
                return;
            _position.x += tmpVelocity * timePassed;
        }
        else if (directionPoint.X() < _position.x + _size.x / 2)
        {
            if ((_position.X() + _size.X() / 2) - directionPoint.X() < _velocity)
                tmpVelocity = (_position.X() + _size.X() / 2) - directionPoint.X();
            if (_position.X() - tmpVelocity < 0)
                return;
            _position.x -= tmpVelocity * timePassed;
        }
    }

    public void update(int timePassed, IPoint magnetPosition)
    {
        if (magnetPosition != null)
            moveTo(timePassed, magnetPosition);
    }
}

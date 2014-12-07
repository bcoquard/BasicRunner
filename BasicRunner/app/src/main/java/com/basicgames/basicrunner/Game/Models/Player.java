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
        _velocity = 0.008f;
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
     * Updates position according to the given position.
     *
     * @param timePassed     ms since the last update
     * @param directionPoint gives the player direction, may be null
     */
    public void update(int timePassed, IPoint directionPoint, IPoint terrainSize)
    {
        if (directionPoint == null)
            return;
        // Displace the direction so the player is centered.
        final float correctedX = directionPoint.X() - _size.X() / 2;
        final float correctedY = directionPoint.Y() - _size.Y() / 2;
        moveTo(timePassed, new Point(correctedX, correctedY), terrainSize);
    }

    /**
     * Moves the player towards the given point.
     *
     * @param timePassed     ms since the last update
     * @param directionPoint gives the player direction, may be null
     */
    private void moveTo(int timePassed, IPoint directionPoint, IPoint terrainSize)
    {
        // Don't move the player if the distance is small.
        final float threshold = _velocity * timePassed;
        if (Math.abs(directionPoint.X() - _position.X()) < threshold)
        {
            _position.x = directionPoint.X();
        }
        else
        {
            _position.x += normalize(directionPoint.X() - _position.X()) * _velocity * timePassed;
        }
        if (_position.X() < 0)
            _position.x = 0;
        if (_position.X() > terrainSize.X() - 1)
            _position.x = terrainSize.X() - 1;
    }

    private int normalize(float x)
    {
        return (x > 0) ? 1 : -1;
    }
}
